package com.whisper.server.services.db.models;

import com.whisper.server.services.db.models.enums.FriendshipStatus;

public class Contact {
    private FriendshipStatus friendshipStatus;
    private String contactDate;
    private int userId;

    public Contact(FriendshipStatus friendshipStatus, String contactDate, int userId) {
        this.friendshipStatus = friendshipStatus;
        this.contactDate = contactDate;
        this.userId = userId;
    }

    public FriendshipStatus getFriendshipStatus() {
        return friendshipStatus;
    }

    public void setFriendshipStatus(FriendshipStatus friendshipStatus) {
        this.friendshipStatus = friendshipStatus;
    }

    public String getContactDate() {
        return contactDate;
    }

    public void setContactDate(String contactDate) {
        this.contactDate = contactDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
