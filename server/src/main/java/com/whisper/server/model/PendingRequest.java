package com.whisper.server.model;

public class PendingRequest {
    private int toUserId;
    private int fromUserId;

    public PendingRequest(int toUserId, int fromUserId, String sentDate, String body) {
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
        this.sentDate = sentDate;
        this.body = body;
    }

    private String sentDate;
    private String body;

    public int getToUserId() {
        return toUserId;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
