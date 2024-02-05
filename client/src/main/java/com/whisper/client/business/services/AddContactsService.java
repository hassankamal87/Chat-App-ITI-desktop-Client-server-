package com.whisper.client.business.services;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.example.serverinterfaces.AddContactsServiceInt;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AddContactsService {
    public void addContact(int userId, int contactId) {
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 8000);
            AddContactsServiceInt addContactsServiceInt = (AddContactsServiceInt) reg.lookup("AddContactsService");
            addContactsServiceInt.addContact(userId, contactId);
        } catch (Exception e) {
            System.out.println("Exception is : " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
        }
    }
}
