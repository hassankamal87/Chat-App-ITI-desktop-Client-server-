package com.whisper.server.persistence.daos;

import com.whisper.server.persistence.db.MyDatabase;
import com.whisper.server.persistence.daos.interfaces.MessageDaoInterface;
import org.example.entities.Message;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDao implements MessageDaoInterface {
    private MyDatabase myDatabase = null;
    private static MessageDaoInterface instance = null;

    private MessageDao(MyDatabase myDatabase) {
        this.myDatabase = myDatabase;
    }
    public synchronized static MessageDaoInterface getInstance(MyDatabase myDatabase) {
        if (instance == null) {
            instance = new MessageDao(myDatabase);
        }
        return instance;
    }

    // Creating a message
    @Override
    public int createMessage(Message message) throws SQLException {
        String query = "INSERT INTO messages (to_chat_id, sent_date, from_user, body, attachment) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)) {
            ps.setInt(1, message.getToChatId());
            ps.setDate(2, new Date(System.currentTimeMillis()));
            ps.setInt(3, message.getFromUserId());
            ps.setString(4, message.getBody());
            ps.setString(5, message.getAttachment());

            return ps.executeUpdate();
        }
    }

    @Override
    public List<Message> getAllByChatId(int chatId) throws SQLException {
        String query = "SELECT * FROM messages WHERE to_chat_id = ?";
        List<Message> messages = new ArrayList<>();

        try (PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)) {
            ps.setInt(1, chatId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Message message = new Message();
                message.setMessageId(rs.getInt("message_id"));
                message.setToChatId(rs.getInt("to_chat_id"));
                message.setSentDate(rs.getDate("sent_date"));
                message.setFromUserId(rs.getInt("from_user"));
                message.setBody(rs.getString("body"));
                message.setAttachment(rs.getString("attachment"));
                messages.add(message);
            }
        }

        return messages;
    }
}
