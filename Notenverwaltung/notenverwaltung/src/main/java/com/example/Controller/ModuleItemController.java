package com.example.Controller;

import com.example.exceptions.UnauthorizedException;
import com.example.models.Module;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.function.Consumer;

public class ModuleItemController {

    @FXML
    private Label moduleNameLabel;
    @FXML
    private Button btnDeleteModule;

    private Module module;

    private Consumer<Module> onDelete;

    public ModuleItemController() {

    }

    public void initModule(Module module, Consumer<Module> onDelete) {
        this.module = module;
        this.onDelete = onDelete;
        moduleNameLabel.setText(module.getName());
    }

    public Module getModule() {
        return this.module;
    }

    @FXML
    private void deleteModule(ActionEvent event) throws UnauthorizedException, IOException {
        onDelete.accept(module);
    }

}
