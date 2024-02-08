package com.whisper.client.business.services;

import com.whisper.client.MyApp;
import com.whisper.client.presentation.controllers.HandlingChatInterface;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.example.clientinterfaces.ClientInterface;
import org.example.entities.Message;
import org.example.entities.RoomChat;
import org.example.entities.User;
import org.example.serverinterfaces.ChatServiceInt;

import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ChattingService {

    private static ChattingService instance = null;
    ChatServiceInt chatService;

    private HandlingChatInterface handlingChat;

    private ChattingService() {
        try {
            Registry reg = LocateRegistry.getRegistry(8000);
            chatService = (ChatServiceInt) reg.lookup("ChatService");
            ClientInterface client = ClientService.getInstance();
            chatService.registerUser(MyApp.getInstance().getCurrentUser().getUserId(), client);
        } catch (NotBoundException | RemoteException | NullPointerException e) {
            System.out.println("cannot register user class chatting service line 37");
            // e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
        }
    }

    public void unRegisterUser() {
        try {
            chatService.unRegisterUser(MyApp.getInstance().getCurrentUser().getUserId(), ClientService.getInstance());
            MyApp.getInstance().setCurrentUser(null);
        } catch (RemoteException e) {
            System.out.println("cannot register user  class -> Chatting Service line 47");

            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
        }
    }

    public void registerHandlingInterface(HandlingChatInterface handlingChat) {
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

            System.out.println("Exception is  : "+e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
        }
    }

    public void sendFile(int senderId, int roomChatId, File file){
        try {
            chatService.sendFileMessage(senderId,roomChatId,file);
        } catch (RemoteException e) {

            System.out.println("Exception is  : "+e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
        }
    }

    public List<RoomChat> getAllRoomChatsForUser(int userId) {
        try {
            List<RoomChat> roomChats = chatService.getRoomChatsForUser(userId);
            return roomChats;
        } catch (RemoteException e) {
            System.out.println("chatting service line 55");

            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
        }

        return null;
    }


    public int getOrCreateRoomChat(int user1Id, int user2Id) {
        int roomChatId = getRoomChatForUsers(user1Id, user2Id);
        if (roomChatId != 0) {
            handlingChat.openExistChat(roomChatId);
            return roomChatId;
        } else {
            try {
                int newId = chatService.createRoomChat(user1Id, user2Id);
                handlingChat.addRoomChat(newId);
                return newId;
            } catch (RemoteException e) {
                System.out.println("Exception is  : "+e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
                alert.showAndWait();
                Platform.exit();
                System.exit(0);
                return -1;
            }
        }
    }

    public int createGroupChat(int user1Id, List<User> contacts) {
        try {
            int newId = chatService.createGroupChat(user1Id, contacts);
            handlingChat.addRoomChat(newId);
            return newId;
        } catch (RemoteException e) {
            System.out.println("Exception is  : "+e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
            return -1;
        }
    }

    private int getRoomChatForUsers(int user1, int user2) {
        try {
            int roomId = chatService.getRoomChatForUsers(user1, user2);
            System.out.println("user " + user1 + " and user " + user2 + " in room number " + roomId);
            return roomId;
        } catch (RemoteException e) {
            System.out.println("Exception is  : "+e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
            return -1;
        }
    }

    public User getUserInCommonRoomChat(int roomChatId) {
        try {
            List<User> users = chatService.getUsersForRoomChat(roomChatId);
            //this 1 should replaced by current user id
            Optional<User> optUser = users.stream().filter(user -> user.getUserId() != MyApp.getInstance().getCurrentUser().getUserId()).findFirst();
            return optUser.get();
        } catch (RemoteException e) {
            System.out.println("Exception is  : "+e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
            return null;
        }
    }

    public List<User> getUsersInCommonRoomChat(int roomChatId) {
        try {
            return chatService.getUsersForRoomChat(roomChatId);
        } catch (RemoteException e) {
            System.out.println("Exception is  : "+e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
            return null;
        }
    }


    public List<Message> getAllMessagesForRoomChat(int roomChatId) {
        try {
            return chatService.getAllMessagesForRoomChat(roomChatId);
        } catch (RemoteException e) {
            System.out.println("Exception is  : "+e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
            return null;
        }
    }

    public List<File> getAllFilesForRoomChat(int roomChatId){
        try {
            return chatService.getAllFilesForRoomChat(roomChatId);
        } catch (RemoteException e) {
            System.out.println("Exception is  : "+e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
            return null;
        }
    }

    public HashMap<Message,File> getMessagesWithFilesForRoomChat(int roomChatId){
        try {
            return chatService.getMessagesAndFilesForRoomChat(roomChatId);
        } catch (RemoteException e) {
            System.out.println("Exception is  : "+e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
            return null;
        }
    }

    public RoomChat getRoomChatByID(int roomChatId) {
        try {
            return chatService.getRoomChatByID(roomChatId);
        } catch (RemoteException e) {
            System.out.println("Exception is  : "+e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
            return null;
        }
    }

    public User getUserById(int userId){
        try {
            return chatService.getUserById(userId);
        } catch (RemoteException e) {
            System.out.println("Exception is  : "+e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
            return null;
        }
    }
}
