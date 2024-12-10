package org.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import org.models.Project;
import org.models.User;
import org.requests.ProjectRequest;
import org.requests.UserRequest;
import org.ui.AvatarFactory;
import org.ui.CleanScreen;
import org.ui.ProjectBoxFactory;

import java.util.List;

public class StaffController {
    @FXML
    public TilePane staffContainer;
    @FXML
    private void initialize() {
        List<User> users = UserRequest.findAll();
        System.out.println(users);
        try {
            if (users != null && !users.isEmpty()) {
                for (User user : users) {
                    Node avatar = AvatarFactory.createAvatar(user);
                    if (avatar != null) {
                        staffContainer.getChildren().add(avatar);
                    }
                }
                Node avatar = AvatarFactory.createEmptyAvatar();
                staffContainer.getChildren().add(avatar);
            } else {
                Node avatar = AvatarFactory.createEmptyAvatar();
                staffContainer.getChildren().add(avatar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
