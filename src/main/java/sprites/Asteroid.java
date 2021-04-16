//package sprites;
//
//import javafx.scene.shape.Polygon;
//
//import java.util.Random;
//
//public class Asteroid extends Character {
//
//    private double rotationalMovement;
//
//    public Asteroid(int x, int y, int size) {
//        super(new PolygonFactory().createPolygon(size), x, y);
//
//
//        Random rnd = new Random();
//
//
//        super.getCharacter().setRotate(rnd.nextInt(360));
//
//        int accelerationAmount = 1 + rnd.nextInt(10);
//        for (int i = 0; i < accelerationAmount; i++) {
//            accelerate();
//        }
//
//        this.rotationalMovement = 0.5 - rnd.nextDouble();
//    }
//
//    @Override
//    public void move() {
//        super.move();
//        super.getCharacter().setRotate(super.getCharacter().getRotate() + rotationalMovement);
//    }
//    public Polygon createPolygonColisonado(Polygon polygon) {
//        Random rnd = new Random();
//
//        double size = 5 + rnd.nextInt(5);
//
//        double c1 = Math.cos(Math.PI * 2 / 5);
//        double c2 = Math.cos(Math.PI / 5);
//        double s1 = Math.sin(Math.PI * 2 / 5);
//        double s2 = Math.sin(Math.PI * 4 / 5);
//
//        polygon.getPoints().addAll(
//                size, 0.0,
//                size * c1, -1 * size * s1,
//                -1 * size * c2, -1 * size * s2,
//                -1 * size * c2, size * s2,
//                size * c1, size * s1);
//
//        for (int i = 0; i < polygon.getPoints().size(); i++) {
//            int change = rnd.nextInt(5) - 2;
//            polygon.getPoints().set(i, polygon.getPoints().get(i) + change);
//        }
//
//        return polygon;
//    }
//
//}
package sprites;

import javafx.scene.shape.Polygon;

import java.util.Random;

public class Asteroid extends Character {

    private double rotationalMovement;

    public Asteroid(int x, int y) {
        super(new PolygonFactory().createPolygon(), x, y);

        Random rnd = new Random();

        super.getCharacter().setRotate(rnd.nextInt(360));

        int accelerationAmount = 1 + rnd.nextInt(10);
        for (int i = 0; i < accelerationAmount; i++) {
            accelerate();
        }

        this.rotationalMovement = 0.5 - rnd.nextDouble();
    }

    @Override
    public void move() {
        super.move();
        super.getCharacter().setRotate(super.getCharacter().getRotate() + rotationalMovement);
    }
}
