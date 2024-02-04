package com.whisper.server.persistence.daos;

import com.whisper.server.persistence.db.MyDatabase;
import com.whisper.server.persistence.daos.interfaces.UserDaoInterface;
import org.example.entities.User;
import org.example.entities.Gender;
import org.example.entities.Mode;
import org.example.entities.Status;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
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
                ", gender, date_of_birth, country, bio, mode, status,profile_photo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
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
            ByteArrayInputStream profilePhoto = new ByteArrayInputStream(object.getProfilePhoto());
            ps.setBlob(11, profilePhoto);

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

                user.setUserId(rs.getInt("user_id"));
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
                Blob profilePhotoBlob = rs.getBlob("profile_photo");
                if (profilePhotoBlob != null) {
                    int blobLength = (int) profilePhotoBlob.length();
                    byte[] profilePhotoBytes = profilePhotoBlob.getBytes(1, blobLength);
                    user.setProfilePhoto(profilePhotoBytes);
                } else {
                    user.setProfilePhoto(null);
                }
            }
        }
        return user;
    }


    // Updating a user using the user object
    public int updateUser(User user) throws SQLException {
        String query = "UPDATE user SET phone_number = ?, password = ?, email = ?," +
                " user_name = ?, gender = ?, date_of_birth = ?, country = ?, bio = ?," +
                " mode = ?, status = ? ,profile_photo = ? WHERE user_id = ?";
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
            ByteArrayInputStream profilePhoto = new ByteArrayInputStream(user.getProfilePhoto());
            ps.setBlob(11, profilePhoto);
            ps.setInt(12, user.getUserId());

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
                Blob profilePhotoBlob = rs.getBlob("profile_photo");
                if (profilePhotoBlob != null) {
                    int blobLength = (int) profilePhotoBlob.length();
                    byte[] profilePhotoBytes = profilePhotoBlob.getBytes(1, blobLength);
                    user.setProfilePhoto(profilePhotoBytes);
                } else {
                    user.setProfilePhoto(null);
                }
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public int getIdByPhoneNumber(String phoneNumber) throws SQLException {
        String query = "SELECT user_id FROM user WHERE phone_number = ?";
        try (PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)) {
            ps.setString(1, phoneNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
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
    public Map<String, Integer> getTopCountries() throws SQLException {
        String query = "SELECT country, COUNT(*) " +
                "AS user_count FROM user GROUP BY country ORDER BY user_count DESC LIMIT 10";
        Map<String, Integer> countries = new HashMap<>();

        try (PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String countryName = rs.getString("country");
                int userCount = rs.getInt("user_count");
                countries.put(countryName, userCount);
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

    @Override
    public boolean isPhoneNumberExists(String phoneNo) throws SQLException {
        String query = "Select * from user where phone_number = ?";
        try(PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)){
            ps.setString(1, phoneNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEmailExists(String email) throws SQLException{
        String query = "Select * from user where email = ?";
        try(PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)){
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
        }
        return false;
    }

//    @Override
//    public boolean getByPhoneAndPassword(String phoneNo, String password) throws SQLException {
//        String query = "Select * from user where phone_number = ? and password = ?";
//        try(PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)){
//            ps.setString(1, phoneNo);
//            ps.setString(2, password);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()){
//                return true;
//            }
//        }
//        return false;
//    }

    public User getByPhoneAndPassword(String phoneNo, String password) throws SQLException {
        User user = null;
        String query = "Select * from user where phone_number = ? and password = ?";
        try(PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)){
            ps.setString(1, phoneNo);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
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
                Blob profilePhotoBlob = rs.getBlob("profile_photo");
                if (profilePhotoBlob != null) {
                    int blobLength = (int) profilePhotoBlob.length();
                    byte[] profilePhotoBytes = profilePhotoBlob.getBytes(1, blobLength);
                    user.setProfilePhoto(profilePhotoBytes);
                } else {
                    user.setProfilePhoto(null);
                }
                return user;
            }
        }
        return null;
    }

    @Override
    public String getPasswordByPhoneNumber(String phoneNo) throws SQLException {
        String query = "Select password from user where phone_number = ?";
        try(PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)){
            ps.setString(1, phoneNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }
        }
        return null;
    }
}