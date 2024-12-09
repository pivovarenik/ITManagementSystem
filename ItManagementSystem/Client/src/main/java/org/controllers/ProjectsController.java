package org.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import org.models.Project;
import org.requests.ProjectRequest;
import org.ui.CleanScreen;
import org.ui.ProjectBoxFactory;

import java.util.List;


public class ProjectsController {
    @FXML
    private VBox projectsContainer;
    @FXML
    private ScrollPane contentPane;
    @FXML
    private Pane mainPane;
    @FXML
    private void initialize() {
        List<Project> projects = ProjectRequest.send();
        try{
            if (projects != null && !projects.isEmpty()) {
                int i =0;
                for (Project project : projects) {
                    HBox projectNode = ProjectBoxFactory.createProjectNode(project);
                    if(i % 2 == 0){
                        projectNode.getStyleClass().add("projectWhite");
                    }
                    else projectNode.getStyleClass().add("projectBlack");
                    projectNode.setMinHeight(75);
                    projectsContainer.getChildren().add(projectNode);
                    i++;
                }
            }
            else {
                projectsContainer.getChildren().add(CleanScreen.load());
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
