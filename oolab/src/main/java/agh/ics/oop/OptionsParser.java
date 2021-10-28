package agh.ics.oop;

import java.util.ArrayList;

public class OptionsParser {
    public static ArrayList<MoveDirection> parse(String[] directions) {
        ArrayList<MoveDirection> parsedDirections = new ArrayList<>();
        for (String direction: directions) {
            MoveDirection parsedDirection = parseDirection(direction);
            if (parsedDirection != null) parsedDirections.add(parsedDirection);
        }
        return parsedDirections;
    }

    public static MoveDirection parseDirection(String direction) {
        return switch (direction.toLowerCase()) {
            case "f", "forward" -> MoveDirection.FORWARD;
            case "b", "backward" -> MoveDirection.BACKWARD;
            case "r", "right" -> MoveDirection.RIGHT;
            case "l", "left" -> MoveDirection.LEFT;
            default -> null;
        };
    }
}