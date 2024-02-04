package com.whisper.client.business.services;

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
        } catch (Exception e) {
            System.out.println("Exception is : " + e.getMessage());
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
}
