package com.whisper.client.presentation.controllers;

import org.example.entities.Message;

import java.io.File;

public interface ReceiveMessageInterface {
    public void receiveMessageFromList(Message message);

    void receiveFileFromList(Message message, File file);
}
