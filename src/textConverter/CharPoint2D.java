package textConverter;

import textConverter.utils.Point2D;

import java.util.List;

public class CharPoint2D extends Point2D {
    CharPoint2D() {
        x = 0;
    }

    public List<Action> xToZero(List<Action> temp) {
        while (x > 0) {
            temp.add(Action.MOVE_W);
            x--;
        }

        return temp;
    }
    public List<Action> yToZero(List<Action> temp) {
        while (y > 0) {
            temp.add(Action.MOVE_N);
            y--;
        }
        return temp;
    }
}
