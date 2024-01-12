module com.whisper.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.jsoup;

    opens com.whisper.client.signin.controller to javafx.fxml;
    exports com.whisper.client;
}