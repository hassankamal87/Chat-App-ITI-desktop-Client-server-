module com.wisper.server {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.wisper.server to javafx.fxml;
    exports com.wisper.server;
}