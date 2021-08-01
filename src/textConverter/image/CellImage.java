package textConverter.image;

import textConverter.utils.specializedTypes.CellType;
import textConverter.utils.Pixel;

import java.util.ArrayList;
import java.util.List;

public class CellImage {
    private final int noneValue = 0;
    private final int boxValue = -16711910;
    private final int activeValue = -16777216;
    public CellType[][] cImage;

    public CellImage() {}
    public CellImage(Pixel[][] img) { refreshCImg(img); }
    public CellImage(CellImage cImg) { cImage = cImg.cImage; }
    public CellImage(CellType[][] cImg) { cImage = cImg; }

    public void refreshCImg(Pixel[][] img) {
        List<List<CellType>> temp = new ArrayList<>();
        for (int x = 0; x < img.length; x++) {
            List<CellType> temp2 = new ArrayList<>();
            for (int y = 0; y < img[0].length; y++) {
                switch (img[x][y].color) {
                    case noneValue:
                        temp2.add(CellType.NONE);
                        break;

                    case boxValue:
                        temp2.add(CellType.BOX);
                        break;

                    case activeValue:
                        temp2.add(CellType.ACTIVE);
                        break;

                    default:
                        temp2.add(CellType.NONE);
                }
            }
            temp.add(temp2);
        }
        cImage = new CellType[temp.size()][temp.get(0).size()];
        for (int i = 0; i < temp.size(); i++) {
            for (int j = 0; j < temp.get(i).size(); j++) {
                cImage[i][j] = temp.get(i).get(j);
            }
        }
        System.out.println("CellImage generated");
    }
}
