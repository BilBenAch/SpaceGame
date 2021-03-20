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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;
import sprites.Enemy;
import sprites.Player;

import java.net.URL;
import java.util.ResourceBundle;

public class GameWindow implements Initializable {
    @FXML
    Canvas canvas;

    private Scene scene;
    private Player player;
    private GraphicsContext gc;
    private Enemy [] enemies;

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.0017), new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            for(int i = 0; i<enemies.length; i++) {
                enemies[i].clear(gc);
                enemies[i].move();
                if (enemies[i].getBoundary().intersects(player.getBoundary())) {
                    enemies[i].setY(Math.random()+20);
                    player.clear(gc);
                   // player.setImage(new Image("sprites/explosion.png"));
                }
                enemies[i].render(gc);
            }
        }
    })
    );



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        player = new Player();
        player.setImage(new Image("sprites/player_ship.png"));
        enemies = new Enemy[5];
        for(int i= 0; i<enemies.length; i++){
            enemies[i] = new Enemy(new Image("sprites/rock1.png"));
        }
        player.render(gc);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

    public void setScene(Scene sc) {
        scene = sc;
        ImagePattern pattern = new ImagePattern(new Image("sprites/background.png"));
        scene.setFill(pattern);

        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Point2D point = new Point2D(mouseEvent.getX(), mouseEvent.getY());
                System.out.println("click");
            }
        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                System.out.println(keyEvent.getCode().toString());
                player.clear(gc);
                player.setDirection(keyEvent.getCode().toString());
                player.render(gc);
            }
        });
    }

}


