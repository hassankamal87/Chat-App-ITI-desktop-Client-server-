package com.whisper.client.presentation.controllers;

import com.whisper.client.MyApp;
import com.whisper.client.business.services.EditProfileService;
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
import java.io.File;
import java.nio.ByteBuffer;
import java.sql.Date;

public class profileController {
    @FXML
    private TextField userName;
    @FXML
    private TextArea userBio;
    @FXML
    private ChoiceBox userMode;
    @FXML
    private DatePicker userDob;
    @FXML
    private Label userNameLabel;
    private EditProfileService profileService = new EditProfileService();
    User myUser = null;
    @FXML
    private Button saveChanges;
    @FXML
    private ImageView userProfile;

    @FXML
    public void initialize() {
        myUser = MyApp.getInstance().getCurrentUser();

        showUserData();
    }

    private void showUserData() {
        userName.setText(myUser.getUserName());
        userBio.setText(myUser.getBio());
        userMode.setValue(myUser.getMode());
        userDob.setValue(myUser.getDateOfBirth().toLocalDate());
        userNameLabel.setText(myUser.getUserName());
        if (myUser.getProfilePhoto() == null){
            System.out.println("Profile photo is null");
        }
        userProfile.setImage(new Image(new ByteArrayInputStream(myUser.getProfilePhoto())));

    }

//    private Image byteArrayToImageView(byte[] profilePhoto) {
//        ByteArrayInputStream bis = new ByteArrayInputStream(profilePhoto);
//        BufferedImage bImage2 = null;
//        try {
//            bImage2 = ImageIO.read(bis);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Image image = new Image(new ByteArrayInputStream(profilePhoto));
//        return image;
//    }


    @FXML
    private void onSaveChangesClicked(ActionEvent actionEvent) {

        int userId = myUser.getUserId();
        String phoneNumber = myUser.getPhoneNumber();
        String password = myUser.getPassword();
        String email = myUser.getEmail();
        String name = userName.getText();
        Gender gender = myUser.getGender();
        Date dob = Date.valueOf(userDob.getValue());
        String country = myUser.getCountry();
        String bio = userBio.getText();
        Mode mode = Mode.valueOf(userMode.getValue().toString());
        Status status = myUser.getStatus();
        byte[] profilePicture = imageViewToByteArray(userProfile);

        User newUser = new User(userId, phoneNumber, password, email, name, gender,
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
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            userProfile.setImage(image);
        }
    }
}
