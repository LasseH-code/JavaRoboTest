package textConverter.utils;

public class Box {
    public Vector2 top = new Vector2();
    public Vector2 left = new Vector2();
    public Vector2 right = new Vector2();
    public Vector2 bottom = new Vector2();

    public Box() {}
    public Box(Vector2 t, Vector2 l, Vector2 r, Vector2 b) { top = t; left = l; right = r; bottom = b; }

    public void fill(Box b) {
        top = b.top;
        left = b.left;
        right = b.right;
        bottom = b.bottom;
    }
}
