package main;

import entity.Entity;
import object.OBJ_Gold;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UI {

    private GamePanel gp;
    private Graphics2D g2;
    private Font arial_40, arial_80B;
    public boolean messageOn = false;
    public String message = "";
    BufferedImage gold_full;

    public UI(GamePanel gp) throws IOException {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        // CREATE HUD OBJECTS
        OBJ_Gold gold = new OBJ_Gold(this.gp, 0, 0);
        gold_full = gold.image;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void drawPlayerStatus() {
        int iconSize = gp.tileSize * 2 ; // Mengatur ukuran ikon lebih besar
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;

        // Posisikan ikon gold lebih ke kiri
        int goldIconX = x - 40; // Geser 20 piksel ke kiri
        int goldIconY = y - 40;

        // DRAW GOLD ICON
        g2.drawImage(gold_full, goldIconX, goldIconY, iconSize, iconSize, null);
        // DRAW GOLD AMOUNT
        int textX = goldIconX + gp.tileSize + 40; // Add some space between the icon and the text
        int textY = goldIconY + gp.tileSize + 20; // Geser angka 10 piksel ke bawah
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        String goldText = String.valueOf(gp.player.gold);
        g2.drawString(goldText, textX, textY); // Adjust y to align with the icon
    }


    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.white);

        // PLAY STATE
        if (gp.gameState == gp.playState) {
            // Do playState stuff later
        }
        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            // PAUSE
        }

        // DIALOGUE STATE
        if (gp.gameState == gp.dialogueState) {
            System.out.println(gp.player.nomorNPC);
            System.out.println("Aku Nino");
            if (gp.player.nomorNPC == 0) {
                gp.npc1.drawDialogueScreen(g2);
            } else if (gp.player.nomorNPC == 1) {
                gp.npc2.drawDialogueScreen(g2);
            } else if (gp.player.nomorNPC == 2) {
                gp.npc3.drawDialogueScreen(g2);
            } else if (gp.player.nomorNPC == 3) {
                gp.npc4.drawDialogueScreen(g2);
            }
            System.out.println(gp.player.nomorNPC);
        }

        drawPlayerStatus();
    }

    public void drawSubWindow(Graphics2D g2, int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210); // Latar belakang hitam dengan transparansi
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255); // Warna putih untuk garis tepi
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x, y, width, height, 35, 35);
    }
}
