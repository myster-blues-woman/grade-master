package com.example.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
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

    @FXML
    private void resetPassword() throws IOException {
        if (!password.getText().equals(password2.getText())) {
            showAlert("Passwort Fehler", "Die Passwörter stimmen nicht überein!");
            return;
        }

        Path path = Paths.get("registrationData.txt");
        List<String> lines = Files.readAllLines(path);
        boolean isMatchingUser = false;
        List<String> updatedLines = new ArrayList<>();

        for (String line : lines) {
            // Prüfen, ob die Zeile den Benutzernamen enthält
            if (line.startsWith("Benutzername: ") && line.substring(14).equals(userName.getText())) {
                isMatchingUser = true; // Beginn des Benutzerblocks
            }

            // Wenn wir uns im Block des Benutzers befinden, suchen wir nach den weiteren
            // Kriterien
            if (isMatchingUser && (line.startsWith("Jahrgang: ") && line.substring(10).equals(jahrgang.getText()) ||
                    line.startsWith("Lehrperson: ") && line.substring(12).equals(lehrperson.getText()))) {
                // Wir befinden uns im richtigen Block, aber wir ändern das Passwort erst, wenn
                // wir die Passwortzeile erreichen
            } else if (isMatchingUser && line.startsWith("Passwort: ")) {
                // Passwortzeile aktualisieren
                line = "Passwort: " + password.getText();
                isMatchingUser = false; // Ende des Benutzerblocks
            }

            updatedLines.add(line);

            // Wenn eine Trennzeile erreicht wird, beenden wir die Suche im aktuellen
            // Benutzerblock
            if (line.trim().equals("--------------------------------------------------")) {
                isMatchingUser = false;
            }
        }

        // Überschreiben der Datei mit den aktualisierten Daten
        Files.write(path, updatedLines, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);

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
