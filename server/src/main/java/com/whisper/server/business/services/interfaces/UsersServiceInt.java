package com.whisper.server.business.services.interfaces;

import org.example.entities.User;

import java.util.List;

public interface UsersServiceInt {
    void updateDataFromDb(List<User> updatedUsers);
}
