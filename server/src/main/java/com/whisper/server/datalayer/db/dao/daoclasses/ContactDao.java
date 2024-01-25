package com.whisper.server.datalayer.db.dao.daoclasses;

import com.whisper.server.datalayer.db.MyDatabase;
import com.whisper.server.datalayer.db.dao.daointerfaces.ContactDaoInterface;
import com.whisper.server.model.Contact.Contact;
import com.whisper.server.model.Contact.contactId;
import com.whisper.server.model.Notification;
import com.whisper.server.model.enums.FriendshipStatus;
import com.whisper.server.model.enums.Mode;

import java.io.Serializable;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class ContactDao implements ContactDaoInterface  {
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
        ps.setInt(1, object.getContactId().getUserId());
        ps.setString(2, "friend"); // Set friendship_status to "friend"
        ps.setDate(3, Date.valueOf(LocalDate.now())); // Set contact_date to the current date
        ps.setInt(4, object.getContactId().getContactId());
        int rowsInserted = ps.executeUpdate();

        ps.close();

        return rowsInserted ;

    }

    @Override
    public List<Contact> getById(int user_id) throws SQLException {
        String query ="SELECT * from contact WHERE user_id = ? ";


        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ps.setInt(1, user_id);



        ResultSet rs = ps.executeQuery();
        List<Contact> contacts = new ArrayList<>();
        while(rs.next()) {
            String status = rs.getString("friendship_status");
            FriendshipStatus friendshipStatus = switch (status) {
                case "pending" -> FriendshipStatus.pending;
                case "notFriend" -> FriendshipStatus.notFriend;
                default -> FriendshipStatus.friend;
            };
            String date = rs.getString("contact_date");
            contactId contactId = new contactId(user_id,rs.getInt("contact_id"));
            Contact contact = new Contact(friendshipStatus, date, contactId);

            contacts.add(contact);

        }
        ps.close();

        return contacts;
    }



    @Override
    public int deleteById(contactId id) throws SQLException {
        String query ="delete FROM contact " +
                "WHERE user_id = ? AND contact_id = ?";

        PreparedStatement ps = myDatabase.getConnection().prepareStatement(query);
        ps.setInt(1, id.getContactId());
        ps.setInt(2,id.getUserId());
        int rowsInserted = ps.executeUpdate();
        ps.close();
        return rowsInserted;
    }

}
