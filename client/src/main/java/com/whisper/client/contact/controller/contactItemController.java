package com.whisper.client.contact.controller;
import com.whisper.client.model.contact;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class contactItemController implements Initializable {

    @FXML
    private Label email;

    @FXML
    private ImageView photo;

    @FXML
    private ImageView modeImg;

    @FXML
    private Label mode;

    @FXML
    private Label name;

    @FXML
    private Label phone;

    public void setData(contact contact ){
        try{

            modeImg.setImage( new Image(getClass().getResourceAsStream("/com/whisper/client/images/modePhoto/" +contact.getMode()+".jpg")));

            photo.setImage(new Image(getClass().getResourceAsStream("/com/whisper/client/images/personalPhoto/" +contact.getImgSrc())));


        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }



        try{
            name.setText(contact.getName());
            phone.setText(contact.getPhoneNumber());
            email.setText(contact.getEmail());
            mode.setText(contact.getMode());
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
