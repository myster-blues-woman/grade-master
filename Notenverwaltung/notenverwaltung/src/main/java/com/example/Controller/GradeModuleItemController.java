package com.example.Controller;

import com.example.models.Module;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GradeModuleItemController {
    @FXML
    private Label moduleNameLabel;
    @FXML
    private Label avgLabel;

    public GradeModuleItemController() {

    }

    public void init(Module module) {
        moduleNameLabel.setText(module.getName());
        avgLabel.setText(String.valueOf(module.getGradeAvg()));
    }
}
