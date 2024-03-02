package com.example.Controller;

import java.io.IOException;
import java.util.function.Consumer;

import com.example.exceptions.UnauthorizedException;
import com.example.models.Grade;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GradeItemController {
    @FXML
    private Label gradeLabel;
    @FXML
    private Label weightGradeLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Button btnDeleteGrade;

    private Grade grade;

    private Consumer<Grade> onDelete;

    public GradeItemController() {

    }

    public void initModule(Grade grade, Consumer<Grade> onDelete) {
        this.grade = grade;
        this.onDelete = onDelete;
        gradeLabel.setText(String.valueOf(grade.getGrade()));
        weightGradeLabel.setText(String.valueOf(grade.getWeight()));
        descriptionLabel.setText(grade.getDescription());
    }

    @FXML
    private void deleteGrade(ActionEvent event) throws UnauthorizedException, IOException {
        onDelete.accept(grade);
    }
}
