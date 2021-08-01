package textConverter.image;

import textConverter.tools.ListToArray;
import textConverter.utils.specializedTypes.Action;
import textConverter.utils.specializedTypes.CharPoint2D;

import java.util.ArrayList;
import java.util.List;

public class ActionChain {
    private boolean builtFlag = false;

    public Action[] createActionChain(boolean[][] inChr) {
        CharPoint2D pos = new CharPoint2D();
        //int pixels = inChr.length*inChr[0].length;
        List<Action> temp = new ArrayList<>();
        for (int i = 0; i < inChr.length; i++) { //boolean[] pixel : inChr
            for (int j = 1; j < inChr[0].length; j++) {
                if(pos.x==0 && inChr[i][pos.x])
                    temp.add(Action.PLACE_BLOCK);
                temp.add(Action.MOVE_E);
                pos.x++;
                if(inChr[i][pos.x]) {
                    temp.add(Action.PLACE_BLOCK);
                }
            }
            if (i+1 != inChr.length) {
                pos.y++;
            }
            temp = pos.xToZero(temp);
            temp.add(Action.MOVE_S);
        }
        temp = pos.yToZero(temp);

        ListToArray<Action> ltr = new ListToArray<Action>();
        return ltr.listToArray(Action.class, temp);
    }
}
