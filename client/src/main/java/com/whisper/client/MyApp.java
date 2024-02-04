package com.whisper.client;

import org.example.entities.User;

public class MyApp {

    private static MyApp instance = null;
    private User currentUser;
    private MyApp(){}
    public static synchronized MyApp getInstance(){
        if(instance == null){
            instance = new MyApp();
        }
        return instance;
    }
    public User getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}
