package view;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {
    private Stage primaryStage;
    private Scene mainScene;
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Simulaattori");
        initLayout();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
    }
    /**
     * Initializes the layout.
     */
    public void initLayout() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view.fxml"));
            mainScene = new Scene(fxmlLoader.load());
            primaryStage.setScene(mainScene);
            primaryStage.show();
            System.out.println("Ensimmäiset" + primaryStage.toString());
            System.out.println("Ensimmäiset" + primaryStage.getScene().toString());
            GUIKontrolleri controller = fxmlLoader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Stage  getPrimaryStage() {
        return primaryStage;
    }
    public Scene getMainScene() {
        return mainScene;
    }
}