package com.whisper.server.datalayer.db.dao.daoclasses;

import com.whisper.server.datalayer.db.MyDatabase;
import com.whisper.server.datalayer.db.dao.Dao;
import com.whisper.server.datalayer.db.dao.DaoInterface;
import com.whisper.server.datalayer.db.dao.daointerfaces.UserDaoInterface;
import com.whisper.server.model.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDao implements UserDaoInterface {
    private MyDatabase myDatabase = null;
    private static UserDaoInterface instance = null;

    private UserDao(MyDatabase myDatabase) {
        this.myDatabase = myDatabase;
    }
    public synchronized static UserDaoInterface getInstance(MyDatabase myDatabase) {
        if (instance == null) {
            instance = new UserDao(myDatabase);
        }
        return instance;
    }
    @Override
    public int create(User object) throws SQLException {
        String query = "INSERT INTO user (phone_number, password, email, user_name, gender, date_of_birth, country, bio, mode, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ps.setString(1, object.getPhoneNumber());
        ps.setString(2, object.getPassword());
        ps.setString(3, object.getEmail());
        ps.setString(4, object.getUserName());
        ps.setString(5, object.getGender().toString()); // Convert enum to string
        ps.setDate(6, object.getDateOfBirth()); // Assuming Date type for date_of_birth
        ps.setString(7, object.getCountry());
        ps.setString(8, object.getBio());
        ps.setString(9, object.getMode().toString()); // Convert enum to string
        ps.setString(10, object.getStatus().toString()); // Convert enum to string

        int rowsInserted = ps.executeUpdate();
        ps.close();
        return rowsInserted;
    }

    @Override
    public User getById(int id) throws SQLException {
        return null;
    }

    @Override
    public int update(User object) throws SQLException {
        return 0;
    }

    @Override
    public int deleteById(int id) throws SQLException {
        return 0;
    }

    @Override
    public List<User> getAll() throws SQLException {
        return null;
    }
}
