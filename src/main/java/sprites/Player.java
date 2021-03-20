package sprites;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Player {
    private final int NUM_SPRITES = 7;
    private Image image;
    private double posX, posY, velX, velY, width = 20, height=20;
    private int dirX, dirY;
    private int index = 0;

    public Player() {
        this.posX = 200f;
        this.posY = 350f;
        this.velX = 2.0f;
        this.velY = 2.0f;
        this.dirX = 1;
        this.dirY = 1;
    }

    public void moveRight() {
        posX += (velX*2);
        index++;
    }

    public void moveLeft() {
        posX -= velX*2;
    }

    public void moveDown() {
        posY += velY*2;
    }


    public void moveUp() {
        posY -= velY*2;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, posX, posY); //, CANVAS_WIDTH/2 - WIDTH/2, CANVAS_HEIGHT/2 - HEIGHT/2, WIDTH, HEIGHT);

    }

    public void setImage(Image i) {
        image = i;
        width = image.getWidth();
        height = image.getHeight();
    }

    public void clear(GraphicsContext gc) {
        gc.clearRect(posX,posY, width, height);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(posX,posY,width,height);
    }

    public boolean isClicked(Point2D p) {
        if(getBoundary().contains(p)) return true;
        else return false;
    }

    public void setDirection(String direction) {
        switch (direction) {
            case "RIGHT":
                moveRight();
                break;
            case "LEFT":
                moveLeft();
                break;
            case "DOWN":
                moveDown();
                break;
            case "UP":
                moveUp();
                break;

        }
    }
}
