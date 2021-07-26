package textConverter.utils;

public class Vector2 {
    public Point2D p1 = new Point2D();
    public Point2D p2 = new Point2D();

    public Vector2() {}
    public Vector2(Vector2 v) { p1.x = v.p1.x; p1.y = v.p1.y; p2.x = v.p2.x; p2.y = v.p2.y; }
    public Vector2(Point2D _p1, Point2D _p2) { p1.x = _p1.x; p1.y = _p1.y; p2.x = _p2.x; p2.y = _p2.y; }
    public Vector2(int x, int y, int x1, int y1) { p1.x = x; p1.y = y; p2.x = x1; p2.y = y1; }

    public Point2D get_distance() {
        int a = p1.x - p2.x;
        a = a < 0 ? -a : a;
        int b = p1.y - p2.y;
        b = b < 0 ? -b : b;
        return new Point2D(a, b);
    }
}
