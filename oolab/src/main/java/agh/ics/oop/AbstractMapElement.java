package agh.ics.oop;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractMapElement implements IMapElement {
    protected Vector2D position;
    protected final Set<IPositionChangeObserver> observers = new HashSet<>(){};

    public AbstractMapElement(Vector2D position) {
        this.position = position;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public void remove() {
        for (IPositionChangeObserver observer: observers) {
            // Element should be removed from a GUI when null is passed as a newPosition
            observer.positionChanged(position, null);
        }
    }

    @Override
    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    public boolean isAt(Vector2D position) {
        return this.position.equals(position);
    }
}
