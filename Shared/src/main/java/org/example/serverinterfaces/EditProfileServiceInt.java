package org.example.serverinterfaces;

import org.example.entities.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EditProfileServiceInt extends Remote {

    User getUserData(int id) throws RemoteException;
    void saveProfileChanges(User user) throws RemoteException;
}
