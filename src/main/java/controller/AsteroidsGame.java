/*package controller;

import controller.CheckCollision;
import controller.Level;
import controller.RemoveSprites;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sprites.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AsteroidsGame extends Application {
  public static int WIDTH = 600;
  public static int HEIGHT = 600;

  public static void main(String[] args) {
    launch(args);
  }

  int maxProjectiles = 0;
  //variable que crea los asteroides en base al nivel
  int nivel = 5;
  int temp;
  int vidas = 3;

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


    Ship ship = new Ship(WIDTH / 2, HEIGHT / 2);

    List<Projectile> projectiles = new ArrayList<>();
    List<Asteroid> asteroids = new ArrayList<>();
    List<Asteroid> asteroids2 = new ArrayList<>();
    List<ShipChunk> shipChunks = new ArrayList<>();

    //Comprobar colisiones
    CheckCollision checkCollision = new CheckCollision(asteroids
            , asteroids2, projectiles, ship, pane, scoreboard);

    //Eliminar items una vez colisonado
    RemoveSprites removeSprites = new RemoveSprites(asteroids, asteroids2, projectiles, ship, pane);

    //Generamos niveles
    Level level = new Level(asteroids, asteroids2, nivel, temp, levelPanel, pane, comprobarNivelPrimeraVez);


    for (int i = 0; i < 1; i++) {
      Random rnd = new Random();
      Asteroid asteroid = new Asteroid(rnd.nextInt(WIDTH / 3), rnd.nextInt(HEIGHT), 1);
      asteroids.add(asteroid);
    }


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
        }
//                System.out.println(System.currentTimeMillis());
//                if (ship.isAlive()) {
//                    ship.move();
//                }

        ship.move();
        asteroids.forEach(asteroid -> asteroid.move());
        projectiles.forEach(projectile -> projectile.move());
        asteroids2.forEach(asteroid -> asteroid.move());

        //comprobamos que si colisiona la nave con el asteoride se pare
        if (!checkCollision.checkCollide()) {
//                    stop();
        }
        //eliminamos sprites
        removeSprites.remove();

        level.checkLevel();

      }
    }.start();

    stage.setTitle("Asteroids");
    stage.setScene(scene);
    stage.show();
  }

}*/