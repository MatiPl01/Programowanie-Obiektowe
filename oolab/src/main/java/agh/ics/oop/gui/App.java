package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class App extends Application {
    private static final int refreshDelay = 1000; // ms
    private static final int sceneHeight = 700;
    private static final int sceneWidth = 700;
    private static final IWorldMap map = new GrassField(10);
    private static final Vector2D[] positions = {
        new Vector2D(2,2),
        new Vector2D(3,4),
        new Vector2D(10, 4),
        new Vector2D(5, 7),
    };
    MapGrid mapGrid = new MapGrid(map);
    SimulationEngine engine;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Set up the grid
        Scene scene = new Scene(mapGrid.getGrid(), sceneWidth, sceneHeight, Color.DARKGRAY);

        // Set up the simulation engine
        MoveDirection[] directions = OptionsParser.parse(getParameters().getRaw().toArray(new String[0]));
        engine = new SimulationEngine(this, map, directions, positions);
        Thread engineThread = new Thread(engine);

        // Render grid
        mapGrid.init();

        // Set the icon and the window title
        Image icon = new Image(new FileInputStream("src/main/resources/images/icon.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Evolution Simulation");

        // Load the scene
        primaryStage.setScene(scene);
        primaryStage.show();

        // Start the simulation engine
        engineThread.start();
    }

    public int getRefreshDelay() {
        return refreshDelay;
    }

    public static void init(String[] args) {
        Application.launch(App.class, args);
    }

    public void refresh() {
        engine.requestNewFrame();
    }
}
