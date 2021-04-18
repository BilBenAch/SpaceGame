package main;

import controller.AsteroidsNewGame;
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

        AsteroidsNewGame asteroidsNewGame = loader.getController();
        asteroidsNewGame.setStage(stage);
        asteroidsNewGame.setScene(scene);

        scene.getStylesheets().add(cssNewGameWindow);
        stage.setTitle("Asteroids");
        stage.setScene(scene);
        stage.show();

        //Ventana de juego
        /*
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameWindow.fxml"));

        Parent root = loader.load();

        GameWindow gameWindow = loader.getController();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(cssGameWindow);

        gameWindow.setScene(scene);
        gameWindow.setStage(stage);
        gameWindow.setPane(root);

        stage.setTitle("Asteroids");
        stage.setScene(scene);
        stage.show();*/
    }
}
