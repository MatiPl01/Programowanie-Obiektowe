package agh.ics.oop;

import java.util.HashMap;
import java.util.Map;

public class Animal extends AbstractMapElement {
    private static final String dirPath = "src/main/resources/images/animals/";
    private static final Map<MapDirection, String> imagesPaths = new HashMap<>() {{
        put(MapDirection.NORTH, "cow-north.png");
        put(MapDirection.SOUTH, "cow-south.png");
        put(MapDirection.EAST, "cow-east.png");
        put(MapDirection.WEST, "cow-west.png");
    }};

    private MapDirection orientation = MapDirection.NORTH;
    private final IWorldMap map;

    public Animal(IWorldMap map) {
        super(new Vector2D(0, 0));
        this.map = map;
        addObserver((IPositionChangeObserver) map);
    }

    public Animal(IWorldMap map, Vector2D initialPosition) {
        super(initialPosition);
        this.map = map;
        addObserver((IPositionChangeObserver) map);
    }

    @Override
    public String toString() {
        return switch(orientation) {
            case NORTH -> "N";
            case SOUTH -> "S";
            case EAST -> "E";
            case WEST -> "W";
        };
    }

    @Override
    public String getImagePath() {
        return dirPath + imagesPaths.get(orientation);
    }

    public void move(MoveDirection direction) {
        Vector2D newPosition = position;

        switch (direction) {
            case RIGHT -> orientation = orientation.next();
            case LEFT -> orientation = orientation.previous();
            case FORWARD -> newPosition = position.add(orientation.toUnitVector());
            case BACKWARD -> newPosition = position.subtract(orientation.toUnitVector());
        }
        if (position == newPosition || map.canMoveTo(newPosition)) {
            changePosition(position, newPosition);
        }
    }

    void changePosition(Vector2D oldPosition, Vector2D newPosition) {
        position = newPosition;
        for (IPositionChangeObserver observer: observers) {
            observer.positionChanged(oldPosition, newPosition);
        }
    }
}
