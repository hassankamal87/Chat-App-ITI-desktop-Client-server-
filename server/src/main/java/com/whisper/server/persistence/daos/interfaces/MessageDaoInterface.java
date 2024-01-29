package com.whisper.server.persistence.daos.interfaces;

import org.example.entities.Message;

import java.sql.SQLException;
import java.util.List;

public interface MessageDaoInterface {

    public int createMessage(Message object) throws SQLException;
    public List<Message> getAllByChatId(int chatId) throws SQLException;

}
