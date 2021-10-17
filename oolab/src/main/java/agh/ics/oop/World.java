/*
 * Java stream API - explanation: https://www.youtube.com/watch?v=q4s0aE3FnCA
 */
package agh.ics.oop;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class World {
    public static void main(String[] args) {
        System.out.println("Start");

        run(Arrays.stream(args)
                .map(World::convert_direction)
                .collect(Collectors.toList())
        );

        System.out.println("Stop");
    }

    public static void run(List<Direction> directions) {
        directions.forEach(World::show_message);
    }

    private static void show_message(Direction dir) {
        switch (dir) {
            case FORWARD  -> System.out.println("Zwierzak idzie do przodu");
            case BACKWARD -> System.out.println("Zwierzak idzie do tyłu");
            case LEFT     -> System.out.println("Zwierzak skręca w lewo");
            case RIGHT    -> System.out.println("Zwierzak skręca w prawo");
            default       -> System.out.println("Nieprawidłowy kierunek");
        };
    }

    private static Direction convert_direction(String dir) {
        return switch (dir) {
            case "f" -> Direction.FORWARD;
            case "b" -> Direction.BACKWARD;
            case "l" -> Direction.LEFT;
            case "r" -> Direction.RIGHT;
            default -> throw new Error("Wrong direction");
        };
    }
}
