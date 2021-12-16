package agh.ics.oop.gui;

import agh.ics.oop.IMapElement;
import agh.ics.oop.IWorldMap;
import agh.ics.oop.Vector2D;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MapGrid {
    private static final int cellSize = 50;
    private final Map<Vector2D, IElementBox> gridElements = new HashMap<>();
    private final IWorldMap map;
    private final GridPane grid;

    MapGrid(IWorldMap map) {
        grid = new GridPane();
        this.map = map;
    }

    public GridPane getGrid() {
        return grid;
    }

    public void init() {
        createGrid();
        addMapElements();
    }

    private void putNode(Vector2D position, Node node) {
        gridElements.put(position, new GuiElementBox(this, node));
    }

    private void putNode(Vector2D position, IElementBox guiElement) {
        gridElements.put(position, guiElement);
    }

    private void createGrid() {
        // Get dimensions of a grid
        Vector2D lowerLeft = map.getLowerLeft();
        Vector2D upperRight = map.getUpperRight();

        int startX = lowerLeft.getX();
        int startY = lowerLeft.getY();
        int endX = upperRight.getX();
        int endY = upperRight.getY();
        int gridWidth = endX - startX + 1;
        int gridHeight = endY - startY + 1;

        // Create columns
        for (int i = 0; i <= gridWidth; i++) {
            grid.getColumnConstraints().add(new ColumnConstraints(cellSize));
        }

        // Create rows
        for (int i = 0; i <= gridHeight; i++) {
            grid.getRowConstraints().add(new RowConstraints(cellSize));
        }

        // Add columns numbers
        for (int i = 1; i <= gridWidth; i++) {
            Label label = new Label(String.valueOf(startX + i - 1));
            grid.add(label, i, 0, 1, 1);
            putNode(new Vector2D(i, 0), label);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        // Add rows numbers
        for (int i = gridHeight; i > 0; i--) {
            Label label = new Label(String.valueOf(startY + gridHeight - i));
            grid.add(label, 0, i, 1, 1);
            putNode(new Vector2D(0, i), label);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        grid.setGridLinesVisible(true);
    }

    private void addMapElements() {
        List<IMapElement> elementsList = map.getMapElements();

        for (IMapElement element: elementsList) {
            IElementBox guiElement;
            try {
                guiElement = new MapElementBox(this, element);
            } catch (FileNotFoundException e) {
                guiElement = new GuiElementBox(this, new Label(MapElementBox.getLabelText(element)));
            }
            Vector2D gridPosition = getCoordinates(element.getPosition());
            putNode(gridPosition, guiElement);
        }
    }

    public void remove(IElementBox guiElement) {
        grid.getChildren().remove(guiElement.getNode());
    }

    public Vector2D getCoordinates(Vector2D position) {
        Vector2D lowerLeft = map.getLowerLeft();
        Vector2D upperRight = map.getUpperRight();
        int startX = lowerLeft.getX();
        int endY = upperRight.getY();
        int x = position.getX();
        int y = position.getY();
        return new Vector2D(x - startX + 1, endY + 1 - y);
    }
}
