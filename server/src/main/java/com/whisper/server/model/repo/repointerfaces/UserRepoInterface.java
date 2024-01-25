package com.whisper.server.model.repo.repointerfaces;

import com.whisper.server.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepoInterface {
    int create (User user) throws SQLException;
    User getById (int id) throws SQLException;
    int update (User user) throws SQLException;
    int deleteById (int id) throws SQLException;
    List<User> getAll() throws SQLException;
}
