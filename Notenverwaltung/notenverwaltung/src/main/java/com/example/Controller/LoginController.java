package com.example.Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.example.interfaces.UserService;
import com.example.services.UserServiceImpl;
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

    private UserService userService;

    public LoginController() {
        this.userService = new UserServiceImpl(App.getUserRepositorySingleton());
    }

    @FXML
    private void switchToRegistration() throws IOException {
        App.setSceneRoot("registration", 836, 456);
    }

    private void switchToDashboard() {
        try {
            String username = signAccountName.getText();
            String password = signInPassword.getText();
            App.setDashboardScene(username, password);
        } catch (IOException e) {
            e.printStackTrace();
            loginMessageLabel.setText("Fehler beim Laden des Dashboards.");
        }
    }

    private void switchToResetPswd() {
        try {
            App.setSceneRoot("resetPassword", 836, 456);
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
        if (userService.login(username, password))
            return true;
        else {
            loginMessageLabel.setText("Fehler beim Lesen der Registrierungsdaten.");
            return false;
        }
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
