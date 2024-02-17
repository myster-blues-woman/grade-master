package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

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
    private void resetPassword() {
        // Prüfe, ob die Passwörter im Eingabefeld übereinstimmen
        if (!password.getText().equals(password2.getText())) {
            showAlert("Passwort Fehler", "Die Passwörter stimmen nicht überein!");
            return;
        }

        // Versuche, das Passwort zu aktualisieren
        if (updatePasswordInFile(userName.getText(), jahrgang.getText(), lehrperson.getText(), password.getText())) {
            showAlert("Erfolg", "Passwort erfolgreich zurückgesetzt!");
        } else {
            showAlert("Fehler", "Benutzerdaten nicht gefunden oder Fehler beim Aktualisieren des Passworts.");
        }
    }

    private boolean updatePasswordInFile(String username, String jahrgangInput, String lehrpersonInput,
            String newPassword) {
        File originalFile = new File("registrationData.txt");
        List<String> lines = new ArrayList<>();
        boolean found = false;
        boolean updateUser = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(originalFile))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // Prüfe auf Benutzerdaten
                System.out.println(currentLine);
                if (currentLine.startsWith("Benutzername: ") && currentLine.contains(username)) {
                    updateUser = true; // Starte die Überprüfung der nächsten Zeilen
                }
                if (updateUser && currentLine.startsWith("Jahrgang: ") && currentLine.contains(jahrgangInput)) {
                    // Zusätzliche Überprüfung für Jahrgang
                } else if (updateUser && currentLine.startsWith("Lehrperson: ")
                        && currentLine.contains(lehrpersonInput)) {
                    // Zusätzliche Überprüfung für Lehrperson
                    found = true; // Alle Kriterien erfüllt
                } else if (found && currentLine.startsWith("Passwort: ")) {
                    // Ersetze das Passwort
                    lines.add("Passwort: " + newPassword);
                    updateUser = false; // Stoppe die Überprüfung der nächsten Zeilen
                    found = false; // Zurücksetzen für den nächsten Durchlauf
                    continue;
                }
                lines.add(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // Schreibe die geänderten Daten zurück in die Datei
        if (!lines.isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(originalFile, false))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
                return true; // Erfolg
            } catch (IOException e) {
                System.err.println("Fehler beim Schreiben in die Datei: " + e.getMessage());
                e.printStackTrace();
                return false;
            }

        } else {
            return false; // Keine Änderungen
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
