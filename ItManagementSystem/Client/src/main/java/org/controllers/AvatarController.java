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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import org.models.Role;
import org.models.User;
import org.requests.UserRequest;
import org.requests.RoleRequest;

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
            Parent root = findRoot(currentNode);
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
        if (user == null) {
              /*user = new User();
                    Role role = new Role();
                    controller.setUserData(user,role);
                    StackPane modalLayer = new StackPane();
                    modalLayer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
                    modalLayer.setPrefSize(rootPane.getWidth(), rootPane.getHeight());
                    modalLayer.setAlignment(Pos.CENTER);
                    modalLayer.getChildren().add(newPane);

                    modalLayer.setOnMouseClicked(event -> {
                        if (event.getTarget() == modalLayer) {
                            rootPane.getChildren().remove(modalLayer);
                        }
                    });

                    rootPane.getChildren().add(modalLayer);*/
            /*controller.createNewUser();*/
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserInfoModal.fxml"));
            Pane newPane = (Pane) loader.load();
            UserInfoModalController controller = loader.getController();

            try {
                if (user != null) {
                    int id = user.getRole_id();
                    Role role = RoleRequest.findRoleById(id);
                    controller.setUserData(user, role);
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
                            Image img = new Image(controller.getUser().getProfile_picture());
                            Image newimage = cropToSquare(img);
                            profilePic.setFill(new ImagePattern(newimage));
                        }
                    });
                    rootPane.getChildren().add(modalLayer);
                }
            } catch (NullPointerException e) {
                System.out.println("Ошибка в аватар контроллере");
                e.printStackTrace();
            }

        }
    }


    private Parent findRoot(Node currentNode) {
        Parent parent = currentNode.getParent();
        while (parent != null && parent.getParent() != null) {
            parent = parent.getParent();
        }
        return parent;
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

}
