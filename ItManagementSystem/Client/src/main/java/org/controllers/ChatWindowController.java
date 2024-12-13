package org.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.models.Chat;
import org.models.Message;
import org.models.User;
import org.requests.MessageRequest;
import org.util.LocalInstantAdapter;
import org.util.UserSession;

import java.io.IOException;
import java.util.List;

public class ChatWindowController {
    @FXML
    public Pane pane;
    @FXML
    public Label name;
    @FXML
    public Label job;
    @FXML
    public Circle profile_pic;

    public VBox getChatScrollPane() {
        return chatScrollPane;
    }

    private VBox chatScrollPane;

    private Chat chat;
    private User anotherUser;

    public void loadChat(Chat chat){
        this.chat = chat;
        initialize();
    }
    public void initialize(){
        if (chat == null) {
            return;
        }

        User curUser = UserSession.getCurrentUser();
        System.out.println(chat.getUserOne());
        System.out.println(chat.getUserTwo());
        System.out.println(curUser);
        if(chat.getUserOne().getFullName().equals(curUser.getFullName())){
            anotherUser = chat.getUserTwo();
        }
        else{
            anotherUser = chat.getUserOne();
        }
        name.setText(anotherUser.getFullName());
        job.setText(anotherUser.getRole().getRoleName());
        Image img = cropToSquare(new Image(anotherUser.getprofilePictureUrl()));
        profile_pic.setFill(new ImagePattern(img));

    }
    public void loadMessages(ScrollPane pane1){
        List<Message> messages = MessageRequest.getChatMessages(chat);
        System.out.println();
        for(Message m : messages){
            if (m.getSender().getFullName().equals(anotherUser.getFullName())){
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/IncomingMessage.fxml"));
                    Pane pane = (Pane) loader.load();
                    IncomingMessageController inc = loader.getController();
                    inc.setContent(m.getMessage(), LocalInstantAdapter.format(m.getSentAt()));
                    chatScrollPane.getChildren().add(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/OutgoingMessage.fxml"));
                    Pane pane = (Pane) loader.load();
                    OutgoingMessageController out = loader.getController();
                    out.setContent(m.getMessage(), LocalInstantAdapter.format(m.getSentAt()));
                    chatScrollPane.getChildren().add(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
    public User getAnotherUser(){
        return anotherUser;
    }

    public void setChatScrollPane(VBox chatScrollPane) {
        this.chatScrollPane = chatScrollPane;
    }
    public void clear(){
        this.chatScrollPane.getChildren().clear();
    }
    public Chat getChat(){
        return chat;
    }
}
