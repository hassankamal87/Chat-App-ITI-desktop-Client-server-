package com.whisper.client.presentation.controllers;

import com.whisper.client.business.services.SignupValidateService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
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

    SignupValidateService validateService = new SignupValidateService();


    public void onGetStartedClicked(ActionEvent actionEvent) {
        if (!validateService.validName(firstName.getText()) || !validateService.validName(lastName.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Name");
            alert.setContentText("First and Last name should only contains letters and spaces");
            alert.showAndWait();
            return;
        }

        if (!validateService.validatePhoneNumber(phoneNumber.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Phone Number");
            alert.setContentText("Phone number should be exactly 11 numbers \n" +
                    "01014234089");
            alert.showAndWait();
            return;
        }

        if (!validateService.validateEmail(email.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Email");
            alert.setContentText("You email should contains numerics, lower and uppercase letters, those are examples " +
                    "of a valid email: \n" +
                    "username@domain.com\n" +
                    "user.name@domain.com\n");
            alert.showAndWait();
            return;
        }

        if (!validateService.validatePassword(password.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Password");
            alert.setContentText("Your password should contains\n" +
                    "at least one small letter\n" +
                    "at least one capital letter\n" +
                    "at least one digit\n" +
                    "at least one special symbol\n" +
                    "minimum length of 8 characters and the maximum length\n" +
                    "of 20 characters");
            alert.showAndWait();
            return;
        }

        if (!validateService.validateConfirmPassword(password.getText(), confirmPassword.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Confirm Password");
            alert.setContentText("Password and Confirm Password have to match!");
            alert.showAndWait();
            return;
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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}