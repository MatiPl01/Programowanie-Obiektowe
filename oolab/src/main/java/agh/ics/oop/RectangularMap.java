package agh.ics.oop;

public class RectangularMap extends AbstractWorldMap {
    public RectangularMap(int width, int height) {
        super();
        this.lowerLeft  = new Vector2D(0, 0);
        this.upperRight = new Vector2D(width - 1, height - 1);
    }

    @Override
    protected boolean isOnMap(Vector2D position) {
        return position.follows(lowerLeft) && position.precedes(upperRight);
    }
}
