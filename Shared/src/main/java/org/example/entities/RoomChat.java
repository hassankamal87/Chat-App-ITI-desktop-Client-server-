package org.example.entities;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;
import java.util.List;

public class RoomChat implements Serializable {
    private int roomChatId;
    private Date createdDate;
    private boolean timeStamp;
    private String groupName;
    private Blob photoBlob;
    private int adminId;
    private String description;
    private List<RoomMember> roomMembers;
    private Type type;


    public RoomChat(int roomChatId, Date createdDate, boolean timeStamp, String groupName, Blob photo, int adminId, String description, Type type) {
        this.roomChatId = roomChatId;
        this.createdDate = createdDate;
        this.timeStamp = timeStamp;
        this.groupName = groupName;
        this.photoBlob = photo;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(boolean timeStamp) {
        this.timeStamp = timeStamp;
    }
    public boolean isTimeStamp() {
        return timeStamp;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public Blob getPhotoBlob() {
        return photoBlob;
    }

    public void setPhotoBlob(Blob photoBlob) {
        this.photoBlob = photoBlob;
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
