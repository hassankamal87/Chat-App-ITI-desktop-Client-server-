package com.whisper.server;

import com.whisper.server.datalayer.db.MyDatabase;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {
    private MyDatabase myDatabase = MyDatabase.getInstance();
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        myDatabase.startConnection();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.
                class.getResource("homeserver/view/homeServerView.fxml"));



        stage.setOnCloseRequest(event -> {
            try {
                myDatabase.closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Connection closed on application shutdown.");
            Platform.exit();
            System.exit(0);
        });
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}