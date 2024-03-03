package com.example.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.example.interfaces.GradeService;
import com.example.interfaces.ModuleService;
import com.example.models.Grade;
import com.example.models.Module;

import com.example.exceptions.UnauthorizedException;
import com.example.services.GradeServiceImpl;
import com.example.services.ModuleServiceImpl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class GradeController {
    @FXML
    private TextField gradeTextFeld;
    @FXML
    private TextField weightTextField;
    @FXML
    private TextField gradeDescriptionTextField;
    @FXML
    private VBox laufendeVBox;
    @FXML
    private Button addGradeButton;
    @FXML
    private Button backToDashboardButton;
    @FXML
    private ComboBox comboBoxModule;
    @FXML
    private Label dropDownError;
    @FXML
    private Button exportButton;

    private ModuleService moduleService;
    private GradeService gradeService;

    public GradeController() {
        this.moduleService = new ModuleServiceImpl(App.getModuleRepositorySingleton(),
                App.getAuthenticatedUserAccessorSingleton());

        this.gradeService = new GradeServiceImpl(App.getModuleRepositorySingleton(), App.getAuthenticatedUserAccessorSingleton());
    }

    @FXML
    private void initialize() {
        dropDownError.setVisible(false);
        loadAndDisplayModules();
    }

    public void loadAndDisplayModules() {
        try {
            laufendeVBox.getChildren().clear();
            addModulesToComboBox();
            List<Module> modules = moduleService.getAllModules();
            for (Module module : modules) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gradeModuleItem.fxml"));
                Node moduleItem = loader.load();
                GradeModuleItemController controller = loader.getController();
                controller.init(module);
                laufendeVBox.getChildren().add(moduleItem);
                for (Grade grade : module.getGrades()) {
                    FXMLLoader gradLoader = new FXMLLoader(getClass().getResource("/gradeItem.fxml")); // Pfad
                    // anpassen
                    Node gradeItem = gradLoader.load();
                    GradeItemController gradeItemController = gradLoader.getController();
                    gradeItemController.initModule(grade, g -> {
                        module.getGrades().remove(g);
                        moduleService.save();
                        loadAndDisplayModules();
                    });
                    laufendeVBox.getChildren().add(gradeItem);
                }
            }
        } catch (IOException | UnauthorizedException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void addModulesToComboBox() {
        try {
            comboBoxModule.getItems().clear();
            List<Module> allModules = moduleService.getAllModules();
            List<Module> filteredModules = allModules.stream()
                    .filter(module -> !module.getStartDate().isAfter(java.time.LocalDate.now()))
                    .collect(Collectors.toList());

            comboBoxModule.getItems().addAll(filteredModules);
        } catch (UnauthorizedException e) {
            e.printStackTrace();
            showAlert("Fehler", "Autorisierungsfehler beim Laden der Module.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void addGrade(ActionEvent event) {
        Module module = (Module) comboBoxModule.getSelectionModel().getSelectedItem();
        if (module == null) {
            dropDownError.setVisible(true);
            dropDownError.setText("Bitte wählen Sie ein Modul aus der Liste.");
        } else {
            dropDownError.setVisible(false);
            try {
                Grade grade = new Grade(Double.parseDouble(gradeTextFeld.getText()),
                        Double.parseDouble(weightTextField.getText()),
                        gradeDescriptionTextField.getText());
                module.getGrades().add(grade);
                moduleService.save();
                loadAndDisplayModules();
            } catch (NumberFormatException e) {
                showAlert("Eingabefehler", "Stellen Sie sicher, dass alle Felder korrekt formatiert sind.",
                        Alert.AlertType.ERROR);
            }
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void switchToDashboard() {
        try {
            App.setSceneRoot("dashboard", 941, 620);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSwitchToDashboardClick() {
        switchToDashboard();
    }

    @FXML
    private void onExportGrades() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = localDate.format(formatter);

        try {
            gradeService.exportGradesToExcel("grade_export_" + formattedDate + ".xlsx");
        } catch (UnauthorizedException e) {
            e.printStackTrace();
        }
    }

}
