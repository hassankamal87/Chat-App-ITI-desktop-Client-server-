package com.whisper.client.business.services;

import java.util.regex.Pattern;

public class SignupValidateService {
    public boolean validName(String name){
        String regex = "^[a-zA-Z ]+$";
        return Pattern.compile(regex)
                .matcher(name)
                .matches();
    }
    public boolean validateEmail(String email){
        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regex)
                .matcher(email)
                .matches();
    }

    public boolean validatePhoneNumber(String phoneNumber){
        String regex = "\\d{11}";
        return Pattern.compile(regex)
                .matcher(phoneNumber)
                .matches();
    }

    public boolean validatePassword(String password){
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
        return Pattern.compile(regex)
                .matcher(password)
                .matches();
    }

    public boolean validateConfirmPassword(String password, String confirmPass){
        return confirmPass.equals(password);
    }

//    public boolean validateCountry(){
//
//    }
}
