package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Entity {
    public GamePanel gp;
    public double worldX;
    public double worldY;
    public double speed;
    public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public String[] dialogues = new String[10];
    public int dialogueIndex = 0;

    private int maxGold;
    public int gold;
    public String name;
    public BufferedImage image;

    public int getMaxGold() {
        return maxGold;
    }

    public void setMaxGold(int maxGold) {
        this.maxGold = maxGold;
    }

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {
        int screenX = (int) (worldX - gp.player.worldX + gp.player.screenX);
        int screenY = (int) (worldY - gp.player.worldY + gp.player.screenY);
        g2.drawImage(down1, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        return setup(imagePath, width, height, 1);
    }

    public BufferedImage setup(String imagePath, int width, int height, double scale) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, (int) (width * scale), (int) (height * scale));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
