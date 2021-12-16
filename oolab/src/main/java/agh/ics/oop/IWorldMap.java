package agh.ics.oop;

import java.util.List;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo
 *
 */
public interface IWorldMap {
    /**
     * Indicate if any object can move to the given position.
     *
     * @param position
     *            The position checked for the movement possibility.
     * @return True if the object can move to that position.
     */
    boolean canMoveTo(Vector2D position);

    /**
     * Place an element on the map.
     *
     * @param element
     *            The element to place on the map.
     */
    void place(IMapElement element);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position
     *            Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2D position);

    /**
     * Return an object at a given position.
     *
     * @param position
     *            The position of the object.
     * @return Object or null if the position is not occupied.
     */
    Object objectAt(Vector2D position);

    /**
     * Return an object at a given position.
     *
     * @return Vector representing coordinates of the lower left map corner
     */
    Vector2D getLowerLeft();

    /**
     * Return an object at a given position.
     *
     * @return Vector representing coordinates of the lower left map corner
     */
    Vector2D getUpperRight();

    /**
     * Return an object at a given position.
     *
     * @return List of map objects pairs
     */
    List<IMapElement> getMapElements();
}
