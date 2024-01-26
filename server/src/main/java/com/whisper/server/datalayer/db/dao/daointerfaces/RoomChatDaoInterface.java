package com.whisper.server.datalayer.db.dao.daointerfaces;

import com.whisper.server.model.RoomChat;

import java.sql.SQLException;
import java.util.List;

public interface RoomChatDaoInterface {
    int createRoomChat (RoomChat roomChat) throws SQLException;
    int deleteRoomChat(int roomChatId) throws SQLException;
    RoomChat getRoomChat(int roomChatId) throws SQLException;
}
