package org.example.clientinterfaces;

import org.example.entities.Notification;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientServiceInt extends Remote {
    public void receiveNotification(Notification notification) throws RemoteException;

    public int getClientId()throws RemoteException;
}
