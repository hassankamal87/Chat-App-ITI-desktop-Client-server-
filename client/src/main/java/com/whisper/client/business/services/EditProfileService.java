package com.whisper.client.business.services;

import com.whisper.client.presentation.services.DialogueManager;
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
            reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            editService = (EditProfileServiceInt) reg.lookup("EditProfileService");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void saveProfileChanges(User user) {
        try {
            editService.saveProfileChanges(user);
            displaySuccess();
        } catch (Exception e) {
            System.out.println("Exception is : " + e.getMessage());
            displayError();
        }
    }

    public User getUser(int id) {
        User user = new User();
        try {
            user = editService.getUserData(id);
        } catch (Exception e) {
            System.out.println("Exception is : " + e.getMessage());
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
