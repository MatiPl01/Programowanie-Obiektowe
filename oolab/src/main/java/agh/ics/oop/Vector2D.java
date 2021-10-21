package agh.ics.oop;

public class Vector2D {
    final int x;
    final int y;

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    public boolean precedes(Vector2D other) {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2D other) {
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2D upperRight(Vector2D other) {
        return new Vector2D(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }

    public Vector2D lowerLeft(Vector2D other) {
        return new Vector2D(Math.min(this.x, other.x), Math.min(this.y, other.y));
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    public boolean equals(Object other) {
       if (this == other) {
           return true;
       } else if (other instanceof Vector2D) {
           Vector2D other_casted = (Vector2D) other;
           return this.x == other_casted.x && this.y == other_casted.y;
       }
       return false;
    }

    public Vector2D opposite() {
        return new Vector2D(-this.x, -this.y);
    }
}
