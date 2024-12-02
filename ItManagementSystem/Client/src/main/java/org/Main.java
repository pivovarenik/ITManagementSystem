package org;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.models.Person;

public class Main extends Application {

    private BorderPane rootLayout;

    public Main() {
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("It Management System");
        primaryStage.getIcons().add(new Image("/images/Main.png"));
        primaryStage.setResizable(false);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/AuthorizationLayout.fxml"));
            Pane l = (Pane) loader.load();
            Scene scene = new Scene(l);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}