package agh.ics.oop;

public interface IPositionChangeObserver {
    /**
     * A method which allows object to get notifications about
     * positions changes
     *
     * @param oldPosition
     *            Previous position of an object that is observed
     * @param newPosition
     *            Updated position of an object that is observed
     */
    void positionChanged(Vector2D oldPosition, Vector2D newPosition);
}
