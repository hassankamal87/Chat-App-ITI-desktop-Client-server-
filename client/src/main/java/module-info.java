module com.wisper.client {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.wisper.client to javafx.fxml;
    exports com.wisper.client;
}