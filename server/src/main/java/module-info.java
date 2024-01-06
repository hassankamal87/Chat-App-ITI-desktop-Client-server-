module com.whisper.server {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.whisper.server to javafx.fxml;
    exports com.whisper.server;
}