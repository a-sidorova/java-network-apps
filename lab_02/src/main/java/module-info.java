module lab_02 {
    requires javafx.controls;
    requires javafx.fxml;

    opens lab_02 to javafx.fxml;
    exports lab_02;
}