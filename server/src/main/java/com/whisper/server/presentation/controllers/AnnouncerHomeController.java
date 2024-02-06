package com.whisper.server.presentation.controllers;

import com.whisper.server.business.services.NotificationServiceImpl;
import com.whisper.server.business.services.SendContactsInvitationServiceImpl;
import com.whisper.server.persistence.db.MyDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import org.example.clientinterfaces.ClientServiceInt;
import org.example.entities.NotifactionType;
import org.example.entities.Notification;
import org.example.serverinterfaces.SendContactsInvitationServiceInt;

import java.rmi.RemoteException;
import java.util.concurrent.CopyOnWriteArrayList;

public class AnnouncerHomeController
{

    @FXML
    private HTMLEditor htmlEditor;
    @FXML
    private AnchorPane announcmentPane;

    public void initialize()
    {

    }

    public void onAnnounceClicked(ActionEvent actionEvent) {
        String htmlText = htmlEditor.getHtmlText();
        System.out.println(htmlText);
        htmlEditor.setHtmlText("");


            CopyOnWriteArrayList<ClientServiceInt> clients = SendContactsInvitationServiceImpl.clientsVector;
            for(ClientServiceInt c:clients){
                try{
                    Notification notification = new Notification(0, c.getClientId(), "Admin",
                            NotifactionType.board,htmlText );
                    NotificationServiceImpl notificationService =  NotificationServiceImpl.getInstance();
                    notificationService.addNotification(notification );
                    c.receiveNotification(notification);
                }catch (RemoteException e){
                    clients.remove(c);
                }



            }



        // the htmlText contains the text with its style
        // need to pass this string to a web view to display it
        // look at the RoomChatController class for an example
        // at method appendMessageInList

    }
}