package com.whisper.client.business.services;

import com.whisper.client.presentation.services.DialogueManager;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.example.entities.Status;
import org.example.entities.User;
import org.example.serverinterfaces.EditProfileServiceInt;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class EditProfileService {
    private EditProfileServiceInt editService;
    private Registry reg;
    DialogueManager dialogueManager = DialogueManager.getInstance();

    public EditProfileService() {
        try {
            reg = LocateRegistry.getRegistry("127.0.0.1", 8000);
            editService = (EditProfileServiceInt) reg.lookup("EditProfileService");
        } catch (Exception e) {
            System.out.println("Exception is  : "+e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
        }
    }


    public void saveProfileChanges(User user) {
        try {
            //System.out.println("7/2" + user.getStatus());
            editService.saveProfileChanges(user);
            displaySuccess();
            user.setStatus(Status.online);
        } catch (Exception e) {
            System.out.println("Exception is : " + e.getMessage());
            displayError();
            System.out.println("Exception is  : "+e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
        }
    }

    public User getUser(int id) {
        User user = new User();
        try {
            user = editService.getUserData(id);
        } catch (Exception e) {
            System.out.println("Exception is : " + e.getMessage());
            System.out.println("Exception is  : "+e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
        }
        return user;
    }

    private void displayError() {
        String Header = "Error happened while changing your profile data.";
        String content = "Please try again later.";
        dialogueManager.showErrorDialog("Error",Header,content);
    }

    private void displaySuccess() {
        String Header = "";
        String content = "Successfully changed your profile data.";
        dialogueManager.showInformationDialog("Success",content);
    }
}
