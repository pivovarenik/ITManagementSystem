package org.controllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.models.Chat;
import org.models.Message;
import org.models.User;
import org.requests.MessageRequest;
import org.requests.UserRequest;
import org.ui.AvatarFactory;
import org.util.LocalInstantAdapter;
import org.util.RootFinder;
import org.util.UserSession;
import org.requests.ChatRequest;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

public class ChatController {
    @FXML
    public HBox myAccount1;
    @FXML
    public Circle myprofPic1;
    @FXML
    public Label accName1;
    @FXML
    public Label accJob1;
    @FXML
    public TextField messageInput;
    @FXML
    public SplitPane mainPane;
    @FXML
    public VBox chatMessages;
    @FXML
    public Button addBut;
    @FXML
    public Button deleteBut;
    @FXML
    private ImageView sendIcon;
    @FXML
    private VBox mainContainer;
    @FXML
    public ScrollPane chatScrollPane;

    private User curUser;
    private List<Chat> chats;
    private Chat selectedChat;
    private User anotherUser;
    @FXML
    public void initialize() {
        curUser = UserSession.getCurrentUser();
        chats = ChatRequest.getUserChats(curUser);
        myAccount1.setVisible(false);
        sendIcon.setDisable(true);
        messageInput.setDisable(true);
        mainPane.setDividerPositions(0.3);
        sendIcon.setOnMouseClicked(event -> sendMessage());
        messageInput.setOnAction(event -> sendMessage());
        if(selectedChat == null){
            deleteBut.setDisable(true);
        }
        else deleteBut.setDisable(false);
        if(!chats.isEmpty()){
            try{
                for(Chat chat : chats){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ChatWindow.fxml"));
                    Pane pane =(Pane) loader.load();
                    ChatWindowController controller = loader.getController();
                    controller.loadChat(chat);
                    controller.setChatScrollPane((VBox) chatScrollPane.getContent());
                    pane.setOnMouseClicked(event -> {
                        for (Node otherPane : mainContainer.getChildren()) {
                            Pane otherPane1 = (Pane) otherPane;
                            otherPane1.getStyleClass().remove("selected");
                        }
                        if (!pane.getStyleClass().contains("selected")) {
                            pane.getStyleClass().add("selected");
                        }
                        controller.clear();
                        myAccount1.setVisible(true);
                        anotherUser = controller.getAnotherUser();
                        accName1.setText(anotherUser.getUsername());
                        accJob1.setText(anotherUser.getRole().getRoleName());
                        Image img= cropToSquare(new Image(anotherUser.getprofilePictureUrl()));
                        myprofPic1.setFill(new ImagePattern(img));
                        messageInput.setDisable(false);
                        sendIcon.setDisable(false);
                        controller.loadMessages(chatScrollPane);
                        selectedChat = controller.getChat();
                        deleteBut.setDisable(false);
                    });

                    mainContainer.getChildren().add(pane);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Ошибка в чатконтроллере");
            }
        }
        else{
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/NothingHere.fxml"));
                Pane pane = (Pane) loader.load();
                mainContainer.getChildren().add(pane);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        addBut.setOnAction(event -> {
            Set<Integer> existingUserIds = new HashSet<>();
            for (Chat chat : chats) {
                existingUserIds.add(chat.getUserOne().getId());
                existingUserIds.add(chat.getUserTwo().getId());
            }

            List<User> allUsers = UserRequest.findAllBut(existingUserIds);
            Node node = RootFinder.findRoot(mainPane);
            Pane root = (Pane) node;

            TilePane userPane = new TilePane();
            userPane.setHgap(10);
            userPane.setVgap(10);
            userPane.setAlignment(Pos.CENTER);
            userPane.setPrefColumns(3);
            userPane.setPrefHeight(83);
            userPane.setPrefWidth(440);
            userPane.setMaxHeight(450);
            userPane.setMaxWidth(950);
            userPane.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-border-radius: 10; -fx-background-radius: 10;");

            StackPane modalLayer = new StackPane();
            modalLayer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
            modalLayer.setPrefSize(root.getWidth(), root.getHeight());
            modalLayer.setAlignment(Pos.CENTER);
            modalLayer.setOnMouseClicked(event2 -> {
                if (!userPane.contains(event2.getX(), event2.getY())) { // Если клик не по панелям
                    root.getChildren().remove(modalLayer);
                }
            });
            modalLayer.getChildren().add(userPane);
            root.getChildren().add(modalLayer);
            for (User user : allUsers) {
                Node avatar = AvatarFactory.createBasicAvatar(user);
                if (avatar != null) {
                    userPane.getChildren().add(avatar);
                }
            }
        });
        deleteBut.setOnAction(event -> {
            if(selectedChat!=null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Подтверждение удаления");
                alert.setHeaderText(null);
                alert.setContentText("Вы действительно хотите удалить этот чат?");

                ButtonType yesButton = new ButtonType("Да");
                ButtonType noButton = new ButtonType("Нет");

                alert.getButtonTypes().setAll(yesButton, noButton);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == yesButton) {
                    if(ChatRequest.deleteChat(selectedChat)){
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("Удаление прошло успешно");
                        alert1.setHeaderText(null);
                        alert1.setContentText("Элемент был успешно удален.");
                        alert1.showAndWait();
                    }
                    else{
                        Alert alert1 = new Alert(Alert.AlertType.ERROR);
                        alert1.setTitle("Произошла ошибка");
                        alert1.setHeaderText(null);
                        alert1.setContentText("Серверная ошибка.");
                        alert1.showAndWait();
                    }
                }
            }
        });

        myAccount1.setOnMouseClicked(event -> {
            try{
                Pane mainPane = (Pane) RootFinder.findRoot(mainContainer);
                showUserCard(mainPane);
            }
            catch(Exception e){
                System.out.println("Ошибка подгрузки карточки");
                e.printStackTrace();
            }
        });
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
    public void sendMessage() {
        String message = messageInput.getText();
        if (message != null && !message.trim().isEmpty()) {
            System.out.println("Message sent: " + message);
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/OutgoingMessage.fxml"));
                Pane pane = (Pane) loader.load();
                Message msg = new Message();
                msg.setMessage(message);
                msg.setSender(curUser);
                msg.setSentAt(Instant.now());
                msg.setChat(selectedChat);
                System.out.println(selectedChat);
                boolean b = MessageRequest.addNewMsg(msg);
                if (b){
                    OutgoingMessageController out = loader.getController();
                    out.setContent(message, LocalInstantAdapter.format(Instant.now()));
                    chatMessages.getChildren().add(pane);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Информация");
                    alert.setHeaderText("Не получилось отправить сообщение");
                    alert.setContentText("Попробуйте позже");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image("/images/logo.png"));
                    alert.showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            messageInput.clear();
        } else {
            System.out.println("Cannot send an empty message!");
        }

    }
    private void showUserCard(Pane rootPane) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserInfoModal.fxml"));
        Pane newPane = (Pane) loader.load();
        UserInfoModalController controller = loader.getController();
        controller.removeMsgIcon();
        try {
            controller.setUserData(anotherUser);
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
