package com.example.Controller;

import java.io.IOException;
import java.util.ResourceBundle;
import java.net.URL;

import com.example.interfaces.AuthenticatedUserAccessor;
import com.example.interfaces.UserService;
import com.example.models.User;
import com.example.services.UserServiceImpl;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class DashboardController implements Initializable {
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
    @FXML
    private Button resetPasswort;

    private AuthenticatedUserAccessor authenticatedUserAccessor;
    private UserService userService;

    private User currentUser;

    public DashboardController() {
        this.authenticatedUserAccessor = App.getAuthenticatedUserAccessorSingleton();
        this.userService = new UserServiceImpl(App.getUserRepositorySingleton(),
                App.getAuthenticatedUserAccessorSingleton());

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        saveButton.setDisable(true);
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

        // Listener hinzufügen
        addTextChangeListeners();
    }

    private void addTextChangeListeners() {
        name.textProperty().addListener((observable, oldValue, newValue) -> checkForChanges());
        vorname.textProperty().addListener((observable, oldValue, newValue) -> checkForChanges());
        schule.textProperty().addListener((observable, oldValue, newValue) -> checkForChanges());
        ortDerSchule.textProperty().addListener((observable, oldValue, newValue) -> checkForChanges());
        jahrgang.textProperty().addListener((observable, oldValue, newValue) -> checkForChanges());
        lehrperson.textProperty().addListener((observable, oldValue, newValue) -> checkForChanges());
        userName.textProperty().addListener((observable, oldValue, newValue) -> checkForChanges());
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
        boolean changesMade = !name.getText().equals(currentUser.getName()) ||
                !vorname.getText().equals(currentUser.getVorname()) ||
                !schule.getText().equals(currentUser.getSchule()) ||
                !ortDerSchule.getText().equals(currentUser.getOrtDerSchule()) ||
                !jahrgang.getText().equals(String.valueOf(currentUser.getJahrgang())) ||
                !lehrperson.getText().equals(currentUser.getLehrperson()) ||
                !userName.getText().equals(currentUser.getUserName());
        saveButton.setDisable(!changesMade);
    }

    @FXML
    private void onSaveButtonClick() {
        String updatedName = name.getText();
        String updatedVorname = vorname.getText();
        String updatedSchule = schule.getText();
        String updatedOrtDerSchule = ortDerSchule.getText();
        String updatedLehrperson = lehrperson.getText();
        String updatedUserName = userName.getText();
        String updatedJahrgang = jahrgang.getText();
        User updatedUser = new User();

        if (!isValidUsername(updatedUserName)) {
            showAlert("Validierungsfehler", "Benutzername muss mindestens 5 Zeichen lang sein.", Alert.AlertType.ERROR);
            return;
        }

        // Validierung des Jahrgangs
        if (!isValidJahrgang(updatedJahrgang)) {
            showAlert("Validierungsfehler",
                    "Jahrgang muss eine gültige Zahl zwischen 1901 und dem aktuellen Jahr sein.",
                    Alert.AlertType.ERROR);
            return;
        }
        int updatedJahrgangToInt = Integer.parseInt(updatedJahrgang);
        updatedUser.setName(updatedName);
        updatedUser.setVorname(updatedVorname);
        updatedUser.setSchule(updatedSchule);
        updatedUser.setOrtDerSchule(updatedOrtDerSchule);
        updatedUser.setJahrgang(updatedJahrgangToInt);
        updatedUser.setLehrperson(updatedLehrperson);
        updatedUser.setUserName(updatedUserName);
        updatedUser.setPassword(currentUser.getPassword());
        try {
            if (userService.update(currentUser.getUserName(), updatedUser)) {
                System.out.println("Benutzerdaten erfolgreich aktualisiert.");
                showAlert("Erfolg", "Benutzerdaten erfolgreich aktualisiert.", Alert.AlertType.INFORMATION);
                saveButton.setDisable(true); // Deaktiviere den Speichern-Button nach erfolgreicher Aktualisierung
            } else {
                showAlert("Fehler", "Benutzer konnte nicht gefunden oder aktualisiert werden.", Alert.AlertType.ERROR);
            }
            saveButton.setDisable(true);
        } catch (Exception e) {
            System.err.println("Fehler beim Aktualisieren der Benutzerdaten: " + e.getMessage());
            showAlert("Fehler", "Fehler beim Aktualisieren der Benutzerdaten: " + e.getMessage(),
                    Alert.AlertType.ERROR);
        }

    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean isValidUsername(String username) {
        // Beispiel: Benutzername muss mindestens 5 Zeichen lang sein
        return username != null && !username.trim().isEmpty() && username.length() >= 5;
    }

    private boolean isValidJahrgang(String jahrgang) {
        // Beispiel: Jahrgang muss eine Zahl sein und ein realistisches Jahr darstellen
        try {
            int jahr = Integer.parseInt(jahrgang);
            return jahr > 1900 && jahr <= java.time.Year.now().getValue();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void switchToResetPswd() {
        try {
            App.setSceneRoot("resetPassword", 836, 456);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleTextFieldChange() {
        checkForChanges();
    }

    @FXML
    private void onResetPasswortClick() {
        switchToResetPswd();
    }
}
