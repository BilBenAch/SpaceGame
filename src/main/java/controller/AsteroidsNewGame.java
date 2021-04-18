package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.AsteroidsGame;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AsteroidsNewGame implements Initializable {

    Stage stage;
    Scene scene;

    String cssGameWindow = getClass().getResource("/styles/game_window_style.css").toExternalForm();
    GameWindow gameWindow;

    @FXML
    Text title;

    @FXML
    Button play_button;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title.setText("Asteroids");
    }

    public void onMouseClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameWindow.fxml"));
        Parent newRoot = loader.load();
        gameWindow = loader.getController();
        scene.getStylesheets().add(cssGameWindow);
        gameWindow.setScene(scene);
        gameWindow.setStage(stage);

        stage.getScene().setRoot(newRoot);

    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
