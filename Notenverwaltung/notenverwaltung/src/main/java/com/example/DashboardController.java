package com.example;

import java.io.IOException;
import javafx.fxml.FXML;

public class DashboardController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setSceneRoot("secondary");
    }
}
