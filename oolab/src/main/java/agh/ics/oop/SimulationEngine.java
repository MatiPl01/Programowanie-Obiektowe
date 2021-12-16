package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SimulationEngine implements IEngine, Runnable {
    private final ArrayList<Animal> animals = new ArrayList<>();
    private final App app;
    private final Vector2D[] positions;
    private final MoveDirection[] moves;
    private final IWorldMap map;
    private int frameIdx = 0;

    public SimulationEngine(App app, IWorldMap map, MoveDirection[] moves, Vector2D[] positions) {
        this.app = app;
        this.positions = positions;
        this.moves = moves;
        this.map = map;
        addAnimalsToMap();
    }

    @Override
    public void run() {
        System.out.println("Engine started");

        // FIXME - think of a way how to stop program run after playing all animation frames
        while (frameIdx < moves.length) { // FIXME - index out of range
            Thread frameThread = new Thread(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(app.getRefreshDelay());
                    Platform.runLater(app::refresh);
                } catch (InterruptedException e) {
                    System.out.println("Symulacja została przerwana!");
                }
            });
            frameThread.start();
            try {
                frameThread.join();
                frameIdx++;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    private void addAnimalsToMap() {
        for (Vector2D position: positions) {
            Animal animal = new Animal(map, position);
            map.placeNewMapElement(animal);
            animals.add(animal);
        }
    }

    public void requestNewFrame() {
        int j = frameIdx % animals.size();
        Animal animal = animals.get(j);

        System.out.println("[j=" + j +  "] Moving animal: " + animal + " (" + animal.getPosition() + "). Move: " + moves[frameIdx]);
        animal.move(moves[frameIdx]);
        System.out.println(map);
    }
}
