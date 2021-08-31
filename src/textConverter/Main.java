package textConverter;

import textConverter.image.*;

import textConverter.roboChar.ActionChain;
import textConverter.roboChar.InstructedChar;
import textConverter.roboChar.InstructedFont;
import textConverter.tools.*;
import textConverter.utils.specializedTypes.Action;
import textConverter.utils.specializedTypes.Alphabet;
import textConverter.utils.specializedTypes.CellType;
import textConverter.utils.Pixel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final boolean[][] charA = {
            {false, true,   true,   false},
            {true,  false,  false,  true},
            {true,  true,   true,   true},
            {true,  false,  false,  true},
            {true,  false,  false,  true},
            {false, false,  false,  false}
    };

    public static void main(String[] args) throws IOException {
        ActionChain ac = new ActionChain();
        Action[] chain = ac.createActionChain(charA);
        Printer pr = new Printer();
        pr.lineBreakThreshold = charA[0].length;
        ListToArray<String> ltr = new ListToArray<String>();
        List<String> chainList = new ArrayList<String>();
        for (int i = 0; i < chain.length; i++) {
            chainList.add(String.valueOf(chain[i]));
        }
        String[] strChain = pr.listToArray(chainList);
        pr.autoConfigurePrinter(strChain);
        pr.printArrayWithEvenSpacing(strChain);
        System.out.println();

        ///*
        FontReader fr = new FontReader();
        Pixel[][] pixels = fr.readImage(new File("F:\\dev\\JavaRoboTest\\src\\textConverter\\image\\TestFont_Compressed.png"));
        String[][] colorVal = new String[pixels.length][pixels[0].length];
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                colorVal[i][j] = String.valueOf(pixels[i][j].color);
            }
        }
        pr.offset = 1;
        pr.printMatrixWithEvenSpacing(colorVal, Printer.AutoCalcType.ONCE);
        System.out.println();

        CellImage ci = new CellImage(pixels);
        String[][] cImg = new String[ci.cImage.length][ci.cImage[0].length];
        for (int i = 0; i < ci.cImage.length; i++) {
            for (int j = 0; j < ci.cImage[0].length; j++) {
                //String n = String.valueOf(ci.cImage[i][j]);
                cImg[i][j] = String.valueOf(ci.cImage[i][j]);
            }
        }
        pr.printMatrixWithEvenSpacing(cImg, Printer.AutoCalcType.ONCE);
        System.out.println();

        CellMask cMsk = new CellMask(ci, CellType.BOX);
        String[][] mskVal = new String[cMsk.value.length][cMsk.value[0].length];
        for (int i = 0; i < cMsk.value.length; i++) {
            for (int j = 0; j < cMsk.value[0].length; j++) {
                mskVal[i][j] = String.valueOf(cMsk.value[i][j]);
            }
        }
        pr.printMatrixWithEvenSpacing(mskVal, Printer.AutoCalcType.ONCE);
        System.out.println();

        //CutCellMask cCM = new CutCellMask(cMsk);
        //cCM.calculate();
        BoxDetector bd = new BoxDetector(cMsk, 0);
        bd.calculate();
        bd.quadify(false);
        for (int i = 0; i < bd.boxes.length; i++) {
            String[][] bdVal = {
                    {
                            "TOP",
                            String.valueOf(bd.boxes[i].top.p1.x),
                            String.valueOf(bd.boxes[i].top.p1.y),
                            "<-p1 p2->",
                            String.valueOf(bd.boxes[i].top.p2.x),
                            String.valueOf(bd.boxes[i].top.p2.y)
                    },
                    {
                            "LEFT",
                            String.valueOf(bd.boxes[i].left.p1.x),
                            String.valueOf(bd.boxes[i].left.p1.y),
                            "<-p1 p2->",
                            String.valueOf(bd.boxes[i].left.p2.x),
                            String.valueOf(bd.boxes[i].left.p2.y)
                    },
                    {
                            "RIGHT",
                            String.valueOf(bd.boxes[i].right.p1.x),
                            String.valueOf(bd.boxes[i].right.p1.y),
                            "<-p1 p2->",
                            String.valueOf(bd.boxes[i].right.p2.x),
                            String.valueOf(bd.boxes[i].right.p2.y)
                    },
                    {
                            "BOTTOM",
                            String.valueOf(bd.boxes[i].bottom.p1.x),
                            String.valueOf(bd.boxes[i].bottom.p1.y),
                            "<-p1 p2->",
                            String.valueOf(bd.boxes[i].bottom.p2.x),
                            String.valueOf(bd.boxes[i].bottom.p2.y)
                    },
            };
            pr.printMatrixWithEvenSpacing(bdVal, Printer.AutoCalcType.ONCE);
            System.out.println();
        }
        //while (true);

        cMsk.maskType = CellType.ACTIVE;
        cMsk.refreshCMask();
        InstructedFont inF = new InstructedFont(bd.boxes, cMsk);
        for(Alphabet a : Alphabet.values()) {
            InstructedChar iChr = inF.search(a);
            Action[] tempA = iChr.actions;
            String[] tempS = new String[tempA.length];
            for (int j = 0; j < tempA.length; j++) {
                tempS[j] = String.valueOf(tempA[j]);
            }
            System.out.println("'" + String.valueOf(iChr.alias) + "': ");
            pr.printArrayWithEvenSpacing(tempS);
        }
        // */
    }
}

