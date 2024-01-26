package com.whisper.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup/view/signUpView.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup/view/signUpView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Whesper!");
        stage.setScene(scene);
        //set min and max size of the window
        stage.setMinWidth(600);
        stage.setMinHeight(400);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}