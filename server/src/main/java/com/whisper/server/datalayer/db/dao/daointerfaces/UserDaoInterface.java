package com.whisper.server.datalayer.db.dao.daointerfaces;

import com.whisper.server.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDaoInterface {
    public int createUser(User object) throws SQLException;
    public User getUserById(int id) throws SQLException;
    public int updateUser(User user) throws SQLException;
    public int deleteById(int id) throws SQLException;
    public List<User> getAll() throws SQLException;

}
