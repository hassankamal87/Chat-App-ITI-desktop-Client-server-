package com.whisper.server.model.Contact;

import java.io.Serializable;

public class contactId implements Serializable {

    private int userId;

    private int contactId;
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

    public contactId(int userId, int contactId) {
        this.userId = userId;
        this.contactId = contactId;
    }
}
