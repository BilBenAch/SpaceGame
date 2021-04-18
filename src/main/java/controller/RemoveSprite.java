package controller;

import javafx.scene.layout.Pane;
import sprites.Asteroid;
import sprites.Projectile;
import sprites.Ship;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveSprite {
    List<Asteroid> asteroids;
    List<Asteroid> asteroidsDivision;
    List<Projectile> projectiles;
    Ship ship;
    Pane pane;

    public RemoveSprite(List<Asteroid> asteroids, List<Asteroid> asteroidsDivision, List<Projectile> projectiles, Ship ship, Pane pane) {
        this.asteroids = asteroids;
        this.asteroidsDivision = asteroidsDivision;
        this.projectiles = projectiles;
        this.ship = ship;
        this.pane = pane;
    }

    public void remove() {
        removeProjectiles();
        removeBigAsteroid();
        removeSmallAsteroid();
        removeProjectilAfterTime();
    }

    public void removeProjectiles() {
        projectiles.stream()
                .filter(projectile -> !projectile.isAlive())
                .forEach(projectile -> pane.getChildren().remove(projectile.getCharacter()));

        projectiles.removeAll(projectiles.stream()
                .filter(projectile -> !projectile.isAlive())
                .collect(Collectors.toList()));
    }

    public void removeBigAsteroid() {
        asteroids.stream()
                .filter(asteroid -> !asteroid.isAlive())
                .forEach(asteroid -> pane.getChildren().remove(asteroid.getCharacter()));

        asteroids.removeAll(asteroids.stream()
                .filter(asteroid -> !asteroid.isAlive())
                .collect(Collectors.toList()));
    }

    public void removeSmallAsteroid() {
        asteroidsDivision.stream()
                .filter(asteroid -> !asteroid.isAlive())
                .forEach(asteroid -> pane.getChildren().remove(asteroid.getCharacter()));

        asteroidsDivision.removeAll(asteroidsDivision.stream()
                .filter(asteroid -> !asteroid.isAlive())
                .collect(Collectors.toList()));
    }

    //si no ha colisionado en 4 segundos se borra el disparo
    public void removeProjectilAfterTime() {
        projectiles.forEach(projectile -> {
//                    System.out.println((System.currentTimeMillis() - projectile.getTime()));
            if ((System.currentTimeMillis() - projectile.getTime()) >= 4000) {
                projectile.setAlive(false);
            }
        });
    }
}