package com.whisper.server.persistence.daos.interfaces;

import com.whisper.server.persistence.entities.Contact;

import java.sql.SQLException;
import java.util.List;

public interface ContactDaoInterface {
    int create (Contact object) throws SQLException;
    List<Contact> getById (int id) throws SQLException;
    public int deleteById(int userId, int contactId) throws SQLException;
}
