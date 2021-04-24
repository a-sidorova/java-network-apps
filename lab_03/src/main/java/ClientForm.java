
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ClientForm extends Application {
    private Label time;
    private ArrayList<Label> alarms = new ArrayList<Label>();
    private GridPane grid;
    private Button startBtn, pauseBtn, stopBtn;
    private Text scenetitle;
    int alarmCount = 0;
    boolean isTime = true;

    InetAddress host;
    int port = 3124;

    Socket cs;

    OutputStream os;
    InputStream is;
    DataInputStream dis;
    DataOutputStream dos;

    Thread t;

    @Override
    public void start(Stage stage) throws IOException {
        initComponents(stage);
        connect();
    }

    private void connect() throws IOException {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        cs = new Socket(host, port);
        System.out.println("Client started!");
        os = cs.getOutputStream();
        is = cs.getInputStream();

        t = new Thread() {
            @Override
            public void run() {
                dos = new DataOutputStream(os);
                dis = new DataInputStream(is);

                while (true) {
                    try {
                        String s = dis.readUTF();
                        System.out.println("Client got: " + s);

                        addTime(s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }

    private void addTime(String s_) {
        String[] ss = s_.split("\n");
        final int[] alarmCountLocal = {0};

        for (String s : ss) {
            if (s.contains(new String("Clock"))) {
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        time.setText(s.replaceAll("Clock", ""));
                        scenetitle.setText("Set Alarm");
                        isTime = false;
                    }
                });
            } else if (s.contains(new String("Alarm"))) {
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        alarmCountLocal[0] += 1;
                        Label timeAlarm = new Label(s.replaceAll("Alarm", ""));

                        if (alarmCountLocal[0] <= alarmCount) {
                            alarms.get(alarmCountLocal[0] - 1).setText(s.replaceAll("Alarm", ""));
                        } else {
                            Label alarmLbl = new Label("Alarm:");
                            alarmCount += 1;
                            grid.add(alarmLbl, 0, 5 + alarmCount);
                            alarms.add(timeAlarm);
                            grid.add(timeAlarm, 1, 5 + alarmCount);
                        }
                    }
                });
            } else if (s.contains(new String("Attention"))) {
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Attention");
                        alert.setHeaderText("");
                        alert.setContentText("!!!Alarm!!!");
                        alert.showAndWait();
                    }
                });
            } else if (s.contains(new String("Start"))) {
                pauseBtn.setDisable(false);
                stopBtn.setDisable(false);
                startBtn.setDisable(true);
            } else if (s.contains(new String("Stop"))) {
                pauseBtn.setDisable(true);
                stopBtn.setDisable(true);
                startBtn.setDisable(true);

                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        for (int i = 5 + alarmCount; i > 5; i--)
                            deleteRow(i);

                        scenetitle.setText("SetClock");
                        time.setText("");
                    }
                });
            } else if (s.contains(new String("Pause"))) {
                pauseBtn.setDisable(false);
                stopBtn.setDisable(false);
                startBtn.setDisable(true);
            }
        }
    }

    private void initComponents(Stage stage) {
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        scenetitle = new Text("Set Clock");
        grid.add(scenetitle, 0, 0, 2, 1);

        Label hours = new Label("Hours:");
        grid.add(hours, 0, 1);
        TextField hoursTextField = new TextField();
        grid.add(hoursTextField, 1, 1);

        Label min = new Label("Minutes:");
        grid.add(min, 0, 2);
        TextField minTextField = new TextField();
        grid.add(minTextField, 1, 2);

        Label sec = new Label("Seconds:");
        grid.add(sec, 0, 3);
        TextField secTextField = new TextField();
        grid.add(secTextField, 1, 3);

        Label clockLbl = new Label("Time:");
        grid.add(clockLbl, 0, 5);
        time = new Label("");
        grid.add(time, 1, 5);

        Button btn = new Button("Set time");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 0, 4);

        setButtons();

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                int h = Integer.parseInt(hoursTextField.getText());
                int m = Integer.parseInt(minTextField.getText());
                int s = 0;

                ClockType type;
                if (secTextField.getText().trim().length() == 0) {
                    type = ClockType.Common;
                } else {
                    type = ClockType.Advanced;
                    s = Integer.parseInt(secTextField.getText());
                }

                if (isTime) {
                    String time = "Clock" + h + ":" + m;
                    if (type == ClockType.Advanced)
                        time += ":" + s;

                    try {
                        dos.writeUTF(time);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                    pauseBtn.setDisable(true);
                    stopBtn.setDisable(true);
                    startBtn.setDisable(false);
                } else if (alarmCount < 3) {
                    String time = "Alarm" + h + ":" + m;
                    if (type == ClockType.Advanced)
                        time += ":" + s;

                    try {
                        dos.writeUTF(time);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.setWidth(500);
        stage.setHeight(400);
        stage.show();
    }

    private void setButtons() {
        HBox hbBtns = new HBox(10);
        startBtn = new Button("Start");
        pauseBtn = new Button("Pause");
        stopBtn = new Button("Stop");

        hbBtns.getChildren().add(startBtn);
        hbBtns.getChildren().add(pauseBtn);
        hbBtns.getChildren().add(stopBtn);

        pauseBtn.setDisable(true);
        stopBtn.setDisable(true);
        startBtn.setDisable(false);

        startBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    dos.writeUTF(new String("Start\n"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        pauseBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    dos.writeUTF(new String("Pause\n"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        stopBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    dos.writeUTF(new String("Stop\n"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        grid.add(hbBtns, 1, 4);
    }

    void deleteRow(final int row) {
        Set<Node> deleteNodes = new HashSet<>();
        for (Node child : grid.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(child);
            int r = rowIndex == null ? 0 : rowIndex;

            if (r > row) {
                GridPane.setRowIndex(child, r-1);
            } else if (r == row) {
                deleteNodes.add(child);
            }
        }

        grid.getChildren().removeAll(deleteNodes);
    }

    public static void main(String[] args) throws IOException {
        ClientForm form = new ClientForm();
        form.launch();
    }
}