package com.whisper.client.business.services;

import com.whisper.client.presentation.services.ErrorDialogue;
import org.example.entities.User;
import org.example.serverinterfaces.EditProfileServiceInt;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class EditProfileService {
    private EditProfileServiceInt editService;
    private Registry reg;

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
        ErrorDialogue.setData("Error",Header,content);
    }

    private void displaySuccess() {
        String Header = "";
        String content = "Successfully changed your profile data.";
        ErrorDialogue.showInformationDialog("Success",content);
    }
}
