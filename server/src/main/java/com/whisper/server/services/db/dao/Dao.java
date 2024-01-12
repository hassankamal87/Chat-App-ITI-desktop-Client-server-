package com.whisper.server.services.db.dao;

import com.whisper.server.services.db.MyDatabase;
import com.whisper.server.services.db.models.*;
import com.whisper.server.services.db.models.enums.Gender;
import com.whisper.server.services.db.models.enums.Mode;
import com.whisper.server.services.db.models.enums.Status;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


// *note: here we should write out functions to get result from database
// *note: functions will include queries

public class Dao implements DaoInterface {

    private static DaoInterface instance = null;

    private Dao(){

    }

    public synchronized static DaoInterface getInstance(){
        if (instance == null)
            instance = new Dao();
        return instance;
    }

    @Override
    public List<User> getUsers() throws SQLException{
        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM User";
        PreparedStatement ps = MyDatabase.getInstance().getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            int userId = rs.getInt(1);
            String phoneNumber = rs.getString(2);
            String password = rs.getString(3);
            String email = rs.getString(4);
            String userName = rs.getString(5);
            Gender gender = /*rs.getObject(6, Gender.class);*/Gender.male;
            Date date = rs.getDate(7);
            String country = rs.getString(8);
            String bio = rs.getString(9);
            Mode mode = /*rs.getObject(10, Mode.class);*/Mode.away;
            Status status = /*rs.getObject(11, Status.class);*/Status.online;

            User user = new User(userId,phoneNumber,password,email,userName,gender,date,country,bio,mode,status);

            users.add(user);
        }
        rs.close();
        ps.close();

        return users;
    }

    @Override
    public User getUserById(int userId) {
        return null;
    }

    @Override
    public User getUserByPhone(String phoneNumber) {
        return null;
    }

    @Override
    public List<User> getUsersContainPhone(String subPhoneNumber) {
        return null;
    }

    @Override
    public Mode getUserMode(int userId) {
        return null;
    }

    @Override
    public Status getUserStatus(int userId) {
        return null;
    }

    @Override
    public List<Contact> getContactsForUser(int userId) {
        return null;
    }

    @Override
    public List<RoomChat> getRoomChatsForUser(int userId) {
        return null;
    }

    @Override
    public List<Notification> getNotificationForUser(int userId) {
        return null;
    }

    @Override
    public List<Message> getMessagesForUser(int userId) {
        return null;
    }

    @Override
    public List<Message> getMessagesFromUser(int userId) {
        return null;
    }

    @Override
    public List<Message> getMessagesForRoomChat(int roomChatId) {
        return null;
    }

    @Override
    public List<PendingRequest> getUserPendingRequests(int toUserId) {
        return null;
    }

    @Override
    public List<PendingRequest> getUserSendingRequest(int fromUserId) {
        return null;
    }

    @Override
    public List<RoomMember> getRoomChatMembers(int roomChatId) {
        return null;
    }

    @Override
    public void closeConnection() throws SQLException {
        MyDatabase.getInstance().closeConnection();
    }

}
