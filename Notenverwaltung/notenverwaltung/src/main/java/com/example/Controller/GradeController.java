package com.example.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.models.Grade;
import com.example.models.Module;

import com.example.exceptions.UnauthorizedException;
import com.example.services.ModuleServiceImpl;
import com.example.models.Module;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
    private ComboBox comboBoxModule;

    private ModuleServiceImpl moduleService;

    public GradeController() {
        this.moduleService = new ModuleServiceImpl(App.getModuleRepositorySingleton(),
                App.getAuthenticatedUserAccessorSingleton());
    }

    @FXML
    private void initialize() {
        loadAndDisplayModules();
    }

    public void loadAndDisplayModules() {
        try {
            laufendeVBox.getChildren().clear();
            addModulesToComboBox();
            List<Module> modules = moduleService.getAllModules();
            for (Module module : modules) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gradeModuleItem.fxml")); // Pfad anpassen
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
            List<Module> modules = moduleService.getAllModules();
            comboBoxModule.getItems().addAll(modules);

        } catch (UnauthorizedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    private void addGrade(ActionEvent event) {
        Module module = (Module) comboBoxModule.getSelectionModel().getSelectedItem();
        Grade grade = new Grade(Double.parseDouble(gradeTextFeld.getText()),
                Double.parseDouble(weightTextField.getText()),
                gradeDescriptionTextField.getText());
        module.getGrades().add(grade);
        moduleService.save();
        loadAndDisplayModules();
    }

}
