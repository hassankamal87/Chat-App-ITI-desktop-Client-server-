package com.whisper.server.model;

public class Message {
    private int messageId;
    private int toChatId;
    private String sentDate;
    private int fromUserId;
    private String body;

    private String attachment;

    public Message(int messageId, int toChatId, String sentDate, int fromUserId, String body, String attachment) {
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

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
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
