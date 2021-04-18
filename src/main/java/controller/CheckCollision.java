package controller;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import sprites.Asteroid;
import sprites.Projectile;
import sprites.Ship;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class CheckCollision {
    List<Asteroid> asteroids;
    List<Asteroid> asteroidsDivision;
    List<Projectile> projectiles;
    Ship ship;
    Pane pane;
    Text scoreboard;
    AtomicInteger points = new AtomicInteger();
    boolean checkColitionBigAsteroidShip = false;
    boolean checkColitionSmallAsteroidShip = false;
    int vidas;
    boolean projectilColisionated = false;
    Lifes lifes;

    public CheckCollision(List<Asteroid> asteroids, List<Asteroid> asteroidsDivision, List<Projectile> projectiles, Ship ship, Pane pane, Text scoreboard) {
        this.asteroids = asteroids;
        this.asteroidsDivision = asteroidsDivision;
        this.projectiles = projectiles;
        this.ship = ship;
        this.pane = pane;
        this.scoreboard = scoreboard;
        this.vidas = 3;
        lifes = new Lifes(ship, pane);
    }

    public boolean checkCollide() {
        checkCollideBigAsteroid();
        checkCollideSmallAsteroid();
        return !checkCollideBigAsteroidWithShip() && !checkCollideSmallAsteroidWithShip();
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
//                            contadorTiempoRespawn = 1;
                    projectilColisionated = true;

                }
            });

            if (!projectile.isAlive() && projectilColisionated) {
                incrementPoints();
                System.out.println("primero");
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
                incrementPoints();
                System.out.println("primero");
                projectilColisionated = false;
            }
        });
    }

    public boolean checkCollideBigAsteroidWithShip() {
        asteroids.forEach(asteroid -> {
            if (ship.collide(asteroid)) {
                System.out.println("has colisionado con 1");
                lifes.newLife();

                checkColitionBigAsteroidShip = true;
            }
        });

        return checkColitionBigAsteroidShip;
    }

    public boolean checkCollideSmallAsteroidWithShip() {

        asteroidsDivision.forEach(asteroid -> {
            if (ship.collide(asteroid)) {
                System.out.println("has colisionado con 2");
                checkColitionSmallAsteroidShip = true;
            }
        });
        return checkColitionSmallAsteroidShip;
    }

    public void incrementPoints() {
        scoreboard.setText("Points: " + points.addAndGet(100));
        System.out.println(points);

    }

}
