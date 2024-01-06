module com.whisper.client {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.whisper.client to javafx.fxml;
    exports com.whisper.client;
}