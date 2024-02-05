package com.whisper.client.business.services;

import com.whisper.client.presentation.controllers.ReceiveMessageInterface;
import com.whisper.client.presentation.controllers.RoomChatController;
import javafx.application.Platform;
import org.example.clientinterfaces.ClientInterface;
import org.example.entities.Message;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class ClientService extends UnicastRemoteObject implements ClientInterface {


    private static ClientService instance = null;
    private HashMap<Integer, ReceiveMessageInterface> chats = new HashMap<>();

    public static synchronized ClientService getInstance() throws RemoteException {
        if (instance == null){
            instance = new ClientService();
        }
        return instance;
    }
    protected ClientService() throws RemoteException {

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
        System.out.println(message.getBody());
        Platform.runLater(()->{
            ReceiveMessageInterface toChat = chats.get(message.getToChatId());
            if(toChat!= null)
                toChat.receiveMessageFromList(message);
        });
    }
}
