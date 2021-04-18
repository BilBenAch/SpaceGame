package sprites;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Polygon;

public class Projectile extends Character {
    private final String shot = getClass().getClassLoader().getResource("sounds/ship-shoot.wav").toExternalForm();
    private Media shotSound = new Media(shot);
    private MediaPlayer mediaPlayer = new MediaPlayer(shotSound);
    private long time;

    public Projectile(int x, int y) {
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
        time = System.currentTimeMillis();
    }

    public void playShootSound(){
        mediaPlayer.play();
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}