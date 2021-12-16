package agh.ics.oop;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected Vector2D lowerLeft;
    protected Vector2D upperRight;

    protected final MapVisualizer mapVisualizer;
    protected final Map<Vector2D, IMapElement> mapElements = new HashMap<>();
    protected Set<IMapElement> newMapElements = new HashSet<>();

    public AbstractWorldMap() {
        this.mapVisualizer = new MapVisualizer(this);
    }

    public String toString() {
        return mapVisualizer.draw(lowerLeft, upperRight);
    }

    public Vector2D getLowerLeft() {
        return lowerLeft;
    }

    public Vector2D getUpperRight() {
        return upperRight;
    }

    // This method ensures that each map element will be returned only once
    @Override
    public Set<IMapElement> getNewMapElements() {
        Set<IMapElement> returnedSet = newMapElements;
        newMapElements = new HashSet<>();
        return returnedSet;
    }

    @Override
    public void placeNewMapElement(IMapElement element) {
        newMapElements.add(element);
        place(element);
    }

    @Override
    public void positionChanged(Vector2D oldPosition, Vector2D newPosition) {
        // Remove a map element from the old position
        IMapElement movedElement = remove(oldPosition);
        // Add the removed map element to the new position only if
        // a newPosition is not null. If it is null, an element should
        // not be added as it was removed from a map
        if (newPosition != null) place(movedElement);
    }

    @Override
    public void place(IMapElement element) throws IllegalArgumentException {
        Vector2D position = element.getPosition();

        if ((!isOccupied(position) || element instanceof Animal) && canMoveTo(position)) {
            // Add an object to the map
            mapElements.put(position, element);
        } else throw new IllegalArgumentException(element + " (" + element.getClass() + ") cannot be placed on position" + position);
    }

    @Override
    public IMapElement objectAt(Vector2D position) {
        return mapElements.get(position);
    }

    public boolean canMoveTo(Vector2D position) {
        return (isOnMap(position) && !(objectAt(position) instanceof Animal));
    }

    public boolean isOccupied(Vector2D position) {
        return mapElements.containsKey(position);
    }

    public IMapElement remove(Vector2D position) {
        return mapElements.remove(position);
    }

    protected abstract boolean isOnMap(Vector2D position);
}
