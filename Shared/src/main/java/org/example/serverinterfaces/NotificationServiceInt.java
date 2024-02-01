package org.example.serverinterfaces;

import org.example.entities.Notification;
import org.example.entities.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface NotificationServiceInt extends Remote {
    public List<Notification> getALLNotifications(int Id) throws RemoteException;
    public int deleteNotificationById(int Id) throws RemoteException;
}
