package com.whisper.server.datalayer.db.dao.daointerfaces;

import com.whisper.server.model.Message;

import java.sql.SQLException;
import java.util.List;

public interface MessageDaoInterface {

    public int createMessage(Message object) throws SQLException;
    public List<Message> getAllByChatId(int chatId) throws SQLException;

}
