package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.IPConfig;
import com.whisper.client.MyApp;
import com.whisper.client.business.services.ClientServiceImpl;
import com.whisper.client.presentation.services.DialogueManager;
import com.whisper.client.presentation.services.EncryptionUtils;
import com.whisper.client.presentation.services.SceneManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.clientinterfaces.ClientServiceInt;
import org.example.entities.User;
import org.example.serverinterfaces.AuthenticationServiceInt;
import org.example.serverinterfaces.SendContactsInvitationServiceInt;

import java.io.*;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;
import java.util.ResourceBundle;

public class SignInController implements Initializable {
    @javafx.fxml.FXML
    private TextField phoneNumber;
    @javafx.fxml.FXML
    private PasswordField password;
    private DialogueManager dialogueManager = DialogueManager.getInstance();
    @javafx.fxml.FXML
    private CheckBox rememberMeChecked;


    @javafx.fxml.FXML
    public void initialize() {

    }

    public void onSigninButtonClick(ActionEvent actionEvent) {
        try {
            Registry reg = LocateRegistry.getRegistry(IPConfig.serverIP, 8000);
            AuthenticationServiceInt authService = (AuthenticationServiceInt) reg.lookup("authService");
            User currentUser  = authService.loginUser(phoneNumber.getText(), password.getText());
            if ( currentUser == null){
                dialogueManager.showErrorDialog("Error", "Invalid Credentials",
                        "Phone number or Password is incorrect");
                return;
            }else{
                Properties props = new Properties();
                props.setProperty("phoneNumber", EncryptionUtils.encrypt(phoneNumber.getText()));
                props.store(new FileOutputStream("userInfo.properties"), "User Properties");
                System.out.println("Property file added");

                if (rememberMeChecked.isSelected()){
                    props.setProperty("phoneNumber", EncryptionUtils.encrypt(phoneNumber.getText()));
                    props.setProperty("password", EncryptionUtils.encrypt(password.getText()));
                    props.setProperty("rememberMe", String.valueOf(true));
                    props.store(new FileOutputStream("userInfo.properties"), "User Properties");
                    System.out.println("Property file modified by remember me");
                }
                MyApp.getInstance().setCurrentUser(currentUser);
                System.out.println("current user "+currentUser.getUserId());
                SceneManager.getInstance().loadView("profileGroupView");

                SendContactsInvitationServiceInt serverRef = (SendContactsInvitationServiceInt) reg.lookup("SendContactsInvitationService");
                ClientServiceInt clientService = ClientServiceImpl.getInstance();
                serverRef.ServerRegister(clientService);
            }
            System.out.println("Client side: signing in succeed");
        } catch (Exception e) {
            System.out.println("Exception : "+e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
        }
    }

    public void onSignupButtonClick(ActionEvent actionEvent) {
        Parent root = SceneManager.getInstance().loadPane("signUpView");
        Scene scene = password.getScene();
        scene.setRoot(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            File file = new File("userInfo.properties");
            if (file.exists()) {
                Properties props = new Properties();
                props.load(new FileInputStream(file));
                phoneNumber.setText(EncryptionUtils.decrypt(props.getProperty("phoneNumber")));
                String rememberMe = props.getProperty("rememberMe");
                System.out.println(props.getProperty("rememberMe"));
                if (rememberMe != null && rememberMe.equals("true")) {
                    signInWithProperties(props.getProperty("phoneNumber"), props.getProperty("password"));
                }
            } else {
                System.out.println("userInfo.properties file does not exist.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void signInWithProperties(String phoneNumber2, String password2) {
        try {
            Registry reg = LocateRegistry.getRegistry(IPConfig.serverIP, 8000);
            AuthenticationServiceInt authService = (AuthenticationServiceInt) reg.lookup("authService");
            User currentUser  = authService.loginUser(
                    EncryptionUtils.decrypt(phoneNumber2), EncryptionUtils.decrypt(password2));
            MyApp.getInstance().setCurrentUser(currentUser);

            SendContactsInvitationServiceInt serverRef = (SendContactsInvitationServiceInt) reg.lookup("SendContactsInvitationService");
            ClientServiceInt clientService = ClientServiceImpl.getInstance();
            serverRef.ServerRegister(clientService);

            System.out.println(password2);
            Platform.runLater(()->{
                SceneManager.getInstance().loadView("mainView");
            });
            System.out.println(password2);
        } catch (Exception e) {
            System.out.println("Exception is : "+e.toString());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
        }
    }
}