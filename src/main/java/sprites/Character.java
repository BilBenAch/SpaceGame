package sprites;

import javafx.geometry.Point2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public abstract class Character {

    private ImageView character;
    private Point2D movement;

    public Character(ImageView polygon, int x, int y) {
        this.character = polygon;
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);

        this.movement = new Point2D(x, y);
    }

    public ImageView getCharacter() {
        return character;
    }

    public void turnLeft() {
        this.character.setRotate(this.character.getRotate() - 5);
    }

    public void turnRight() {
        this.character.setRotate(this.character.getRotate() + 5);
    }

    public void move() {
        this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
        this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());
    }

    public void accelerate() {
        double changeX = Math.cos(Math.toRadians(this.character.getRotate() - 90));
        double changeY = Math.sin(Math.toRadians(this.character.getRotate() - 90));

        changeX *= 1;
        changeY *= 1;

        this.movement = this.movement.add(changeX, changeY);
    }

    public void render(GraphicsContext gc){
        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setFill(Color.TRANSPARENT);
        Image image = character.snapshot(snapshotParameters, null);
        System.out.println(movement.getX() + " " + movement.getY());
        gc.drawImage(image, movement.getX(), movement.getY());
    }
}
