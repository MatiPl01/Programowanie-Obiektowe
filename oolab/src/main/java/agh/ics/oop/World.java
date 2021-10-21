/*
 * Java stream API - explanation: https://www.youtube.com/watch?v=q4s0aE3FnCA
 */
package agh.ics.oop;
import java.util.List;
import java.util.Map;


public class World {
    public static void main(String[] args) {
        // Vector2D class tests
        Vector2D position1 = new Vector2D(1,2);
        System.out.println(position1);
        Vector2D position2 = new Vector2D(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));

        // MapDirection enum tests
        MapDirection north = MapDirection.NORTH;
        System.out.println(north);
        System.out.println(north.next());
        System.out.println(north.previous());
        System.out.println(north.previous().next());
        System.out.println(north.previous().previous());
        System.out.println(north.previous().previous().previous().previous());

        System.out.println(north.toUnitVector());
        System.out.println(MapDirection.EAST.toUnitVector());
        System.out.println(MapDirection.SOUTH.toUnitVector());
        System.out.println(MapDirection.WEST.toUnitVector());

        // Tests

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
        }
    }

    private static Direction convert_direction(String dir) {
        return switch (dir) {
            case "f" -> Direction.FORWARD;
            case "b" -> Direction.BACKWARD;
            case "l" -> Direction.LEFT;
            case "r" -> Direction.RIGHT;
            default  -> throw new Error("Wrong direction");
        };
    }
}
