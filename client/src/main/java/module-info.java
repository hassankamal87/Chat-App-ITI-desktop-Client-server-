module com.whisper.client {
    requires javafx.controls;
    requires javafx.fxml;



    exports com.whisper.client ;

    exports com.whisper.client.contact.controller;
    opens com.whisper.client.contact.controller to javafx.fxml;


}