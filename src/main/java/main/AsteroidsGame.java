package main;

import controller.CheckCollision;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sprites.Asteroid;
import sprites.Projectile;
import sprites.Ship;
import sprites.ShipChunk;

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

    int maxProjectiles = 0;
//    int contadorTiempoRespawn = 0;
    //variable que crea los asteroides en base al nivel
    int nivel = 5;
    int temp;
    //booleana para comprobar que entre donde los niveles
    boolean comprobarIncrementoNivel = false;

    //boolean para que me cuenta el primer nivel
    boolean comprobarNivelPrimeraVez = true;
    @Override
    public void start(Stage stage) throws Exception {


        Pane pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);

        Text scoreboard = new Text(10, 20, "Points: 0");
        pane.getChildren().add(scoreboard);

        //panel para niveles
        Text levelPanel = new Text(500, 20, "Level: 1");
        pane.getChildren().add(levelPanel);

        AtomicInteger points = new AtomicInteger();

        //contador de niveles
        AtomicInteger levels = new AtomicInteger();

        Ship ship = new Ship(WIDTH / 2, HEIGHT / 2);
        List<Projectile> projectiles = new ArrayList<>();
        List<Asteroid> asteroids = new ArrayList<>();
        List<Asteroid> asteroids2 = new ArrayList<>();
        List<ShipChunk> shipChunks = new ArrayList<>();

        CheckCollision checkCollision = new CheckCollision(asteroids
        ,asteroids2,projectiles,ship,pane,scoreboard);
        for (int i = 0; i < 1; i++) {
            Random rnd = new Random();
            Asteroid asteroid = new Asteroid(rnd.nextInt(WIDTH / 3), rnd.nextInt(HEIGHT), 1);
            asteroids.add(asteroid);
        }
//        for (int i = 0; i < 10; i++) {
//            Random rnd = new Random();
////            Asteroid asteroid2 = new Asteroid(rnd.nextInt(WIDTH / 3), rnd.nextInt(HEIGHT/3), 2);
////            asteroids2.add(asteroid2);
//        }


        pane.getChildren().add(ship.getCharacter());

        asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));

//        asteroids2.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));


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
//                contadorTiempoRespawn = 0;
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

                    if (maxProjectiles % 10 == 0) {

                        Projectile projectile = new Projectile((int) ship.getCharacter().getTranslateX(), (int) ship.getCharacter().getTranslateY());
                        projectile.getCharacter().setRotate(ship.getCharacter().getRotate());
                        projectiles.add(projectile);

                        projectile.accelerate();
                        projectile.setMovement(projectile.getMovement().normalize().multiply(2));

                        pane.getChildren().add(projectile.getCharacter());
                    }
                    maxProjectiles++;
//                    System.out.println(projectiles.get(0).getTime());
                }
//                System.out.println(System.currentTimeMillis());

                if(ship.isAlive()) {
                    ship.move();
                }

                asteroids.forEach(asteroid -> asteroid.move());
                projectiles.forEach(projectile -> projectile.move());
                asteroids2.forEach(asteroid -> asteroid.move());


                projectiles.forEach(projectile -> {
                    asteroids.forEach(asteroid -> {
                        if (projectile.collide(asteroid)) {
                            projectile.setAlive(false);
                            asteroid.setAlive(false);

                            Asteroid asteroid2 = new Asteroid((int) asteroid.getCharacter().getTranslateX(), (int) asteroid.getCharacter().getTranslateY(), 2);
                            Asteroid asteroid3 = new Asteroid((int) asteroid.getCharacter().getTranslateX(), (int) asteroid.getCharacter().getTranslateY(), 2);
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
//                            contadorTiempoRespawn = 1;
                        }
                    });
                    if (!projectile.isAlive()) {
                        scoreboard.setText("Points: " + points.addAndGet(100));
                    }
                });



                projectiles.forEach(projectile -> {
                    asteroids2.forEach(asteroid -> {
                        if (projectile.collide(asteroid)) {
                            projectile.setAlive(false);
                            asteroid.setAlive(false);
                        }
                    });
                    if (!projectile.isAlive()) {
                        scoreboard.setText("Points: " + points.addAndGet(100));
                    }
                });

//                checkCollision.checkCollide();

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

                asteroids2.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .forEach(asteroid -> pane.getChildren().remove(asteroid.getCharacter()));

                asteroids2.removeAll(asteroids2.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .collect(Collectors.toList()));

                asteroids.forEach(asteroid -> {
                    if (ship.collide(asteroid)) {
                        System.out.println("has colisionado con 1");

//                        ship.setAlive(false);
//                        ship.setMovement(new Point2D(0, 0));
//                        for (int i = 0; i < 20; i++) {
//                            ShipChunk shipchunk = new ShipChunk(ship.getCharacter().getTranslateX(), ship.getCharacter().getTranslateY());
//                            shipChunks.add(shipchunk);
//                        }
//                        shipChunks.forEach(shipchunk -> pane.getChildren().add(shipchunk.getCharacter()));
//                        pane.getChildren().remove(ship.getCharacter());
//                        stop();
                    }
                });
//                shipChunks.forEach(shipChunk -> shipChunk.move());



                asteroids2.forEach(asteroid -> {
                    if (ship.collide(asteroid)) {
                        System.out.println("has colisionado con 2");
//                        stop();
                    }
                });

                if (comprobarNivelPrimeraVez) {
                    levelPanel.setText("Level: " + levels.addAndGet(1));
                    comprobarNivelPrimeraVez = false;
                }
//
                //si ambos arrays estan vacíos se incrementa el nivel y se crean más asteroides
                if (asteroids.isEmpty() && asteroids2.isEmpty()) {
//                        System.out.println("entro 1");
                    for (int i = 0; i < nivel; i++) {
                        Asteroid asteroid = new Asteroid(WIDTH, HEIGHT, 1);
                        asteroids.add(asteroid);
                        pane.getChildren().add(asteroid.getCharacter());

                    }
                    //incrementamos el nivel y sumamos 1
                    levelPanel.setText("Level: " + levels.addAndGet(1));
//                    System.out.println("entro 2 ");
                    comprobarIncrementoNivel = true;
                    System.out.println("tamaño asteroide creado por nivel --> " + asteroids.size());
                }
                //
                if (comprobarIncrementoNivel) {
                    temp = nivel;
                    nivel += temp;
                    comprobarIncrementoNivel = false;
                }

                //si no ha colisionado en 4 segundos se borra el disparo
                projectiles.forEach(projectile -> {
//                    System.out.println("ENTRO");
//                    System.out.println((System.currentTimeMillis() - projectile.getTime()));
                    if((System.currentTimeMillis() - projectile.getTime()) >= 4000){
                        projectile.setAlive(false);
                    }
                });

//                System.out.println("tamaño asteroide  --> "+asteroids.size());
//                System.out.println("tamaño asteroides hijos  --> "+asteroids2.size());
            }
        }.start();

        stage.setTitle("Asteroids");
        stage.setScene(scene);
        stage.show();
    }

}