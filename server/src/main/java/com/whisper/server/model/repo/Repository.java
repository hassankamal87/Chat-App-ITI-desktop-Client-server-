package com.whisper.server.model.repo;


import com.whisper.server.model.*;
import com.whisper.server.datalayer.db.dao.DaoInterface;
import com.whisper.server.model.enums.Mode;
import com.whisper.server.model.enums.Status;

import java.sql.SQLException;
import java.util.List;

public class Repository implements RepositoryInterface{
    private static RepositoryInterface instance = null;
    private DaoInterface dao = null;

    private Repository(DaoInterface dao){
        this.dao = dao;
    }
    public static synchronized RepositoryInterface getInstance(DaoInterface dao){
        if (instance == null)
            instance = new Repository(dao);
        return instance;
    }

    @Override
    public List<User> getUsers() throws SQLException {
        return dao.getUsers();
    }

    @Override
    public User getUserById(int userId) throws SQLException {
        return dao.getUserById(userId);
    }

    @Override
    public User getUserByPhone(String phoneNumber) throws SQLException {
        return dao.getUserByPhone(phoneNumber);
    }

    @Override
    public List<User> getUsersContainPhone(String subPhoneNumber) throws SQLException {
        return dao.getUsersContainPhone(subPhoneNumber);
    }

    @Override
    public Mode getUserMode(int userId) throws SQLException {
        return dao.getUserMode(userId);
    }

    @Override
    public Status getUserStatus(int userId) throws SQLException {
        return dao.getUserStatus(userId);
    }

    @Override
    public List<Contact> getContactsForUser(int userId) throws SQLException {
        return dao.getContactsForUser(userId);
    }

    @Override
    public List<RoomChat> getRoomChatsForUser(int userId) throws SQLException {
        return dao.getRoomChatsForUser(userId);
    }

    @Override
    public List<Notification> getNotificationForUser(int userId) throws SQLException {
        return dao.getNotificationForUser(userId);
    }

    @Override
    public List<Message> getMessagesForUser(int userId) throws SQLException {
        return dao.getMessagesForUser(userId);
    }

    @Override
    public List<Message> getMessagesFromUser(int userId) throws SQLException {
        return dao.getMessagesFromUser(userId);
    }

    @Override
    public List<Message> getMessagesForRoomChat(int roomChatId) throws SQLException {
        return dao.getMessagesForRoomChat(roomChatId);
    }

    @Override
    public List<PendingRequest> getUserPendingRequests(int toUserId) throws SQLException {
        return dao.getUserPendingRequests(toUserId);
    }

    @Override
    public List<PendingRequest> getUserSendingRequest(int fromUserId) throws SQLException {
        return dao.getUserSendingRequest(fromUserId);
    }

    @Override
    public List<RoomMember> getRoomChatMembers(int roomChatId) throws SQLException {
        return dao.getRoomChatMembers(roomChatId);
    }

    @Override
    public boolean createUser(User user) throws SQLException {
        return dao.createUser(user);
    }

    @Override
    public boolean updateUser(User newUser) {
        return dao.updateUser(newUser);
    }

    @Override
    public boolean deleteUserById(int userId) {
        return dao.deleteUserById(userId);
    }

    @Override
    public boolean addContact(int userId, int contactId) {
        return dao.addContact(userId, contactId);
    }

    @Override
    public void closeConnection() throws SQLException {
        dao.closeConnection();
    }
}
