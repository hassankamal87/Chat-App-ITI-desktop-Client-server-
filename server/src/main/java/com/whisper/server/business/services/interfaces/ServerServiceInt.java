package com.whisper.server.business.services.interfaces;

import org.example.entities.User;

import java.util.List;

public interface ServerServiceInt {
    public void startServer();
    public void stopServer();
    public void Announcement(String s);
    public List<User> viewClients();
}
