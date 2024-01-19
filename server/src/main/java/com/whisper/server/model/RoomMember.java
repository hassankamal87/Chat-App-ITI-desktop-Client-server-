package com.whisper.server.model;

public class RoomMember {
    private int roomChatId;
    private int userId;

    public RoomMember(int roomChatId, int userId) {
        this.roomChatId = roomChatId;
        this.userId = userId;
    }

    public int getRoomChatId() {
        return roomChatId;
    }

    public void setRoomChatId(int roomChatId) {
        this.roomChatId = roomChatId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
