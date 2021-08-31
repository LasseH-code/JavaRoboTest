package textConverter.tools;

import textConverter.image.CellMask;
import textConverter.image.CutCellMask;
import textConverter.utils.specializedTypes.Box;
import textConverter.utils.Point2D;
import textConverter.utils.Vector2;

import java.util.ArrayList;
import java.util.List;

public class BoxDetector {
    public CellMask source;
    private CutCellMask wipSource;
    public Box[] boxes;
    private List<Box> boxes1 = new ArrayList<>();
    public int offset = 0;

    public BoxDetector() {}
    public BoxDetector(BoxDetector bd) { source = bd.source; wipSource = bd.wipSource; boxes = bd.boxes; boxes1 = bd.boxes1; offset = bd.offset; }
    public BoxDetector(CutCellMask cMsk) { source = new CellMask(cMsk.source); wipSource = cMsk; }
    public BoxDetector(CellMask cMsk) { source = cMsk; wipSource = new CutCellMask(source); }
    public BoxDetector(Box[] p) { boxes = p; }
    public BoxDetector(int o) { offset = o; }
    public BoxDetector(CutCellMask cMsk, Box[] p) { source = new CellMask(cMsk.source); wipSource = cMsk; boxes = p; }
    public BoxDetector(CellMask cMsk, Box[] p) { source = cMsk; wipSource = new CutCellMask(cMsk); boxes = p; }
    public BoxDetector(CutCellMask cMsk, int o) { source = new CellMask(cMsk.source); wipSource = cMsk; offset = o; }
    public BoxDetector(CellMask cMsk, int o) {
        source = cMsk;
        wipSource = new CutCellMask(cMsk);
        offset = o;
    }
    public BoxDetector(CutCellMask cMsk, Box[] p, int o) { source = new CellMask(cMsk.source); wipSource = cMsk; boxes = p; offset = o; }
    public BoxDetector(CellMask cMsk, Box[] p, int o) { source = cMsk; wipSource = new CutCellMask(cMsk); boxes = p; offset = o; }

    public void setup(CutCellMask cMsk, Box[] p, int o) {
        source = new CellMask(cMsk.source);
        wipSource = cMsk;
        boxes = p;
        offset = o;
    }
    public void setup(CellMask cMsk, Box[] p, int o) {
        source = cMsk;
        wipSource = new CutCellMask(cMsk);
        boxes = p;
        offset = o;
    }

    public void setupAndCalculate(CutCellMask cMsk, Box[] p, int o) {
        setup(cMsk, p, o);
        calculate();
    }
    public void setupAndCalculate(CellMask cMsk, Box[] p, int o) {
        setup(cMsk, p, o);
        calculate();
    }

    public void quadify(boolean safety) {
        for (int i = 0; i < boxes.length; i++) {
            boxes[i].fillQuad(safety);
        }
    }

    private Box applyOffset(Box b) {
        Vector2 to = new Vector2(new Point2D(b.top.p1.x+offset, b.top.p1.y+offset), new Point2D(b.top.p2.x-offset, b.top.p2.y+offset));
        Vector2 le = new Vector2(new Point2D(b.left.p1.x+offset, b.left.p1.y+offset), new Point2D(b.left.p2.x+offset, b.left.p2.y-offset));
        Vector2 ri = new Vector2(new Point2D(b.right.p1.x-offset, b.right.p1.y+offset), new Point2D(b.right.p2.x-offset, b.right.p2.y-offset));
        Vector2 bo = new Vector2(new Point2D(b.bottom.p1.x+offset, b.bottom.p1.y-offset), new Point2D(b.bottom.p2.x-offset, b.bottom.p2.y-offset));

        return new Box(to, le, ri, bo);
    }

    private Box floorDetection(Box b) {
        /*Vector2 bottom = new Vector2();
        /*bottom.p1 = new Point2D(b.left.p2);
        bottom.p2 = new Point2D(b.left.p2);
        /*for (int i = b.left.p2.x; i < wipSource.cut.value[b.left.p2.y].length; i++) {
            if (wipSource.cut.value[b.left.p2.y][i] && bottom.p2.x <= i) {
                bottom.p2.x = i;
            } else {
                break;
            }
        }
        if (bottom.p2.x == b.right.p2.x && bottom.p2.y == b.right.p2.y) {
            return new Box(b.top, b.left, b.right, bottom);
        } else {
            System.err.println("FloorDetection failed");
            return b;
        }*/
        if (b.left.p2.y == b.right.p2.y) {
            return new Box(b.top, b.left, b.right, new Vector2(new Point2D(b.left.p2.x, b.left.p2.y), new Point2D(b.right.p2.x, b.right.p2.y)));

        } else {
            System.err.println("FloorDetection failed");
            return b;
        }
    }

    private Box sideDetection(Box b) {
        Vector2 l = new Vector2();
        Vector2 r = new Vector2();
        l.p1 = new Point2D(b.top.p1);
        l.p2 = new Point2D(b.top.p1);
        for (int i = b.top.p1.inverse(Point2D.axis.X); i < wipSource.cut.value.length; i++) {
            if(wipSource.cut.value[i][b.top.p1.x] && i >= l.p2.y) {
                l.p2.y = i;
            } else {
                break;
            }
        }
        r.p1 = new Point2D(b.top.p2);
        r.p2 = new Point2D(b.top.p2);
        for (int i = b.top.p2.inverse(Point2D.axis.X); i < wipSource.cut.value.length; i++) {
            if(wipSource.cut.value[i][b.top.p2.x] && i >= r.p2.y) {
                r.p2.y = i;
            } else {
                break;
            }
        }
        if (r.p2.y == l.p2.y) {
            return new Box(b.top, l, r, new Vector2());
        } else {
            System.err.println("SideDetection failed.");
            return b;
        }
    }

    public void calculate() {
        if (wipSource == null) {
            System.err.println("Box detection not yet set up!");
            return;
        }
        wipSource.initCut();
        //List<Box> boxes1 = new ArrayList<>();
        boolean first = true;
        int jOffset = 0;
        for (int i = 0; i < wipSource.cut.value.length; i++) {
            for (int j = jOffset; j < wipSource.cut.value[i].length; j++) {
                if (wipSource.cut.value[i][j]) {
                    if (first) {
                        boxes1.add(new Box());
                        boxes1.get(boxes1.size()-1).top.p1 = new Point2D(j, i);
                        boxes1.get(boxes1.size()-1).top.p2 = new Point2D(j, i);
                        first = false;
                    } else {
                        boxes1.get(boxes1.size()-1).top.p2.x = j; // > boxes1.get(boxes1.size()-1).top.p2.y ? i : boxes1.get(boxes1.size()-1).top.p2.y;
                        //boxes1.get(boxes1.size()-1).top.p2.x = j > boxes1.get(boxes1.size()-1).top.p2.x ? j : boxes1.get(boxes1.size()-1).top.p2.x;
                    }
                } else if(!first) {
                    break;
                }
            }
            if (!first) {
                boxes1.get(boxes1.size()-1).fill(sideDetection(boxes1.get(boxes1.size()-1)));
                boxes1.get(boxes1.size()-1).fill(floorDetection(boxes1.get(boxes1.size()-1)));
                first = true;

                //jOffset += new Vector2(new Point2D(boxes1.get(boxes1.size()-1).bottom.p1), new Point2D(boxes1.get(boxes1.size()-1).top.p1)).get_distance().y;
                //jOffset += boxes1.get(boxes1.size()-1).bottom.p1.y - boxes1.get(boxes1.size()-1).top.p1.y;

                wipSource.calculate(new Vector2(new Point2D(boxes1.get(boxes1.size()-1).top.p1), new Point2D(boxes1.get(boxes1.size()-1).bottom.p2)));
                boxes1.get(boxes1.size()-1).fill(applyOffset(boxes1.get(boxes1.size()-1)));
                i = -1;
                //break;
            }
            /*if (i == wipSource.cut.value.length-1) {
                break;
            }*/
        }
        ListToArray<Box> ltr = new ListToArray<>();
        boxes = ltr.listToArray(Box.class, boxes1);
        System.out.println("Box Found!");
    }
}
