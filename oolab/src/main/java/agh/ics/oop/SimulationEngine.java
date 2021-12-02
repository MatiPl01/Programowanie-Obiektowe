package agh.ics.oop;

import java.util.ArrayList;

public class SimulationEngine implements IEngine {
    private final ArrayList<Animal> animals = new ArrayList<>();
    private final Vector2D[] positions;
    private final MoveDirection[] moves;
    private final IWorldMap map;

    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2D[] positions) {
        this.positions = positions;
        this.moves = moves;
        this.map = map;
    }

    private void addAnimalsToMap() {
        for (Vector2D position: positions) {
            Animal animal = new Animal(map, position);
            map.place(animal);
            animals.add(animal);
        }
    }

    @Override
    public void run() {
        addAnimalsToMap();

        int j;
        for (int i = 0; i < moves.length; i++) {
            j = i % animals.size();
            Animal animal = animals.get(j);

            if (j == 0) System.out.println(map);
            System.out.println("[j=" + j +  "] Moving animal: " + animal + " (" + animal.getPosition() + "). Move: " + moves[i]);

            animal.move(moves[i]);
        }
    }
}
