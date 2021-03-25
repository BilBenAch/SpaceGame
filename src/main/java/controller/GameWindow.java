package controller;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.util.Duration;
import sprites.Enemy;
import sprites.Player;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class GameWindow implements Initializable {
    @FXML
    Canvas canvas;

    Scene scene;
    Stage primaryStage;
    private Player player;
    private GraphicsContext gc;
    private Enemy[] enemies;

    Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

    public void setScene(Scene sc) {
        this.scene = sc;

        scene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        player = new Player(new ImageView(new Image("sprites/player_ship.png")), 0, 0);
        //player.setImage(new Image("sprites/player_ship.png"));
        enemies = new Enemy[5];
        for (int i = 0; i < enemies.length; i++) {
            enemies[i] = new Enemy(new Image("sprites/rock1.png"));
        }
        player.render(gc);
        //timeline.setCycleCount(Timeline.INDEFINITE);
        //timeline.play();

        new AnimationTimer() {

            @Override
            public void handle(long now) {

                if(pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                    player.turnLeft();
                }

                if(pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                    player.turnRight();
                }

                if(pressedKeys.getOrDefault(KeyCode.UP, false)) {
                    player.accelerate();
                }

                player.move();
                player.render(gc);
            }
        }.start();


    }


    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Scene getScene() {
        return scene;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}


