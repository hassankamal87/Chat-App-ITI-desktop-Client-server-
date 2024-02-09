package org.example.serverinterfaces;

import org.example.clientinterfaces.ClientInterface;
import org.example.entities.Message;
import org.example.entities.RoomChat;
import org.example.entities.RoomMember;
import org.example.entities.User;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public interface ChatServiceInt extends Remote {
    void sendMessage(Message message) throws RemoteException;

    void sendFileMessage(int senderId ,int roomChatId, File file) throws RemoteException;

    void registerUser(int userId,ClientInterface client) throws RemoteException;
    void unRegisterUser(int userId,ClientInterface client) throws RemoteException;

    List<RoomChat> getRoomChatsForUser(int userId) throws RemoteException;

    int getRoomChatForUsers(int user1, int user2) throws RemoteException;

    int createRoomChat(int user1Id, int user2Id) throws RemoteException;

    public int createGroupChat(int user1Id, List<User> users) throws RemoteException;

    List<User> getUsersForRoomChat(int roomChatId) throws RemoteException;

    List<Message> getAllMessagesForRoomChat(int roomChatId) throws RemoteException;

    List<File> getAllFilesForRoomChat(int roomChatId) throws RemoteException;

    HashMap<Message,File> getMessagesAndFilesForRoomChat(int roomChatId) throws RemoteException;
    RoomChat getRoomChatByID(int roomChatId) throws RemoteException;

    User getUserById(int userId) throws RemoteException;

    List<User> getGroupMembers(int roomId) throws RemoteException;

    void removeGroupMembers(RoomMember member);
}
