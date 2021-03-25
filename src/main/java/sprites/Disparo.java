package sprites;

import javafx.scene.image.Image;

public class Disparo extends Sprite{
    private double velX, velY;
    private int dirX, dirY;

    public Disparo(Image image) {
        super(image);
        setX(0);
        setY(0);
        this.velX = 1.0f;
        this.velY = 0.5f;
        this.dirX = 1;
        this.dirY = 1;
    }

    @Override
    public void move() {
        if(dirY == 1) {
            setY(getPosY() + velY);
            if(getPosY()>=600-getHeight()) {
                dirY = (-1)*dirY;
                setX(getPosX() + velX);
            }
        }else {
            setY(getPosY() - velY);
            if(getPosY() <= 0) dirY = (-1)*dirY;
        }
    }

    public void setDirection(String direction) {
        switch (direction) {
            case "SPACE":
                move();
                break;
        }
    }

}