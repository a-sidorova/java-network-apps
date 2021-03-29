package lab_02;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private IClock clock;
    private Label time;
    private boolean isTime;

    @Override
    public void start(Stage stage) throws IOException {
        initComponents(stage);
    }

    private void initComponents(Stage stage) {
        isTime = false;

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Set Clock");
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
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

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
                } else {
                    IAlarm alarm = FAlarm.build(type);
                    alarm.setHours(h);
                    alarm.setMinutes(m);
                    if (type == ClockType.Advanced)
                        alarm.setSeconds(s);

                    clock.addAlarm(alarm);
                }
            }
        });

        Label clock = new Label("Time:");
        grid.add(clock, 0, 5);
        time = new Label("");
        grid.add(time, 1, 5);

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.setWidth(500);
        stage.setHeight(300);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}