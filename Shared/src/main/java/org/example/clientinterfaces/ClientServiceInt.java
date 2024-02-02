package org.example.clientinterfaces;

import org.example.entities.Notification;
import org.example.entities.Status;
import org.example.entities.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientServiceInt extends Remote {
    public void receiveNotification(Notification notification) throws RemoteException;

    public void ClientStatusAnnounce(User user) throws RemoteException;

    public int getClientId()throws RemoteException;
}
