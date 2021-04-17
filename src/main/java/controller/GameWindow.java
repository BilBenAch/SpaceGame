package controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sprites.Asteroid;
import sprites.Ship;

import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class GameWindow implements Initializable {
    public static int WIDTH = 600;
    public static int HEIGHT = 600;

    Scene scene;
    Stage primaryStage;

    @FXML
    Pane pane;

    @FXML
    Text scoreboard;
    AtomicInteger points;

    @FXML
    Text level;
    AtomicInteger levelNumber;

    Ship ship;
    List<Asteroid> asteroids;

    Map<KeyCode, Boolean> pressedKeys;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pressedKeys = new HashMap<>();

        points = new AtomicInteger();
        levelNumber = new AtomicInteger();

        ship = new Ship(WIDTH / 2, HEIGHT / 2, pane);
        asteroids = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Random rnd = new Random();
            Asteroid asteroid = new Asteroid(rnd.nextInt(WIDTH / 3), rnd.nextInt(HEIGHT));
            asteroids.add(asteroid);
        }

        pane.getChildren().addAll(ship.getCharacter());
        asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));

        new AnimationTimer() {

            @Override
            public void handle(long now) {

                if (pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                    ship.turnLeft();
                }

                if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                    ship.turnRight();
                }

                if (pressedKeys.getOrDefault(KeyCode.UP, false)) {
                    ship.accelerate();
                }

                if (pressedKeys.getOrDefault(KeyCode.SPACE, false) && ship.getProjectiles().size() < 5) {
                    ship.shoot();
                }

                ship.move();
                asteroids.forEach(asteroid -> asteroid.move());
                ship.getProjectiles().forEach(projectile -> projectile.move());

                ship.getProjectiles().forEach(projectile -> {
                    asteroids.forEach(asteroid -> {
                        if (projectile.collide(asteroid)) {
                            projectile.setAlive(false);
                            asteroid.setAlive(false);
                        }
                    });
                    if (!projectile.isAlive()) {
                        scoreboard.setText("Points: " + points.addAndGet(100));
                    }
                });

                ship.getProjectiles().stream()
                        .filter(projectile -> !projectile.isAlive())
                        .forEach(projectile -> pane.getChildren().remove(projectile.getCharacter()));

                ship.getProjectiles().removeAll(ship.getProjectiles().stream()
                        .filter(projectile -> !projectile.isAlive())
                        .collect(Collectors.toList()));

                asteroids.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .forEach(asteroid -> pane.getChildren().remove(asteroid.getCharacter()));
                asteroids.removeAll(asteroids.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .collect(Collectors.toList()));

                asteroids.forEach(asteroid -> {
                    if (ship.collide(asteroid)) {
                        stop();
                    }
                });
            }
        }.start();
    }

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setScene(Scene sc) {
        this.scene = sc;

        scene.setOnKeyPressed(event -> pressedKeys.put(event.getCode(), Boolean.TRUE));

        scene.setOnKeyReleased(event -> pressedKeys.put(event.getCode(), Boolean.FALSE));
    }

    public Scene getScene() {
        return scene;
    }

    public void setPane(Parent root) {
        this.pane = (Pane) root;
    }
}