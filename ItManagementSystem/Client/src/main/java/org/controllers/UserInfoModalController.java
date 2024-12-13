package org.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import org.requests.RoleRequest;
import org.requests.UserRequest;
import org.util.AgeParser;
import org.util.RootFinder;
import org.util.UserSession;

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
    @FXML
    public Label errorMsg;

    private User user;
    private String pathToProfilePic = "";
    public User getUser() {
        return user;
    }
    public void setUserData(User uuser) {
        this.user = uuser;
        fullNameId.setText(user.getFullName());
        email.setText(user.getEmail());
        this.role.setText(user.getRole().getRoleName());
        username.setText(user.getUsername());
        age.setText(String.valueOf(user.getAge()));
        country.setText(user.getCountry());
        if(country.getText().equals("")){
            deactivate();
        }
        city.setText(user.getCity());
        phone.setText(user.getPhone());
        Image image;
        if (user.getprofilePictureUrl() != null && !user.getprofilePictureUrl().isEmpty()) {
            image = new Image(user.getprofilePictureUrl());
            pathToProfilePic = user.getprofilePictureUrl();
        } else {
            image = new Image("/images/baseIcon.png");
            pathToProfilePic = "/images/baseIcon.png";
        }
        Image croppedImage = cropToSquare(image);
        userPhoto.setFill(new ImagePattern(croppedImage));

        User curUser = UserSession.getCurrentUser();
        if( user.getRole().getId() == 1 && curUser.getRole().getId() != 1 ){
            removeEditingIcons();
        }
        if(curUser.getRole().getId()!=1 && !user.getFullName().equals(curUser.getFullName())){
            removeEditingIcons();
        }
        if(curUser.getFullName().equals(user.getFullName())){
            removeMsgIcon();
        }
        if(curUser.getRole().getId()!=1){
            deleteIcon.setVisible(false);
            deleteIcon.setOpacity(0);
        }
        if(user.getRole().getId()==1){
            deleteIcon.setVisible(false);
            deleteIcon.setOpacity(0);
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

                editController.setUserData(this.user);
                Node rootPane = RootFinder.findRoot(city);
                Pane root = (Pane) rootPane;
                editController.setOnSaveCallback(updatedUser -> {
                    setUserData(updatedUser);
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
    public void handleSave() {
        user.setFullName(fullNameId.getText().isEmpty() ? user.getFullName() : fullNameId.getText());
        user.setEmail(email.getText().isEmpty() ? user.getEmail() : email.getText());
        user.setAge(age.getText().isEmpty() ? user.getAge() : AgeParser.parseageOrDefault(age.getText(), user.getAge()));
        user.setCountry(country.getText().isEmpty() ? user.getCountry() : country.getText());
        user.setCity(city.getText().isEmpty() ? user.getCity() : city.getText());
        user.setPhone(phone.getText().isEmpty() ? user.getPhone() : phone.getText());
        System.out.println(pathToProfilePic);
        user.setprofilePictureUrl(pathToProfilePic);
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
                    Node mainNode = RootFinder.findRoot(city);
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
    private void deactivate() {
        errorMsg.setOpacity(1);
        errorMsg.setDisable(false);
        editIcon.setDisable(true);
        msgIcon.setDisable(true);
        errorMsg.setStyle("-fx-font-size: 20px; -fx-text-fill: red; -fx-font-weight: bold;");
        errorMsg.setLayoutX(40);
        errorMsg.setAlignment(Pos.CENTER);
    }
    public void removeMsgIcon(){
        msgIcon.setOpacity(0);
        msgIcon.setDisable(true);
    }
    public void removeEditingIcons(){
        editIcon.setOpacity(0);
        editIcon.setDisable(true);
        deleteIcon.setOpacity(0);
        deleteIcon.setDisable(true);
    }
}

