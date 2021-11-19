package agh.ics.oop;

public abstract class AbstractWorldMap implements IWorldMap {
    protected Vector2D lowerLeft;
    protected Vector2D upperRight;

    protected final MapVisualizer mapVisualizer;

    public AbstractWorldMap() {
        this.mapVisualizer = new MapVisualizer(this);
    }

    public boolean canMoveTo(Vector2D position) {
        return (this.isOnMap(position) && !(objectAt(position) instanceof Animal));
    }

    public String toString() {
        return mapVisualizer.draw(lowerLeft, upperRight);
    }

    public abstract boolean place(IMapElement animal);

    public abstract boolean isOccupied(Vector2D position);

    protected abstract boolean isOnMap(Vector2D position);

    public abstract Object objectAt(Vector2D position);
}
