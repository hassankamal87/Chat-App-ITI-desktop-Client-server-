package com.whisper.server.services.db.models;

import javafx.scene.image.Image;

import java.util.List;

public class RoomChat {
    private int roomChatId;
    private String createdDate;
    private String timeStamp; // mktoba fel database Enum !!
    private String groupName;
    private Image photo;
    private int adminId;
    private String description;
    private List<RoomMember> roomMembers;


    public RoomChat(int roomChatId, String createdDate, String timeStamp, String groupName, Image photo, int adminId, String description) {
        this.roomChatId = roomChatId;
        this.createdDate = createdDate;
        this.timeStamp = timeStamp;
        this.groupName = groupName;
        this.photo = photo;
        this.adminId = adminId;
        this.description = description;
    }

    public List<RoomMember> getRoomMembers() {
        return roomMembers;
    }

    public void setRoomMembers(List<RoomMember> roomMembers) {
        this.roomMembers = roomMembers;
    }

    public int getRoomChatId() {
        return roomChatId;
    }

    public void setRoomChatId(int roomChatId) {
        this.roomChatId = roomChatId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
