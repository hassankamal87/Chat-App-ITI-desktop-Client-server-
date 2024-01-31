package com.whisper.server.persistence.daos;

import com.whisper.server.persistence.db.MyDatabase;
import com.whisper.server.persistence.daos.interfaces.UserDaoInterface;
import org.example.entities.User;
import org.example.entities.Gender;
import org.example.entities.Mode;
import org.example.entities.Status;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

    // Creating a user
    public int createUser(User object) throws SQLException {
        String query = "INSERT INTO user (phone_number, password, email, user_name" +
                ", gender, date_of_birth, country, bio, mode, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)) {
            ps.setString(1, object.getPhoneNumber());
            ps.setString(2, object.getPassword());
            ps.setString(3, object.getEmail());
            ps.setString(4, object.getUserName());
            ps.setString(5, object.getGender().toString());
            ps.setDate(6, object.getDateOfBirth());
            ps.setString(7, object.getCountry());
            ps.setString(8, object.getBio());
            ps.setString(9, object.getMode().toString());
            ps.setString(10, object.getStatus().toString());

            return ps.executeUpdate();
        }
    }

    // Getting a user by id
    public User getUserById(int id) throws SQLException {
        String query = "SELECT * FROM user WHERE user_id = ?";
        User user = null;

        try (PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setUserName(rs.getString("user_name"));
                user.setGender(Gender.valueOf(rs.getString("gender")));
                user.setDateOfBirth(rs.getDate("date_of_birth"));
                user.setCountry(rs.getString("country"));
                user.setBio(rs.getString("bio"));
                user.setMode(Mode.valueOf(rs.getString("mode")));
                user.setStatus(Status.valueOf(rs.getString("status")));
            }
        }
        return user;
    }


    // Updating a user using the user object
    public int updateUser(User user) throws SQLException {
        String query = "UPDATE user SET phone_number = ?, password = ?, email = ?," +
                " user_name = ?, gender = ?, date_of_birth = ?, country = ?, bio = ?," +
                " mode = ?, status = ? WHERE user_id = ?";
        try (PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)) {
            ps.setString(1, user.getPhoneNumber());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getUserName());
            ps.setString(5, user.getGender().toString());
            ps.setDate(6, user.getDateOfBirth());
            ps.setString(7, user.getCountry());
            ps.setString(8, user.getBio());
            ps.setString(9, user.getMode().toString());
            ps.setString(10, user.getStatus().toString());
            ps.setInt(11, user.getUserId());

            return ps.executeUpdate();
        }
    }

    // Deleting a user by id
    public int deleteById(int id) throws SQLException {
        String query = "DELETE FROM user WHERE user_id = ?";

        try (PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }

    // Getting all users
    public List<User> getAll() throws SQLException {
        String query = "SELECT * FROM user";
        List<User> users = new ArrayList<>();

        try (PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setUserName(rs.getString("user_name"));
                user.setGender(Gender.valueOf(rs.getString("gender")));
                user.setDateOfBirth(rs.getDate("date_of_birth"));
                user.setCountry(rs.getString("country"));
                user.setBio(rs.getString("bio"));
                user.setMode(Mode.valueOf(rs.getString("mode")));
                user.setStatus(Status.valueOf(rs.getString("status")));
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public int getMaleUsersCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM user WHERE gender = ?";
        try (PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)) {
            ps.setString(1, Gender.male.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    @Override
    public int getFemaleUsersCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM user WHERE gender = ?";
        try (PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)) {
            ps.setString(1, Gender.female.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    @Override
    public List<Map<String, Number>> getTopCountries() throws SQLException {
        String query = "SELECT country, COUNT(*) AS user_count FROM user GROUP BY country ORDER BY user_count DESC";
        List<Map<String, Number>> countries = new ArrayList<>();

        try (PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Number> country = new HashMap<>();
                country.put(rs.getString("country"), rs.getInt("user_count"));
                countries.add(country);
            }
        }
        return countries;
    }

    @Override
    public int getOnlineUsersCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM user WHERE status = ?";
        try (PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)) {
            ps.setString(1, Status.online.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    @Override
    public int getOfflineUsersCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM user WHERE status = ?";
        try (PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)) {
            ps.setString(1, Status.offline.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}
