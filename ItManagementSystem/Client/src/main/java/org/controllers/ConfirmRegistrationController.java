package org.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.models.User;
import org.requests.UserRequest;
import org.util.UserSession;

import java.io.File;
import java.io.IOException;

public class ConfirmRegistrationController {

    @FXML
    public Button logButton;
    @FXML
    public TextField phoneField;
    @FXML
    public ImageView arrowBack;
    @FXML
    public ImageView profilePic;
    @FXML
    public AnchorPane mainCard;
    @FXML
    public Label phoneLabel;
    private boolean isShifted = false;
    private String pathtoProfilepic = "";
    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            mainCard.requestFocus();
        });
        System.out.println(UserSession.getCurrentUser());
        profilePic.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Выберите изображение");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Изображения", "*.png", "*.jpg", "*.jpeg", "*.gif")
            );
            Stage stage = (Stage) profilePic.getScene().getWindow();
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                Image image = new Image(file.toURI().toString());
                pathtoProfilepic = file.toURI().toString();
                profilePic.setImage(image);
            }
        });
        arrowBack.setOnMouseClicked(event -> {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AuthorizationLayoutMain.fxml"));
                AnchorPane pane = (AnchorPane) loader.load();
                Scene scene = new Scene(pane);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                stage.centerOnScreen();
                stage.setTitle("It Management System");
                stage.getIcons().add(new Image("/images/Main.png"));
                stage.setResizable(false);
                Stage currentStage = (Stage) phoneField.getScene().getWindow();
                currentStage.close();
            }
            catch (IOException e){
                e.printStackTrace();
                System.out.println("Ошибка при возвращении назад");
            }
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
            if(phoneField.getText().equals("") || !validPhoneInput(phoneField.getText())) {
                phoneField.requestFocus();
                phoneField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                phoneLabel.setText("Введите корректный номер");
                if (!isShifted) {
                    double x = phoneLabel.getLayoutX();
                    phoneLabel.setLayoutX(x - 20);
                    isShifted = true;
                }
                phoneField.setText("");
            }
            else{
                User user = UserSession.getCurrentUser();
                user.setPhone(phoneField.getText());
                user.setprofilePictureUrl(pathtoProfilepic);
                UserSession.setCurrentUser(user);
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainWindow.fxml"));
                    Pane pane = (Pane) loader.load();
                    Scene scene = new Scene(pane);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                    stage.centerOnScreen();
                    stage.setTitle("It Management System");
                    stage.getIcons().add(new Image("/images/Main.png"));
                    stage.setResizable(false);
                    UserSession.setCurrentUser(UserRequest.register(user));
                    System.out.println(UserSession.getCurrentUser());
                    Stage currentStage = (Stage) phoneField.getScene().getWindow();
                    currentStage.close();
                } catch (Exception e) {
                    System.out.println("ошибка в подгрузке главного окна confirmRegistrationController");
                    e.printStackTrace();
                }
            }
        });
    }
    private boolean validPhoneInput(String phoneInput){
        String phoneRegex = "^\\+?\\d{10,15}$";
        return phoneInput.matches(phoneRegex);
    }
}
