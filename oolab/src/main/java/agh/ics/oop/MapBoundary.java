package agh.ics.oop;

import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver {
    private final SortedSet<IMapElement> sortedByX = new TreeSet<>(new XComparator());
    private final SortedSet<IMapElement> sortedByY = new TreeSet<>(new YComparator());
    private final IWorldMap map;

    public MapBoundary(IWorldMap map) {
        this.map = map;
    }

    @Override
    public void positionChanged(Vector2D oldPosition, Vector2D newPosition) {
        IMapElement element = (IMapElement) map.objectAt(newPosition);
        remove(element);
        add(element);
    }

    public void remove(IMapElement element) {
        sortedByX.remove(element);
        sortedByY.remove(element);
    }

    public void add(IMapElement element) {
        sortedByX.add(element);
        sortedByY.add(element);
    }

    public Vector2D getLowerLeftBound() {
        return sortedByX.first().getPosition().lowerLeft(sortedByY.first().getPosition());
    }

    public Vector2D getUpperRightBound() {
        return sortedByX.last().getPosition().upperRight(sortedByY.last().getPosition());
    }
}
