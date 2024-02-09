package com.whisper.client.presentation.controllers;

import com.whisper.client.IPConfig;
import com.whisper.client.business.services.SignupValidateService;
import com.whisper.client.presentation.services.DialogueManager;
import com.whisper.client.presentation.services.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.example.serverinterfaces.AuthenticationServiceInt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @javafx.fxml.FXML
    private TextField firstName;
    @javafx.fxml.FXML
    private TextField lastName;
    @javafx.fxml.FXML
    private TextField email;
    @javafx.fxml.FXML
    private TextField phoneNumber;
    @FXML
    private BorderPane mainSignUpPane;
    private DialogueManager dialogueManager = DialogueManager.getInstance();
    SignupValidateService validateService = new SignupValidateService();
    private String hashedPassword;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;

    @Deprecated
    public void getStartedClicked(ActionEvent actionEvent) {
        try {
            File file = new File("userInfo.properties");
            if (file.exists()) {
                Properties props = new Properties();
                props.load(new FileInputStream(file));

                props.setProperty("password", "");
                props.setProperty("phoneNumber", "");
                props.setProperty("rememberMe", "false");

                props.store(new FileOutputStream(file), "User Properties");
            } else {
                System.out.println("userInfo.properties file does not exist.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!validateService.validName(firstName.getText()) || !validateService.validName(lastName.getText())){
            dialogueManager.showErrorDialog("Error", "Invalid Name",
                    "First and Last name should only contains letters and spaces");
            return;
        }

        if (!validateService.validatePhoneNumber(phoneNumber.getText())){
            dialogueManager.showErrorDialog("Error", "Invalid Phone Number",
                    "Phone number should be exactly 11 numbers");
            return;
        }

        if (!validateService.validateEmail(email.getText())){
            dialogueManager.showErrorDialog("Error", "Invalid Email",
                    "You email should contains numerics, lower and uppercase letters, those are examples " +
                            "of a valid email: \n" +
                            "username@domain.com\n" +
                            "user.name@domain.com\n");
            return;
        }

        if (!validateService.validatePassword(password.getText())){
            dialogueManager.showErrorDialog("Error", "Invalid Password",
                    "Your password should contains\n" +
                            "at least one small letter\n" +
                            "at least one capital letter\n" +
                            "at least one digit\n" +
                            "at least one special symbol\n" +
                            "minimum length of 8 characters and the maximum length\n" +
                            "of 20 characters");
            return;
        }

        if (!validateService.validateConfirmPassword(password.getText(), confirmPassword.getText())){
            dialogueManager.showErrorDialog("Error", "Invalid Confirm Password",
                    "Password and Confirm Password have to match!");
            return;
        }

        try {
            Registry reg = LocateRegistry.getRegistry(IPConfig.serverIP, 8000);
            AuthenticationServiceInt authService = (AuthenticationServiceInt) reg.lookup("authService");
            if (!authService.validatePhoneNumber(phoneNumber.getText())){
                dialogueManager.showErrorDialog("Error", "Invalid Phone number",
                        "This phone number already exists, try another one");
                return;
            }
            if (!authService.validateEmail(email.getText())){
                dialogueManager.showErrorDialog("Error", "Invalid Email Address",
                        "This email address already exists, try another one");
                return;
            }

            hashedPassword = authService.hashPassword(password.getText());
            System.out.println(hashedPassword);
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }

        FXMLLoader loader =new FXMLLoader(getClass().getResource("/com/whisper/client/views/continuingSignUpView.fxml"));
        try {
            Parent root = loader.load();

            ContinuingSignUpController controller = loader.getController();
            controller.setData(firstName.getText(), lastName.getText(), email.getText(), phoneNumber.getText(),
                    hashedPassword);
            mainSignUpPane.setCenter(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void onAlreadyHaveAccountClicked(ActionEvent actionEvent) {
        Parent root = SceneManager.getInstance().loadPane("signInView");
        Scene scene = firstName.getScene();
        scene.setRoot(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}