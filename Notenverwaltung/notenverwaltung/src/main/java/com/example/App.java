package com.example;

import com.example.interfaces.ModuleRepository;
import com.example.interfaces.ModuleService;
import com.example.models.Grade;
import com.example.models.Module;
import com.example.models.Occurrence;
import com.example.models.OccurrenceRepetition;
import com.example.repositories.ModuleRepositoryImpl;
import com.example.services.GradeServiceImpl;
import com.example.services.ModuleServiceImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static ModuleRepository moduleRepository;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 900, 600);
        stage.setScene(scene);
        stage.show();

        ModuleRepository repo = getModuleRepositorySingleton();
        var moduleService = new ModuleServiceImpl(repo);
        var gradeService = new GradeServiceImpl(repo);
        var module = new Module("test", new ArrayList<>(), new ArrayList<>());
        module.getOccurrences().add(new Occurrence(LocalDateTime.now(), LocalDateTime.now().plusHours(1), OccurrenceRepetition.weekly));
        module.getGrades().add(new Grade(5.5, 1, "testtest"));
        moduleService.addModule(module);

        gradeService.exportGradesToExcel("./test.xlsx");
    }

    static void setSceneRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static ModuleRepository getModuleRepositorySingleton() {
        if (moduleRepository == null)
            moduleRepository = new ModuleRepositoryImpl();

        return moduleRepository;
    }

}