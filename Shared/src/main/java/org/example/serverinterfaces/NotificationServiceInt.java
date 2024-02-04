package org.example.serverinterfaces;

import org.example.entities.Notification;
import org.example.entities.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface NotificationServiceInt extends Remote {
    List<Notification> getALLNotifications(int Id) throws RemoteException;
    int deleteNotificationById(int Id) throws RemoteException;
}
