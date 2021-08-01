package textConverter.utils;

public class Point2D {
    public int x = 0;
    public int y = 0;

    public Point2D() {}
    public Point2D(int _x, int _y) { x = _x; y = _y; }
    public Point2D(Point2D p) { x = p.x; y = p.y; }

    public enum axis {
        X,
        Y
    }

    public int inverse(axis a) {
        return a == axis.X ? y : x;
    }

    public void fill(int _x, int _y) {
        x = _x;
        y = _y;
    }
    public void fill(Point2D p) {
        x = p.x;
        y = p.y;
    }

    public boolean compare(Point2D p) {
        return x == p.x && y == p.y;
    }
}


