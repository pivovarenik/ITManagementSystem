package org.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.models.Role;
import org.models.User;
import org.requests.RoleRequest;
import org.requests.UserRequest;
import org.util.RootFinder;

import java.io.File;
import java.util.List;

public class NewUserInfoEditModalController {

    @FXML
    public ImageView saveIcon;
    @FXML
    public TextField MainName;
    @FXML
    public ComboBox roleField;
    @FXML
    public ImageView cancelIcon;

    private List<Role> all_roles;
    @FXML
    public void initialize() {
        all_roles = RoleRequest.getAll();
        for (Role role : all_roles) {
            roleField.getItems().add(role.getRoleName());
        }
        roleField.getSelectionModel().selectFirst();
    }

    @FXML
    public void handleSave(){
        if (isInputValid()) {
            User user = new User();
            user.setFullName(MainName.getText());
            for (Role role : all_roles) {
                if (role.getRoleName().equals(roleField.getSelectionModel().getSelectedItem())) {
                    user.setRole(role);
                }
            }
            user.setUsername(MainName.getText());
            user.setEmail(MainName.getText());
            if(UserRequest.createNewUser(user)){
                try{
                    Node node = RootFinder.findRoot(MainName);
                    Pane root = (Pane) node;
                    root.getChildren().removeLast();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Staff.fxml"));
                    Pane pane = (Pane) fxmlLoader.load();
                    root.getChildren().add(pane);
                } catch (Exception e) {
                    System.out.println("Ошибка при выходе из создания клиента");
                    e.printStackTrace();
                }
            }
        }

    }
    public void goBack() {
        Node node = RootFinder.findRoot(MainName);
        Pane pane = (Pane) node;
        pane.getChildren().removeLast();
    }

    private boolean isInputValid() {
        boolean isValid = true;
        if (MainName.getText() == null || !MainName.getText().matches("\\S+\\s\\S+")) {
            setError(MainName);
            isValid = false;
        }
        return isValid;
    }

    private void setError(TextField field) {
        field.clear();
        field.setPromptText("Некорректный ввод");
        field.setStyle("-fx-prompt-text-fill: red;");
    }
}
