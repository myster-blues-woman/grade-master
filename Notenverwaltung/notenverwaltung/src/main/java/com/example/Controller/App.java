package com.example.Controller;

import com.example.interfaces.ModuleRepository;
import com.example.interfaces.UserRepository;
import com.example.repositories.ModuleRepositoryImpl;
import com.example.repositories.UserRepositoryImpl;
import com.example.services.AuthenticatedUserAccessorImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static ModuleRepository moduleRepository;
    private static UserRepository userRepository;
    private static AuthenticatedUserAccessorImpl authenticatedUserAccessor;

    private static Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        logger.info("Starting grade master");
        scene = new Scene(loadFXML("login"), 836, 456);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
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

    public static AuthenticatedUserAccessorImpl getAuthenticatedUserAccessorSingleton() {
        if (authenticatedUserAccessor == null)
            authenticatedUserAccessor = new AuthenticatedUserAccessorImpl();

        return authenticatedUserAccessor;
    }
}