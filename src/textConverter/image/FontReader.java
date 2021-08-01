package textConverter.image;

import textConverter.utils.Pixel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FontReader {

    public Pixel[][] readImage(File file) throws IOException {
        BufferedImage img = ImageIO.read(file);
        System.out.println("Image fetched");
        int height = img.getHeight();
        int width = img.getWidth();
        Pixel[][] temp = new Pixel[height][];
        for (int i = 0; i < height; i++) {              //FIXME: Replace with textConverter.tools.Initializer
            temp[i] = new Pixel[width];
            for (int j = 0; j < width; j++) {
                temp[i][j] = new Pixel();
            }
        }
        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int d = img.getRGB(j, i);
                temp[i][j].color = d;
            }
        }
        System.out.println("Image loaded");
        return temp;
    }

    public boolean[][][] convertImage(CellImage cImg) {  //TODO: Implement CellImage to Box Conversion
        return null;
    }
}
