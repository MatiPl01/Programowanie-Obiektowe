package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2DTest {
    @Test
    public void equalsTest() {
        Vector2D v1 = new Vector2D(1, 2);
        Vector2D v2 = new Vector2D(1, 2);
        Vector2D v3 = new Vector2D(-3, 2);
        assertEquals(v1, v2);
        assertNotEquals(v1, v3);
    }

    @Test
    public void toStringTest() {
        Vector2D v1 = new Vector2D(1, 2);
        assertEquals(v1.toString(), "(1, 2)");
    }

    @Test
    public void precedesTest() {
        Vector2D v1 = new Vector2D(1, 2);
        Vector2D v2 = new Vector2D(-3, 2);
        assertTrue(v2.precedes(v1));
        assertFalse(v1.precedes(v2));
    }

    @Test
    public void followsTest() {
        Vector2D v1 = new Vector2D(1, 2);
        Vector2D v2 = new Vector2D(-3, 2);
        assertFalse(v2.follows(v1));
        assertTrue(v1.follows(v2));
    }

    @Test
    public void upperRightTest() {
        Vector2D v1 = new Vector2D(1, -2);
        Vector2D v2 = new Vector2D(-3, 2);
        assertEquals(v1.upperRight(v2), new Vector2D(1, 2));
    }

    @Test
    public void lowerLeftTest() {
        Vector2D v1 = new Vector2D(1, -2);
        Vector2D v2 = new Vector2D(-3, 2);
        assertEquals(v1.lowerLeft(v2), new Vector2D(-3, -2));
    }

    @Test
    public void addTest() {
        Vector2D v1 = new Vector2D(1, -2);
        Vector2D v2 = new Vector2D(-3, 2);
        assertEquals(v1.add(v2), new Vector2D(-2, 0));
    }

    @Test
    public void subtractTest() {
        Vector2D v1 = new Vector2D(1, -2);
        Vector2D v2 = new Vector2D(-3, 2);
        assertEquals(v1.subtract(v2), new Vector2D(4, -4));
    }

    @Test
    public void oppositeTest() {
        Vector2D v1 = new Vector2D(1, -2);
        assertEquals(v1.opposite(), new Vector2D(-1, 2));
    }
}
