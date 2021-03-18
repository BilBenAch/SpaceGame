package controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sprites.Player;

import java.net.URL;
import java.util.ResourceBundle;

public class GameWindow implements Initializable {
    GraphicsContext gc;
    Player player;

    @FXML
    Canvas canvas;

    private AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            player.clear(gc);
            player.move();
            player.render(gc);
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        player = new Player(new Image("sprites/player_ship.png"));
        gc = canvas.getGraphicsContext2D();
        animationTimer.start();
    }
}
