package agh.ics.oop.gui;

import javafx.scene.Node;

public class GuiElementBox implements IElementBox {
    private final MapGrid grid;
    private final Node node;

    GuiElementBox(MapGrid grid, Node node) {
        this.node = node;
        this.grid = grid;
    }

    @Override
    public Node getNode() {
        return node;
    }

    @Override
    public void remove() {
        this.grid.remove(this);
    }
}
