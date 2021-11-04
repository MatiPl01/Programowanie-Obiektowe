package agh.ics.oop;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalsOnMapTest {
    @Test
    public void testMoves() {
        MoveDirection[] directions = OptionsParser.parse("f b r l f f r r f f f f f f f f".split(" "));
        IWorldMap map = new RectangularMap(10, 5);
        Vector2D[] positions = { new Vector2D(2,2), new Vector2D(3,4) };

        String[][] expected_positions = {
                { "(2, 3)", "(2, 3)", "(2, 3)", "(2, 3)", "(2, 2)", "(2, 1)", "(2, 0)", "(2, 0)" },
                { "(3, 3)", "(3, 3)", "(3, 3)", "(3, 3)", "(3, 4)", "(3, 4)", "(3, 4)", "(3, 4)" }
        };
        String[][] expected_orientations = {
                { "N", "E", "E", "S", "S", "S", "S", "S" },
                { "N", "W", "W", "N", "N", "N", "N", "N" }
        };

        // Check if inputted positions match expected number of positions and orientations
        assertEquals(expected_positions.length, positions.length, expected_orientations.length);

        ArrayList<Animal> animals = new ArrayList<>();
        for (Vector2D position: positions) {
            Animal animal = new Animal(map, position);
            animals.add(animal);
            map.place(animal);
        }

        int j;
        for (int i = 0; i < directions.length; i++) {
            j = i % animals.size();
            Animal animal = animals.get(j);
            animal.move(directions[i]);

            System.out.println("[i=" + i + "] Animal #: " + j + " (Animal details: " + animal + " "
                    + animal.getPosition() + "); Input: " + directions[i] + "; Expected output: "
                    + expected_positions[j][i / 2] + ", " + expected_orientations[j][i / 2]);
            if (j == 1) System.out.println(map);

            assertEquals(expected_positions[j][i / 2], animal.getPosition().toString());
            assertEquals(expected_orientations[j][i / 2], animal.toString());
        }
    }
}
