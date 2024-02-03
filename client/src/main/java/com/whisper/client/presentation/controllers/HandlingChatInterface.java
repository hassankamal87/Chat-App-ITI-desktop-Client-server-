package com.whisper.client.presentation.controllers;

import org.example.entities.RoomChat;

public interface HandlingChatInterface {
    void addRoomChat(int roomChatID);
    void openExistChat(int roomChatID);
}
