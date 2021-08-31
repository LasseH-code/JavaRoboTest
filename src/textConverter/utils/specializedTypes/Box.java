package textConverter.utils.specializedTypes;

import textConverter.utils.Point2D;
import textConverter.utils.Quad;
import textConverter.utils.Vector2;

public class Box {
    public Vector2 top = new Vector2();
    public Vector2 left = new Vector2();
    public Vector2 right = new Vector2();
    public Vector2 bottom = new Vector2();
    public Quad q;

    public Box() {}
    public Box(Vector2 t, Vector2 l, Vector2 r, Vector2 b) { top = t; left = l; right = r; bottom = b; }

    public void fill(Box b) {
        top = b.top;
        left = b.left;
        right = b.right;
        bottom = b.bottom;
    }

    public Point2D quadSize() {
        if (q != null) {
            return new Vector2(q.topL, q.bottomR).get_distance();
        }
        return new Point2D();
    }

    public void fillQuad(boolean safety) {

        if (safety && !(top.p1.compare(left.p1) && left.p2.compare(bottom.p1) && bottom.p2 == right.p2 && right.p1.compare(top.p2))) {
            System.err.println("Could not convert to quad!");
            return;
        }

        q = new Quad(top.p1, top.p2, bottom.p1, bottom.p2);
    }

    public Quad toQuad(boolean safety, boolean fill) {

        if (safety && !(top.p1.compare(left.p1) && left.p2.compare(bottom.p1) && bottom.p2 == right.p2 && right.p1.compare(top.p2))) {
            System.err.println("Could not convert to quad!");
            return new Quad();
        }

        if (fill) {
        q = new Quad(top.p1, top.p2, bottom.p1, bottom.p2);
        }
        return new Quad(top.p1, top.p2, bottom.p1, bottom.p2);
    }
}
