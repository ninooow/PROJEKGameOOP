package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_Gold extends Entity {

    GamePanel gp;
    public Rectangle hitBox;

    public OBJ_Gold(GamePanel gp, double worldX, double worldY) {
        super(gp);
        this.gp = gp;
        this.worldX = worldX;
        this.worldY = worldY;
        name = "Gold";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/gold.png"));
            image = setup("/res/objects/gold", gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Tentukan ukuran dan posisi hit box
        hitBox = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
    }

    public void draw(Graphics2D g2, int screenX, int screenY) {
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
