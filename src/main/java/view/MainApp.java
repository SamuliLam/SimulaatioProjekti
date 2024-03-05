package view;
import java.io.IOException;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class MainApp extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Simulaattori");

        initLayout();

    }

    /**
     * Initializes the layout.
     */
    public void initLayout() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view.fxml"));

            // Show the scene containing the root layout.
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}