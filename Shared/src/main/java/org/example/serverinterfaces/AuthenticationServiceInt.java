package org.example.serverinterfaces;

import org.example.entities.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AuthenticationServiceInt extends Remote {
    String createNewUser(User user) throws RemoteException;
}
