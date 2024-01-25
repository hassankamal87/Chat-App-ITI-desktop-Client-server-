package com.whisper.server.datalayer.db.dao.daoclasses;

import com.whisper.server.datalayer.db.MyDatabase;
import com.whisper.server.datalayer.db.dao.daointerfaces.RoomChatDaoInterface;
import com.whisper.server.model.RoomChat;
import com.whisper.server.model.enums.Type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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
        try(PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)){
            ps.setString(1, String.valueOf(date));
            ps.setString(2, roomChat.getTimeStamp());
            ps.setString(3, roomChat.getGroupName());
            ps.setBlob(4, roomChat.getPhoto());
            ps.setObject(5, roomChat.getAdminId());
            ps.setString(6, roomChat.getDescription());
            ps.setString(7, roomChat.getType().name());
            return ps.executeUpdate();
        }
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
                roomChat = new RoomChat(
                        rs.getString("room_chat_id"),
                        rs.getString("time_stamp"),
                        rs.getString("group_name"),
                        rs.getBlob("photo"),
                        rs.getInt("admin_id"),
                        rs.getString("description"),
                        type
                );
            }
            rs.close();
            return roomChat;
        }
    }
}
