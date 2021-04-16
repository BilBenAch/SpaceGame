package sprites;

import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Polygon;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Ship extends Character {

    Pane pane;
    List<Projectile> projectiles;
    int maxProjectiles = 0;

    Media shot = new Media ("laser_shot.mp3");
    MediaPlayer player = new MediaPlayer(shot);

    public Ship(int x, int y, Pane pane) {
        super(new Polygon(-5, -5, 10, 0, -5, 5), x, y);
        this.projectiles = new ArrayList<>();
        this.pane = pane;
    }

    public void shoot(){
        Projectile projectile;

        if (maxProjectiles % 5 == 0) {
            projectile = new Projectile((int) this.getCharacter().getTranslateX(), (int) this.getCharacter().getTranslateY());
            projectile.getCharacter().setRotate(this.getCharacter().getRotate());
            projectiles.add(projectile);
            projectile.accelerate();
            projectile.setMovement(projectile.getMovement().normalize().multiply(3));
            pane.getChildren().add(projectile.getCharacter());
        }
        maxProjectiles++;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(List<Projectile> projectiles) {
        this.projectiles = projectiles;
    }
}