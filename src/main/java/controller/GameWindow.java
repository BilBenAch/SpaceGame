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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;
import sprites.Disparo;
import sprites.Enemy;
import sprites.Player;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameWindow implements Initializable {
    @FXML
    Canvas canvas;

    private Scene scene;
    private Player player;
    private Disparo disparo;
    private GraphicsContext gc;
    private List<Enemy> enemies;

    //meteoritos
//    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), new EventHandler<ActionEvent>() {
//        @Override
//        public void handle(ActionEvent event) {
//            for (int i = 0; i < enemies.size(); i++) {
//                enemies.get(i).clear(gc);
//                enemies.get(i).move();
//                if (enemies.get(i).getBoundary().intersects(player.getBoundary())) {
//                    enemies.get(i).setY(Math.random() + 20);
//                    player.clear(gc);
////                    player.setImage(new Image("sprites/explosion1.jpg"));
//                }
//                enemies.get(i).render(gc);
//            }
//        }
//    })
//    );

    //disparos
//    Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(0.0017), new EventHandler<ActionEvent>() {
//        @Override
//        public void handle(ActionEvent event) {
//            disparo.clear(gc);
//            disparo.move();
//            disparo.render(gc);
//        }
//    })
//    );


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        player = new Player(new ImageView(new Image("sprites/player_ship.png")), 0, 0);
//        disparo = new Disparo(new Image("sprites/player_laser.png"));
//        player.setImage(new Image("sprites/player_ship.png"));
//        enemies = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            enemies.add(new Enemy(new Image("sprites/rock1.png")));
//            enemies.add(new Enemy(new Image("sprites/rock3.png")));
//        }
//        player.render(gc);
    //        timeline.setCycleCount(Timeline.INDEFINITE);
    //        timeline.play();
    //        timeline2.setCycleCount(Timeline.INDEFINITE);
    //        timeline2.play();



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
//                disparo.clear(gc);
//                disparo.setDirection(keyEvent.getCode().toString());
//                disparo.render(gc);
            }
        });
    }

}


