package controller;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import sprites.Ship;

import java.util.concurrent.atomic.AtomicInteger;

import static controller.GameWindow.HEIGHT;
import static controller.GameWindow.WIDTH;

public class Life {
    private Ship ship;
    private Pane pane;
    private int lifes;
    private Text lifes_counter;
    private AtomicInteger lifes_counter_number = new AtomicInteger();

    public Life(Ship ship, Pane pane, Text lifes_counter) {
        this.ship = ship;
        this.pane = pane;
        this.lifes = 3;
        this.lifes_counter = lifes_counter;
    }

    public void newLife() {

        this.ship.getCharacter().setTranslateX(WIDTH / 2);
        this.ship.getCharacter().setTranslateY(HEIGHT / 2);
        ship.setMovement(new Point2D(0, 0));
        this.lifes--;
        lifes_counter.setText("Lifes: " + getLifes());
    }

    public int getLifes() {
        return lifes;
    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
    }
}