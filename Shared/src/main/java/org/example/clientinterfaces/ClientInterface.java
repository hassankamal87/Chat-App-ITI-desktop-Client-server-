package org.example.clientinterfaces;

import org.example.entities.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    void notifyUserWithMessage(Message message) throws RemoteException;
}
