package textConverter.roboChar;

import org.jetbrains.annotations.NotNull;
import textConverter.image.CellMask;
import textConverter.utils.Point2D;
import textConverter.utils.Vector2;
import textConverter.utils.specializedTypes.Alphabet;
import textConverter.utils.specializedTypes.Box;

public class InstructedFont {
    InstructedChar[] iChars;

    public InstructedFont(int l) { iChars = new InstructedChar[l]; }
    public InstructedFont(InstructedFont inF) { fill(inF); }
    public InstructedFont(Box[] b, CellMask cm) { calculate(b, cm); }

    public void fill(InstructedFont inF) {
        iChars = inF.iChars;
    }

    public void calculate(Box @NotNull [] boxes, CellMask cm) {
        iChars = new InstructedChar[boxes.length];
        for (int i = 0; i < boxes.length; i++) {
            Point2D size = boxes[i].quadSize();
            size.x--;
            size.y--;
            boolean[][] chr = new boolean[size.y][size.x];
            for (int j = 0; j < size.y; j++) {
                for (int n = 0; n < size.x; n++) {
                    chr[j][n] = cm.value[boxes[i].q.topL.y+j+1][boxes[i].q.topL.x+n+1];
                }
            }
            iChars[i] = new InstructedChar(chr, Alphabet.values()[i]);
        }
    }

    public InstructedChar search(Alphabet chr) {
        for (int i = 0; i < iChars.length; i++) {
            if (iChars[i].alias == chr) {
                return iChars[i];
            }
        }
        return null;
    }
}
