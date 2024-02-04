package com.whisper.client.presentation.controllers;

import com.whisper.client.business.services.UserSearchService;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;


public class UserSearchController {

    @javafx.fxml.FXML
    private TextField phone1;
    @javafx.fxml.FXML
    private VBox vBox;
    @javafx.fxml.FXML
    private Button addMoreContact;
    int i = 0;
    List<String> phoneNumbers = new ArrayList<>();
    List<TextField> textFields = new ArrayList<>();

    public void initialize() {

    }

    @javafx.fxml.FXML
    public void sendInvitationClicked(ActionEvent actionEvent) {
        textFields.add(phone1);
        for (TextField textField : textFields) {
            if (!textField.getText().isEmpty()) phoneNumbers.add(textField.getText());
        }

        List<String> validatedNumbers = validate(phoneNumbers);

        UserSearchService userSearchService = new UserSearchService();

        userSearchService.sendInvitation(9, validatedNumbers);

        System.out.println(validatedNumbers);
        for (TextField textField : textFields) {
            textField.clear();
        }
        validatedNumbers.clear();
        phoneNumbers.clear();
    }

    private List<String> validate(List<String> phoneNumbers) {
        List<String> validatedNumbers = new ArrayList<>();
        String regex = "^(00201|\\+201|01)[0-2,5]{1}[0-9]{8}$";
        Pattern pattern = Pattern.compile(regex);

        for (String phoneNumber : phoneNumbers) {
            if (pattern.matcher(phoneNumber).matches()) {
                validatedNumbers.add(phoneNumber);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "This Number  : "+phoneNumber+" is Not Valid", ButtonType.OK);
                alert.showAndWait();
            }
        }
        // remove duplicates from the List
        validatedNumbers = new ArrayList<>(new HashSet<>(validatedNumbers));

        return validatedNumbers;
    }

    @javafx.fxml.FXML
    public void addMoreContactButton(ActionEvent actionEvent) {
        if (i < 6) {
            TextField newPhone = new TextField();
            i++;
            newPhone.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/whisper/client/style/newCss.css")).toExternalForm());
            newPhone.getStyleClass().add("text-field2");
            newPhone.setPromptText("Enter phone number");
            VBox.setMargin(newPhone, new Insets(20, 250, 10, 100));
            vBox.getChildren().add(newPhone);
            textFields.add(newPhone);
        }
        if (i == 6) {
            addMoreContact.setDisable(true);
        }
    }
}