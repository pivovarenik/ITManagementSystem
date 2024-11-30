package org;


import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        primaryStage.setTitle("Example Application");
        primaryStage.setScene(new Scene(root,388,275));
        primaryStage.show();
    }
    public static void main(String[] args) {launch(args);}
}