package com.whisper.server.persistence.daos.interfaces;

import org.example.entities.RoomChat;
import org.example.entities.RoomMember;
import org.example.entities.User;

import java.sql.SQLException;
import java.util.List;

public interface RoomChatDaoInterface {
    int createRoomChat (RoomChat roomChat) throws SQLException;
    int deleteRoomChat(int roomChatId) throws SQLException;
    RoomChat getRoomChat(int roomChatId) throws SQLException;
    public List<RoomChat> getAllRoomChats(int userId) throws SQLException;
    public List<User> getAllUsers(int roomChatId) throws SQLException;
    public int addRoomMember(RoomMember object) throws SQLException;

    int removeRoomMember(int roomId, int memberId) throws SQLException;

    int getRoomChatForUsers(int user1Id, int user2Id) throws SQLException;

    List<User> getGroupMembers(int roomChatId) throws SQLException;
}
