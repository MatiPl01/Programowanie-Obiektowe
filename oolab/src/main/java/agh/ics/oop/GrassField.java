package agh.ics.oop;

public class GrassField extends AbstractWorldMap {
    private final int fieldsCount;
    private final int maxFieldIndex;

    private static final Vector2D lowerLeftBound  = new Vector2D(0, 0);
    private static final Vector2D upperRightBound = new Vector2D(Integer.MAX_VALUE, Integer.MAX_VALUE);

    public GrassField(int fieldsCount) {
        if (fieldsCount <= 0) throw new Error("Number of grass fields should be a non-zero natural number");
        this.fieldsCount = fieldsCount;
        this.maxFieldIndex = Math.min((int) Math.sqrt(fieldsCount * 10), Integer.MAX_VALUE);
        spawnGrass();
    }

    @Override
    public boolean place(IMapElement element) {
        Vector2D position = element.getPosition();
        // Check if an element will override a Grass object
        boolean overrodeGrass = objectAt(position) instanceof Grass;
        // Check if an element was placed
        boolean isPlaced = super.place(element);
        // Update map bounds
        if (isPlaced) updateMapBounds(element.getPosition());
        // Respawn a Grass object if it was overridden
        if (overrodeGrass) spawnSingleGrass();
        return isPlaced;
    }

    @Override
    protected boolean isOnMap(Vector2D position) {
        return position.follows(lowerLeftBound) && position.precedes(upperRightBound);
    }

    private void updateMapBounds(Vector2D position) {
        if      (position.x < lowerLeft.x)  lowerLeft  = new Vector2D(position.x, lowerLeft.y);
        else if (position.x > upperRight.x) upperRight = new Vector2D(position.x, upperRight.y);
        if      (position.y < lowerLeft.y)  lowerLeft  = new Vector2D(lowerLeft.x, position.y);
        else if (position.y > upperRight.y) upperRight = new Vector2D(upperRight.x, position.y);
    }

    private Vector2D getNextEmptyField(int maxX, int maxY) {
        Vector2D position;
        do position = Vector2D.randomVector(maxX, maxY); while (isOccupied(position));
        return position;
    }

    private void spawnGrass() {
        // Add the first grass field to the map and update map bounds
        IMapElement initialGrass = new Grass(getNextEmptyField(maxFieldIndex, maxFieldIndex));
        lowerLeft = upperRight = initialGrass.getPosition();
        place(initialGrass);
        // Add the remaining grass elements to the map
        for (int i = 1; i < fieldsCount; i++) spawnSingleGrass();
    }

    private void spawnSingleGrass() {
        IMapElement grass = new Grass(getNextEmptyField(maxFieldIndex, maxFieldIndex));
        place(grass);
    }
}
