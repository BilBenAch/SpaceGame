package main;

import controller.GameWindow;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sprites.Asteroid;
//import sprites.Player;
import sprites.Projectile;
import sprites.Ship;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AsteroidsGame extends Application {
    public static int WIDTH = 600;
    public static int HEIGHT = 600;
    public static double posicionX;
    public static double posicionY;
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {


        Pane pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);

        Text scoreboard = new Text(10, 20, "Points: 0");
        pane.getChildren().add(scoreboard);

        AtomicInteger points = new AtomicInteger();

        Ship ship = new Ship(WIDTH / 2, HEIGHT / 2);
        List<Projectile> projectiles = new ArrayList<>();

        List<Asteroid> asteroids = new ArrayList<>();
        List<Asteroid> asteroids2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Random rnd = new Random();
            Asteroid asteroid = new Asteroid(rnd.nextInt(WIDTH / 3), rnd.nextInt(HEIGHT), 1);
            asteroids.add(asteroid);
        }
        for (int i = 0; i<10;i++){
            Random rnd = new Random();
            Asteroid asteroid2 = new Asteroid(rnd.nextInt(WIDTH / 3), rnd.nextInt(HEIGHT/3), 2);
            asteroids2.add(asteroid2);
        }

//        for (int i = 0; i < 5; i++) {
//            Random rnd = new Random();
//            Asteroid asteroid = new Asteroid(rnd.nextInt((WIDTH) / 3), rnd.nextInt(HEIGHT));
//            asteroids2.add(asteroid);
//        }

        pane.getChildren().add(ship.getCharacter());

        asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));




        Scene scene = new Scene(pane);

        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

        scene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });


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

                if (pressedKeys.getOrDefault(KeyCode.SPACE, false) && projectiles.size() < 5) {

                    Projectile projectile = new Projectile((int) ship.getCharacter().getTranslateX(), (int) ship.getCharacter().getTranslateY());
                    projectile.getCharacter().setRotate(ship.getCharacter().getRotate());
                    projectiles.add(projectile);

                    projectile.accelerate();
                    projectile.setMovement(projectile.getMovement().normalize().multiply(3));

                    pane.getChildren().add(projectile.getCharacter());
                }

                ship.move();
                asteroids.forEach(asteroid -> asteroid.move());
                projectiles.forEach(projectile -> projectile.move());
                asteroids2.forEach(asteroid -> asteroid.move());

                projectiles.forEach(projectile -> {
                    asteroids.forEach(asteroid -> {
                        if (projectile.collide(asteroid)){
                            projectile.setAlive(false);
                            asteroid.setAlive(false);

                            Asteroid asteroid2 = new Asteroid((int) asteroid.getCharacter().getTranslateX(), (int) asteroid.getCharacter().getTranslateY(),2);
                            Asteroid asteroid3 = new Asteroid((int) asteroid.getCharacter().getTranslateX(), (int) asteroid.getCharacter().getTranslateY(),2);
                            asteroid2.getCharacter().setRotate(asteroid.getCharacter().getRotate());
                            asteroid3.getCharacter().setRotate(asteroid.getCharacter().getRotate());
                            asteroids2.add(asteroid2);
                            asteroids2.add(asteroid3);

                            asteroid2.accelerate();
                            asteroid2.setMovement(asteroid2.getMovement());

                            asteroid3.accelerate();
                            asteroid3.setMovement(asteroid3.getMovement());


                            pane.getChildren().add(asteroid2.getCharacter());
                            pane.getChildren().add(asteroid3.getCharacter());

                        }
                    });
                if (!projectile.isAlive()){
                    scoreboard.setText("Points: " + points.addAndGet(100));
                    }
                });

                projectiles.forEach(projectile -> {
                    asteroids2.forEach(asteroid -> {
                        if (projectile.collide(asteroid)){
                            projectile.setAlive(false);
                            asteroid.setAlive(false);
                        }
                    });
                    if (!projectile.isAlive()){
                        scoreboard.setText("Points: " + points.addAndGet(100));
                    }
                });

                projectiles.stream()
                        .filter(projectile -> !projectile.isAlive())
                        .forEach(projectile -> pane.getChildren().remove(projectile.getCharacter()));

                projectiles.removeAll(projectiles.stream()
                        .filter(projectile -> !projectile.isAlive())
                        .collect(Collectors.toList()));

                asteroids.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .forEach(asteroid -> pane.getChildren().remove(asteroid.getCharacter()));

                asteroids.removeAll(asteroids.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .collect(Collectors.toList()));

//                asteroids2.stream()
//                        .filter(asteroid -> !asteroid.isAlive())
//                        .forEach(asteroid -> pane.getChildren().remove(asteroid.getCharacter()));

//                asteroids2.removeAll(asteroids.stream()
//                        .filter(asteroid -> !asteroid.isAlive())
//                        .collect(Collectors.toList()));

                asteroids.forEach(asteroid -> {
                    if (ship.collide(asteroid)) {
                        stop();
                    }
                });
//                asteroids2.forEach(asteroid -> {
//                    if (ship.collide(asteroid)) {
//                        stop();
//                    }
//                });

//                if(Math.random() < 0.005) {
//                    Asteroid asteroid = new Asteroid(WIDTH, HEIGHT);
//                    if(!asteroid.collide(ship)) {
//                        asteroids.add(asteroid);
//                        pane.getChildren().add(asteroid.getCharacter());
//                    }
//                }


//
                if(Math.random() < 0.005) {
                    Asteroid asteroid = new Asteroid( WIDTH,  HEIGHT,1);
                    if(!asteroid.isAlive()) {
                        asteroids.add(asteroid);
                        pane.getChildren().add(asteroid.getCharacter());
                    }
                }


            }
        }.start();

        stage.setTitle("Asteroids");
        stage.setScene(scene);
        stage.show();
    }

}