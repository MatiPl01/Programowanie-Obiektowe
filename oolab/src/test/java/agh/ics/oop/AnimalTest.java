package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class AnimalTest {
    @Test
    public void testParser() {
        String[] input = "f something f f BACKwarD :) r RIGht rights l b null :P LEFT".split(" ");
        MoveDirection[] expected = {
            MoveDirection.FORWARD,
            MoveDirection.FORWARD,
            MoveDirection.FORWARD,
            MoveDirection.BACKWARD,
            MoveDirection.RIGHT,
            MoveDirection.RIGHT,
            MoveDirection.LEFT,
            MoveDirection.BACKWARD,
            MoveDirection.LEFT,
        };

        ArrayList<MoveDirection> parsed = OptionsParser.parse(input);
        assertEquals(parsed.size(), expected.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], parsed.get(i));
        }
    }

    @Test
    public void testOrientation() {
        String[] input = "f something f f BACKwarD :) r RIGht rights l b null :P LEFT l f f".split(" ");
        MapDirection[] expected = {
            MapDirection.NORTH,
            MapDirection.NORTH,
            MapDirection.NORTH,
            MapDirection.NORTH,
            MapDirection.EAST,
            MapDirection.SOUTH,
            MapDirection.EAST,
            MapDirection.EAST,
            MapDirection.NORTH,
            MapDirection.WEST,
            MapDirection.WEST,
            MapDirection.WEST
        };

        ArrayList<MoveDirection> parsed = OptionsParser.parse(input);
        Animal animal = new Animal();

        for (int i = 0; i < expected.length; i++) {
            animal.move(parsed.get(i));
            assertEquals(animal.orientation, expected[i]);
        }
    }

    @Test
    public void testPositions() {
        String[] input = "f something f f BACKwarD :) r RIGht rights l b null :P LEFT l f f".split(" ");
        String[] expected = {
            "(2, 3)",
            "(2, 4)",
            "(2, 4)",
            "(2, 3)",
            "(2, 3)",
            "(2, 3)",
            "(2, 3)",
            "(1, 3)",
            "(1, 3)",
            "(1, 3)",
            "(0, 3)",
            "(0, 3)"
        };

        ArrayList<MoveDirection> parsed = OptionsParser.parse(input);
        Animal animal = new Animal();

        for (int i = 0; i < expected.length; i++) {
            animal.move(parsed.get(i));
            assertEquals(animal.currPosition.toString(), expected[i]);
        }
    }

    @Test
    public void testIfInMap() {
        ArrayList<String> moves = randomMoves(10000);
        ArrayList<MoveDirection> parsed = OptionsParser.parse(moves.toArray(new String[0]));
        Animal animal = new Animal();

        for (MoveDirection moveDirection : parsed) {
//            System.out.println("test: " + moveDirection);
            animal.move(moveDirection);
            Vector2D pos = animal.currPosition;
            assertTrue(pos.x >= 0 && pos.x <= 4 && pos.y >= 0 && pos.y <= 4);
        }
    }

    public static ArrayList<String> randomMoves(int count) {
        ArrayList<String> moves = new ArrayList<>();
        for (int i = 0; i < count; i++) moves.add(randomMove());
        return moves;
    }

    public static String randomMove() {
        return new String[]{"f", "l", "b", "r"}[((int)Math.floor(Math.random() * 4))];
    }
}
