package com.example.Controller;

import com.example.interfaces.ModuleRepository;
import com.example.interfaces.UserRepository;
import com.example.models.Grade;
import com.example.models.Module;
import com.example.models.Occurrence;
import com.example.models.OccurrenceRepetition;
import com.example.repositories.ModuleRepositoryImpl;
import com.example.repositories.UserRepositoryImpl;
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
    private static UserRepository userRepository;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 836, 456);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // ModuleRepository repo = getModuleRepositorySingleton();
        // var moduleService = new ModuleServiceImpl(repo);
        // var gradeService = new GradeServiceImpl(repo);
        // var module = new Module("test", new ArrayList<>(), new ArrayList<>());
        // module.getOccurrences().add(
        // new Occurrence(LocalDateTime.now(), LocalDateTime.now().plusHours(1),
        // OccurrenceRepetition.weekly));
        // module.getGrades().add(new Grade(5.5, 1, "testtest"));
        // moduleService.addModule(module);
        //
        // gradeService.exportGradesToExcel("./test.xlsx");
    }

    static void setSceneRoot(String fxml, int width, int height) throws IOException {
        scene.setRoot(loadFXML(fxml));
        Stage stage = (Stage) scene.getWindow();
        stage.setWidth(width);
        stage.setHeight(height);
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

    public static UserRepository getUserRepositorySingleton() {
        if (userRepository == null)
            userRepository = new UserRepositoryImpl();

        return userRepository;
    }

    public static void setDashboardScene(String username, String password) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/dashboard.fxml"));
        Parent root = loader.load();

        DashboardController dashboardController = loader.getController();
        dashboardController.initializeWithCredentials(username, password);

        Scene scene = new Scene(root, 917, 609);
        Stage stage = (Stage) scene.getWindow();
        if (stage == null) {
            stage = new Stage();
            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(root);
        }
        stage.show();
    }

}