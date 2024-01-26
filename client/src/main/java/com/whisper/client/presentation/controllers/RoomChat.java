package com.whisper.client.presentation.controllers;

import javafx.scene.image.Image;

public class RoomChat {
    private int roomId;
    private String date;

    private Image image;

    private String mode;

    private String name;

    public RoomChat(Image image, String mode, String name) {
        this.image = image;
        this.mode = mode;
        this.name = name;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
