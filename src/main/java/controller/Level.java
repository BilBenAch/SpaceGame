package controller;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import sprites.Asteroid;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import static main.AsteroidsGame.HEIGHT;
import static main.AsteroidsGame.WIDTH;

public class Level {
    List<Asteroid> asteroidsBigs;
    List<Asteroid> asteroidsSmalls;
    int level;
    int drawAsteroids = 5;
    Text levelPanel;
    Pane pane;
    AtomicInteger levels = new AtomicInteger();
    boolean comprobarPrimerNivel;

    public Level(List<Asteroid> asteroidsBigs, List<Asteroid> asteroidsSmalls, Integer level, Integer drawAsteroids, Text levelPanel, Pane pane, boolean comprobarPrimerNivel) {
        this.asteroidsBigs = asteroidsBigs;
        this.asteroidsSmalls = asteroidsSmalls;
        this.level = level;
        this.drawAsteroids = drawAsteroids;
        this.levelPanel = levelPanel;
        this.pane = pane;
        this.comprobarPrimerNivel = comprobarPrimerNivel;
    }

    public void checkLevel() {
        levelCompleted();
    }

    //Comprobamos si se ha superado el nivel si es que si incrementLevel vale true y sube un nivel
    public void levelCompleted() {
        //con este metodo compruebo que pase de level 1 al 2 al principio, si no lo pongo no itera en ese tramo
        if (comprobarPrimerNivel) {
            levelPanel.setText("Level: " + levels.addAndGet(1));
            comprobarPrimerNivel = false;
        }

        //si ambos arrays estan vacíos se incrementa el nivel y se crean más asteroides
        if (asteroidsBigs.isEmpty() && asteroidsSmalls.isEmpty()) {
            levelUp();
            for (int i = 0; i < level; i++) {
                Asteroid asteroid = new Asteroid(WIDTH, HEIGHT, 1);
                asteroidsBigs.add(asteroid);
                pane.getChildren().add(asteroid.getCharacter());

            }
            //incrementamos el nivel y sumamos 1
            levelPanel.setText("Level: " + levels.addAndGet(1));
            System.out.println("tamaño asteroide creado por nivel --> " + asteroidsBigs.size());
        }

    }

    //  metodo para incrementar nivel
    public void levelUp() {
        this.level += drawAsteroids;
    }

}
