package org.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.models.User;
import org.requests.LoginRequest;

import java.io.IOException;


public class AuthorizationLayoutController {
    @FXML
    private TextField loginField;
    @FXML
    private TextField passField;
    @FXML
    private AnchorPane mainCard;
    @FXML
    private Button logButton;
    @FXML
    private AnchorPane root;
    @FXML
    private Label regLink;
    @FXML
    private Label wLabel;


    private Pane overlay;
    private Pane overlay1;
    private AnchorPane sidePanel;

    private void loadRegistrationPanel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RegistrationPanel.fxml"));
            Node registrationPanel = loader.load();
            overlay = (Pane) registrationPanel.lookup("#overlay");
            overlay1 = (Pane) registrationPanel.lookup("#overlay1");
            sidePanel = (AnchorPane) registrationPanel.lookup("#sidePanel");
            root.getChildren().add(registrationPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openSidePanel() {
        overlay.setVisible(true);
        overlay1.setVisible(true);
        sidePanel.setVisible(true);
        Timeline fadeIn = new Timeline(
                new KeyFrame(Duration.millis(300), new KeyValue(overlay.opacityProperty(), 1))
        );
        fadeIn.play();
        Timeline slideIn = new Timeline(
                new KeyFrame(Duration.millis(300), new KeyValue(sidePanel.layoutXProperty(), 738))
        );
        slideIn.play();
        Platform.runLater(() -> {
            mainCard.requestFocus();
        });
    }

    private void closeSidePanel() {
        Timeline fadeOut = new Timeline(
                new KeyFrame(Duration.millis(300), new KeyValue(overlay.opacityProperty(), 0))
        );
        fadeOut.setOnFinished(event -> overlay.setVisible(false));
        fadeOut.play();
        Timeline slideOut = new Timeline(
                new KeyFrame(Duration.millis(300), new KeyValue(sidePanel.layoutXProperty(), 1200))
        );
        slideOut.setOnFinished(event -> sidePanel.setVisible(false));
        slideOut.play();
    }
    @FXML
    private void initialize() {
        loadRegistrationPanel();
        regLink.setOnMouseClicked(event -> openSidePanel());
        if (overlay != null) {
            overlay1.setOnMouseClicked(event -> closeSidePanel());
        }
        Platform.runLater(() -> {
            mainCard.requestFocus();
        });
        logButton.setOnMousePressed(event -> logButton.setStyle("-fx-background-color: #003d80; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 5, 0, 0, 1); "));
        logButton.setOnMouseEntered(event -> {
            Timeline hoverAnimation = new Timeline(
                    new KeyFrame(Duration.millis(10),
                            new KeyValue(logButton.styleProperty(), "-fx-background-color: #0056b3;"))
            );
            hoverAnimation.play();
        });
        logButton.setOnMouseExited(event -> {
            Timeline exitAnimation = new Timeline(
                    new KeyFrame(Duration.millis(10),
                            new KeyValue(logButton.styleProperty(), "-fx-background-color: #00FF00;"))
            );
            exitAnimation.play();
        });
        logButton.setOnMouseReleased(event -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(10),
                            new KeyValue(logButton.styleProperty(), "-fx-background-color: #0056b3;-fx-effect:none;"))
            );
            timeline.play();
            String response = LoginRequest.send(new User(loginField.getText(),passField.getText()));
            if(response==null){
                wLabel.setText("Произошла ошибка!");
            }
            else if(!response.equals("Успешная авторизация!")){
                wLabel.setText(response);
            }
            else {
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainWindow.fxml"));
                    Pane pane = loader.load();
                    Scene scene = new Scene(pane);
                    Stage stage =(Stage) logButton.getScene().getWindow();
                    stage.setScene(scene);
                    stage.centerOnScreen();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }

        });
        regLink.setOnMouseEntered(event -> {
            Timeline hoverAnimation = new Timeline(
                    new KeyFrame(Duration.millis(10),
                            new KeyValue(regLink.styleProperty(), "-fx-text-fill: #00FF00;"))
            );
            hoverAnimation.play();
        });
        regLink.setOnMouseExited(event -> {
            Timeline hoverAnimation = new Timeline(
                    new KeyFrame(Duration.millis(10),
                            new KeyValue(regLink.styleProperty(), "-fx-text-fill: black;"))
            );
            hoverAnimation.play();
        });
    }

}
