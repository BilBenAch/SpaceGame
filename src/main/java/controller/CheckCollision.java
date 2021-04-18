package controller;

import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import sprites.Asteroid;
import sprites.Projectile;
import sprites.Ship;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CheckCollision {
    private final String asteroidExplosion = getClass().getClassLoader().getResource("sounds/explosion.mp3").toExternalForm();
    private Media asteroidExplosionSound = new Media(asteroidExplosion);
    private MediaPlayer asteroidMediaPlayer = new MediaPlayer(asteroidExplosionSound);

    private final String shipExplosion = getClass().getClassLoader().getResource("sounds/big_explosion.mp3").toExternalForm();
    private Media shipExplosionSound = new Media(shipExplosion);
    private MediaPlayer shipMediaPlayer = new MediaPlayer(shipExplosionSound);

    private List<Asteroid> asteroids;
    private List<Asteroid> asteroidsDivision;
    private List<Projectile> projectiles;
    private Ship ship;
    private Pane pane;
    private Text scoreboard;
    private AtomicInteger points = new AtomicInteger();
    private boolean projectilColisionated = false;
    private Life life;

    public CheckCollision(List<Asteroid> asteroids, List<Asteroid> asteroidsDivision, List<Projectile> projectiles, Ship ship, Pane pane, Text scoreboard, Text lifes_count) {
        this.asteroids = asteroids;
        this.asteroidsDivision = asteroidsDivision;
        this.projectiles = projectiles;
        this.ship = ship;
        this.pane = pane;
        this.scoreboard = scoreboard;
        this.life = new Life(this.ship, this.pane, lifes_count);
    }

    public void checkCollide() {
        checkCollideBigAsteroid();
        checkCollideSmallAsteroid();
        checkCollideSmallAsteroidWithShip();
        checkCollideBigAsteroidWithShip();
    }

    //comprobamos la colision del primer asteride, si colisiona lo dividimos en 2
    public void checkCollideBigAsteroid() {
        projectiles.forEach(projectile -> {
            asteroids.forEach(asteroid -> {
                if (projectile.collide(asteroid)) {
                    projectile.setAlive(false);
                    asteroid.setAlive(false);

                    Asteroid asteroid2 = new Asteroid((int) asteroid.getCharacter().getTranslateX(), (int) asteroid.getCharacter().getTranslateY(), 2);
                    Asteroid asteroid3 = new Asteroid((int) asteroid.getCharacter().getTranslateX(), (int) asteroid.getCharacter().getTranslateY(), 2);
                    asteroid2.getCharacter().setRotate(asteroid.getCharacter().getRotate());
                    asteroid3.getCharacter().setRotate(asteroid.getCharacter().getRotate());
                    asteroidsDivision.add(asteroid2);
                    asteroidsDivision.add(asteroid3);

                    asteroid2.accelerate();
                    asteroid2.setMovement(asteroid2.getMovement());

                    asteroid3.accelerate();
                    asteroid3.setMovement(asteroid3.getMovement());

                    pane.getChildren().add(asteroid2.getCharacter());
                    pane.getChildren().add(asteroid3.getCharacter());

                    projectilColisionated = true;
                }
            });

            if (!projectile.isAlive() && projectilColisionated) {
                asteroidMediaPlayer.play();
                asteroidMediaPlayer.stop();
                incrementPoints();
                projectilColisionated = false;
            }
        });
    }

    //comprobamos la colision del segundo asteroide
    public void checkCollideSmallAsteroid() {
        projectiles.forEach(projectile -> {
            asteroidsDivision.forEach(asteroid -> {
                if (projectile.collide(asteroid)) {
                    projectile.setAlive(false);
                    asteroid.setAlive(false);
                    projectilColisionated = true;
                }
            });
            if (!projectile.isAlive() && projectilColisionated) {
                asteroidMediaPlayer.play();
                asteroidMediaPlayer.stop();
                incrementPoints();
                projectilColisionated = false;
            }
        });
    }

    public void checkCollideBigAsteroidWithShip() {
        asteroids.forEach(asteroid -> {
            if (ship.collide(asteroid)) {
                shipMediaPlayer.play();
                life.newLife();
            }
        });
    }

    public void checkCollideSmallAsteroidWithShip() {
        asteroidsDivision.forEach(asteroid -> {
            if (ship.collide(asteroid)) {
                shipMediaPlayer.play();
                life.newLife();
            }
        });
    }

    public void incrementPoints() {
        asteroidMediaPlayer.play();
        asteroidMediaPlayer.stop();
        scoreboard.setText("Points: " + points.addAndGet(100));
    }

    public Life getLife() {
        return life;
    }

    public void setLife(Life life) {
        this.life = life;
    }
}