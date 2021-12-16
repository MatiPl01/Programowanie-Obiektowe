package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MapElementBox implements IPositionChangeObserver, IElementBox {
    private static final int imageSize = 30;
    private static final int fontSize = 12;
    private final MapGrid grid;
    private final ImageView imageView;
    private final Label label;
    private final IMapElement element;
    private final VBox node;

    // TODO - clean up this constructor
    MapElementBox(MapGrid grid, IMapElement element) throws FileNotFoundException {
        this.grid = grid;
        this.element = element;
        Image image = new Image(new FileInputStream(element.getImagePath()));
        this.imageView = new ImageView(image);
        imageView.setFitWidth(imageSize);
        imageView.setFitHeight(imageSize);
        this.label = new Label(getLabelText(element));
        label.setFont(new Font(fontSize));
        this.node = new VBox(imageView, label);
        node.setAlignment(Pos.BASELINE_CENTER);
        addToGrid();
        System.out.println("ADDED NEW GRID ELEMENT: " + element + ", at: " + grid.getCoordinates(element.getPosition()));
        element.addObserver(this);
    }

    @Override
    public void remove() {
        System.out.println("CALLED REMOVE IN MapElementBox for: " + element + ", at: " + grid.getCoordinates(element.getPosition()));
        grid.getGrid().getChildren().remove(node);
        grid.update(grid.getCoordinates(element.getPosition()), null);
    }

    @Override
    public void positionChanged(Vector2D oldPosition, Vector2D newPosition) {
        // Remove a map element if a newPosition is null
        if (newPosition == null) remove();
        else {
            grid.update(grid.getCoordinates(oldPosition), grid.getCoordinates(newPosition));
            // Change an image of the element
            update();
        }
    }

    @Override
    public Node getNode() {
        return node;
    }

    public void update() {
        System.out.println("==== UPDATE ====");
        // TODO - improve error handling (do something better)
        try {
            Image image = new Image(new FileInputStream(element.getImagePath()));
            imageView.setImage(image);
            label.setText(getLabelText(element));
            System.out.println("UPDATED: " + element.getImagePath() + ", orientation: " + element);
        } catch (FileNotFoundException e) {
            label.setText("cannot load image");
        }
    }

    private void addToGrid() {
        System.out.println("ELEMENT " + element + " WAS SUCCESSFULLY ADDED TO GRID AT: " + grid.getCoordinates(element.getPosition()));
        Vector2D position = grid.getCoordinates(element.getPosition());
        int x = position.getX();
        int y = position.getY();
        grid.getGrid().add(node, x, y, 1, 1);
        GridPane.setHalignment(node, HPos.CENTER);
        GridPane.setValignment(node, VPos.CENTER);
    }

    public static String getLabelText(IMapElement element) {
        if (element instanceof Animal) return element + " " + element.getPosition();
        if (element instanceof Grass) return "Trawa";
        return "Unknown";
    }
}
