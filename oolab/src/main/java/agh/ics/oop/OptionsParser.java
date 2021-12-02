package agh.ics.oop;

import java.util.ArrayList;

public class OptionsParser {
    public static MoveDirection[] parse(String[] directions) {
        ArrayList<MoveDirection> parsedDirections = new ArrayList<>();
        for (String direction: directions) {
            MoveDirection parsedDirection = parseDirection(direction);
            if (parsedDirection != null) parsedDirections.add(parsedDirection);
        }
        return parsedDirections.toArray(new MoveDirection[0]); // IMPORTANT - how to cast ArrayList to Array
                                                               // (We have to provide one element of the specified type)
    }

    public static MoveDirection parseDirection(String direction) throws IllegalArgumentException {
        return switch (direction.toLowerCase()) {
            case "f", "forward" -> MoveDirection.FORWARD;
            case "b", "backward" -> MoveDirection.BACKWARD;
            case "r", "right" -> MoveDirection.RIGHT;
            case "l", "left" -> MoveDirection.LEFT;
            default -> throw new IllegalArgumentException(direction + " is not valid direction specification");
        };
    }
}
