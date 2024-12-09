package org.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import org.models.Project;

public class ProjectController {

    @FXML
    private VBox nameBox;
    @FXML
    private VBox startDateBox;
    @FXML
    private VBox deadlineBox;
    @FXML
    private VBox statusBox;
    @FXML
    private VBox progressBox;

    @FXML
    private Label nameLabel;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label deadlineLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private ProgressBar progressBar;
    public void initialize(Project project) {
        nameLabel.setText(project.getName());
        startDateLabel.setText(project.getStartDate().toString());
        deadlineLabel.setText(project.getDeadline().toString());
        statusLabel.setText(project.getStatus());
        progressBar.setProgress(project.getPercentage() / 100);
    }
}
