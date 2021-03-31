package lab_02;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * JavaFX App
 */
public class App extends Application {
    private IClock clock;
    private Label time;
    private GridPane grid;
    private Button startBtn, pauseBtn, stopBtn;
    private Text scenetitle;
    private boolean isTime, threadFlag, isPause;
    private int alarmCount;
    Thread t;

    @Override
    public void start(Stage stage) throws IOException {
        initComponents(stage);
    }

    private void initComponents(Stage stage) {
        isTime = false;
        isPause = true;
        alarmCount = 0;

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

        Button btn = new Button("Set time");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 0, 4);

        setButtons();

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Button");
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

                if (!isTime) {
                    isTime = true;

                    clock = FClock.build(type);
                    clock.setHours(h);
                    clock.setMinutes(m);
                    if (type == ClockType.Advanced)
                        clock.setSeconds(s);

                    scenetitle.setText("Set Alarm");

                    time.setText(clock.printTime());

                    pauseBtn.setDisable(true);
                    stopBtn.setDisable(true);
                    startBtn.setDisable(false);
                } else if (alarmCount < 3) {
                    IAlarm alarm = FAlarm.build(type);
                    alarm.setHours(h);
                    alarm.setMinutes(m);
                    if (type == ClockType.Advanced)
                        alarm.setSeconds(s);

                    clock.addAlarm(alarm);

                    Label alarmLbl = new Label("Alarm:");
                    alarmCount += 1;
                    grid.add(alarmLbl, 0, 5 + alarmCount);
                    Label timeAlarm = new Label(alarm.printTime());
                    grid.add(timeAlarm, 1, 5 + alarmCount);
                }

                Label clock = new Label("Time:");
                grid.add(clock, 0, 5);
                time = new Label("");
                grid.add(time, 1, 5);
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
                start();

                pauseBtn.setDisable(false);
                stopBtn.setDisable(false);
                startBtn.setDisable(true);
            }
        });

        pauseBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (isPause) {
                    pauseBtn.setText("Resume");
                    threadFlag = false;
                    isPause = false;
                } else {
                    pauseBtn.setText("Pause");
                    threadFlag = true;
                    isPause = true;

                    stop();
                    start();
                }

                pauseBtn.setDisable(false);
                stopBtn.setDisable(false);
                startBtn.setDisable(true);
            }
        });

        stopBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                stop();

                pauseBtn.setDisable(true);
                stopBtn.setDisable(true);
                startBtn.setDisable(true);

                for (int i = 5 + alarmCount; i >= 5; i--)
                    deleteRow(i);

                alarmCount = 0;
                isTime = false;
                scenetitle.setText("SetClock");
            }
        });

        grid.add(hbBtns, 1, 4);
    }

    private void start() {
        if (t == null) {
            int step = clock.getStep();
            t = new Thread(new Runnable() {
                int step = clock.getStep();
                @Override
                public void run() {
                    threadFlag = true;
                    System.out.println("Clock is starting!");

                    while (threadFlag) {
                        try {
                            Platform.runLater(new Runnable() {
                                @Override public void run() {
                                    time.setText(clock.printTime());
                                    if (clock.checkAlarms()) {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Attention");
                                        alert.setHeaderText(clock.printTime());
                                        alert.setContentText("!!!Alarm!!!");
                                        alert.showAndWait();
                                    }
                                }
                            });
                            Thread.sleep(step);
                            clock.increaseTime(1);
                        } catch (InterruptedException e) {
                            threadFlag = false;
                        }
                    }
                }
            });
        }

        t.start();
    }

    public void stop() {
        if (t != null) {
            t.interrupt();
            t = null;
            System.out.println("Clock is ending!");
        }
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
}