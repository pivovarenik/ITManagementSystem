package org.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.models.Role;
import org.models.User;
import org.util.AgeParser;

import java.io.File;
import java.util.function.Consumer;

public class UserInfoEditModalController {
    @FXML
    public Pane editUserCard;
    @FXML
    public Circle userPhoto;
    @FXML
    public ImageView saveIcon;
    @FXML
    public TextField roleField;
    @FXML
    public ImageView cancelIcon;
    @FXML
    public TextField usernameField;
    @FXML
    public TextField emailField;
    @FXML
    public TextField ageField;
    @FXML
    public TextField countryField;
    @FXML
    public TextField cityField;
    @FXML
    public TextField phoneField;
    @FXML
    public Label fullNameId;

    private Consumer<User> onSaveCallback;

    private User user;
    private Role curRole;
    private String pathToProfilePic = "";
    @FXML
    public void initialize() {
        Platform.runLater(editUserCard::requestFocus);
        userPhoto.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Выберите изображение");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Изображения", "*.png", "*.jpg", "*.jpeg", "*.gif")
            );
            Stage stage = (Stage) userPhoto.getScene().getWindow();
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                pathToProfilePic = file.toURI().toString();
                Image image = new Image(file.toURI().toString());
                userPhoto.setFill(new ImagePattern(image));
            }
        });
    }
    public void setUserData(User user, Role role) {
        this.user = user;
        this.curRole = role;
        fullNameId.setText(user.getFullName());
        emailField.setText(user.getEmail());
        emailField.setPromptText("email");
        this.roleField.setText(role.getRoleName());
        this.roleField.setPromptText("role");
        usernameField.setText(user.getUsername());
        usernameField.setPromptText("username");
        ageField.setText(String.valueOf(user.getAge()));
        ageField.setPromptText(String.valueOf(user.getAge()));
        countryField.setText(user.getCountry());
        countryField.setPromptText(user.getCountry());
        cityField.setText(user.getCity());
        cityField.setPromptText(user.getCity());
        phoneField.setText(user.getPhone());
        phoneField.setPromptText(user.getPhone());
        Image image;
        if (user.getProfile_picture() != null && !user.getProfile_picture().isEmpty()) {
            image = new Image(user.getProfile_picture());
            pathToProfilePic = user.getProfile_picture();
        } else {
            image = new Image("/images/baseIcon.png");
        }
        Image croppedImage = cropToSquare(image);
        userPhoto.setFill(new ImagePattern(croppedImage));
    }
    private Image cropToSquare(Image image) {
        double minSize = Math.min(image.getWidth(), image.getHeight());
        double x = (image.getWidth() - minSize) / 2;
        double y = (image.getHeight() - minSize) / 2;

        WritableImage croppedImage = new WritableImage((int) minSize, (int) minSize);
        PixelReader pixelReader = image.getPixelReader();
        if (pixelReader != null) {
            croppedImage.getPixelWriter().setPixels(
                    0, 0, (int) minSize, (int) minSize,
                    pixelReader, (int) x, (int) y
            );
        }

        return croppedImage;
    }
    public void setOnSaveCallback(Consumer<User> callback) {
        this.onSaveCallback = callback;
    }
    @FXML
    private void handleSave() {
        user.setFullName(fullNameId.getText().isEmpty() ? user.getFullName() : fullNameId.getText());
        user.setEmail(emailField.getText().isEmpty() ? user.getEmail() : emailField.getText());
        user.setAge(ageField.getText().isEmpty() ? user.getAge() : AgeParser.parseageOrDefault(ageField.getText(), user.getAge()));
        user.setCountry(countryField.getText().isEmpty() ? user.getCountry() : countryField.getText());
        user.setCity(cityField.getText().isEmpty() ? user.getCity() : cityField.getText());
        user.setPhone(phoneField.getText().isEmpty() ? user.getPhone() : phoneField.getText());
        user.setProfile_picture((pathToProfilePic == null || pathToProfilePic.isEmpty()) ? user.getProfile_picture() : pathToProfilePic);
        user.setUsername(usernameField.getText().isEmpty() ? user.getUsername() : usernameField.getText());

        if (onSaveCallback != null) {
            onSaveCallback.accept(user);
        }
    }
    private Parent findRoot(Node currentNode) {
        Parent parent = currentNode.getParent();
        while (parent != null && parent.getParent() != null) {
            parent = parent.getParent();
        }
        return parent;
    }
    public void goBack() {
        Node node = findRoot(phoneField);
        Pane pane = (Pane) node;
        pane.getChildren().removeLast();
    }
}
