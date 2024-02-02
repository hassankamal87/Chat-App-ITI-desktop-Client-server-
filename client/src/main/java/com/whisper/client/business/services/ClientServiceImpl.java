package com.whisper.client.business.services;

import javafx.application.Platform;
import org.example.clientinterfaces.ClientServiceInt;
import org.example.entities.NotifactionType;
import org.example.entities.Notification;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientServiceImpl extends UnicastRemoteObject implements ClientServiceInt {


    private static ClientServiceImpl instance = null;


    public static synchronized ClientServiceImpl getInstance() throws RemoteException {
        if(instance == null)
            instance = new ClientServiceImpl();
        return instance;
    }

    private ClientServiceImpl() throws RemoteException {
        super();
    }

    private int ClientId =4;
    @Override
    public void receiveNotification(Notification notification) throws RemoteException {

        NotificationService notificationService = new NotificationService();
        NotifactionType notifactionType = notification.getType();
        if(notifactionType==NotifactionType.inv){
           notificationService.sendInvitation(notification);
        }
        else if(notifactionType==NotifactionType.msg){
            notificationService.sendMessage(notification);
        }
    }

    public int getClientId() throws RemoteException{
        return ClientId;
    }
}
