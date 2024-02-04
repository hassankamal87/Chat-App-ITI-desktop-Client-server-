package com.whisper.client.business.services;

import com.whisper.client.MyApp;
import com.whisper.client.presentation.controllers.HandlingChatInterface;
import org.example.clientinterfaces.ClientInterface;
import org.example.entities.Message;
import org.example.entities.RoomChat;
import org.example.entities.Type;
import org.example.entities.User;
import org.example.serverinterfaces.ChatServiceInt;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Blob;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class ChattingService {

    private static ChattingService instance = null;
    ChatServiceInt chatService;

    private HandlingChatInterface handlingChat;

    private ChattingService() {
        try {
            Registry reg = LocateRegistry.getRegistry(1099);
            chatService = (ChatServiceInt) reg.lookup("ChatService");
            ClientInterface client = ClientService.getInstance();
            chatService.registerUser(MyApp.getInstance().getCurrentUser().getUserId(), client);
        } catch (NotBoundException | RemoteException e) {
            System.out.println("error here  "+e.getMessage());
           // e.printStackTrace();
        }
    }

    public void registerHandlingInterface(HandlingChatInterface handlingChat){
        this.handlingChat = handlingChat;
    }

    public static synchronized ChattingService getInstance() {
        if (instance == null) {
            instance = new ChattingService();
        }
        return instance;
    }

    public void sendMessage(int senderId, int roomChatId, String messageStr) {
        Message newMessage = new Message(-1, roomChatId, Date.valueOf(LocalDate.now()), senderId, messageStr, null);
        try {
            chatService.sendMessage(newMessage);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public List<RoomChat> getAllRoomChatsForUser(int userId){
        try {
            List<RoomChat> roomChats = chatService.getRoomChatsForUser(userId);
            return roomChats;
        } catch (RemoteException e) {
            System.out.println("chatting service line 55");
            e.printStackTrace();
        }

        return null;
    }


    public int getOrCreateRoomChat(int user1Id, int user2Id){
        int roomChatId = getRoomChatForUsers(user1Id,user2Id);
        if(roomChatId != 0){
            handlingChat.openExistChat(roomChatId);
            return roomChatId;
        }else{
            try {
                int newId = chatService.createRoomChat(user1Id,user2Id);
                handlingChat.addRoomChat(newId);
                return newId;
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private int getRoomChatForUsers(int user1, int user2){
        try {
            int roomId = chatService.getRoomChatForUsers(user1,user2);
            System.out.println("user "+user1+" and user "+user2+" in room number "+ roomId );
            return roomId;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserInCommonRoomChat(int roomChatId){
        try {
            List<User> users = chatService.getUsersForRoomChat(roomChatId);
            //this 1 should replaced by current user id
            Optional<User> optUser = users.stream().filter(user -> user.getUserId() != MyApp.getInstance().getCurrentUser().getUserId()).findFirst();
            return optUser.get();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Message> getAllMessagesForRoomChat(int roomChatId){
        try {
            return chatService.getAllMessagesForRoomChat(roomChatId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
