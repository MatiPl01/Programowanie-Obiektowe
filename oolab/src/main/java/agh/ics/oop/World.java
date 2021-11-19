package agh.ics.oop;

// f f b r r b f b b f l l l r l r f l b l f l b l r l b f l r b l f f f f f f f f f r l f f
public class World {
    public static void main(String[] args) {
        MoveDirection[] directions = OptionsParser.parse(args);
        IWorldMap map = new GrassField(10);
        Vector2D[] positions = {
                new Vector2D(2,2),
                new Vector2D(3,4),
                new Vector2D(10, 4),
                new Vector2D(5, 7),
                new Vector2D(3, 4), // This one won't be added
                new Vector2D(-5, 3) // This won't be added too
        };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
    }
}
