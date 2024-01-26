package com.whisper.server.model.repo.repoclasses;

import com.whisper.server.datalayer.db.dao.DaoInterface;
import com.whisper.server.datalayer.db.dao.daointerfaces.UserDaoInterface;
import com.whisper.server.model.User;
import com.whisper.server.model.repo.repointerfaces.UserRepoInterface;

import java.sql.SQLException;
import java.util.List;

public class UserRepo implements UserRepoInterface {

    private static UserRepoInterface instance = null;
    private UserDaoInterface userDao = null;

    private UserRepo(UserDaoInterface dao){
        this.userDao = dao;
    }
    public static synchronized UserRepoInterface getInstance(UserDaoInterface dao){
        if (instance == null)
            instance = new UserRepo(dao);
        return instance;
    }

    @Override
    public int create(User user) throws SQLException {
        return userDao.createUser(user);
    }

    @Override
    public User getById(int id) throws SQLException {
        return userDao.getUserById(id);
    }

    @Override
    public int update(User user) throws SQLException {
        return userDao.updateUser(user);
    }

    @Override
    public int deleteById(int id) throws SQLException {
        return userDao.deleteById(id);
    }

    @Override
    public List<User> getAll() throws SQLException {
        return userDao.getAll();
    }
}
