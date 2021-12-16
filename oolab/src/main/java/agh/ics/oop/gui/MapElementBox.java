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
        element.addObserver(this);
    }

    @Override
    public void remove() {
        grid.getGrid().getChildren().remove(imageView);
    }

    @Override
    public void positionChanged(Vector2D oldPosition, Vector2D newPosition) {
        // Remove a map element if a newPosition is null
        if (newPosition == null) remove();
        else {
            // Change image of an element
            try { // TODO - improve error handling (do something better)
                imageView.setImage(new Image(new FileInputStream(element.getImagePath())));
                label.setText(getLabelText(element));
            } catch (FileNotFoundException e) {
                label.setText("cannot load image");
            }

            // TODO - respawn map element on a gui (in another place)
        }
    }

    @Override
    public Node getNode() {
        return node;
    }

    private void addToGrid() {
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
