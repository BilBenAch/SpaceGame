package sprites;

import javafx.scene.shape.Polygon;

public class Projectile extends Character {
    long time;


    public Projectile(int x, int y) {
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
        time = System.currentTimeMillis();
    }
    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}