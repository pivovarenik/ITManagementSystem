package org.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.util.ValidateRegistrationInput;

import java.util.ArrayList;
import java.util.List;

public class RegistrationPanelController {
    @FXML
    private Pane overlay;
    @FXML
    private TextField login;
    @FXML
    private TextField password;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private TextField age;
    @FXML
    private TextField country;
    @FXML
    private TextField city;
    @FXML
    private Button confirmButton;
    @FXML
    private ImageView out1;
    @FXML
    private ImageView out2;
    @FXML
    private ImageView out3;
    @FXML
    private ImageView out4;
    @FXML
    private ImageView out5;
    @FXML
    private ImageView out6;
    @FXML
    private ImageView out7;
    @FXML
    private ImageView out8;

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            overlay.requestFocus();
        });
        confirmButton.setOnMousePressed(event -> confirmButton.setStyle("-fx-background-color: #003d80; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 5, 0, 0, 1); "));
        confirmButton.setOnMouseEntered(event -> {
            Timeline hoverAnimation = new Timeline(
                    new KeyFrame(Duration.millis(10),
                            new KeyValue(confirmButton.styleProperty(), "-fx-background-color: #0056b3;"))
            );
            hoverAnimation.play();
        });
        confirmButton.setOnMouseExited(event -> {
            Timeline exitAnimation = new Timeline(
                    new KeyFrame(Duration.millis(10),
                            new KeyValue(confirmButton.styleProperty(), "-fx-background-color: #00FF00;"))
            );
            exitAnimation.play();
        });
        confirmButton.setOnMouseReleased(event -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(10),
                            new KeyValue(confirmButton.styleProperty(), "-fx-background-color: #0056b3;-fx-effect:none;"))
            );
            timeline.play();
            ValidateRegistrationInput validator = new ValidateRegistrationInput();
            ArrayList<Boolean> inputs =  validator.validateInputs(login.getText(), password.getText(),firstName.getText(),lastName.getText(),email.getText(),age.getText(),country.getText(),city.getText());

            int i = 0;
            List<ImageView> imageViews = List.of(out1, out2, out3, out4, out5, out6, out7, out8);
            for (ImageView imageView : imageViews) {
                if(!inputs.get(i)) {
                    imageView.setImage(new Image("/images/cross.png"));
                }
                else imageView.setImage(new Image("/images/tick.png"));
                imageView.setOpacity(1);
                i++;
            }
        });
    }

}
