package com.whisper.client.presentation.controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.example.entities.Mode;
import org.example.entities.User;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class contactItemController implements Initializable {

    @FXML
    private Label email;

    @FXML
    private ImageView photo;



    @FXML
    private Label mode;

    @FXML
    private Label name;

    @FXML
    private Label phone;

    @FXML
    private Circle modeIcon;
    public void setData(User contact ){
        try{

            Color color = null;
            if(contact.getMode()==Mode.offline)
                color=Color.DARKRED;
            else if(contact.getMode()==Mode.available)
                color=Color.LIGHTGREEN;
            else if(contact.getMode()==Mode.away)
                color=Color.LIGHTBLUE;
            else
                color=Color.ORANGE;
            modeIcon.setFill(color);
            modeIcon.setEffect(new DropShadow(+25d,0d,+2d, Color.LIGHTBLUE));


            if(contact.getProfilePhoto()!=null) {
             Image profile = new Image(new ByteArrayInputStream(contact.getProfilePhoto()));
                photo.setImage(profile);
            }
            else {
                photo.setImage(new Image(getClass().getResourceAsStream("/com/whisper/client/images/personalPhoto/defaultProfile.jpg")));
            }
            photo.setFitHeight(50);
            photo.setFitWidth(50);

            photo.setPreserveRatio(true);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        try{
            name.setText(contact.getUserName());
            phone.setText(contact.getPhoneNumber());
            email.setText(contact.getEmail());
            mode.setText(contact.getMode().toString());
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
