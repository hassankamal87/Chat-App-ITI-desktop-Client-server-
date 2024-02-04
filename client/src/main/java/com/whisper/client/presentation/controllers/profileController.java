package com.whisper.client.presentation.controllers;

import com.whisper.client.business.services.EditProfileService;
import com.whisper.client.presentation.services.ErrorDialogue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.entities.Gender;
import org.example.entities.Mode;
import org.example.entities.Status;
import org.example.entities.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.rmi.RemoteException;
import java.sql.Date;

public class profileController {
    @FXML
    private ImageView userPicture;
    @FXML
    private TextField userName;
    @FXML
    private TextArea userBio;
    @FXML
    private ChoiceBox userMode;
    @FXML
    private ChoiceBox userCountry;
    @FXML
    private DatePicker userDob;
    @FXML
    private Button saveChanges;
    @FXML
    private Label userNameLabel;
    private EditProfileService profileService = new EditProfileService();

    @FXML
    public void initialize() {
        showUserData();
    }

    private void showUserData() {
        User myUser = profileService.getUser(24);
        userName.setText(myUser.getUserName());
        userBio.setText(myUser.getBio());
        userMode.setValue(myUser.getMode());
        userCountry.setValue(myUser.getCountry());
        userDob.setValue(myUser.getDateOfBirth().toLocalDate());
        userNameLabel.setText(myUser.getUserName());
        if(myUser.getProfilePhoto()!=null) {
            userPicture.setImage(new Image(new ByteArrayInputStream(myUser.getProfilePhoto())));
        }
    }


    @FXML
    private void onSaveChangesClicked(ActionEvent actionEvent) {

        int userId = 24;
        String phoneNumber = "01100793613";
        String name = userName.getText();
        String password = "Ahmed@12345";
        String email = "Ahmed@12345";
        String bio = userBio.getText();
        Mode mode = (Mode) userMode.getValue();
        String country = userCountry.getValue().toString();
        Date dob = Date.valueOf(userDob.getValue());
        Status status = Status.online;
        byte[] profilePicture = imageViewToByteArray(userPicture);

        User newUser = new User(userId, phoneNumber, password, email, name, Gender.female,
                dob, country, bio, mode, status, profilePicture);

        profileService.saveProfileChanges(newUser);
    }

    private byte[] imageViewToByteArray(ImageView imageView) {
        Image image = imageView.getImage();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        PixelReader pixelReader = image.getPixelReader();
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 * width * height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = pixelReader.getArgb(x, y);
                byteBuffer.putInt(argb);
            }
        }

        return byteBuffer.array();
    }

    @FXML
    public void onProfileClicked(Event event) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpg, *.jpeg, *.gif)", "*.png", "*.jpg", "*.jpeg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            userPicture.setImage(new Image(image.getUrl()));
        }
    }
}
