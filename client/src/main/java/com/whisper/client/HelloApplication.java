//package com.whisper.client;
//
//import com.whisper.client.business.services.ClientServiceImpl;
//import com.whisper.client.presentation.services.SceneManager;
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import org.example.clientinterfaces.ClientServiceInt;
//import org.example.serverinterfaces.SendContactsInvitationServiceInt;
//
//import java.io.IOException;
//import java.rmi.NotBoundException;
//import java.rmi.RemoteException;
//import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;
//
//public class HelloApplication extends Application {
//    @Override
//    public void start(Stage stage) throws IOException, NotBoundException {
//        SceneManager.getInstance().initStage(stage);
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/userSearchView.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        stage.setTitle("Whisper!");
//        stage.setScene(scene);
//        stage.setMinWidth(800);
//        stage.setMinHeight(600);
//        stage.setResizable(false);
//        Registry reg = LocateRegistry.getRegistry(8000);
//        SendContactsInvitationServiceInt serverRef = (SendContactsInvitationServiceInt) reg.lookup("SendContactsInvitationService");
//        ClientServiceInt clientService =ClientServiceImpl.getInstance();
//        serverRef.ServerRegister(clientService);
//        stage.setOnCloseRequest(event -> {
//
//            try {
//                serverRef.ServerUnRegister(clientService);
//            } catch (RemoteException e) {
//                throw new RuntimeException(e);
//            }
//            Platform.exit();
//            System.exit(0);
//        });
//
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        launch();
//    }
//}

package com.whisper.client;

import com.whisper.client.presentation.services.SceneManager;
import com.whisper.client.presentation.services.UnRegister;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneManager.getInstance().initStage(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/signInView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Whisper!");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        //stage.setResizable(false);

        stage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit Confirmation");
            alert.setHeaderText("Are you sure you want to exit?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                UnRegister.getInstance().unregister();
                Platform.exit();
                System.exit(0);
            } else {
                // User chose Cancel or closed the dialog
                event.consume();
            }


        });

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

