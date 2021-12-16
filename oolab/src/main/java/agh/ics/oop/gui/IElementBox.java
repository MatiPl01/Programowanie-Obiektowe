package agh.ics.oop.gui;

import javafx.scene.Node;

public interface IElementBox {
    /**
     * Remove element from the stage
     */
    void remove();

    /**
     * Get node which is represented by a class implementing this interface
     */
    Node getNode();
}
