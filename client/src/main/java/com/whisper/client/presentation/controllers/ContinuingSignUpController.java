package com.whisper.client.presentation.controllers;

import com.whisper.client.presentation.services.DialogueManager;
import com.whisper.client.presentation.services.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.entities.Gender;
import org.example.entities.Mode;
import org.example.entities.Status;
import org.example.entities.User;
import org.example.serverinterfaces.AuthenticationServiceInt;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ContinuingSignUpController
{
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private ComboBox country;
    @FXML
    private ImageView profilePicture;
    @FXML
    private RadioButton maleCheckBox;
    @FXML
    private RadioButton femaleCheckBox;
    @FXML
    private TextArea userBio;
    @FXML
    private Button pictureButton;
    @FXML
    private ToggleGroup gender;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String confirmPassword;
    DialogueManager dialogue;


    public ContinuingSignUpController(){

    }

    @FXML
    public void initialize() {
        gender = new ToggleGroup();
        maleCheckBox.setToggleGroup(gender);
        femaleCheckBox.setToggleGroup(gender);
        // Populate the ComboBox with a list of countries
        List<String> countryList = getAllCountries();
        ObservableList<String> observableList = FXCollections.observableArrayList(countryList);
        country.setItems(observableList);
    }

    private List<String> getAllCountries() {
        String[] countryCodes = Locale.getISOCountries();
        List<String> countryList = Arrays.asList(countryCodes);

        // Convert country codes to country names
        for (int i = 0; i < countryList.size(); i++) {
            Locale locale = new Locale("", countryList.get(i));
            countryList.set(i, locale.getDisplayCountry());
        }

        return countryList;
    }
    public void setData(String firstName, String lastName, String email, String phoneNumber,
                        String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    @FXML
    public void onProfilePicMouseClicked(Event event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        Stage stage = (Stage) pictureButton.getScene().getWindow();
        var selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            Image selectedImage = new Image(selectedFile.toURI().toString());
            profilePicture.setImage(selectedImage);
        }
    }
    private byte[] imageViewToByteArray(ImageView imageView) {
        Image image = imageView.getImage();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }
    @FXML
    public void onSignUpClicked(ActionEvent actionEvent) {
        byte[] profilePhoto = null;
        if (profilePicture.getImage()!=null){
            profilePhoto = imageViewToByteArray(profilePicture);
        }else {
            InputStream defaultImageStream = getClass().getResourceAsStream("/com/whisper/client/images/profile.png");
            try {
                if (defaultImageStream != null) {
                    profilePhoto = defaultImageStream.readAllBytes();
                    defaultImageStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (country.getValue() == null){
            dialogue = new DialogueManager();
            dialogue.setData("Error", "Invalid Country", "Please Choose Your Country");
            return;
        }
        if (dateOfBirth.getValue() == null){
            dialogue = new DialogueManager();
            dialogue.setData("Error", "Invalid date of birth", "Please Choose Your Date of Birth");
            return;
        }
        if (gender.getSelectedToggle() == null){
            dialogue = new DialogueManager();
            dialogue.setData("Error", "Invalid Gender", "Please Choose Your Gender");
            return;
        }
        try{
            Random rand = new Random();
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            AuthenticationServiceInt authService = (AuthenticationServiceInt) reg.lookup("authService");
            authService.registerUser(new User(
                    rand.nextInt(10000),
                    phoneNumber,
                    password,
                    email,
                    firstName + lastName,
                    ((RadioButton) gender.getSelectedToggle()).getText().equals("Male") ? Gender.male : Gender.female,
                    java.sql.Date.valueOf(dateOfBirth.getValue()),
                    country.getValue().toString(),
                    userBio.getText(),
                    Mode.away,
                    Status.offline,
                    profilePhoto
            ));
            Parent root = SceneManager.getInstance().loadPane("signInView");
            Scene scene = profilePicture.getScene();
            scene.setRoot(root);
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}