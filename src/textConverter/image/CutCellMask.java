package textConverter.image;

import textConverter.utils.Point2D;
import textConverter.utils.Vector2;

public class CutCellMask {
    public CellMask source = new CellMask();
    public CellMask cut = new CellMask();

    public CutCellMask() {}
    public CutCellMask(CellMask s) { source = s; initCut();}
    public CutCellMask(CellMask s, CellMask cm) { source = s; cut = cm; }
    public CutCellMask(CutCellMask cCM) { source = cCM.source; cut = cCM.cut; }

    public void initCut() {
        //cut.setUpMask(source.unmasked, source.maskType);
        //cut.value = source.value;
        cut.value = new boolean[source.value.length][source.value[0].length];
        for (int i = 0; i < source.value.length; i++) {
            for (int j = 0; j < source.value[0].length; j++) {
                cut.value[i][j] = source.value[i][j]; //? new Boolean(true) : new Boolean(false);
            }
        }
    }

    public void calculate(Vector2 b) {
        if (cut == null) {
            initCut();
        }

        //Point2D dist = b.get_distance();
        for (int i = b.p1.y; i <= b.p2.y; i++) {
            for (int j = b.p1.x; j <= b.p2.x; j++) {
                cut.value[i][j] = false;
            }
        }
    }
}
