package textConverter.utils;

import textConverter.utils.Point2D;

public class Quad {
    public Point2D topL;
    public Point2D topR;
    public Point2D bottomL;
    public Point2D bottomR;

    public Quad() {}
    public Quad(Point2D tl, Point2D tr, Point2D bl, Point2D br) { topL = new Point2D(tl); topR = new Point2D(tr); bottomL = new Point2D(bl); bottomR = new Point2D(br); }
}
