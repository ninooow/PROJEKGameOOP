package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {
    // Existing methods

    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
        return scaleImage(original, width, height, 1.0);
    }

    public BufferedImage scaleImage(BufferedImage original, int width, int height, double scale) {
        int scaledWidth = (int) (width * scale);
        int scaledHeight = (int) (height * scale);
        BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, scaledWidth, scaledHeight, null);
        g2.dispose();
        return scaledImage;
    }
}

