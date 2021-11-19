package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class RectangularMapTest {
    int width = 10;
    int height = 5;
    IWorldMap map = new RectangularMap(width, height);

    @Test
    public void testPlaceAndObjectAt() {
        Vector2D[] positions = { new Vector2D(2,2), new Vector2D(3,4), new Vector2D(2,2) };
        ArrayList<Animal> animals = new ArrayList<>();

        for (Vector2D position: positions) {
            Animal animal = new Animal(map, position);
            map.place(animal);
            animals.add(animal);
            assertTrue(map.isOccupied(position));
        }

        // Check if object of index 2 never appears
        assertEquals(animals.get(0), map.objectAt(positions[0]));
        assertEquals(animals.get(1), map.objectAt(positions[1]));
        assertEquals(animals.get(0), map.objectAt(positions[2]));
    }

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
        for (int i = -1; i <= width; i++) {
            for (int j = -1; j <= height; j++) {
                position = new Vector2D(i, j);
                // Check if canMoveTo() returns false for coordinates which don't belong to the map
                if (i == -1 || i == width || j == -1 || j == height) {
                    assertFalse(map.canMoveTo(position));
                // Check if canMoveTo() returns false for coordinates occupied by animals and true
                // for all the other coordinates
                } else {
                    if (positionsSet.contains(position.toString())) assertFalse(map.canMoveTo(position));
                    else assertTrue(map.canMoveTo(position));
                }
            }
        }
    }

    @Test
    public void testIsOccupied() {
        Vector2D[] positions = { new Vector2D(0,0), new Vector2D(9,9), new Vector2D(0,1) };
        HashSet<String> positionsSet = new HashSet<>();

        for (Vector2D position: positions) {
            Animal animal = new Animal(map, position);
            positionsSet.add(position.toString());
            map.place(animal);
        }

        Vector2D position;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                position = new Vector2D(i, j);
                if (positionsSet.contains(position.toString())) assertTrue(map.isOccupied(position));
                else assertFalse(map.isOccupied(position));
            }
        }
    }

    @Test
    public void testMoveAnimal() {
        Animal animal1 = new Animal(map, new Vector2D(1, 1));
        Animal animal2 = new Animal(map, new Vector2D(1, 2));

        map.place(animal1);
        map.place(animal2);

        map.moveAnimal(animal1, MoveDirection.FORWARD);
        assertEquals(new Vector2D(1, 1), animal1.getPosition());
        map.moveAnimal(animal2, MoveDirection.BACKWARD);
        assertEquals(new Vector2D(1, 2), animal2.getPosition());

        map.moveAnimal(animal1, MoveDirection.RIGHT);
        map.moveAnimal(animal1, MoveDirection.FORWARD);
        assertEquals(new Vector2D(2, 1), animal1.getPosition());

        map.moveAnimal(animal2, MoveDirection.LEFT);
        map.moveAnimal(animal2, MoveDirection.BACKWARD);
        assertEquals(new Vector2D(2, 2), animal2.getPosition());

        for (int i = 0; i < 100; i++) {
            map.moveAnimal(animal1, MoveDirection.BACKWARD);
            map.moveAnimal(animal2, MoveDirection.BACKWARD);
        }

        assertEquals(new Vector2D(0, 1), animal1.getPosition());
        assertEquals(new Vector2D(9, 2), animal2.getPosition());
    }
}
