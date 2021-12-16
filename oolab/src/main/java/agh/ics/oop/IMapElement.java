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

    /**
     * Get a path to the image representing a map element
     *
     * @return The string containing a path to the image
     */
    String getImagePath();

    /**
     * Remove an element from a GUI
     */
    void remove();

    /**
     * Add an object observing a map element
     *
     * @param observer
     *              Object that observes a map element
     */
    void addObserver(IPositionChangeObserver observer);

    /**
     * Remove an object observing a map element
     *
     * @param observer
     *              Object that observes a map element
     */
    void removeObserver(IPositionChangeObserver observer);
}
