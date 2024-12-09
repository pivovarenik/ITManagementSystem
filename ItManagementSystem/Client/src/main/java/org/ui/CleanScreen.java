package org.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.controllers.ProjectController;
import org.models.Project;

import java.io.IOException;

public class CleanScreen {
    public static Pane load() throws IOException {
        FXMLLoader loader = new FXMLLoader(CleanScreen.class.getResource("/views/NothingHere.fxml"));
        return (Pane) loader.load();
    }
}
