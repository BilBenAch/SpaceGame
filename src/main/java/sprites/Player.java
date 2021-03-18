package sprites;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player {

    private Image image;
    private double posX, posY, velX, velY, width, height;
    private int dirX, dirY;

    public Player(Image image) {
        this.posX = Math.random()*600;
        this.posY = Math.random()*600;
        this.velX = 1.0f;
        this.velY = 1.0f;
        this.dirX = 1;
        this.dirY = 1;
        setImage(image);
    }

    /**
     * El moviment de la pilota és gestionat per la mateixa pilota
     * En aquest exemple només cal generalitzar les mides per on es
     * pot moure. En aquest cas en una finestra de 600x400
     */
    public void move() {
        if(dirX == 1) {
            posX += velX;
            if(posX>=600-width) dirX = (-1)*dirX;
        }else {
            posX -= velX;
            if(posX<=0) dirX = (-1)*dirX;
        }
        if(dirY == 1){
            posY += velY;
            if(posY>=600-height) dirY = (-1)*dirY;
        }
        else {
            posY -= velY;
            if(posY<=0) dirY = (-1)*dirY;
        }
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, posX, posY);
    }

    public void setImage(Image i) {
        image = i;
        width = image.getWidth();
        height = image.getHeight();
    }

    /**
     * Netejar la zona que ocupa l'objecte dins del graphicsContext
     * Al clearRect li passem la posició i les mides de la imatge.
     *
     * @param gc
     */
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

    public void changeDir() {
        double t = Math.random();
        if(0.33 > t) dirX = dirX*(-1);
        if(0.33 < t && 0.66 > t) dirY = dirY*(-1);
        if(0.66 < t) {
            dirY = dirY*(-1);
            dirX = dirX*(-1);
        }

    }

    public void setDirection(String direction) {
        switch (direction) {
            case "RIGHT":
                dirX = 1;
                break;
            case "LEFT":
                dirX = -1;
                break;
            case "DOWN":
                dirY = 1;
                break;
            case "UP":
                dirY = -1;
                break;
        }
    }
}
