package com.example.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import com.example.interfaces.UserService;
import com.example.services.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ResetPasswordController {

    @FXML
    private TextField userName;
    @FXML
    private TextField jahrgang;
    @FXML
    private TextField lehrperson;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField password2;

    private UserService userService;

    public ResetPasswordController() {
        this.userService = new UserServiceImpl(App.getUserRepositorySingleton());
    }

    @FXML
    private void resetPassword() throws IOException {
        if (!password.getText().equals(password2.getText())) {
            showAlert("Passwort Fehler", "Die Passwörter stimmen nicht überein!");
            return;
        }

        userService.resetPassword(userName.getText(), Integer.parseInt(jahrgang.getText()), lehrperson.getText(), password.getText());

        showAlert("Erfolg", "Passwort erfolgreich zurückgesetzt.");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}