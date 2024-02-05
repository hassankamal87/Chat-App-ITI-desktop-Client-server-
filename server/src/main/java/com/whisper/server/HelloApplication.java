package com.whisper.server;

import com.whisper.server.presentation.services.SceneManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        stage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit");
            alert.setHeaderText("Are you sure you want to exit?");
            alert.showAndWait();

          //  ServerService.getInstance().stopServer();
            Platform.exit();
            System.exit(0);
        });
        SceneManager.getInstance().initStage(stage);
        SceneManager.getInstance().loadView("homeServerView");
        stage.setTitle("Hello!");
        stage.setResizable(false);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}