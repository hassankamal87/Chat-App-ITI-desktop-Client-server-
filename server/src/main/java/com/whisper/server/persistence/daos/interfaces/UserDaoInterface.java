package com.whisper.server.persistence.daos.interfaces;

import com.whisper.server.persistence.entities.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDaoInterface {
    public int createUser(User object) throws SQLException;
    public User getUserById(int id) throws SQLException;
    public int updateUser(User user) throws SQLException;
    public int deleteById(int id) throws SQLException;
    public List<User> getAll() throws SQLException;

}
