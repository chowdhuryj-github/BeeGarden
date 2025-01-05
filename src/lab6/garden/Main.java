/*
 * Course: SWE2410
 * Fall 2024
 * Lab 6: Planting the Garden
 * Name: Jawadul Chowdhury & Mathias Galvan
 * Submission Date: 10/21/24
 */
package lab6.garden;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

/**
 * Main class for the project, initializes application
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("bee_simulator.fxml"));
        primaryStage.setTitle("Bee Runner 2049");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
