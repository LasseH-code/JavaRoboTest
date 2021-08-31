package textConverter.tools;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

public class Printer {
    /*
    TODO: Add sequential lineThresholds (as an int[] instead of int)
     */

    public int spacing = 0;
    public int offset = 1;
    public int lineBreakThreshold = 10;
    public boolean lineBreak = true;
    public boolean separator = true;
    private int aSpacing = 0;
    private final float spacingFactor = 1.0f;

    private void calculateASpacing() {
        aSpacing = spacing+offset;
        aSpacing *= spacingFactor;
    }

    public String[] listToArray(List<String> list) {
        String[] temp = new String[list.size()];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = list.get(i);
        }
        return temp;
    }

    public String[] flattenMatrix(String[][] input) {
        List<String> temp = new ArrayList<String>();
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                temp.add(input[i][j]);
            }
        }
        return listToArray(temp);
    }

    public int autoConfigurePrinter(String[] input) {
        int longest = 0;
        for (int i = 0; i < input.length; i++) {
            if (input[i].length() > longest) {
                longest = input[i].length();
            }
        }
        spacing = longest;
        return longest;
    }

    public void printArrayWithEvenSpacing(String[] input) {
        autoConfigurePrinter(input);
        calculateASpacing();
        //autoConfigurePrinter(input);
        if (separator) {
            System.out.print("|");
            for (int j = 0; j < offset*spacingFactor; j++) {
                System.out.print(" ");
            }
        }
        for (int i = 0; i < input.length; i++) {
            int lengthToGo = (int) max(aSpacing-input[i].length()*spacingFactor,0);
            System.out.print(input[i]);
            if (lengthToGo > 0) {
                for (int j = 0; j < lengthToGo; j++) {
                    System.out.print(" ");
                }
            }
            if (separator) {
                System.out.print("|");
                for (int j = 0; j < offset*spacingFactor; j++) {
                    System.out.print(" ");
                }
            }
            if ((i+1) % lineBreakThreshold == 0 && lineBreak && i+1 != input.length) {
                System.out.println();
                System.out.print("|");
                for (int j = 0; j < offset*spacingFactor; j++) {
                    System.out.print(" ");
                }
            }
        }
        System.out.println();
    }

    public enum AutoCalcType {
        NOT,
        ONCE,
        REPEATEDLY
    }

    public void printMatrixWithEvenSpacing(String[][] input, AutoCalcType act) {
        if (act == AutoCalcType.ONCE) {
            autoConfigurePrinter(flattenMatrix(input));
        }
        boolean oldLineBreak = lineBreak;
        lineBreak = false;
        for (int i = 0; i < input.length; i++) {
            if (act == AutoCalcType.REPEATEDLY) {
                autoConfigurePrinter(input[i]);
            }
            printArrayWithEvenSpacing(input[i]);
        }
        lineBreak = oldLineBreak;
    }
}
