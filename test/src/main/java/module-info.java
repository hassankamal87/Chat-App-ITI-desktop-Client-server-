module com.whisper.test {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.whisper.test to javafx.fxml;
    exports com.whisper.test;
}