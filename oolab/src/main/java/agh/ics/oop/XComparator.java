package agh.ics.oop;

import java.util.Comparator;

public class XComparator implements Comparator<IMapElement> {
    @Override
    public int compare(IMapElement e1, IMapElement e2) {
        Vector2D e1Pos = e1.getPosition();
        Vector2D e2Pos = e2.getPosition();
        return e1Pos.x != e2Pos.x ? e1Pos.x - e2Pos.x : e1Pos.y - e2Pos.y;
    }
}
