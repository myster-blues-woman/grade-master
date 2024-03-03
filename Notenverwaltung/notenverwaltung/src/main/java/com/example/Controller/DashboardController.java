package com.example.Controller;

import java.io.IOException;

import com.example.interfaces.AuthenticatedUserAccessor;
import com.example.models.User;
import com.example.services.AuthenticatedUserAccessorImpl;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class DashboardController {
    @FXML
    private Pane profilePane;

    @FXML
    private TextField name;
    @FXML
    private TextField vorname;
    @FXML
    private TextField schule;
    @FXML
    private TextField ortDerSchule;
    @FXML
    private TextField jahrgang;
    @FXML
    private TextField lehrperson;
    @FXML
    private TextField userName;
    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Text profileLabel;

    private AuthenticatedUserAccessor authenticatedUserAccessor;

    private User currentUser;

    public DashboardController() {
        this.authenticatedUserAccessor = App.getAuthenticatedUserAccessorSingleton();
    }

    @FXML
    private void initialize() {
        currentUser = authenticatedUserAccessor.getAuthenticatedUser();
        if (currentUser != null) {
            System.out.println(currentUser);
            name.setText(currentUser.getName());
            vorname.setText(currentUser.getVorname());
            schule.setText(currentUser.getSchule());
            ortDerSchule.setText(currentUser.getOrtDerSchule());
            jahrgang.setText(String.valueOf(currentUser.getJahrgang()));
            lehrperson.setText(currentUser.getLehrperson());
            userName.setText(currentUser.getUserName());
        }
    }

    @FXML
    private void onProfileNavButtonClick() {
        profilePane.setVisible(true);
        profileLabel.setVisible(true);
    }

    @FXML
    private void onModuleNavButtonClick() throws IOException {
        profilePane.setVisible(false);
        profileLabel.setVisible(false);
        App.setSceneRoot("module", 1049, 594);
    }

    @FXML
    private void onNotenVltNavButtonClick() throws IOException {
        profilePane.setVisible(false);
        profileLabel.setVisible(false);
        App.setSceneRoot("grades", 1049, 594);
    }

    @FXML
    public void clearFields() {
        name.clear();
        userName.clear();
        vorname.clear();
        schule.clear();
        ortDerSchule.clear();
        jahrgang.clear();
        lehrperson.clear();
    }

    private void checkForChanges() {
        // Überprüfe, ob Änderungen an den Textfeldern vorgenommen wurden
        boolean changesMade = !name.getText().equals(currentUser.getName()) ||
                !vorname.getText().equals(currentUser.getVorname()) ||
                !schule.getText().equals(currentUser.getSchule()) ||
                !ortDerSchule.getText().equals(currentUser.getOrtDerSchule()) ||
                !jahrgang.getText().equals(String.valueOf(currentUser.getJahrgang())) ||
                !lehrperson.getText().equals(currentUser.getLehrperson()) ||
                !userName.getText().equals(currentUser.getUserName());

        // Wenn Änderungen vorhanden sind, mache den Speichern-Button sichtbar, sonst
        // unsichtbar
        saveButton.setDisable(changesMade);
    }

    @FXML
    private void handleTextFieldChange() {
        checkForChanges();
    }
}
