package agh.ics.oop;


import java.util.ArrayList;

public class World {
    public static final Vector2D bottomLeftVector = new Vector2D(0, 0);
    public static final Vector2D topRightVector = new Vector2D(4, 4);

    public static void main(String[] args) {
        /* Animal movement (exercise test) */
        Animal animal = new Animal();
        System.out.println(animal);
        ArrayList<MoveDirection> directions = OptionsParser.parse(new String[]{
                "r", "f", "f", "f"
        });
        for (MoveDirection direction: directions) {
            animal.move(direction);
        }
        System.out.println(animal);

        /* Animal movement (more tests) */
        String movesString = "l f f f b b b b b f XDD r f r f f :P f l l l f f b f b f f xD f";
        directions = OptionsParser.parse(movesString.split(" "));
        for (MoveDirection direction: directions) {
            animal.move(direction);
        }
        System.out.println(animal);
    }
}
