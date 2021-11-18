package agh.ics.oop;

public class Animal implements IMapElement {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2D currPosition;
    private final IWorldMap map;

    public Animal(IWorldMap map) {
        this.map = map;
        currPosition = new Vector2D(0, 0);
    }

    public Animal(IWorldMap map, Vector2D initialPosition) {
        this.map = map;
        currPosition = initialPosition;
    }

    public Vector2D getPosition() {
        return currPosition;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public String toString() {
        return switch(orientation) {
            case NORTH -> "N";
            case SOUTH -> "S";
            case EAST -> "E";
            case WEST -> "W";
        };
    }

    public boolean isAt(Vector2D position) {
        return currPosition.equals(position);
    }

    public void move(MoveDirection direction) {
        Vector2D newPosition;

        switch (direction) {
            case RIGHT -> orientation = orientation.next();
            case LEFT -> orientation = orientation.previous();
            case FORWARD -> {
                newPosition = currPosition.add(orientation.toUnitVector());
                if (map.canMoveTo(newPosition)) currPosition = newPosition;
            }
            case BACKWARD -> {
                newPosition = currPosition.subtract(orientation.toUnitVector());
                if (map.canMoveTo(newPosition)) currPosition = newPosition;
            }
        }
    }
}
