package agh.ics.oop;

public interface IMapElement {
    /**
     * Get the position of the map element.
     *
     * @return The position vector of the map element.
     */
    Vector2D getPosition();

    /**
     * Get String representation of an element,
     *
     * @return The string representing an element.
     */
    String toString();
}
