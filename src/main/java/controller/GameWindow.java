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

    private Scene scene;
    private Stage stage;

    private final String cssNewGame = getClass().getResource("/styles/new_game_window_style.css").toExternalForm();
    private NewGameWindow newGameWindow;

    @FXML
    private Pane pane;

    @FXML
    private Text scoreboard;
    private AtomicInteger points;

    @FXML
    private Text level;
    private AtomicInteger levelNumber;

    @FXML
    private Button restart_button;

    @FXML
    private Text game_over;

    @FXML
    private Text lifes_counter;

    private Ship ship;
    private List<Asteroid> bigAsteroids;
    private List<Asteroid> smallAsteroids;

    //Comprobar colisiones
    private CheckCollision checkCollision;
    //Eliminar items una vez colisonado
    private RemoveSprite removeSprites;
    //Generamos niveles
    private Level levelLevel;

    private Map<KeyCode, Boolean> pressedKeys;

    private int nivel = 5;

    //boolean para que me cuenta el primer nivel
    private boolean comprobarNivelPrimeraVez = true;

    private final String backgroundSong = getClass().getClassLoader().getResource("sounds/space_battles_music.mp3").toExternalForm();
    private Media backgroundMusic;
    private MediaPlayer mediaPlayerGameMusic;

    private final String gameOverSong = getClass().getClassLoader().getResource("sounds/game_over.mp3").toExternalForm();
    private Media gameOverMusic;
    private MediaPlayer mediaPlayerGameOver;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameStarted = true;

        backgroundMusic = new Media(backgroundSong);
        mediaPlayerGameMusic = new MediaPlayer(backgroundMusic);
        mediaPlayerGameMusic.play();
        mediaPlayerGameMusic.setCycleCount(MediaPlayer.INDEFINITE);

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
        bigAsteroids = new ArrayList<>();
        smallAsteroids = new ArrayList<>();

        checkCollision = new CheckCollision(bigAsteroids, smallAsteroids, ship.getProjectiles(), ship, pane, scoreboard, lifes_counter);
        removeSprites = new RemoveSprite(bigAsteroids, smallAsteroids, ship.getProjectiles(), pane);
        levelLevel = new Level(bigAsteroids, smallAsteroids, nivel, level, pane, comprobarNivelPrimeraVez);


        for (int i = 0; i < 5; i++) {
            Random rnd = new Random();
            Asteroid asteroid = new Asteroid(rnd.nextInt(WIDTH / 3), rnd.nextInt(HEIGHT), 1);
            bigAsteroids.add(asteroid);
        }

        pane.getChildren().addAll(ship.getCharacter());
        bigAsteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));

        new AnimationTimer() {

            @Override
            public void handle(long now) {

                if (checkCollision.getLife().getLives() == 0) {
                    gameStarted = false;
                    game_over.setVisible(true);
                    game_over.setDisable(false);
                    restart_button.setVisible(true);
                    restart_button.setDisable(false);
                    mediaPlayerGameMusic.stop();
                    mediaPlayerGameOver.play();
                    stop();
                }

                if (gameStarted) {
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

                if (pressedKeys.getOrDefault(KeyCode.SPACE, false) && ship.getProjectiles().size() < 10) {
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
                bigAsteroids.forEach(asteroid -> asteroid.move());
                smallAsteroids.forEach(asteroid -> asteroid.move());
                ship.getProjectiles().forEach(projectile -> projectile.move());

                checkCollision.checkCollide();

                //eliminamos sprites
                removeSprites.remove();

                levelLevel.checkLevel();

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

    public void onMouseClicked() throws IOException {
        mediaPlayerGameMusic.stop();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AsteroidsNewGame.fxml"));
        Parent newRoot = loader.load();
        newGameWindow = loader.getController();
        scene.getStylesheets().add(cssNewGame);
        newGameWindow.setScene(scene);
        newGameWindow.setStage(stage);

        stage.getScene().setRoot(newRoot);
    }
}