package com.example.Controller;

import com.example.exceptions.UnauthorizedException;
import com.example.models.Module;
import com.example.services.ModuleServiceImpl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ModuleController {

    @FXML
    private TextField moduleTextFeld;
    @FXML
    private VBox laufendeVBox; // Für laufende Module
    @FXML
    private VBox zukunftigeVbox; // Für zukünftige Module
    @FXML
    private Button addModuleButton;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button backToDashboardButton;

    private ModuleServiceImpl moduleService;

    public ModuleController() {
        this.moduleService = new ModuleServiceImpl(App.getModuleRepositorySingleton(),
                App.getAuthenticatedUserAccessorSingleton());
    }

    @FXML
    private void initialize() {
        loadAndDisplayModules();
    }

    public void loadAndDisplayModules() {
        try {
            zukunftigeVbox.getChildren().clear();
            laufendeVBox.getChildren().clear();
            List<Module> modules = moduleService.getAllModules();
            for (Module module : modules) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/modulItem.fxml")); // Pfad anpassen
                Node moduleItem = loader.load();
                ModuleItemController controller = loader.getController();
                controller.initModule(module, m -> {
                    try {
                        moduleService.deleteModule(m.getUsername(), m.getName());
                    } catch (UnauthorizedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    loadAndDisplayModules();
                });
                if (module.getStartDate().isAfter(LocalDate.now())) {
                    zukunftigeVbox.getChildren().add(moduleItem);
                } else {
                    laufendeVBox.getChildren().add(moduleItem);
                }
            }
        } catch (IOException | UnauthorizedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addModule(ActionEvent event) {
        LocalDate moduleDate = datePicker.getValue();

        try {

            Module module = new Module(moduleTextFeld.getText(),
                    App.getAuthenticatedUserAccessorSingleton().getAuthenticatedUser().getUserName(), new ArrayList<>(),
                    new ArrayList<>(),
                    moduleDate);
            moduleService.addModule(module);
            loadAndDisplayModules();

        } catch (UnauthorizedException e) {
            e.printStackTrace();
        }
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
}
