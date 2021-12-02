package agh.ics.oop;

public class GrassField extends AbstractWorldMap {
    private int grassCount;
    private int elementsCount = 0;
    private final int maxGrassFieldIndex;

    private static final Vector2D lowerLeftBound  = new Vector2D(0, 0);
    private static final Vector2D upperRightBound = new Vector2D(Integer.MAX_VALUE, Integer.MAX_VALUE);

    private final MapBoundary mapBoundary = new MapBoundary(this);

    public GrassField(int grassCount) {
        if (grassCount <= 0) throw new Error("Number of grass fields should be a non-zero natural number");
        this.grassCount = grassCount;
        this.maxGrassFieldIndex = Math.min((int) Math.sqrt(grassCount * 10), Integer.MAX_VALUE);
        spawnGrass();
    }

    @Override
    public void place(IMapElement element) throws IllegalArgumentException {
        Vector2D position = element.getPosition();
        // If added a new element, increment the elements counter
        if (objectAt(position) != element) elementsCount++;
        // Check if an element will override a Grass object
        boolean overrodeGrass = objectAt(position) instanceof Grass;
        super.place(element);
        // Update map boundary
        updateMapBounds(null, position);
        // Respawn a Grass object if it was overridden
        if (overrodeGrass) spawnSingleGrass();
    }

    @Override
    protected boolean isOnMap(Vector2D position) {
        return position.follows(lowerLeftBound) && position.precedes(upperRightBound);
    }

    private void updateMapBounds(Vector2D oldPosition, Vector2D newPosition) {
        mapBoundary.positionChanged(oldPosition, newPosition);
        lowerLeft  = mapBoundary.getLowerLeftBound();
        upperRight = mapBoundary.getUpperRightBound();
    }

    private Vector2D getNextEmptyField(int maxX, int maxY) {
        Vector2D position;
        do position = Vector2D.randomVector(maxX, maxY); while (isOccupied(position));
        return position;
    }

    private void spawnGrass() {
        // Add the remaining grass elements to the map
        for (int i = 0; i < maxGrassFieldIndex; i++) spawnSingleGrass();
    }

    private void spawnSingleGrass() {
        // Respawn a grass object only if there is available space in which it can be respawned
        if (elementsCount <= maxGrassFieldIndex * maxGrassFieldIndex) {
            IMapElement grass = new Grass(getNextEmptyField(maxGrassFieldIndex, maxGrassFieldIndex));
            place(grass);
            updateMapBounds(null, grass.getPosition());
        }
    }
}
