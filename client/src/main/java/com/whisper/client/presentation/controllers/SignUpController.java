package com.whisper.client.presentation.controllers;

import com.whisper.client.business.services.SignupValidateService;
import com.whisper.client.presentation.services.ErrorDialogue;
import com.whisper.client.presentation.services.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.example.serverinterfaces.AuthenticationServiceInt;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
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
    @FXML
    private Pane firstSignUpSubPane;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    ErrorDialogue dialogue;
    SignupValidateService validateService = new SignupValidateService();


    public void onGetStartedClicked(ActionEvent actionEvent) {
        if (!validateService.validName(firstName.getText()) || !validateService.validName(lastName.getText())){
            dialogue = new ErrorDialogue();
            dialogue.setData("Error", "Invalid Name",
                    "First and Last name should only contains letters and spaces");
            return;
        }

        if (!validateService.validatePhoneNumber(phoneNumber.getText())){
            dialogue = new ErrorDialogue();
            dialogue.setData("Error", "Invalid Phone Number",
                    "Phone number should be exactly 11 numbers");
            return;
        }

        if (!validateService.validateEmail(email.getText())){
            dialogue = new ErrorDialogue();
            dialogue.setData("Error", "Invalid Email",
                    "You email should contains numerics, lower and uppercase letters, those are examples " +
                            "of a valid email: \n" +
                            "username@domain.com\n" +
                            "user.name@domain.com\n");
            return;
        }

        if (!validateService.validatePassword(password.getText())){
            dialogue = new ErrorDialogue();
            dialogue.setData("Error", "Invalid Password",
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
            dialogue = new ErrorDialogue();
            dialogue.setData("Error", "Invalid Confirm Password",
                    "Password and Confirm Password have to match!");
            return;
        }

        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            AuthenticationServiceInt authService = (AuthenticationServiceInt) reg.lookup("authService");
            if (!authService.validatePhoneNumber(phoneNumber.getText())){
                dialogue = new ErrorDialogue();
                dialogue.setData("Error", "Invalid Phone number",
                        "This phone number already exists, try another one");
                return;
            }
            if (!authService.validateEmail(email.getText())){
                dialogue = new ErrorDialogue();
                dialogue.setData("Error", "Invalid Email Address",
                        "This email address already exists, try another one");
                return;
            }
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }

        FXMLLoader loader =new FXMLLoader(getClass().getResource("/com/whisper/client/views/continuingSignUpView.fxml"));
        try {
            Parent root = loader.load();

            ContinuingSignUpController controller = loader.getController();
            controller.setData(firstName.getText(), lastName.getText(), email.getText(), phoneNumber.getText(),
                password.getText(), confirmPassword.getText());
            mainSignUpPane.setCenter(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/continuingSignUpView"));
//        loader.setControllerFactory(clazz -> {
//            if (clazz == ContinuingSignUpController.class) {
//                try {
//                    return new ContinuingSignUpController(name);
//                } catch (RemoteException e) {
//                    throw new RuntimeException(e);
//                }
//            } else {
//                try {
//                    return clazz.newInstance();
//                } catch (Exception ex) {
//                    throw new RuntimeException(ex);
//                }
//            }
//        });
//        try {
//            Parent root = loader.load();
//            mainSignUpPane.setCenter(root);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }


//        Parent root = SceneManager.getInstance().loadPane("continuingSignUpView");
//        mainSignUpPane.setCenter(root);
//        FXMLLoader signupScreen = new FXMLLoader(ContinuingSignUpController.class.getResource("continuingSignUpView.fxml"));
//        ContinuingSignUpController signUpController = signupScreen.getController();
//        ContinuingSignUpController.setChatUserName(username);
//        ContinuingSignUpController.setChatProfilePic(profilePic);
    }

    public void onAlreadyHaveAccountClicked(ActionEvent actionEvent) {
        Parent root = SceneManager.getInstance().loadPane("signInView");
        Scene scene = firstName.getScene();
        scene.setRoot(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}