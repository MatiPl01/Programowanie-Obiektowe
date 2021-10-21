package agh.ics.oop;

public enum MapDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public String toString() {
        return switch(this) {
            case NORTH -> "Północ";
            case SOUTH -> "Południe";
            case EAST  -> "Wschód";
            case WEST  -> "Zachód";
        };
    }

    public MapDirection next() {
        MapDirection[] values = MapDirection.values();
        return values[(this.ordinal() + 1) % values.length];
    }

    public MapDirection previous() {
        MapDirection[] values = MapDirection.values();
        return values[(this.ordinal() - 1 + values.length) % values.length];
    }

    public Vector2D toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2D(0, 1);
            case SOUTH -> new Vector2D(0, -1);
            case EAST  -> new Vector2D(1, 0);
            case WEST  -> new Vector2D(-1, 0);
        };
    }
}
