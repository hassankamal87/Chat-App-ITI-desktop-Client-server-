package com.whisper.server.persistence.daos.interfaces;

import org.example.entities.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface UserDaoInterface {
    public int createUser(User object) throws SQLException;
    public User getUserById(int id) throws SQLException;
    public int updateUser(User user) throws SQLException;
    public int deleteById(int id) throws SQLException;
    public List<User> getAll() throws SQLException;
    public int getIdByPhoneNumber(String phoneNumber) throws SQLException;
    public int getMaleUsersCount() throws SQLException;
    public int getFemaleUsersCount() throws SQLException;
    public Map<String, Integer> getTopCountries() throws SQLException;
    public int getOnlineUsersCount() throws SQLException;
    public int getOfflineUsersCount() throws SQLException;

    boolean isPhoneNumberExists(String phoneNo) throws SQLException;

    boolean isEmailExists(String email) throws SQLException;

    User getByPhoneAndPassword(String phoneNo, String password) throws SQLException;
    //public List<Map<String, Number>> getTopEntries() throws SQLException;
}