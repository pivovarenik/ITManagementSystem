package org.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.models.Project;
import org.requests.ProjectRequest;
import org.ui.ProjectBoxFactory;

import java.io.IOException;
import java.util.List;

public class MainWindowController {
    @FXML
    private ImageView logo;
    @FXML
    private HBox main;
    @FXML
    private HBox projects;
    @FXML
    private HBox staff;
    @FXML
    private HBox chat;
    @FXML
    private HBox departments;
    @FXML
    private HBox attendance;
    @FXML
    private VBox menu;
    @FXML
    private Pane mainPane;
    @FXML
    private Label currentLabel;


    private VBox projectVBox;
    private ScrollPane contentPane;
    private Pane centerPane;
    private Node currentPane = null;

    @FXML
    private void initialize() {
        HBox[] hboxItems = {main, projects, staff, chat, departments, attendance};
        for (HBox hbox : hboxItems) {
            hbox.setOnMouseClicked(event -> {
                for (HBox item : hboxItems) {
                    item.getStyleClass().remove("selected");
                }
                hbox.getStyleClass().add("selected");
            });
        }
        projects.setOnMouseClicked(event -> {
            for (HBox item : hboxItems) {
                item.getStyleClass().remove("selected");
            }
            projects.getStyleClass().add("selected");
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Projects.fxml"));
                Node pane = loader.load();
                if (currentPane != null) {
                    mainPane.getChildren().remove(currentPane);
                }
                currentLabel.setText("Проекты");
                centerPane = (Pane) pane.lookup("#mainPane");
                contentPane = (ScrollPane) pane.lookup("#contentPane");
                projectVBox = (VBox) pane.lookup("#projectsContainer");
                mainPane.getChildren().add(pane);
                currentPane = pane;
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });
        staff.setOnMouseClicked(event -> {
            for (HBox item : hboxItems) {
                item.getStyleClass().remove("selected");
            }
            staff.getStyleClass().add("selected");
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Staff.fxml"));
                Node pane = loader.load();
                currentLabel.setText("Сотрудники");
                if (currentPane != null) {
                    mainPane.getChildren().remove(currentPane);
                }
                mainPane.getChildren().add(pane);
                currentPane = pane;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}
