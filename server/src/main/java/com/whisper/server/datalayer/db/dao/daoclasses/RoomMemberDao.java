package com.whisper.server.datalayer.db.dao.daoclasses;

import com.whisper.server.datalayer.db.MyDatabase;
import com.whisper.server.datalayer.db.dao.daointerfaces.RoomMemberDaoInterface;
import com.whisper.server.model.RoomChat;
import com.whisper.server.model.RoomMember;
import com.whisper.server.model.User;
import com.whisper.server.model.enums.Gender;
import com.whisper.server.model.enums.Mode;
import com.whisper.server.model.enums.Status;
import com.whisper.server.model.enums.Type;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoomMemberDao implements RoomMemberDaoInterface {
    private static RoomMemberDaoInterface instance;
    private MyDatabase myDatabase = null;

    private RoomMemberDao(MyDatabase myDatabase){
        this.myDatabase = myDatabase;
    }
    public synchronized static RoomMemberDaoInterface getInstance(MyDatabase myDatabase) {
        if (instance == null) {
            instance = new RoomMemberDao(myDatabase);
        }
        return instance;
    }

    @Override
    public int create(RoomMember object) throws SQLException {
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
    public List<RoomChat> getAllRoomChats(int userId) throws SQLException {
        List<RoomChat> roomChats = new ArrayList<>();
        String query = "select * from \n" +
                "room_chat \n" +
                "where room_chat_id  = (select room_chat_id \n" +
                "from room_chat_user \n" +
                "where user_id = ?);";
        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            int roomChatId = rs.getInt(1);
            String createdDate = rs.getString(2);
            boolean timeStamp = rs.getString(3) == "true";
            String groupName = rs.getString(4);
            Blob photoBlob = rs.getBlob(5);
            int adminId = rs.getInt(6);
            String desc = rs.getString(7);
            Type type = rs.getString(8) == "individual" ? Type.individual : Type.group;

            RoomChat room = new RoomChat(roomChatId,createdDate,timeStamp,groupName,photoBlob,adminId,desc,type);
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
                "where user_id  = (select user_id \n" +
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
                default -> Mode.avalible;
            };
            String statusStr = rs.getString(11);
            Status status = Objects.equals(statusStr, "online") ? Status.online : Status.offline;

            User user = new User(userId, phoneNumber, password, email, userName, gender, date, country, bio, mode, status);

            users.add(user);
        }
        rs.close();
        ps.close();
        return users;
    }
}
