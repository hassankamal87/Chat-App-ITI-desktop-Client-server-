package org.example.serverinterfaces;

import org.example.clientinterfaces.ClientInterface;
import org.example.entities.Message;
import org.example.entities.RoomChat;
import org.example.entities.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatServiceInt extends Remote {
    void sendMessage(Message message) throws RemoteException;

    void registerUser(int userId,ClientInterface client) throws RemoteException;

    List<RoomChat> getRoomChatsForUser(int userId) throws RemoteException;

    int getRoomChatForUsers(int user1, int user2) throws RemoteException;

    int createRoomChat(int user1Id, int user2Id) throws RemoteException;

    List<User> getUsersForRoomChat(int roomChatId) throws RemoteException;
}