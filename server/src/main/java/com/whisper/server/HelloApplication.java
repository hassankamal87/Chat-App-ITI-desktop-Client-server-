package com.whisper.server;

import com.whisper.server.presentation.services.SceneManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        stage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit Confirmation");
            alert.setHeaderText("Are you sure you want to exit?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // User chose OK
                Platform.exit();
                System.exit(0);
            } else {
                // User chose Cancel or closed the dialog
                event.consume();
            }
        });
        SceneManager.getInstance().initStage(stage);
        SceneManager.getInstance().loadView("homeServerView");
        stage.setTitle("Whisper Server");
        Image icon = new Image(getClass().getResourceAsStream("images/server.png")); // Adjust the path accordingly
        stage.getIcons().add(icon);
        stage.setResizable(false);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}