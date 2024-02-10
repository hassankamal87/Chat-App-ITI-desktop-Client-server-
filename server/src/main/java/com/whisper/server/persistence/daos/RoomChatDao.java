package com.whisper.server.persistence.daos;

import com.whisper.server.persistence.db.MyDatabase;
import com.whisper.server.persistence.daos.interfaces.RoomChatDaoInterface;
import org.example.entities.RoomChat;
import org.example.entities.RoomMember;
import org.example.entities.User;
import org.example.entities.Gender;
import org.example.entities.Mode;
import org.example.entities.Status;
import org.example.entities.Type;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoomChatDao implements RoomChatDaoInterface {

    private MyDatabase myDatabase = null;
    private static RoomChatDao instance = null;

    private RoomChatDao(MyDatabase myDatabase) {
        this.myDatabase = myDatabase;
    }
    public synchronized static RoomChatDao getInstance(MyDatabase myDatabase) {
        if (instance == null) {
            instance = new RoomChatDao(myDatabase);
        }
        return instance;
    }
    @Override
    public int createRoomChat (RoomChat roomChat) throws SQLException {
        LocalDate date = LocalDate.now();
        String query = "Insert into room_chat (created_date, time_stamp, group_name, " +
                "photo, admin_id, description, type) " +
                "values (?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement ps = myDatabase.getConnection().prepareStatement(query,Statement.RETURN_GENERATED_KEYS);){
            ps.setString(1, String.valueOf(date));
            ps.setString(2, roomChat.getTimeStamp() ? "true" : "false");
            ps.setString(3, roomChat.getGroupName());
            ByteArrayInputStream profilePhoto = new ByteArrayInputStream(roomChat.getPhotoBlob());
            ps.setBlob(4, profilePhoto);
            ps.setObject(5, roomChat.getAdminId());
            ps.setString(6, roomChat.getDescription());
            ps.setString(7, roomChat.getType().name());

            int rowsAffected =  ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();

                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        return -1;
    }

    @Override
    public int deleteRoomChat(int roomChatId) throws SQLException {
        String query = "Delete from room_chat where room_chat_id = ?";
        try(PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)){
            ps.setInt(1, roomChatId);
            return ps.executeUpdate();
        }
    }

    @Override
    public RoomChat getRoomChat(int roomChatId) throws SQLException {
        RoomChat roomChat = null;
        String query = "Select * from room_chat where room_chat_id = ?";
        try(PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)) {
            ps.setInt(1, roomChatId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                String roomChatType = rs.getString("type");
                Type type = Type.valueOf(roomChatType);
                Blob profilePhotoBlob = rs.getBlob("photo");
                byte[] profilePhotoBytes =null;
                if (profilePhotoBlob != null) {
                    int blobLength = (int) profilePhotoBlob.length();
                    profilePhotoBytes = profilePhotoBlob.getBytes(1, blobLength);
                }
                roomChat = new RoomChat(
                        rs.getInt("room_chat_id"),
                        rs.getDate("created_date"),
                        rs.getString("time_stamp") == "true",
                        rs.getString("group_name"),
                        profilePhotoBytes,
                        rs.getInt("admin_id"),
                        rs.getString("description"),
                        Objects.equals(rs.getString("type"), "individual") ? Type.individual : Type.group
                );
            }
            rs.close();
            return roomChat;
        }
    }

    @Override
    public List<RoomChat> getAllRoomChats(int userId) throws SQLException {
        List<RoomChat> roomChats = new ArrayList<>();
        String query = "select * from \n" +
                "room_chat \n" +
                "where room_chat_id  in (select room_chat_id \n" +
                "from room_chat_user \n" +
                "where user_id = ?);";
        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            int roomChatId = rs.getInt(1);
            Date createdDate = rs.getDate(2);
            boolean timeStamp = rs.getString(3) == "true";
            String groupName = rs.getString(4);

            Blob profilePhotoBlob = rs.getBlob(5);
            byte[] profilePhotoBytes =null;
            if (profilePhotoBlob != null) {
                int blobLength = (int) profilePhotoBlob.length();
                profilePhotoBytes = profilePhotoBlob.getBytes(1, blobLength);
            }


            int adminId = rs.getInt(6);
            String desc = rs.getString(7);
            Type type = rs.getString(8) == "individual" ? Type.individual : Type.group;

            RoomChat room = new RoomChat(roomChatId,createdDate,timeStamp,groupName,profilePhotoBytes,adminId,desc,type);
            roomChats.add(room);
        }
        rs.close();
        ps.close();
        return roomChats;
    }

    @Override
    public List<User> getAllUsers(int roomChatId) throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "select * from \n" +
                "user \n" +
                "where user_id in (select user_id \n" +
                "from room_chat_user \n" +
                "where room_chat_id = ?);";
        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ps.setInt(1, roomChatId);

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
                default -> Mode.available;
            };
            String statusStr = rs.getString(11);
            Status status = Objects.equals(statusStr, "online") ? Status.online : Status.offline;
            Blob profilePhotoBlob = rs.getBlob("profile_photo");
            byte[] profilePhotoBytes=null;
            if (profilePhotoBlob != null) {
                int blobLength = (int) profilePhotoBlob.length();
                profilePhotoBytes = profilePhotoBlob.getBytes(1, blobLength);
            }

            User user = new User(userId, phoneNumber, password, email, userName, gender, date, country, bio, mode, status,profilePhotoBytes);

            users.add(user);
        }
        rs.close();
        ps.close();
        return users;
    }

    @Override
    public int addRoomMember(RoomMember object) throws SQLException {
        String query = "INSERT INTO room_chat_user (room_chat_id,user_id) " +
                "VALUES (?,?)";
        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ps.setInt(1, object.getRoomChatId());
        ps.setInt(2, object.getUserId());

        int rowsInserted = ps.executeUpdate();
        ps.close();
        return rowsInserted;
    }

    @Override
    public int removeRoomMember(int roomId, int memberId) throws SQLException {
        String query = "DELETE FROM room_chat_user WHERE room_chat_id = ? AND user_id = ?";
        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ps.setInt(1, roomId);
        ps.setInt(2, memberId);

        int rowsDeleted = ps.executeUpdate();
        ps.close();
        return rowsDeleted;
    }


    //get individual room chat
    @Override
    public int getRoomChatForUsers(int user1Id, int user2Id) throws SQLException {
        String query = "SELECT rcu1.room_chat_id\n" +
                "FROM room_chat_user rcu1\n" +
                "JOIN room_chat_user rcu2 ON rcu1.room_chat_id = rcu2.room_chat_id\n" +
                "JOIN room_chat rc ON rcu1.room_chat_id = rc.room_chat_id\n" +
                "WHERE rcu1.user_id = ?" +
                "  AND rcu2.user_id = ?" +
                "  AND rc.type <> 'group';";
        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ps.setInt(1,user1Id);
        ps.setInt(2,user2Id);

        ResultSet rs = ps.executeQuery();

        int roomChatId = 0;
        if(rs.next()){
            roomChatId = rs.getInt(1);
        }

        rs.close();
        ps.close();
        return roomChatId;
    }

    @Override
    public List<User> getGroupMembers(int roomChatId) throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "select * from \n" +
                "room_chat \n" +
                "where room_chat_id  in (select room_chat_id \n" +
                "from room_chat_user \n" +
                "where user_id = ?);";
        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ps.setInt(1, roomChatId);

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
                default -> Mode.available;
            };
            String statusStr = rs.getString(11);
            Status status = Objects.equals(statusStr, "online") ? Status.online : Status.offline;
            Blob profilePhotoBlob = rs.getBlob("profile_photo");
            byte[] profilePhotoBytes=null;
            if (profilePhotoBlob != null) {
                int blobLength = (int) profilePhotoBlob.length();
                profilePhotoBytes = profilePhotoBlob.getBytes(1, blobLength);
            }

            User user = new User(userId, phoneNumber, password, email, userName, gender, date, country, bio, mode, status,profilePhotoBytes);

            users.add(user);
        }
        rs.close();
        ps.close();
        return users;
    }

    @Override
    public int updateRoomChat(RoomChat roomChat) throws SQLException{
        String query = "Update room_chat set group_name = ?, photo=?, description= ? " +
                "where room_chat_id = ?";
        try(PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)){
            ps.setString(1, roomChat.getGroupName());
            ByteArrayInputStream profilePhoto = new ByteArrayInputStream(roomChat.getPhotoBlob());
            ps.setBlob(2, profilePhoto);
            ps.setString(3, roomChat.getDescription());
            ps.setInt(4, roomChat.getRoomChatId());
            return ps.executeUpdate();
        }
    }
}
