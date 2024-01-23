package com.whisper.server.model;

import com.whisper.server.model.enums.Type;
import javafx.scene.image.Image;

import java.sql.Blob;
import java.util.List;

public class RoomChat {
    private int roomChatId;
    private String createdDate;
    private String timeStamp; // mktoba fel database Enum !!
    private String groupName;
    private Blob photo;
    private Integer adminId;
    private String description;
    private List<RoomMember> roomMembers;

    private Type type;


    public RoomChat(String createdDate, String timeStamp, String groupName, Blob photo, Integer adminId, String description, Type type) {
//        this.roomChatId = roomChatId;
        this.createdDate = createdDate;
        this.timeStamp = timeStamp;
        this.groupName = groupName;
        this.photo = photo;
        this.adminId = adminId;
        this.description = description;
        this.type = type;
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

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
