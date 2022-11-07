package com.example.softmeth_project3;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
/**
 Initializes a new Gym Manager GUI and runs the program.
 @author William Chen
 @author Kalrav Pandit
 */
public class GymManagerMain extends Application {

    /**
     * Starts the gym manager GUI.
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("GymManagerView.fxml"));
            Scene scene = new Scene(root,400,400);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Gym Manager");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}