package com.whisper.server.datalayer.db.dao.daointerfaces;

import com.whisper.server.model.RoomChat;
import com.whisper.server.model.RoomMember;
import com.whisper.server.model.User;

import java.sql.SQLException;
import java.util.List;

public interface RoomChatDaoInterface {
    int createRoomChat (RoomChat roomChat) throws SQLException;
    int deleteRoomChat(int roomChatId) throws SQLException;
    RoomChat getRoomChat(int roomChatId) throws SQLException;
    public List<RoomChat> getAllRoomChats(int userId) throws SQLException;
    public List<User> getAllUsers(int roomChatId) throws SQLException;
    public int addRoomMember(RoomMember object) throws SQLException;
}
