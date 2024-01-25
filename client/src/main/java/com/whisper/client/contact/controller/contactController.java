package com.whisper.client.contact.controller;

import com.whisper.client.HelloApplication;
import com.whisper.client.model.contact;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class contactController implements Initializable
{
    @javafx.fxml.FXML
    private VBox contactsLayout;

    @javafx.fxml.FXML
    private ScrollPane scroll ;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        List<contact> contacts = new ArrayList<>(contacts());
        for(int i=0;i<contacts.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("contact/view/contactItemView.fxml"));

            try{
                HBox hBox=fxmlLoader.load();

                contactItemController cic = fxmlLoader.getController();
                cic.setData(contacts.get(i));
                contactsLayout.getChildren().add(hBox);

            }catch (IOException e){
                e.printStackTrace();
            }


        }

    }

    private List<contact> contacts(){

        List<contact> ls = new ArrayList<>();
        contact contact = new contact();
        contact.setName("Menna Mansour");
        contact.setImgSrc("img.jpg");
        contact.setPhoneNumber("+369.xxx.xxx.xxx");
        contact.setEmail("abc.aeg@gmail.com");
        contact.setMode("Online");
        ls.add(contact);

        contact.setName("Menna Mansour");
        contact.setImgSrc("img.jpg");
        contact.setPhoneNumber("+369.xxx.xxx.xxx");
        contact.setEmail("abc.aeg@gmail.com");
        contact.setMode("Online");
        ls.add(contact);

        contact.setName("Menna Mansour");
        contact.setImgSrc("img.jpg");
        contact.setPhoneNumber("+369.xxx.xxx.xxx");
        contact.setEmail("abc.aeg@gmail.com");
        contact.setMode("Online");
        ls.add(contact);

        contact.setName("Menna Mansour");
        contact.setImgSrc("img.jpg");
        contact.setPhoneNumber("+369.xxx.xxx.xxx");
        contact.setEmail("abc.aeg@gmail.com");
        contact.setMode("Online");
        ls.add(contact);

        contact.setName("Menna Mansour");
        contact.setImgSrc("img.jpg");
        contact.setPhoneNumber("+369.xxx.xxx.xxx");
        contact.setEmail("abc.aeg@gmail.com");
        contact.setMode("Online");
        ls.add(contact);

        contact.setName("Menna Mansour");
        contact.setImgSrc("img.jpg");
        contact.setPhoneNumber("+369.xxx.xxx.xxx");
        contact.setEmail("abc.aeg@gmail.com");
        contact.setMode("Online");
        ls.add(contact);
        contact.setName("Menna Mansour");
        contact.setImgSrc("img.jpg");
        contact.setPhoneNumber("+369.xxx.xxx.xxx");
        contact.setEmail("abc.aeg@gmail.com");
        contact.setMode("Online");
        ls.add(contact);

        contact.setName("Menna Mansour");
        contact.setImgSrc("img.jpg");
        contact.setPhoneNumber("+369.xxx.xxx.xxx");
        contact.setEmail("abc.aeg@gmail.com");
        contact.setMode("Online");
        ls.add(contact);

        contact.setName("Menna Mansour");
        contact.setImgSrc("img.jpg");
        contact.setPhoneNumber("+369.xxx.xxx.xxx");
        contact.setEmail("abc.aeg@gmail.com");
        contact.setMode("Online");
        ls.add(contact);
        contact.setName("Menna Mansour");
        contact.setImgSrc("img.jpg");
        contact.setPhoneNumber("+369.xxx.xxx.xxx");
        contact.setEmail("abc.aeg@gmail.com");
        contact.setMode("Online");
        ls.add(contact);

        contact.setName("Menna Mansour");
        contact.setImgSrc("img.jpg");
        contact.setPhoneNumber("+369.xxx.xxx.xxx");
        contact.setEmail("abc.aeg@gmail.com");
        contact.setMode("Online");
        ls.add(contact);

        contact.setName("Menna Mansour");
        contact.setImgSrc("img.jpg");
        contact.setPhoneNumber("+369.xxx.xxx.xxx");
        contact.setEmail("abc.aeg@gmail.com");
        contact.setMode("Online");
        ls.add(contact);
        contact.setName("Menna Mansour");
        contact.setImgSrc("img.jpg");
        contact.setPhoneNumber("+369.xxx.xxx.xxx");
        contact.setEmail("abc.aeg@gmail.com");
        contact.setMode("Online");
        ls.add(contact);

        contact.setName("Menna Mansour");
        contact.setImgSrc("img.jpg");
        contact.setPhoneNumber("+369.xxx.xxx.xxx");
        contact.setEmail("abc.aeg@gmail.com");
        contact.setMode("Online");
        ls.add(contact);

        contact.setName("Menna Mansour");
        contact.setImgSrc("img.jpg");
        contact.setPhoneNumber("+369.xxx.xxx.xxx");
        contact.setEmail("abc.aeg@gmail.com");
        contact.setMode("Online");
        ls.add(contact);

        contact.setName("Menna Mansour");
        contact.setImgSrc("img.jpg");
        contact.setPhoneNumber("+369.xxx.xxx.xxx");
        contact.setEmail("abc.aeg@gmail.com");
        contact.setMode("Online");
        ls.add(contact);

        contact.setName("Menna Mansour");
        contact.setImgSrc("img.jpg");
        contact.setPhoneNumber("+369.xxx.xxx.xxx");
        contact.setEmail("abc.aeg@gmail.com");
        contact.setMode("Online");
        ls.add(contact);

        contact.setName("Menna Mansour");
        contact.setImgSrc("img.jpg");
        contact.setPhoneNumber("+369.xxx.xxx.xxx");
        contact.setEmail("abc.aeg@gmail.com");
        contact.setMode("Online");
        ls.add(contact);
        contact.setName("Menna Mansour");
        contact.setImgSrc("img.jpg");
        contact.setPhoneNumber("+369.xxx.xxx.xxx");
        contact.setEmail("abc.aeg@gmail.com");
        contact.setMode("Online");
        ls.add(contact);

        contact.setName("Menna Mansour");
        contact.setImgSrc("img.jpg");
        contact.setPhoneNumber("+369.xxx.xxx.xxx");
        contact.setEmail("abc.aeg@gmail.com");
        contact.setMode("Online");
        ls.add(contact);

        contact.setName("Menna Mansour");
        contact.setImgSrc("img.jpg");
        contact.setPhoneNumber("+369.xxx.xxx.xxx");
        contact.setEmail("abc.aeg@gmail.com");
        contact.setMode("Online");
        ls.add(contact);
        contact.setName("Menna Mansour");
        contact.setImgSrc("img.jpg");
        contact.setPhoneNumber("+369.xxx.xxx.xxx");
        contact.setEmail("abc.aeg@gmail.com");
        contact.setMode("Online");
        ls.add(contact);

        contact.setName("Menna Mansour");
        contact.setImgSrc("img.jpg");
        contact.setPhoneNumber("+369.xxx.xxx.xxx");
        contact.setEmail("abc.aeg@gmail.com");
        contact.setMode("Online");
        ls.add(contact);

        contact.setName("Menna Mansour");
        contact.setImgSrc("img.jpg");
        contact.setPhoneNumber("+369.xxx.xxx.xxx");
        contact.setEmail("abc.aeg@gmail.com");
        contact.setMode("Online");
        ls.add(contact);

        return ls;

    }


}