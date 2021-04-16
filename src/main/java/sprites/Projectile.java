package sprites;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Polygon;

public class Projectile extends Character {
    private String shot = getClass().getClassLoader().getResource("sounds/laser_shot.mp3").toExternalForm();
    Media shotSound = new Media(shot);
    MediaPlayer mediaPlayer = new MediaPlayer(shotSound);

    public Projectile(int x, int y) {
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
    }

    public void playShootSound(){
        mediaPlayer.play();
        //mediaPlayer.stop();
    }
}