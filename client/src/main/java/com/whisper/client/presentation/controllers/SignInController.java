package com.whisper.client.presentation.controllers;

import com.whisper.client.presentation.services.ErrorDialogue;
import com.whisper.client.presentation.services.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import org.example.serverinterfaces.AuthenticationServiceInt;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Locale;
import java.util.ResourceBundle;

public class SignInController implements Initializable {
    @javafx.fxml.FXML
    private TextField phoneNumber;
    @javafx.fxml.FXML
    private PasswordField password;
    @javafx.fxml.FXML
    private CheckBox rememberMeChecked;
    ErrorDialogue dialogue;


    @javafx.fxml.FXML
    public void initialize() {
    }

    public void onSigninButtonClick(ActionEvent actionEvent) {
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            AuthenticationServiceInt authService = (AuthenticationServiceInt) reg.lookup("authService");
            if (authService.loginUser(phoneNumber.getText(), password.getText()) == null){
                dialogue = new ErrorDialogue();
                dialogue.setData("Error", "Invalid Credentials",
                        "Phone number or Password is incorrect");
                return;
            }
            System.out.println("Client side: signing in succeed");
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
        Parent root = SceneManager.getInstance().loadPane("homeView");
        Scene scene = password.getScene();
        scene.setRoot(root);
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