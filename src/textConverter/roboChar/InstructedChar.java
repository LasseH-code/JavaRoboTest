package textConverter.roboChar;

import textConverter.image.CellMask;
import textConverter.utils.specializedTypes.Action;
import textConverter.utils.specializedTypes.Alphabet;
import textConverter.utils.specializedTypes.Box;

public class InstructedChar {
    public Action[] actions;
    public Alphabet alias;

    public InstructedChar() {}
    public InstructedChar(InstructedChar ic) { fill(ic); }
    public InstructedChar(boolean[][] chr) { calculate(chr); }
    public InstructedChar(boolean[][] chr, Alphabet a) { calculate(chr); alias = a; }

    public void fill(InstructedChar ic) {
        actions = ic.actions;
        alias = ic.alias;
    }

    public void calculate(boolean[][] chr) {
        actions = new ActionChain().createActionChain(chr);
    }
}
