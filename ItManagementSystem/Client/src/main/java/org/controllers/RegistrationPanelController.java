package org.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.models.User;
import org.requests.UserRequest;
import org.util.RootFinder;
import org.util.UserSession;
import org.util.ValidateRegistrationInput;

import java.io.IOException;
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
            boolean ok = true;
            ValidateRegistrationInput validator = new ValidateRegistrationInput();
            ArrayList<Boolean> inputs = validator.validateInputs(login.getText(), password.getText(),firstName.getText(),lastName.getText(),email.getText(),age.getText(),country.getText(),city.getText());
            int i = 0;
            List<ImageView> imageViews = List.of(out1, out2, out3, out4, out5, out6, out7, out8);
            for (ImageView imageView : imageViews) {
                if(!inputs.get(i)) {
                    imageView.setImage(new Image("/images/cross.png"));
                }
                else imageView.setImage(new Image("/images/tick.png"));
                imageView.setOpacity(1);
                if(!inputs.get(i)) {
                    ok = false;
                }
                i++;

            }
            if(ok) {
            User user = new User(login.getText(),
                        password.getText(),
                        lastName.getText()+ " " +firstName.getText(),
                        email.getText(),
                        Integer.parseInt(age.getText()),
                        country.getText(),
                        city.getText());
            user.setConfirmed(true);
            System.out.println(user);
            User userfromback = UserRequest.findUserByName(lastName.getText()+ " " +firstName.getText());
            if(userfromback != null) {
                if(userfromback.isConfirmed()){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Информация");
                    alert.setHeaderText("Вы уже зарегистрированы");
                    alert.setContentText("Авторизуйтесь");

                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image("/images/logo.png"));

                    alert.showAndWait();
                }
                else {
                    try {
                        user.setRole(userfromback.getRole());
                        user.setId(userfromback.getId());
                        UserSession.setCurrentUser(user);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ConfirmRegistration.fxml"));
                        Pane pane = (Pane) loader.load();
                        Scene scene = new Scene(pane);
                        Stage stage = (Stage) lastName.getScene().getWindow();
                        stage.setScene(scene);
                        stage.centerOnScreen();
                        stage.setTitle("It Management System");
                        stage.getIcons().add(new Image("/images/Main.png"));
                        stage.setResizable(false);
                    } catch (IOException e) {
                        System.out.println("ошибка загрузки файла в RegistrationPanelController");
                    }
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Информация");
                alert.setHeaderText("Пользователь не найден");
                alert.setContentText("Пользователь с именем "+user.getFullName()+ "  не существует в системе.");

                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("/images/logo.png"));

                alert.showAndWait();
            }
            }
        });
    }
}