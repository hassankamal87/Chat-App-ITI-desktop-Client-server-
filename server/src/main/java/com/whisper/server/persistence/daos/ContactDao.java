package com.whisper.server.persistence.daos;

import com.whisper.server.persistence.db.MyDatabase;
import com.whisper.server.persistence.daos.interfaces.ContactDaoInterface;
import org.example.entities.Contact;
import org.example.entities.FriendshipStatus;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class ContactDao implements ContactDaoInterface {
    private MyDatabase myDatabase = null;
    private static ContactDaoInterface instance = null;

    private ContactDao(MyDatabase myDatabase) {
        this.myDatabase = myDatabase;
    }
    public synchronized static ContactDaoInterface getInstance(MyDatabase myDatabase) {
        if (instance == null) {
            instance = new ContactDao(myDatabase);
        }
        return instance;
    }
    @Override
    public int create(Contact object) throws SQLException {
        String query = "INSERT INTO contact " +
                "(contact_id, friendship_status," +
                " contact_date, user_id)" +
                " VALUES (?, ?, ?, ?)";


        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ps.setInt(1, object.getUserId());
        ps.setString(2, "friend"); // Set friendship_status to "friend"
        ps.setDate(3, Date.valueOf(LocalDate.now())); // Set contact_date to the current date
        ps.setInt(4, object.getContactId());
        int rowsInserted = ps.executeUpdate();

        ps.close();

         query = "INSERT INTO contact " +
                "(contact_id, friendship_status," +
                " contact_date, user_id)" +
                " VALUES (?, ?, ?, ?)";


         ps = myDatabase.getConnection().prepareStatement(query);
        ps.setInt(1, object.getContactId());
        ps.setString(2, "friend"); // Set friendship_status to "friend"
        ps.setDate(3, Date.valueOf(LocalDate.now())); // Set contact_date to the current date
        ps.setInt(4, object.getUserId());
        rowsInserted += ps.executeUpdate();

        ps.close();

        return rowsInserted ;

    }

    @Override
    public List<Contact> getById(int user_id) throws SQLException {
        String query ="SELECT * from contact WHERE user_id = ? AND friendship_status = 'friend' ";


        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ps.setInt(1, user_id);



        ResultSet rs = ps.executeQuery();
        System.out.println("here");
        List<Contact> contacts = new ArrayList<>();
        while(rs.next()) {
            String status = rs.getString("friendship_status");
            FriendshipStatus friendshipStatus = switch (status) {
                case "pending" -> FriendshipStatus.pending;
                case "notFriend" -> FriendshipStatus.notFriend;
                default -> FriendshipStatus.friend;
            };
            Date date = rs.getDate("contact_date");
            Contact contact = new Contact(friendshipStatus, date, user_id, rs.getInt("contact_id"));

            contacts.add(contact);

        }
        ps.close();


        return contacts;
    }



    @Override
    public int deleteById(int userId, int contactId) throws SQLException {
        String query ="delete FROM contact " +
                "WHERE user_id = ? AND contact_id = ?";

        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ps.setInt(1, userId);
        ps.setInt(2, contactId);
        int rowsInserted = ps.executeUpdate();
        ps.close();
        return rowsInserted;
    }

    @Override
    public boolean isContact(int userId, int contactId) throws SQLException {
        String query ="SELECT * from contact WHERE user_id = ? AND contact_id = ? AND friendship_status = 'friend' ";
        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ps.setInt(1, userId);
        ps.setInt(2, contactId);
        ResultSet rs = ps.executeQuery();
        boolean result = rs.next();
        ps.close();
        return result;
    }

    @Override
    public void blockContact(int userId, int contactId) throws SQLException {
        String query ="UPDATE contact SET friendship_status = 'blocked' " +
                "WHERE user_id = ? AND contact_id = ?";
        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ps.setInt(1, userId);
        ps.setInt(2, contactId);
        ps.executeUpdate();
        ps.setInt(1, contactId);
        ps.setInt(2, userId);
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public void unblockContact(int userId, int contactId) throws SQLException {
        String query ="UPDATE contact SET friendship_status = 'friend' " +
                "WHERE user_id = ? AND contact_id = ?";
        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ps.setInt(1, userId);
        ps.setInt(2, contactId);
        ps.executeUpdate();
        ps.setInt(1, contactId);
        ps.setInt(2, userId);
        ps.executeUpdate();
        ps.close();
    }

}
