package controller;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import sprites.Asteroid;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Level {
    private List<Asteroid> asteroidsBigs;
    private List<Asteroid> asteroidsSmalls;
    private int level;
    private Text levelPanel;
    private Pane pane;
    private AtomicInteger levels = new AtomicInteger();
    private boolean comprobarPrimerNivel;

    public Level(List<Asteroid> asteroidsBigs, List<Asteroid> asteroidsSmalls, Integer level, Text levelPanel, Pane pane, boolean comprobarPrimerNivel) {
        this.asteroidsBigs = asteroidsBigs;
        this.asteroidsSmalls = asteroidsSmalls;
        this.level = level;
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
                Asteroid asteroid = new Asteroid(GameWindow.WIDTH, GameWindow.HEIGHT, 1);
                asteroidsBigs.add(asteroid);
                pane.getChildren().add(asteroid.getCharacter());
            }
            //incrementamos el nivel y sumamos 1
            levelPanel.setText("Level: " + levels.addAndGet(1));
        }

    }

    //  metodo para incrementar nivel
    public void levelUp() {
        level += 5;
    }

}