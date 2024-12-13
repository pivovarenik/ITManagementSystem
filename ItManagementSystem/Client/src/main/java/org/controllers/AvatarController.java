package org.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import org.models.Chat;
import org.models.Role;
import org.models.User;
import org.requests.ChatRequest;
import org.requests.UserRequest;
import org.requests.RoleRequest;
import org.util.RootFinder;
import org.util.UserSession;

import java.io.IOException;
import java.time.Instant;

public class AvatarController {
    @FXML
    private Circle profilePic;
    @FXML
    private Label fullNameLabel;

    public void setAvatar(String name, String imageUrl) {
        fullNameLabel.setText(name);
        fullNameLabel.setWrapText(true);
        fullNameLabel.setMaxWidth(120);
        fullNameLabel.setAlignment(Pos.CENTER);
        fullNameLabel.setTextAlignment(TextAlignment.CENTER);
        Image image;
        if (imageUrl != null && !imageUrl.isEmpty()) {
            image = new Image(imageUrl);
        } else {
            image = new Image("/images/baseIcon.png");
        }
        Image croppedImage = cropToSquare(image);
        profilePic.setFill(new ImagePattern(croppedImage));
        profilePic.setOnMouseReleased(event -> {
            Node currentNode = profilePic;
            Parent root = RootFinder.findRoot(currentNode);
            if (root instanceof Pane) {
                Pane rootPane = (Pane) root;
                try {
                    showUserCard(rootPane);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showUserCard(Pane rootPane) throws Exception {
        User user = UserRequest.findUserByName(fullNameLabel.getText());
        System.out.println(user);
        if (user == null) {
           addNewUser();
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserInfoModal.fxml"));
            Pane newPane = (Pane) loader.load();
            UserInfoModalController controller = loader.getController();
            try {
                controller.setUserData(user);
                StackPane modalLayer = new StackPane();
                modalLayer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
                modalLayer.setPrefSize(rootPane.getWidth(), rootPane.getHeight());
                modalLayer.setAlignment(Pos.CENTER);
                modalLayer.getChildren().add(newPane);
                modalLayer.setOnMouseClicked(event -> {
                    if (event.getTarget() == modalLayer) {
                        rootPane.getChildren().remove(modalLayer);
                        controller.handleSave();
                        fullNameLabel.setText(controller.getUser().getFullName());
                        Image img = new Image(controller.getUser().getprofilePictureUrl());
                        Image newimage = cropToSquare(img);
                        profilePic.setFill(new ImagePattern(newimage));
                    }
                });
                rootPane.getChildren().add(modalLayer);

            } catch (NullPointerException e) {
                System.out.println("Ошибка в аватар контроллере");
                e.printStackTrace();
            }

        }
    }

    public void addNewUser() {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/NewUserInfoEditModal.fxml"));
            Pane pane = (Pane) loader.load();
            Node curroot = RootFinder.findRoot(fullNameLabel);
            Pane root = (Pane) curroot;
            StackPane modalLayer = new StackPane();
            modalLayer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
            modalLayer.setPrefSize(root.getWidth(), root.getHeight());
            modalLayer.setAlignment(Pos.CENTER);

            modalLayer.getChildren().add(pane);

            root.getChildren().add(modalLayer);
        }
        catch (IOException e){
            System.out.println("addNewUser Error");
            e.printStackTrace();
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
    public void disable(){
        profilePic.setOnMouseReleased(event -> {
            Node node = RootFinder.findRoot(profilePic);
            Pane pane = (Pane) node;
            pane.getChildren().removeLast();
            Chat chat = new Chat();
            chat.setCreatedAt(Instant.now());
            chat.setUserOne(UserSession.getCurrentUser());
            chat.setUserTwo(UserRequest.findUserByName(fullNameLabel.getText()));
            System.out.println(ChatRequest.createNewChat(chat));
        });
    }
}
