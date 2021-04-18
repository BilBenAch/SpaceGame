package controller;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import sprites.Ship;

import static controller.GameWindow.HEIGHT;
import static controller.GameWindow.WIDTH;

public class Life {
    private Ship ship;
    private Pane pane;
    private int lifes;

    public Life(Ship ship, Pane pane) {
        this.ship = ship;
        this.pane = pane;
        this.lifes = 3;
    }

    public void newLife() {
//        pane.getChildren().remove(ship.getCharacter());
        this.ship.getCharacter().setTranslateX(WIDTH / 2);
        this.ship.getCharacter().setTranslateY(HEIGHT / 2);
//        this.pane.getChildren().add(this.ship.getCharacter());
        ship.setMovement(new Point2D(0, 0));
        this.lifes--;
    }

    public int getLifes() {
        return lifes;
    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
    }
}