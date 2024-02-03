package org.example.entities;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private int messageId;
    private int toChatId;
    private Date sentDate;
    private int fromUserId;
    private String body;

    private String attachment;

    public Message() {}

    public Message(int messageId, int toChatId, Date sentDate, int fromUserId, String body, String attachment) {
        this.messageId = messageId;
        this.toChatId = toChatId;
        this.sentDate = sentDate;
        this.fromUserId = fromUserId;
        this.body = body;
        this.attachment = attachment;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getToChatId() {
        return toChatId;
    }

    public void setToChatId(int toChatId) {
        this.toChatId = toChatId;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
