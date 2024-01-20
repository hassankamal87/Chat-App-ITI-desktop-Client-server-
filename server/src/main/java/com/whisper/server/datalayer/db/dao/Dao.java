package com.whisper.server.datalayer.db.dao;

import com.whisper.server.model.*;
import com.whisper.server.datalayer.db.MyDatabase;
import com.whisper.server.model.enums.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


// *note: here we should write out functions to get result from database
// *note: functions will include queries

public class Dao implements DaoInterface {

    private static DaoInterface instance = null;
    private MyDatabase myDatabase = MyDatabase.getInstance();

    private Dao(MyDatabase myDatabase) {
    }

    public synchronized static DaoInterface getInstance(MyDatabase myDatabase) {
        if (instance == null) {
            instance = new Dao(myDatabase);
        }
        return instance;
    }

    @Override
    public List<User> getUsers() throws SQLException {
        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM User";
        //myDatabase.startConnection();
        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
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

            User user = new User(userId, phoneNumber, password, email, userName, gender, date, country, bio, mode, status);

            users.add(user);
        }
        rs.close();
        ps.close();
       myDatabase.closeConnection();

        return users;
    }

    @Override
    public User getUserById(int userId) throws SQLException {
        List<User> list = new ArrayList<>();

        String query = String.format("SELECT * FROM User WHERE user_id=%d", userId);
        myDatabase.startConnection();
        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
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

            User user = new User(userId, phoneNumber, password, email, userName, gender, date, country, bio, mode, status);
            list.add(user);
        }


        rs.close();
        ps.close();
        myDatabase.closeConnection();

        return list.get(0);
    }

    @Override
    public User getUserByPhone(String phoneNumber) throws SQLException {
        List<User> list = new ArrayList<>();

        String query = String.format("SELECT * FROM User WHERE phone_number=%s", phoneNumber);
        myDatabase.startConnection();
        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int userId = rs.getInt(1);

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

            User user = new User(userId, phoneNumber, password, email, userName, gender, date, country, bio, mode, status);
            list.add(user);
        }

        rs.close();
        ps.close();
        myDatabase.closeConnection();

        return list.get(0);
    }

    @Override
    public List<User> getUsersContainPhone(String subPhoneNumber) throws SQLException {
        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM User WHERE LOCATE(?, phone_number) > 0";
        myDatabase.startConnection();
        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ps.setString(1, subPhoneNumber);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

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

            User user = new User(userId, phoneNumber, password, email, userName, gender, date, country, bio, mode, status);

            users.add(user);
        }
        rs.close();
        ps.close();
        myDatabase.closeConnection();

        return users;
    }

    @Override
    public Mode getUserMode(int userId) throws SQLException {
        String modeStr = new String();
        Mode mode = null;
        String query = String.format("SELECT mode FROM User WHERE user_id = %d", userId);
        myDatabase.startConnection();
        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            modeStr = rs.getString(1);
            mode = switch (modeStr) {
                case "away" -> Mode.away;
                case "busy" -> Mode.busy;
                case "offline" -> Mode.offline;
                default -> Mode.avalible;
            };
        }
        rs.close();
        ps.close();
        myDatabase.closeConnection();

        return mode;
    }

    @Override
    public Status getUserStatus(int userId) throws SQLException {
        String query = "Select status from user where user_id = ?";
        Status userStatus = null;
        myDatabase.startConnection();
        try (PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String status = rs.getString(1);
                userStatus = Status.valueOf(status);
            }
        }
        myDatabase.closeConnection();
        return userStatus;
    }

    @Override
    public List<Contact> getContactsForUser(int userId) throws SQLException {
        List<Contact> contacts = new ArrayList<>();
        String query = "Select * from contact where user_id = ?";
        myDatabase.startConnection();
        try (PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FriendshipStatus friendshipStatus = FriendshipStatus.valueOf(rs.getString("friendship_status"));
                String contactDate = rs.getString("contact_date");
                int contactId = rs.getInt("contact_id");
                contacts.add(new Contact(friendshipStatus, contactDate, contactId));
            }
        }
        myDatabase.closeConnection();
        return contacts;
    }

    @Override
    public List<RoomChat> getRoomChatsForUser(int userId) {
        return null;
    }

    @Override
    public List<Notification> getNotificationForUser(int userId) throws SQLException {
        List<Notification> notifications = new ArrayList<>();
        String query = "Select * from notification where to_user_id = ?";
        myDatabase.startConnection();
        try (PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int notificationId = rs.getInt("notification_id");
                String username = rs.getString("from_user_name");
                NotifactionType type = NotifactionType.valueOf(rs.getString("type"));
                String body = rs.getString("body");
                notifications.add(new Notification(notificationId, userId, username, type, body));
                rs.close();
            }
        }
        myDatabase.closeConnection();
        return notifications;
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
    public List<PendingRequest> getUserPendingRequests(int toUserId) throws SQLException {
        List<PendingRequest> pendingRequests = new ArrayList<>();
        String query = "Select * from pending_request where to_user_id = ?";
        myDatabase.startConnection();
        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ps.setInt(1, toUserId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int fromUserId = rs.getInt("from_user_id");
            String sentDate = rs.getString("sent_date");
            String body = rs.getString("body");
            pendingRequests.add(new PendingRequest(toUserId, fromUserId, sentDate, body));
        }
        rs.close();
        ps.close();
        myDatabase.closeConnection();
        return pendingRequests;
    }

    @Override
    public List<PendingRequest> getUserSendingRequest(int fromUserId) throws SQLException {
        List<PendingRequest> pendingRequests = new ArrayList<>();
        String query = "Select * from pending_request where from_user_id = ?";
        myDatabase.startConnection();
        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ps.setInt(1, fromUserId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int toUserId = rs.getInt("to_user_id");
            String sentDate = rs.getString("sent_date");
            String body = rs.getString("body");
            pendingRequests.add(new PendingRequest(toUserId, fromUserId, sentDate, body));
        }

        rs.close();
        ps.close();
        myDatabase.closeConnection();
        return pendingRequests;
    }

    @Override
    public List<RoomMember> getRoomChatMembers(int roomChatId) throws SQLException {
        return null;
    }

    @Override
    public void closeConnection() throws SQLException {
        MyDatabase.getInstance().closeConnection();
    }

    @Override
    public boolean createUser(User user) throws SQLException {
        String query = "INSERT INTO user (phone_number, password, email, user_name, gender, date_of_birth, country, bio, mode, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        myDatabase.startConnection();
        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ps.setString(1, user.getPhoneNumber());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getEmail());
        ps.setString(4, user.getUserName());
        ps.setString(5, user.getGender().toString()); // Convert enum to string
        ps.setDate(6, user.getDateOfBirth()); // Assuming Date type for date_of_birth
        ps.setString(7, user.getCountry());
        ps.setString(8, user.getBio());
        ps.setString(9, user.getMode().toString()); // Convert enum to string
        ps.setString(10, user.getStatus().toString()); // Convert enum to string

        int rowsInserted = ps.executeUpdate();
        ps.close();
        myDatabase.closeConnection();
        return rowsInserted > 0; // If rowsInserted > 0, insertion was successful

    }

    @Override
    public boolean deleteUserById(int userId) throws SQLException {
        String query = "DELETE FROM user WHERE user_id = ?";

        myDatabase.startConnection();
        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ps.setInt(1, userId);

        int rowsDeleted = ps.executeUpdate();
        ps.close();
        myDatabase.closeConnection();
        return rowsDeleted > 0; // If rowsDeleted > 0, deletion was successful
    }

    @Override
    public boolean addContact(int userId, int contactId) throws SQLException {
        String query = "INSERT INTO contact " +
                "(contact_id, friendship_status," +
                " contact_date, user_id)" +
                " VALUES (?, ?, ?, ?)";

        myDatabase.startConnection();
        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ps.setInt(1, userId);
        ps.setString(2, "friend"); // Set friendship_status to "friend"
        ps.setDate(3, Date.valueOf(LocalDate.now())); // Set contact_date to the current date
        ps.setInt(4, contactId);
        int rowsInserted = ps.executeUpdate();

        ps.setInt(4, userId);
        ps.setString(2, "friend"); // Set friendship_status to "friend"
        ps.setDate(3, Date.valueOf(LocalDate.now())); // Set contact_date to the current date
        ps.setInt(1, contactId);

        rowsInserted = ps.executeUpdate();

        ps.close();
        myDatabase.closeConnection();
        return rowsInserted > 1; // If rowsInserted > 0, insertion was successful
    }

    @Override
    public boolean updateUser(User newUser) throws SQLException {
        String query = "UPDATE user SET phone_number = ?, password = ?, email = ?, user_name = ?, " +
                "gender = ?, date_of_birth = ?, country = ?, bio = ?, mode = ?, status = ? " +
                "WHERE user_id = ?";

        myDatabase.startConnection();
        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);

        ps.setString(1, newUser.getPhoneNumber());
        ps.setString(2, newUser.getPassword());
        ps.setString(3, newUser.getEmail());
        ps.setString(4, newUser.getUserName());
        ps.setString(5, newUser.getGender().toString()); // Convert enum to string
        ps.setDate(6, newUser.getDateOfBirth()); // Assuming Date type for date_of_birth
        ps.setString(7, newUser.getCountry());
        ps.setString(8, newUser.getBio());
        ps.setString(9, newUser.getMode().toString()); // Convert enum to string
        ps.setString(10, newUser.getStatus().toString()); // Convert enum to string
        ps.setInt(11, newUser.getUserId()); // Assuming userId is a property in the User class

        int rowsUpdated = ps.executeUpdate();
        ps.close();
        myDatabase.closeConnection();
        return rowsUpdated > 0; // If rowsUpdated > 0, update was successful
    }

}
