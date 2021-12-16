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
import java.util.Set;

class MapGrid {
    private static final int cellSize = 50;
    private final Map<Vector2D, IElementBox> gridElements = new HashMap<>();
    private final IWorldMap map;
    private final GridPane grid;
    private Vector2D dimensions;

    MapGrid(IWorldMap map) {
        grid = new GridPane();
        this.map = map;
    }

    public GridPane getGrid() {
        return grid;
    }

    public void init() {
        createGrid();
        addNewMapElements();
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
        dimensions = new Vector2D(gridWidth, gridHeight);

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

    private void addNewMapElements() {
        System.out.println("=== SPAWNING NEW ELEMENTS ===");
        Set<IMapElement> elementsList = map.getNewMapElements();

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

    public void update(Vector2D oldPosition, Vector2D newPosition) {
        System.out.println("IN GRID UPDATE: " + oldPosition + ", " + newPosition);
        // Get expected and current dimensions of a grid
        Vector2D lowerLeft = map.getLowerLeft();
        Vector2D upperRight = map.getUpperRight();
        int startX = lowerLeft.getX();
        int startY = lowerLeft.getY();
        int endX = upperRight.getX();
        int endY = upperRight.getY();
        int expectedGridWidth = endX - startX + 1;
        int expectedGridHeight = endY - startY + 1;
        int actualGridWidth = dimensions.getX();
        int actualGridHeight = dimensions.getY();

        if (expectedGridHeight > actualGridHeight) {
            System.out.println("IN: expectedGridHeight > actualGridHeight");
            addGridRows(expectedGridHeight, actualGridHeight);
            if (expectedGridWidth > actualGridWidth) {
                System.out.println("IN: expectedGridHeight > actualGridHeight && expectedGridWidth > actualGridWidth");
                addGridColumns(expectedGridWidth, actualGridWidth);
                updateColumnsNumbers(startX, expectedGridWidth);
            } else if (expectedGridWidth < actualGridWidth) {
                System.out.println("IN: expectedGridHeight > actualGridHeight && expectedGridWidth < actualGridWidth");
                removeGridColumns(expectedGridWidth, actualGridWidth);
                updateColumnsNumbers(startX, expectedGridWidth);
            }
            updateRowsNumbers(startY, expectedGridHeight);

        } else if (expectedGridHeight < actualGridHeight) {
            System.out.println("IN: expectedGridHeight < actualGridHeight");
            removeGridRows(expectedGridHeight, actualGridHeight);
            if (expectedGridWidth > actualGridWidth) {
                System.out.println("IN: expectedGridHeight < actualGridHeight && expectedGridWidth > actualGridWidth");
                addGridColumns(expectedGridWidth, actualGridWidth);
                updateColumnsNumbers(startX, expectedGridWidth);
            } else if (expectedGridWidth < actualGridWidth) {
                System.out.println("IN: expectedGridHeight < actualGridHeight && expectedGridWidth < actualGridWidth");
                removeGridColumns(expectedGridWidth, actualGridWidth);
                updateColumnsNumbers(startX, expectedGridWidth);
            }
            updateRowsNumbers(startY, expectedGridHeight);

        } else {
            System.out.println("IN: expectedGridHeight == actualGridHeight");
            if (expectedGridWidth > actualGridWidth) {
                System.out.println("IN: expectedGridHeight == actualGridHeight && expectedGridWidth > actualGridWidth");
                addGridColumns(expectedGridWidth, actualGridWidth);
                updateColumnsNumbers(startX, expectedGridWidth);
            } else if (expectedGridWidth < actualGridWidth) {
                System.out.println("IN: expectedGridHeight == actualGridHeight && expectedGridWidth < actualGridWidth");
                removeGridColumns(expectedGridWidth, actualGridWidth);
                updateColumnsNumbers(startX, expectedGridWidth);
            }
        }
        dimensions = new Vector2D(expectedGridWidth, expectedGridHeight);
        System.out.println("UPDATED GRID DIMENSIONS: " + dimensions);

        if (newPosition != null) {
            System.out.println("BEFORE MOVING ELEMENT");
            IElementBox element = gridElements.remove(oldPosition);
            grid.getChildren().remove(element.getNode());
            putNode(newPosition, element);
            grid.add(element.getNode(), newPosition.getX(), newPosition.getY(), 1, 1);
            System.out.println("AFTER MOVING ELEMENT");
        }
        System.out.println("UPDATE FINISHED");

        // Especially useful for respawned grass objects
        addNewMapElements();
    }

    private void removeGridColumns(int expectedGridWidth, int actualGridWidth) {
        System.out.println("+++ REMOVE COLUMNS +++");
        for (int i = expectedGridWidth; i < actualGridWidth; i++) {
            // Remove also a label number
            grid.getChildren().remove(gridElements.remove(new Vector2D(i, 0)).getNode());
        }
        grid.getColumnConstraints().remove(expectedGridWidth, actualGridWidth);
    }

    private void removeGridRows(int expectedGridHeight, int actualGridHeight) {
        System.out.println("+++ REMOVE ROWS +++");
        for (int i = expectedGridHeight; i < actualGridHeight; i++) {
            grid.getChildren().remove(gridElements.remove(new Vector2D(0, i)).getNode());
        }
        grid.getRowConstraints().remove(expectedGridHeight, actualGridHeight);
    }

    private void addGridColumns(int expectedGridWidth, int actualGridWidth) {
        for (int i = 0; i < expectedGridWidth - actualGridWidth; i++) {
            grid.getColumnConstraints().add(new ColumnConstraints(cellSize));
        }
    }

    private void addGridRows(int expectedGridHeight, int actualGridHeight) {
        for (int i = 0; i < expectedGridHeight - actualGridHeight; i++) {
            grid.getRowConstraints().add(new RowConstraints(cellSize));
        }
    }

    private void updateColumnsNumbers(int startX, int gridWidth) {
        for (int i = 1; i < gridWidth; i++) {
            // TODO - add some type checking and error handling
            ((Label) gridElements.get(new Vector2D(i, 0)).getNode()).setText(String.valueOf(startX + i - 1));
        }
    }

    private void updateRowsNumbers(int startY, int gridHeight) {
        for (int i = gridHeight - 1; i > 0; i--) {
            // TODO - add some type checking and error handling
            ((Label) gridElements.get(new Vector2D(0, i)).getNode()).setText(String.valueOf(startY + gridHeight - i));
        }
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
