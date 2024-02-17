package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;

public class LoginController {

    @FXML
    private TextField signAccountName;
    @FXML
    private PasswordField signInPassword;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private Button signInRegister;

    @FXML
    private void switchToRegistration() throws IOException {
        App.setSceneRoot("registration");
    }

    @FXML
    private void login() {
        if (checkCredentials(signAccountName.getText(), signInPassword.getText())) {
            loginMessageLabel.setText("Erfolgreich eingeloggt!");
        } else {
            loginMessageLabel.setText("Login fehlgeschlagen. Benutzername oder Passwort ist falsch.");
        }
    }

    private boolean checkCredentials(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("registrationData.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Angenommen, jede Zeile hat Format "Benutzername:Passwort"
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String fileUsername = parts[0].trim();
                    String filePassword = parts[1].trim();
                    if (fileUsername.equals(username) && filePassword.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            loginMessageLabel.setText("Fehler beim Lesen der Registrierungsdaten.");
        }
        return false;
    }
}
