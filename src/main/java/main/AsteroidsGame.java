package main;

import controller.NewGameWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AsteroidsGame extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String cssNewGameWindow = getClass().getResource("/styles/new_game_window_style.css").toExternalForm();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AsteroidsNewGame.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);

        NewGameWindow newGameWindow = loader.getController();
        newGameWindow.setStage(stage);
        newGameWindow.setScene(scene);

        scene.getStylesheets().add(cssNewGameWindow);
        stage.setTitle("Asteroids");
        stage.setScene(scene);
        stage.show();
    }
}
