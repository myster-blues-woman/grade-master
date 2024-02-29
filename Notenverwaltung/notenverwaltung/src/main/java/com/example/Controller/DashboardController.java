package com.example.Controller;

import com.example.interfaces.UserService;
import com.example.models.User;
import com.example.services.UserServiceImpl;

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

    private UserService userService;

    private User currentUser;

    public DashboardController() {
        this.userService = new UserServiceImpl(App.getUserRepositorySingleton());
    }

    public void initializeWithCredentials(String username, String password) {
        userService.getUserByUsernameAndPassword(username, password).ifPresent(user -> {
            name.setText(user.getName());
            vorname.setText(user.getVorname());
            schule.setText(user.getSchule());
            ortDerSchule.setText(user.getOrtDerSchule());
            jahrgang.setText(String.valueOf(user.getJahrgang()));
            lehrperson.setText(user.getLehrperson());
            userName.setText(user.getUserName());

            currentUser = new User(
                    user.getName(),
                    user.getVorname(),
                    user.getSchule(),
                    user.getOrtDerSchule(),
                    user.getJahrgang(),
                    user.getLehrperson(),
                    user.getUserName());
        });
    }

    @FXML
    private void onProfileNavButtonClick() {
        profilePane.setVisible(true);
        profileLabel.setVisible(true);
    }

    @FXML
    private void onModuleNavButtonClick() {
        profilePane.setVisible(false);
        profileLabel.setVisible(false);
    }

    @FXML
    private void onNotenVltNavButtonClick() {
        profilePane.setVisible(false);
        profileLabel.setVisible(false);
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
