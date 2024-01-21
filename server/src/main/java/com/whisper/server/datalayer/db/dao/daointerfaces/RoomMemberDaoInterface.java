package com.whisper.server.datalayer.db.dao.daointerfaces;

import com.whisper.server.model.RoomChat;
import com.whisper.server.model.RoomMember;
import com.whisper.server.model.User;

import java.sql.SQLException;
import java.util.List;

public interface RoomMemberDaoInterface {
    int create(RoomMember object) throws SQLException;
    List<RoomChat> getAllRoomChats(int userId) throws SQLException;
    public List<User> getAllUsers(int roomChatId) throws SQLException;
}
