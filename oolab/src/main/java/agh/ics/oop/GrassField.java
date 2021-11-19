package agh.ics.oop;

import java.util.HashMap;
import java.util.Map;

public class GrassField extends AbstractWorldMap {
    private final int fieldsCount;
    private final int maxFieldIndex;
    public final Map<String, IMapElement> mapElements = new HashMap<>();

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
        if ((!isOccupied(position) || element instanceof Animal) && canMoveTo(position)) {
            // Add an object to the map
            mapElements.put(position.toString(), element);
            // Update map bounds
            updateMapBounds(position);
            return true;
        }
        return false;
    }

    @Override
    public boolean isOccupied(Vector2D position) {
        return mapElements.containsKey(position.toString());
    }

    @Override
    public Object objectAt(Vector2D position) {
        return mapElements.get(position.toString());
    }

    @Override
    public void moveAnimal(Animal animal, MoveDirection move) {
        Vector2D prevPosition = animal.getPosition();
        animal.move(move);
        Vector2D currPosition = animal.getPosition();
        // Update animal position in the mapElements HashMap if an animal was moved
        // (When animal encounters a grass field, a grass field in a HashMap will be overwritten,
        // so a grass will disappear from this field. We will respawn randomly this field of grass)
        boolean isGrass = false;
        if (!currPosition.equals(prevPosition)) {
            mapElements.remove(prevPosition.toString());
            isGrass = objectAt(animal.getPosition()) instanceof Grass;
            mapElements.put(currPosition.toString(), animal);
        }
        // Respawn a grass field if an animal went into the field with grass
        if (isGrass) {
            Vector2D position = getNextEmptyField(maxFieldIndex, maxFieldIndex);
            place(new Grass(position));
        }
    }

    private void spawnGrass() {
        // Add the first grass field to the map and update map bounds
        IMapElement grass = new Grass(getNextEmptyField(maxFieldIndex, maxFieldIndex));
        Vector2D grassPosition = grass.getPosition();
        lowerLeft = upperRight = grassPosition;
        mapElements.put(grassPosition.toString(), grass);
        // Add the remaining grass elements to the map
        for (int i = 1; i < fieldsCount; i++) place(new Grass(getNextEmptyField(maxFieldIndex, maxFieldIndex)));
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

    protected boolean isOnMap(Vector2D position) {
        return position.follows(lowerLeftBound) && position.precedes(upperRightBound);
    }
}
