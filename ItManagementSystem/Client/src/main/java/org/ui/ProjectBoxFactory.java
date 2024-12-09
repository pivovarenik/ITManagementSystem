package org.ui;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.controllers.ProjectController;
import org.models.Project;

import java.io.IOException;

public class ProjectBoxFactory {
    public static HBox createProjectNode(Project project) throws IOException {
        FXMLLoader loader = new FXMLLoader(ProjectBoxFactory.class.getResource("/views/Project.fxml"));
        HBox projectBox = (HBox) loader.load();
        ProjectController controller = loader.getController();
        controller.initialize(project);
        return projectBox;
    }
}