package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected Vector2D lowerLeft;
    protected Vector2D upperRight;

    protected final MapVisualizer mapVisualizer;
    protected final Map<Vector2D, IMapElement> mapElements = new HashMap<>();

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

    public List<Pair<Vector2D, String>> getMapElements() {
        List<Pair<Vector2D, String>> elementsList = new ArrayList<>();
        for (Vector2D key: mapElements.keySet()) {
            elementsList.add(new Pair<>(key, mapElements.get(key).toString()));
        }
        return elementsList;
    }

    public boolean canMoveTo(Vector2D position) {
        return (isOnMap(position) && !(objectAt(position) instanceof Animal));
    }

    public void place(IMapElement element) throws IllegalArgumentException {
        Vector2D position = element.getPosition();

        if ((!isOccupied(position) || element instanceof Animal) && canMoveTo(position)) {
            // Add an object to the map
            mapElements.put(position, element);
        } else throw new IllegalArgumentException(element + " (" + element.getClass() + ") cannot be placed on position" + position);
    }

    public boolean isOccupied(Vector2D position) {
        return mapElements.containsKey(position);
    }

    public IMapElement objectAt(Vector2D position) {
        return mapElements.get(position);
    }

    public IMapElement remove(Vector2D position) {
        return mapElements.remove(position);
    }

    @Override
    public void positionChanged(Vector2D oldPosition, Vector2D newPosition) {
        // Remove a map element from the old position
        IMapElement movedElement = remove(oldPosition);
        // Add the removed map element to the new position
        place(movedElement);
    }

    protected abstract boolean isOnMap(Vector2D position);
}
