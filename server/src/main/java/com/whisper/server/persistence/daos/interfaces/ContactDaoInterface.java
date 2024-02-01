package com.whisper.server.persistence.daos.interfaces;

import org.example.entities.Contact;

import java.sql.SQLException;
import java.util.List;

public interface ContactDaoInterface {
    int create (Contact object) throws SQLException;
    List<Contact> getById (int id) throws SQLException;
    public int deleteById(int userId, int contactId) throws SQLException;
    public boolean isContact(int userId, int contactId) throws SQLException;
    public  void blockContact(int userId, int contactId) throws SQLException;
    public  void unblockContact(int userId, int contactId) throws SQLException;
}
