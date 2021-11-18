package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        MoveDirection[] directions = OptionsParser.parse(args);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2D[] positions = { new Vector2D(2,2), new Vector2D(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

//        MoveDirection[] directions = OptionsParser.parse(args);
//        IWorldMap map = new GrassField(10);
//        Vector2D[] positions = { new Vector2D(2,2), new Vector2D(3,4) };
//        IEngine engine = new SimulationEngine(directions, map, positions);
//        engine.run();
    }
}
