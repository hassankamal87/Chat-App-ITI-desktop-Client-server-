package com.whisper.server.repo;

import com.whisper.server.services.db.LocalSource;
import com.whisper.server.services.db.models.*;
import com.whisper.server.services.db.models.enums.Mode;
import com.whisper.server.services.db.models.enums.Status;

import java.sql.SQLException;
import java.util.List;

public class Repository implements RepositoryInterface{
    private static RepositoryInterface instance = null;
    private LocalSource localSource = null;

    private Repository(LocalSource localSource){
        this.localSource = localSource;
    }
    public static synchronized RepositoryInterface getInstance(LocalSource localSource){
        if (instance == null)
            instance = new Repository(localSource);
        return instance;
    }

    @Override
    public List<User> getUsers() throws SQLException {
        return localSource.getUsers();
    }

    @Override
    public User getUserById(int userId) throws SQLException {
        return localSource.getUserById(userId);
    }

    @Override
    public User getUserByPhone(String phoneNumber) throws SQLException {
        return localSource.getUserByPhone(phoneNumber);
    }

    @Override
    public List<User> getUsersContainPhone(String subPhoneNumber) throws SQLException {
        return localSource.getUsersContainPhone(subPhoneNumber);
    }

    @Override
    public Mode getUserMode(int userId) throws SQLException {
        return localSource.getUserMode(userId);
    }

    @Override
    public Status getUserStatus(int userId) throws SQLException {
        return localSource.getUserStatus(userId);
    }

    @Override
    public List<Contact> getContactsForUser(int userId) throws SQLException {
        return localSource.getContactsForUser(userId);
    }

    @Override
    public List<RoomChat> getRoomChatsForUser(int userId) throws SQLException {
        return localSource.getRoomChatsForUser(userId);
    }

    @Override
    public List<Notification> getNotificationForUser(int userId) throws SQLException {
        return localSource.getNotificationForUser(userId);
    }

    @Override
    public List<Message> getMessagesForUser(int userId) throws SQLException {
        return localSource.getMessagesForUser(userId);
    }

    @Override
    public List<Message> getMessagesFromUser(int userId) throws SQLException {
        return localSource.getMessagesFromUser(userId);
    }

    @Override
    public List<Message> getMessagesForRoomChat(int roomChatId) throws SQLException {
        return localSource.getMessagesForRoomChat(roomChatId);
    }

    @Override
    public List<PendingRequest> getUserPendingRequests(int toUserId) throws SQLException {
        return localSource.getUserPendingRequests(toUserId);
    }

    @Override
    public List<PendingRequest> getUserSendingRequest(int fromUserId) throws SQLException {
        return localSource.getUserSendingRequest(fromUserId);
    }

    @Override
    public List<RoomMember> getRoomChatMembers(int roomChatId) throws SQLException {
        return localSource.getRoomChatMembers(roomChatId);
    }

    @Override
    public void closeConnection() throws SQLException {
        localSource.closeConnection();
    }
}
