package com.whisper.server.model;

import com.whisper.server.model.enums.NotifactionType;

public class Notification {
    private int notificationId;
    
    private int toUserId;
    
    private String fromUserName;
    private NotifactionType type;
    private String body;

    public Notification(int notificationId, int toUserId, String fromUserName, NotifactionType type, String body) {
        this.notificationId = notificationId;
        this.toUserId = toUserId;
        this.fromUserName = fromUserName;
        this.type = type;
        this.body = body;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public NotifactionType getType() {
        return type;
    }

    public void setType(NotifactionType type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
