package org.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.controllers.AvatarController;
import org.models.User;

import java.io.IOException;

public class AvatarFactory {
    public static Node createAvatar(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(AvatarFactory.class.getResource("/views/Avatar.fxml"));
            Node avatarNode = loader.load();
            AvatarController controller = loader.getController();
            controller.setAvatar(user.getFullName(), user.getProfile_picture());
            return avatarNode;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Node createEmptyAvatar(){
        try {
            FXMLLoader loader = new FXMLLoader(AvatarFactory.class.getResource("/views/Avatar.fxml"));
            Node avatarNode = loader.load();
            AvatarController controller = loader.getController();
            controller.setAvatar("Добавить пользователя", "/images/add_user.png");
            return avatarNode;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
