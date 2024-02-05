package com.whisper.client.presentation.controllers;

import com.whisper.client.MyApp;
import com.whisper.client.business.services.ClientServiceImpl;
import com.whisper.client.presentation.services.DialogueManager;
import com.whisper.client.presentation.services.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import org.example.clientinterfaces.ClientServiceInt;
import org.example.entities.User;
import org.example.serverinterfaces.AuthenticationServiceInt;
import org.example.serverinterfaces.SendContactsInvitationServiceInt;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class SignInController implements Initializable {
    @javafx.fxml.FXML
    private TextField phoneNumber;
    @javafx.fxml.FXML
    private PasswordField password;
    @javafx.fxml.FXML
    private CheckBox rememberMeChecked;
    DialogueManager dialogue;


    @javafx.fxml.FXML
    public void initialize() {
    }

    public void onSigninButtonClick(ActionEvent actionEvent) {
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            AuthenticationServiceInt authService = (AuthenticationServiceInt) reg.lookup("authService");
            User currentUser  = authService.loginUser(phoneNumber.getText(), password.getText());
            if ( currentUser == null){
                dialogue = new DialogueManager();
                dialogue.setData("Error", "Invalid Credentials",
                        "Phone number or Password is incorrect");
                return;
            }else{
                MyApp.getInstance().setCurrentUser(currentUser);
                System.out.println("current user "+currentUser.getUserId());
                Parent root = SceneManager.getInstance().loadPane("mainView");
                Scene scene = password.getScene();
                scene.setRoot(root);


                SendContactsInvitationServiceInt serverRef = (SendContactsInvitationServiceInt) reg.lookup("SendContactsInvitationService");
                ClientServiceInt clientService = ClientServiceImpl.getInstance();
                serverRef.ServerRegister(clientService);


            }
            System.out.println("Client side: signing in succeed");
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void onSignupButtonClick(ActionEvent actionEvent) {
        Parent root = SceneManager.getInstance().loadPane("signUpView");
        Scene scene = password.getScene();
        scene.setRoot(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}