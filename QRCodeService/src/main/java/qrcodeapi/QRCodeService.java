package qrcodeapi;

import java.awt.*;
import java.awt.image.BufferedImage;

public class QRCodeService {

    public static BufferedImage blankCode(int size) {
        BufferedImage blank = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = blank.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, size, size);
        return blank;
    }
}
