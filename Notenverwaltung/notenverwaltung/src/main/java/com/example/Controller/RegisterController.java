package com.example.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.example.models.User;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RegisterController {

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
    private TextField password;
    @FXML
    private TextField password2;
    @FXML
    private Button registerButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button backToLoginButton;
    @FXML
    private Label userNameError;
    @FXML
    private Label passwordError;
    @FXML
    private Label jahrgangError;

    @FXML
    public void register() throws IOException {
        if (!isValidUsername(userName.getText())) {
            userNameError.setText("Benutzername muss mindestens 5 Zeichen lang sein.");
            userNameError.setVisible(true);
            return;
        }
        if (!isValidJahrgang(jahrgang.getText())) {
            jahrgangError.setText("Ungültiger Jahrgang. Muss eine Zahl zwischen 1900 und dem aktuellen Jahr sein.");
            jahrgangError.setVisible(true);
            return;
        }

        // Überprüfe, ob Passwörter übereinstimmen
        if (!password.getText().equals(password2.getText())) {
            passwordError.setText("Passwörter stimmen nicht überein.");
            passwordError.setVisible(true);
            return;
        }

        int jahrgangInt = Integer.parseInt(jahrgang.getText()); // Konvertieren des Jahrgangs in int
        User user = new User(name.getText(), vorname.getText(), schule.getText(), ortDerSchule.getText(), jahrgangInt,
                lehrperson.getText(), userName.getText(), password.getText());

        // Konvertieren des User-Objekts in einen String, der geschrieben werden soll
        String content = String.format(
                "Name: %s\nVorname: %s\nSchule: %s\nOrt der Schule: %s\nJahrgang: %d\nLehrperson: %s\nBenutzername: %s\nPasswort: %s\n--------------------------------------------------\n",
                user.getName(), user.getVorname(), user.getSchule(), user.getOrtDerSchule(), user.getJahrgang(),
                user.getLehrperson(), user.getUserName(), user.getPassword());

        // Schreiben des Inhalts in die Datei mit Streams
        try {
            Files.write(Paths.get("registrationData.txt"), content.getBytes(), StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
            // Zeige eine Benachrichtigung an, dass die Registrierung erfolgreich war
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Registrierung Erfolgreich");
            alert.setHeaderText(null);
            alert.setContentText("Sie haben sich erfolgreich registriert!");
            alert.showAndWait();
            switchToLogin();
        } catch (IOException e) {
            throw new IOException("Fehler beim Schreiben in die Datei", e);
        }

    }

    @FXML
    private void switchToLogin() throws IOException {
        App.setSceneRoot("login");
    }

    @FXML
    public void clearFields() {
        name.clear();
        vorname.clear();
        schule.clear();
        ortDerSchule.clear();
        jahrgang.clear();
        lehrperson.clear();
        userName.clear();
        password.clear();
        password2.clear();
        if (userNameError != null) {
            userNameError.setVisible(false);
            userNameError.setText("");
        }
        if (jahrgangError != null) {
            jahrgangError.setVisible(false);
            jahrgangError.setText("");
        }
        if (passwordError != null) {
            passwordError.setVisible(false);
            passwordError.setText("");
        }
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
}
