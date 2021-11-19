package agh.ics.oop;

public class Grass implements IMapElement {
    private static final String sign = "*";
    private final Vector2D position;

    public Grass(Vector2D position) {
        this.position = position;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return sign;
    }
}
