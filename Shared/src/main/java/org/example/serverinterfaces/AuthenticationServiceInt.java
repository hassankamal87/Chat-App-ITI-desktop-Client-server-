package org.example.serverinterfaces;

import org.example.entities.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AuthenticationServiceInt extends Remote {
    boolean registerUser(User user) throws RemoteException;
    boolean loginUser(String phoneNumber, String password) throws RemoteException;
    boolean validatePhoneNumber(String phoneNo) throws RemoteException;
    boolean validateEmail(String email) throws RemoteException;
}
