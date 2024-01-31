package org.example.entities;

import java.io.Serializable;
import java.sql.Date;


public class User implements Serializable {
    private int userId;
    private String phoneNumber;
    private String password;
    private String email;
    private String userName;
    private Gender gender;
    private Date dateOfBirth;
    private String country;
    private String bio;
    private Mode mode;
    private Status status;
    public User() {}

    public User(int userId, String phoneNumber, String password, String email,
                String userName, Gender gender, Date dateOfBirth, String country,
                String bio, Mode mode, Status status) {
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;
        this.userName = userName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.bio = bio;
        this.mode = mode;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
