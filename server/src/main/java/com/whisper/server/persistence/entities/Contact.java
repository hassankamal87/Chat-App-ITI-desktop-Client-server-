package com.whisper.server.persistence.entities;

import java.sql.Date;

public class Contact {
    private FriendshipStatus friendshipStatus;
    private Date contactDate;
    private int userId;
    private int contactId;

    public Contact(FriendshipStatus friendshipStatus, Date contactDate,int userId, int contactId) {
        this.friendshipStatus = friendshipStatus;
        this.contactDate = contactDate;
        this.userId = userId;
       this.contactId=contactId;

    }

    public FriendshipStatus getFriendshipStatus() {
        return friendshipStatus;
    }

    public void setFriendshipStatus(FriendshipStatus friendshipStatus) {
        this.friendshipStatus = friendshipStatus;
    }

    public Date getContactDate() {
        return contactDate;
    }

    public void setContactDate(Date contactDate) {
        this.contactDate = contactDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
