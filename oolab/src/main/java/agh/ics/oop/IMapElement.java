package agh.ics.oop;

public interface IMapElement {

    Vector2D getPosition();

    String toString();

    boolean isAt(Vector2D position);
}
