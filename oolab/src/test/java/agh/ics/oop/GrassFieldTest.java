package agh.ics.oop;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class GrassFieldTest {
    IWorldMap map = new GrassField(10);

    @Test
    public void testCanMoveTo() {
        Vector2D[] positions = { new Vector2D(0,0), new Vector2D(9,9), new Vector2D(0,1) };
        HashSet<String> positionsSet = new HashSet<>();

        for (Vector2D position: positions) {
            Animal animal = new Animal(map, position);
            positionsSet.add(position.toString());
            map.place(animal);
        }

        Vector2D position;
        for (int i = 0; i <= 11; i++) {
            for (int j = 0; j <= 11; j++) {
                position = new Vector2D(i, j);
                if (positionsSet.contains(position.toString())) assertFalse(map.canMoveTo(position));
                else assertTrue(map.canMoveTo(position));
            }
        }
    }

    @Test
    public void testPlace() {
        // Test if 10 grass fields were put
        // (since max distance is sqrt(10 * 10) = 10, we will check all fields which have x and y coordinate
        // between 0 and 10 (inclusive) and count grass fields)
        int grassCount = 0;
        Vector2D position;
        for (int x = 0; x <= 10; x++) {
            for (int y = 0; y <= 10; y++) {
                position = new Vector2D(x, y);
                if (map.objectAt(position) instanceof Grass) grassCount++;
            }
        }
        assertEquals(10, grassCount);

        // Place animals on the whole 11x11 square and check if there is no more grass
        // and are 100 animals placed where they should
        ArrayList<Animal> animals = new ArrayList<>();
        Animal animal;
        for (int x = 0; x <= 10; x++) {
            for (int y = 0; y <= 10; y++) {
                animal = new Animal(map, new Vector2D(x, y));
                animals.add(animal);
                map.place(animal);
            }
        }

        // Count animals and grass
        int animalsCount = 0;
        grassCount = 0;
        for (int x = 0; x <= 10; x++) {
            for (int y = 0; y <= 10; y++) {
                position = new Vector2D(x, y);
                if (map.objectAt(position) instanceof Animal) animalsCount++;
                else if (map.objectAt(position) instanceof Grass) grassCount++;
            }
        }
        assertEquals(0, grassCount);
        assertEquals(11 * 11, animalsCount);

        // Check if animals were placed where they should
        for (Animal a: animals) assertEquals(a, map.objectAt(a.getPosition()));
    }

    @Test
    public void testIsOccupied() {
        int[][] grassCoords = {
                {0, 1}, {1, 3}, {6, 7}
        };
        int[][] animalCoords = {
                {0, 1}, {1, 4}, {9, 11}
        };
        placeList("g", grassCoords);
        placeList("a", animalCoords);
        HashSet<Vector2D> occupiedCoords = new HashSet<>();
        occupiedCoords.addAll(Arrays.stream(grassCoords).map(this::listToVector).collect(Collectors.toList()));
        occupiedCoords.addAll(Arrays.stream(animalCoords).map(this::listToVector).collect(Collectors.toList()));

        for (Vector2D v: occupiedCoords) assertTrue(map.isOccupied(v));
    }

    @Test
    public void testObjectAt() {
        int[][] grassCoords = {
                {0, 1}, {1, 3}, {6, 7}
        };
        int[][] animalCoords = {
                {0, 1}, {1, 4}, {9, 11}
        };
        ArrayList<IMapElement> grass   = placeList("g", grassCoords);
        ArrayList<IMapElement> animals = placeList("a", animalCoords);

        assertEquals(animals.get(0), map.objectAt(listToVector(grassCoords[0]))); // !!! (animal has higher priority than grass)
        assertTrue(map.objectAt(listToVector(grassCoords[1])) instanceof Grass); // there can be already another grass object spawned, so we don't test for equality
        assertTrue(map.objectAt(listToVector(grassCoords[2])) instanceof Grass);
        assertEquals(animals.get(0), map.objectAt(listToVector(animalCoords[0])));
        assertEquals(animals.get(1), map.objectAt(listToVector(animalCoords[1])));
        assertEquals(animals.get(2), map.objectAt(listToVector(animalCoords[2])));
    }

    @Test
    public void testMoveAnimal() {
        Animal animal1 = new Animal(map, new Vector2D(1, 1));
        Animal animal2 = new Animal(map, new Vector2D(1, 2));

        map.place(animal1);
        map.place(animal2);

        animal1.move(MoveDirection.FORWARD);
        assertEquals(new Vector2D(1, 1), animal1.getPosition());
        animal2.move(MoveDirection.BACKWARD);
        assertEquals(new Vector2D(1, 2), animal2.getPosition());

        animal1.move(MoveDirection.RIGHT);
        animal1.move(MoveDirection.FORWARD);
        assertEquals(new Vector2D(2, 1), animal1.getPosition());

        animal2.move(MoveDirection.LEFT);
        animal2.move(MoveDirection.BACKWARD);
        assertEquals(new Vector2D(2, 2), animal2.getPosition());

        for (int i = 0; i < 100; i++) {
            animal1.move(MoveDirection.BACKWARD);
            animal2.move(MoveDirection.BACKWARD);
        }

        assertEquals(new Vector2D(0, 1), animal1.getPosition());
        assertEquals(new Vector2D(102, 2), animal2.getPosition());
    }

    private ArrayList<IMapElement> placeList(String type, int[][] coords) {
        ArrayList<IMapElement> objects = new ArrayList<>();
        IMapElement object;

        for (int[] c: coords) {
            object = switch (type) {
                case "a" -> new Animal(map, listToVector(c));
                case "g" -> new Grass(listToVector(c));
                default -> null;
            };
            objects.add(object);
            map.place(object);
        }

        return objects;
    }

    private Vector2D listToVector(int[] coords) {
        return new Vector2D(coords[0], coords[1]);
    }
}
