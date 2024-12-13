package org.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.models.Project;
import org.models.Role;
import org.models.User;
import org.requests.ProjectRequest;
import org.requests.RoleRequest;
import org.requests.UserRequest;
import org.ui.ProjectBoxFactory;
import org.util.UserSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainWindowController {
    @FXML
    public HBox myAccount;
    @FXML
    public Circle myprofPic;
    @FXML
    public Label accName;
    @FXML
    public Label accJob;
    @FXML
    private ImageView logo;
    @FXML
    private HBox main;
    @FXML
    private HBox projects;
    @FXML
    private HBox staff;
    @FXML
    private HBox chat;
    @FXML
    private HBox departments;
    @FXML
    private HBox attendance;
    @FXML
    private HBox menu;
    @FXML
    private Pane mainPane;
    @FXML
    private Label currentLabel;
    @FXML
    private Button reportBut;

    private List<Node> initialChildren;
    private VBox projectVBox;
    private ScrollPane contentPane;
    private Pane centerPane;
    private Node currentPane = null;
    private User curUser;

    @FXML
    private void initialize() {
        curUser = UserSession.getCurrentUser();
        Image image = new Image(curUser.getprofilePictureUrl().isEmpty() ? "/images/baseIcon.png" : curUser.getprofilePictureUrl());
        Image croppedImage = cropToSquare(image);
        myprofPic.setFill(new ImagePattern(croppedImage));
        accJob.setText(curUser.getRole().getRoleName());
        myAccount.setOnMouseClicked(event -> {
            try{
                showUserCard(mainPane);
            }
            catch(Exception e){
                System.out.println("Ошибка подгрузки карточки");
                e.printStackTrace();
            }
        });
        accName.setText(curUser.getFullName());
        initialChildren = new ArrayList<>(mainPane.getChildren());
        HBox[] hboxItems = {main, projects, staff, chat, departments, attendance};
        for (HBox hbox : hboxItems) {
            hbox.setOnMouseClicked(event -> {
                for (HBox item : hboxItems) {
                    item.getStyleClass().remove("selected");
                }
                hbox.getStyleClass().add("selected");
            });
        }
        main.getStyleClass().add("selected");
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AdminMainPage.fxml"));
            Node pane = loader.load();
            if (currentPane != null) {
                mainPane.getChildren().remove(currentPane);
            }
            currentLabel.setText("Главная");
            centerPane = (Pane) pane.lookup("#mainPane");
            contentPane = (ScrollPane) pane.lookup("#contentPane");
            projectVBox = (VBox) pane.lookup("#projectsContainer");
            mainPane.getChildren().add(pane);
            currentPane = pane;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        User user = UserSession.getCurrentUser();
        main.setOnMouseClicked(event -> {
            resetMainWindow();
            for (HBox item : hboxItems) {
                item.getStyleClass().remove("selected");
            }
            main.getStyleClass().add("selected");
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AdminMainPage.fxml"));
                Node pane = loader.load();
                if (currentPane != null) {
                    mainPane.getChildren().remove(currentPane);
                }
                currentLabel.setText("Главная");
                centerPane = (Pane) pane.lookup("#mainPane");
                contentPane = (ScrollPane) pane.lookup("#contentPane");
                projectVBox = (VBox) pane.lookup("#projectsContainer");
                mainPane.getChildren().add(pane);
                currentPane = pane;
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });
        projects.setOnMouseClicked(event -> {
            resetMainWindow();
            for (HBox item : hboxItems) {
                item.getStyleClass().remove("selected");
            }
            projects.getStyleClass().add("selected");
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Projects.fxml"));
                Node pane = loader.load();
                if (currentPane != null) {
                    mainPane.getChildren().remove(currentPane);
                }
                currentLabel.setText("Проекты");
                centerPane = (Pane) pane.lookup("#mainPane");
                contentPane = (ScrollPane) pane.lookup("#contentPane");
                projectVBox = (VBox) pane.lookup("#projectsContainer");
                mainPane.getChildren().add(pane);
                currentPane = pane;
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });
        staff.setOnMouseClicked(event -> {
            resetMainWindow();
            for (HBox item : hboxItems) {
                item.getStyleClass().remove("selected");
            }
            staff.getStyleClass().add("selected");
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Staff.fxml"));
                Node pane = loader.load();
                currentLabel.setText("Сотрудники");
                if (currentPane != null) {
                    mainPane.getChildren().remove(currentPane);
                }
                mainPane.getChildren().add(pane);
                currentPane = pane;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        chat.setOnMouseClicked(event -> {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Chat.fxml"));
                Node pane = loader.load();
                currentLabel.setText("Чат");
                if (currentPane != null) {
                    mainPane.getChildren().remove(currentPane);
                }
                mainPane.getChildren().add(pane);
                currentPane = pane;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
    private void resetMainWindow() {
        mainPane.getChildren().retainAll(initialChildren);
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
    private void showUserCard(Pane rootPane) throws Exception {
        User user = UserSession.getCurrentUser();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserInfoModal.fxml"));
        Pane newPane = (Pane) loader.load();
        UserInfoModalController controller = loader.getController();
        controller.removeMsgIcon();
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
                }
            });
            rootPane.getChildren().add(modalLayer);

        } catch (NullPointerException e) {
            System.out.println("Ошибка в аватар контроллере");
            e.printStackTrace();
        }

    }
}
