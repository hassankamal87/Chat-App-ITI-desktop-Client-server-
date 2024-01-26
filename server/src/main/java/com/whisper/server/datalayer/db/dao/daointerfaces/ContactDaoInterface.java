package com.whisper.server.datalayer.db.dao.daointerfaces;

import com.whisper.server.model.Contact.Contact;

import java.sql.SQLException;
import java.util.List;

public interface ContactDaoInterface {
    int create (Contact object) throws SQLException;
    List<Contact> getById (int id) throws SQLException;
    public int deleteById(int userId, int contactId) throws SQLException;
}
