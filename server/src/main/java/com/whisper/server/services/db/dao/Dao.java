package com.whisper.server.services.db.dao;

import com.whisper.server.model.*;
import com.whisper.server.services.db.MyDatabase;
import com.whisper.server.model.enums.Gender;
import com.whisper.server.model.enums.Mode;
import com.whisper.server.model.enums.Status;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


// *note: here we should write out functions to get result from database
// *note: functions will include queries

public class Dao implements DaoInterface {

    private static DaoInterface instance = null;
    private MyDatabase myDatabase = MyDatabase.getInstance();

    private Dao(){}

    public synchronized static DaoInterface getInstance(){
        if (instance == null){
            instance = new Dao();
        }
        return instance;
    }

    @Override
    public List<User> getUsers() throws SQLException{
        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM User";
        myDatabase.startConnection();
        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            int userId = rs.getInt(1);
            String phoneNumber = rs.getString(2);
            String password = rs.getString(3);
            String email = rs.getString(4);
            String userName = rs.getString(5);
            String genderStr = rs.getString(6);
            Gender gender = Objects.equals(genderStr, "female") ? Gender.female : Gender.male;
            Date date = rs.getDate(7);
            String country = rs.getString(8);
            String bio = rs.getString(9);
            String modeStr = rs.getString(10);
            Mode mode = switch (modeStr) {
                case "away" -> Mode.away;
                case "busy" -> Mode.busy;
                case "offline" -> Mode.offline;
                default -> Mode.avalible;
            };
            String statusStr = rs.getString(11);
            Status status = Objects.equals(statusStr, "online") ? Status.online : Status.offline;

            User user = new User(userId,phoneNumber,password,email,userName,gender,date,country,bio,mode,status);

            users.add(user);
        }
        rs.close();
        ps.close();
        myDatabase.closeConnection();

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
