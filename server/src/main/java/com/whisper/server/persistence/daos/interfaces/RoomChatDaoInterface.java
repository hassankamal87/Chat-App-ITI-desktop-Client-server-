package com.whisper.server.persistence.daos.interfaces;

import com.whisper.server.persistence.entities.RoomChat;
import com.whisper.server.persistence.entities.RoomMember;
import com.whisper.server.persistence.entities.User;

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
