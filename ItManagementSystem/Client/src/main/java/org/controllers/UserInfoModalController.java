package org.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.models.Role;
import org.models.User;
import org.requests.UserRequest;
import org.util.AgeParser;

import java.io.IOException;

public class UserInfoModalController {
    @FXML
    public Pane userInfoCard;
    @FXML
    public Circle userPhoto;
    @FXML
    public ImageView editIcon;
    @FXML
    public Label fullNameId;
    @FXML
    public Label role;
    @FXML
    public ImageView msgIcon;
    @FXML
    public Label username;
    @FXML
    public Label email;
    @FXML
    public Label age;
    @FXML
    public Label country;
    @FXML
    public Label city;
    @FXML
    public Label phone;
    @FXML
    public ImageView deleteIcon;

    private User user;
    private Role curRole;
    private String pathToProfilePic = "";
    public User getUser() {
        return user;
    }
    public void setUserData(User user, Role role) {
        this.user = user;
        this.curRole = role;
        fullNameId.setText(user.getFullName());
        email.setText(user.getEmail());
        this.role.setText(role.getRoleName());
        username.setText(user.getUsername());
        age.setText(String.valueOf(user.getAge()));
        country.setText(user.getCountry());
        city.setText(user.getCity());
        phone.setText(user.getPhone());
        Image image;
        if (user.getProfile_picture() != null && !user.getProfile_picture().isEmpty()) {
            image = new Image(user.getProfile_picture());
            pathToProfilePic = user.getProfile_picture();
        } else {
            image = new Image("/images/baseIcon.png");
            pathToProfilePic = "/images/baseIcon.png";
        }
        Image croppedImage = cropToSquare(image);
        userPhoto.setFill(new ImagePattern(croppedImage));
        if( user.getRole_id() == 1){
            deleteIcon.setVisible(false);
            deleteIcon.setDisable(true);
        }
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
    @FXML
    public void initialize() {

        deleteIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showConfirmationDialog();
            }
        });
        editIcon.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserInfoEditModal.fxml"));
                Pane editPane = loader.load();

                UserInfoEditModalController editController = loader.getController();

                editController.setUserData(this.user, this.curRole);
                Node rootPane = findRoot(city);
                Pane root = (Pane) rootPane;
                editController.setOnSaveCallback(updatedUser -> {
                    setUserData(updatedUser, curRole);
                    root.getChildren().removeLast();
                });

                StackPane modalLayer = new StackPane();
                modalLayer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
                modalLayer.setPrefSize(root.getWidth(), root.getHeight());
                modalLayer.setAlignment(Pos.CENTER);

                modalLayer.getChildren().add(editPane);

                root.getChildren().add(modalLayer);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private Parent findRoot(Node currentNode) {
        Parent parent = currentNode.getParent();
        while (parent != null && parent.getParent() != null) {
            parent = parent.getParent();
        }
        return parent;
    }
    public void handleSave() {
        user.setFullName(fullNameId.getText().isEmpty() ? user.getFullName() : fullNameId.getText());
        user.setEmail(email.getText().isEmpty() ? user.getEmail() : email.getText());
        user.setAge(age.getText().isEmpty() ? user.getAge() : AgeParser.parseageOrDefault(age.getText(), user.getAge()));
        user.setCountry(country.getText().isEmpty() ? user.getCountry() : country.getText());
        user.setCity(city.getText().isEmpty() ? user.getCity() : city.getText());
        user.setPhone(phone.getText().isEmpty() ? user.getPhone() : phone.getText());
        user.setProfile_picture(pathToProfilePic);
        user.setUsername(username.getText().isEmpty() ? user.getUsername() : username.getText());
        System.out.println(UserRequest.save(user));
    }
    private void showConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтвердите удаление");
        alert.setHeaderText("Вы действительно хотите удалить этого пользователя?");
        alert.setContentText("Это действие нельзя отменить.");


        ButtonType buttonTypeYes = new ButtonType("Да");
        ButtonType buttonTypeNo = new ButtonType("Нет");


        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);


        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                deleteUser();
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Staff.fxml"));
                    Pane pane = (Pane) loader.load();
                    Node mainNode = findRoot(city);
                    Pane root = (Pane) mainNode;
                    root.getChildren().removeLast();
                    root.getChildren().add(pane);
                }
               catch(IOException e){
                    e.printStackTrace();
               }
            }
        });
    }
    private void deleteUser() {
        handleSave();
        UserRequest.delete(user);
    }

    public void createNewUser() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/NewUserInfoEditModal.fxml"));
            Pane pane = (Pane) loader.load();
            Node rootNode = findRoot(city);
            Pane root = (Pane) rootNode;
            StackPane modalLayer = new StackPane();
            modalLayer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
            modalLayer.setPrefSize(root.getWidth(), root.getHeight());
            modalLayer.setAlignment(Pos.CENTER);
            modalLayer.getChildren().add(pane);
          /*  modalLayer.setOnMouseClicked(event -> {
                if (event.getTarget() == modalLayer) {
                    root.getChildren().remove(modalLayer);
                    controller.handleSave();
                    fullNameLabel.setText(controller.getUser().getFullName());
                    Image img = new Image(controller.getUser().getProfile_picture());
                    Image newimage = cropToSquare(img);
                    profilePic.setFill(new ImagePattern(newimage));
                }
            });*/
            root.getChildren().add(modalLayer);

            root.getChildren().add(pane);
        }
        catch(IOException e){
            System.out.println("ошибка при создании перчика");
            e.printStackTrace();
        }
    }
}

