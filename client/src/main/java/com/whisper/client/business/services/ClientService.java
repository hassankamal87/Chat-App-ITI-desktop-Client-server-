package com.whisper.client.business.services;

import com.whisper.client.MyApp;
import com.whisper.client.presentation.controllers.HandlingChatInterface;
import com.whisper.client.presentation.controllers.ReceiveMessageInterface;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.example.clientinterfaces.ClientInterface;
import org.example.entities.Message;
import org.example.entities.NotifactionType;
import org.example.entities.Notification;
import org.example.entities.User;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class ClientService extends UnicastRemoteObject implements ClientInterface {


    private static ClientService instance = null;
    private HashMap<Integer, ReceiveMessageInterface> chats = new HashMap<>();
    private HandlingChatInterface handlingChatInHome;

    public static synchronized ClientService getInstance() throws RemoteException {
        if (instance == null){
            instance = new ClientService();
        }
        return instance;
    }
    protected ClientService() throws RemoteException {

    }

    //this register to access add room chat function to add new room if user start chat with another user, sorry for this :(
    public void registerHomePane(HandlingChatInterface handlingChat){
        this.handlingChatInHome = handlingChat;
    }

    public void registerChat(int roomId,ReceiveMessageInterface chat){
        chats.put(roomId,chat);
        chats.forEach((id,rec) ->{
            System.out.println(id);
        });
    }

    public void unRegisterChats(){
        chats.clear();
    }

    @Override
    public void notifyUserWithMessage(Message message) throws RemoteException {
        User user = ChattingService.getInstance().getUserById(message.getFromUserId());
        System.out.println(message.getBody());
        Platform.runLater(()->{
            ReceiveMessageInterface toChat = chats.get(message.getToChatId());
            if(toChat!= null)
                toChat.receiveMessageFromList(message);
            else{
                sendNotification(message, user);
                handlingChatInHome.addRoomChat(message.getToChatId());
            }
        });
    }

    @Override
    public void notifyUserWithFile(Message message, File file) throws RemoteException {
        User user = ChattingService.getInstance().getUserById(message.getFromUserId());
        System.out.println(message.getBody());
        Platform.runLater(()->{
            ReceiveMessageInterface toChat = chats.get(message.getToChatId());
            if(toChat!= null)
                toChat.receiveMessageFromList(message);
            else{
                sendNotification(message, user);
                handlingChatInHome.addRoomChat(message.getToChatId());
            }
        });
    }

    private void sendNotification(Message message, User user) {
        Notification messageNotification = new Notification(-1, MyApp.getInstance().getCurrentUser().getUserId(), user.getUserName(), NotifactionType.msg, message.getBody());
        NotificationService notifyService = new NotificationService();
        notifyService.sendMessage(messageNotification);
        try {
            notifyService.addNotification(messageNotification);
        } catch (RemoteException e) {
            System.out.println("exception in client service line 90");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
        }
    }
}
