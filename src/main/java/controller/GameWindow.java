package controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sprites.Asteroid;
import sprites.Ship;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GameWindow implements Initializable {
    public static int WIDTH = 600;
    public static int HEIGHT = 600;
    private boolean gameStarted;

    Scene scene;
    Stage stage;

    String cssNewGame = getClass().getResource("/styles/new_game_window_style.css").toExternalForm();
    AsteroidsNewGame asteroidsNewGame;

    @FXML
    Pane pane;

    @FXML
    Text scoreboard;
    AtomicInteger points;

    @FXML
    Text level;
    AtomicInteger levelNumber;

    @FXML
    Button restart_button;

    @FXML
    Text game_over;

    @FXML
    Text lifes_counter;

    Ship ship;
    List<Asteroid> asteroids;
    List<Asteroid> asteroids2;

    //Comprobar colisiones
    CheckCollision checkCollision;
    //Eliminar items una vez colisonado
    RemoveSprite removeSprites;
    //Generamos niveles
    Level levelLevel;

    Map<KeyCode, Boolean> pressedKeys;

    int nivel = 5;
    int temp;
    int vidas = 3;

    boolean comprobarIncrementoNivel = false;
    //boolean para que me cuenta el primer nivel
    boolean comprobarNivelPrimeraVez = true;

    private String backgroundSong;
    Media backgroundMusic;
    MediaPlayer mediaPlayerGameMusic;

    private String gameOverSong;
    Media gameOverMusic;
    MediaPlayer mediaPlayerGameOver;

    Life life;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameStarted = true;

        backgroundSong = getClass().getClassLoader().getResource("sounds/space_battles_music.mp3").toExternalForm();
        backgroundMusic = new Media(backgroundSong);
        mediaPlayerGameMusic = new MediaPlayer(backgroundMusic);
        mediaPlayerGameMusic.play();
        mediaPlayerGameMusic.setCycleCount(MediaPlayer.INDEFINITE);

        gameOverSong = getClass().getClassLoader().getResource("sounds/game_over.mp3").toExternalForm();
        gameOverMusic = new Media(gameOverSong);
        mediaPlayerGameOver = new MediaPlayer(gameOverMusic);

        pressedKeys = new HashMap<>();


        restart_button.setDisable(true);
        restart_button.setVisible(false);

        game_over.setDisable(true);
        game_over.setVisible(false);

        points = new AtomicInteger();
        levelNumber = new AtomicInteger();

        ship = new Ship(WIDTH / 2, HEIGHT / 2, pane);
        asteroids = new ArrayList<>();
        asteroids2 = new ArrayList<>();

        checkCollision = new CheckCollision(asteroids, asteroids2, ship.getProjectiles(), ship, pane, scoreboard, lifes_counter);
        removeSprites = new RemoveSprite(asteroids, asteroids2, ship.getProjectiles(), ship, pane);
        levelLevel = new Level(asteroids, asteroids2, nivel, temp, level, pane, comprobarNivelPrimeraVez);

        life = new Life(ship, pane, lifes_counter);

        for (int i = 0; i < 5; i++) {
            Random rnd = new Random();
            Asteroid asteroid = new Asteroid(rnd.nextInt(WIDTH / 3), rnd.nextInt(HEIGHT), 1);
            asteroids.add(asteroid);
        }

        pane.getChildren().addAll(ship.getCharacter());
        asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));

        new AnimationTimer() {

            @Override
            public void handle(long now) {

                if (checkCollision.life.getLives() == 0){
                    gameStarted = false;
                    game_over.setVisible(true);
                    game_over.setDisable(false);
                    restart_button.setVisible(true);
                    restart_button.setDisable(false);
                    mediaPlayerGameMusic.stop();
                    mediaPlayerGameOver.play();
                    stop();
                    //asteroids.forEach(asteroid -> asteroid.setAlive(false));
                    //asteroids2.forEach(asteroid -> asteroid.setAlive(false));
                    //removeSprites.remove();

                }

                if (gameStarted){
                    start();
                }

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

                if (pressedKeys.getOrDefault(KeyCode.M, false)) {

                    if (mediaPlayerGameMusic.getStatus() == MediaPlayer.Status.PLAYING) {
                        mediaPlayerGameMusic.pause();
                    }

                    if (mediaPlayerGameMusic.getStatus() == MediaPlayer.Status.PAUSED) {
                        mediaPlayerGameMusic.play();
                    }

                }

                ship.move();
                asteroids.forEach(asteroid -> asteroid.move());
                asteroids2.forEach(asteroid -> asteroid.move());
                ship.getProjectiles().forEach(projectile -> projectile.move());

                if (!checkCollision.checkCollide()) {
                    //stop();
                }
                //eliminamos sprites
                removeSprites.remove();

                levelLevel.checkLevel();


                /*ship.getProjectiles().forEach(projectile -> {
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
                });*/
            }


        }.start();

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
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

    public void newGame() {
        ship.getCharacter().setTranslateX(WIDTH / 2);
        ship.getCharacter().setTranslateY(HEIGHT / 2);
        removeSprites.remove();
        checkCollision.life.setLives(3);
        points.set(0);
        levelNumber.set(1);
        asteroids.forEach(asteroid -> asteroid.setAlive(true));
        asteroids2.forEach(asteroid -> asteroid.setAlive(true));
    }

    public void onMouseClicked() throws IOException {
        mediaPlayerGameMusic.stop();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AsteroidsNewGame.fxml"));
        Parent newRoot = loader.load();
        asteroidsNewGame = loader.getController();
        scene.getStylesheets().add(cssNewGame);
        asteroidsNewGame.setScene(scene);
        asteroidsNewGame.setStage(stage);

        stage.getScene().setRoot(newRoot);
    }
}