package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

    private void switchToDashboard() {
        try {
            App.setSceneRoot("dashboard");
        } catch (IOException e) {
            e.printStackTrace();
            loginMessageLabel.setText("Fehler beim Laden des Dashboards.");
        }
    }

    private void switchToResetPswd() {
        try {
            App.setSceneRoot("resetPassword");
        } catch (IOException e) {
            e.printStackTrace();
            loginMessageLabel.setText("Fehler beim Laden des Reset Password.");
        }
    }

    @FXML
    private void login() {
        if (checkCredentials(signAccountName.getText(), signInPassword.getText())) {
            loginMessageLabel.setText("Erfolgreich eingeloggt!");
            loginMessageLabel.setVisible(true);
            // Wechsle zur Dashboard-Ansicht nach erfolgreichem Login
            switchToDashboard();
        } else {
            loginMessageLabel.setText("Login fehlgeschlagen. Benutzername oder Passwort ist falsch.");
            loginMessageLabel.setVisible(true);
        }
    }

    private boolean checkCredentials(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("registrationData.txt"))) {
            String line;
            String fileUsername = "";
            String filePassword = "";
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Benutzername: ")) {
                    fileUsername = line.split(": ")[1].trim();
                } else if (line.startsWith("Passwort: ")) {
                    filePassword = line.split(": ")[1].trim();
                }

                if (!fileUsername.isEmpty() && !filePassword.isEmpty()) {
                    if (fileUsername.equals(username) && filePassword.equals(password)) {
                        return true;
                    }
                    fileUsername = "";
                    filePassword = "";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            loginMessageLabel.setText("Fehler beim Lesen der Registrierungsdaten.");
        }
        return false;
    }

    @FXML
    private void handlePasswordForgotClick(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Passwort zurücksetzen");
        alert.setHeaderText(null); // Kein Header-Text
        alert.setContentText("Möchten Sie Ihr Passwort zurücksetzen?");

        // Zeige den Alert und warte auf eine Benutzerantwort
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                switchToResetPswd();
            }
        });
    }
}
