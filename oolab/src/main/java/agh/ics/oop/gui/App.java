package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App extends Application {
    private static final int cellSize = 50;
    private static final String animalImgName = "cow.png";
    private static final String grassImgName = "grass.png";

    private static final IWorldMap map = new GrassField(10);
    private static final Vector2D[] positions = {
        new Vector2D(2,2),
        new Vector2D(3,4),
        new Vector2D(10, 4),
        new Vector2D(5, 7),
    };

    private final Map<Vector2D, Node> gridElements = new HashMap<>();
    private GridPane grid;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Setting grid
        grid = new GridPane();
        Scene scene = new Scene(grid, 600, 600, Color.DARKGRAY);

        // Setting up the simulation engine
        MoveDirection[] directions = OptionsParser.parse(getParameters().getRaw().toArray(new String[0]));
        IEngine engine = new SimulationEngine(this, directions, map, positions);
        createGrid();
        renderMapElements();
        engine.run();

        // Setting the icon and the window title
        Image icon = new Image(String.valueOf(getClass().getResource("/images/icon.png")));
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Evolution Simulation");

        // Loading the scene
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void update() {
        // TODO - implement me
    }

    public static void init(String[] args) {
        Application.launch(App.class, args);
    }

    private Node nodeAt(Vector2D position) {
        return gridElements.get(position);
    }

    private void addNodeAt(Vector2D position, Node node) {
        gridElements.put(position, node);
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
            Label num = new Label(String.valueOf(startX + i - 1));
            grid.add(num, i, 0, 1, 1);
            GridPane.setHalignment(num, HPos.CENTER);
        }

        // Add rows numbers
        for (int i = gridHeight; i > 0; i--) {
            Label num = new Label(String.valueOf(startY + gridHeight - i));
            grid.add(num, 0, i, 1, 1);
            GridPane.setHalignment(num, HPos.CENTER);
        }

        grid.setGridLinesVisible(true);
    }

    private void renderMapElements() {
        List<Pair<Vector2D, String>> elementsList = map.getMapElements();

        for (Pair<Vector2D, String> element: elementsList) {
            renderMapElement(element);
        }
    }

    private void renderMapElement(Pair<Vector2D, String> element) {
        Vector2D position = element.getKey();
        String elemString = element.getValue();
        Vector2D gridCoordinates = getGridCoordinates(position);
        int x = gridCoordinates.getX();
        int y = gridCoordinates.getY();
        grid.add(getElementImageView(elemString), x, y, 1, 1);
    }

    private Vector2D getGridCoordinates(Vector2D position) {
        Vector2D lowerLeft = map.getLowerLeft();
        Vector2D upperRight = map.getUpperRight();
        int startX = lowerLeft.getX();
        int endY = upperRight.getY();
        int x = position.getX();
        int y = position.getY();
        return new Vector2D(x - startX + 1, endY + 1 - y);
    }

    private ImageView getElementImageView(String type) throws IllegalArgumentException {
        String imgName;
        switch (type) {
            case "N", "S", "E", "W" -> imgName = animalImgName;
            case "*" -> imgName = grassImgName;
            default -> throw new IllegalArgumentException("Type: " +  type + " is not a valid mapElement type");
        }
        Image img = new Image(String.valueOf(getClass().getResource("/images/mapElements/" + imgName)));
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(cellSize);
        imgView.setFitWidth(cellSize);
        GridPane.setHalignment(imgView, HPos.CENTER);
        return imgView;
    }
}
