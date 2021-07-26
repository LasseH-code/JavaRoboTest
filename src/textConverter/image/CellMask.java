package textConverter.image;

import java.util.ArrayList;
import java.util.List;

public class CellMask {
    public boolean[][] value;
    public CellImage unmasked;
    public CellType maskType;

    public CellMask() {}
    public CellMask(CellImage c) { unmasked =  c; }
    public CellMask(CellType c) { maskType = c; }
    public CellMask(CellMask m) { value = m.value; setUpMask(m.unmasked, m.maskType); }
    public CellMask(boolean[][] v) { value = v; }
    public CellMask(CellImage cImg, CellType cType) { setUpMask(cImg, cType); refreshCMask(); }

    public void setUpMask(CellImage cImg, CellType cTpe) {
        unmasked = cImg;
        maskType = cTpe;
    }

    public void refreshCMask() {
        if (unmasked == null || maskType == null) {
            System.err.println("CellMask not yet set up!");
            return;
        }
        value = new boolean[unmasked.cImage.length][unmasked.cImage[0].length];
        for (int i = 0; i < unmasked.cImage.length; i++) {
            for (int j = 0; j < unmasked.cImage[0].length; j++) {
                if (unmasked.cImage[i][j] == maskType) {
                    value[i][j] = true;
                } else {
                    value[i][j] = false;
                }
            }
        }
        System.out.println("CellMask for " + maskType + " generated");
    }

}
