package agh.ics.oop;

public class Animal {
    MapDirection orientation = MapDirection.NORTH;
    Vector2D currPosition = new Vector2D(2, 2);

    public String toString() {
        return currPosition + " - " + orientation;
    }

    public boolean isAt(Vector2D position) {
        return currPosition.equals(position);
    }

    public void move(MoveDirection direction) {
        Vector2D newPosition;

        switch (direction) {
            case RIGHT:
                orientation = orientation.next();
                break;
            case LEFT:
                orientation = orientation.previous();
                break;
            case FORWARD:
                newPosition = currPosition.add(orientation.toUnitVector());
                if (isOnMap(newPosition)) currPosition = newPosition;
                break;
            case BACKWARD:
                newPosition = currPosition.subtract(orientation.toUnitVector());
                if (isOnMap(newPosition)) currPosition = newPosition;
        }
    }

    public static boolean isOnMap(Vector2D position) {
        return (position.follows(World.bottomLeftVector) && position.precedes(World.topRightVector));
    }
}
