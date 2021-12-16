package agh.ics.oop;

public class Grass extends AbstractMapElement {
    private static final String imgPath = "src/main/resources/images/plants/grass.png";
    private static final String sign = "*";

    public Grass(Vector2D position) {
        super(position);
    }

    @Override
    public String toString() {
        return sign;
    }

    @Override
    public String getImagePath() {
        return imgPath;
    }
}
