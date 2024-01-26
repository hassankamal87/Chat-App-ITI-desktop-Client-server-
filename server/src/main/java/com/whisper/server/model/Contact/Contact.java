package com.whisper.server.model.Contact;

import com.whisper.server.model.enums.FriendshipStatus;

public class Contact {
    private FriendshipStatus friendshipStatus;
    private String contactDate;
    private contactId contactId;

    public contactId getContactId() {
        return contactId;
    }

    public void setContactId(contactId contactId) {
        this.contactId = contactId;
    }

    public Contact(FriendshipStatus friendshipStatus, String contactDate, contactId contactId) {
        this.friendshipStatus = friendshipStatus;
        this.contactDate = contactDate;
       this.contactId=contactId;

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


}
