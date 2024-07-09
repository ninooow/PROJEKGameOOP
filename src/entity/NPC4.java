package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class NPC4 extends Entity implements IDialogue {
    public NPC4(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        getImage();
        setDialogues();
    }

    public void getImage() {
        try {
            down1 = ImageIO.read(getClass().getResource("/res/npc/npc4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDialogues(){
        dialogues[0] = "Player : Halo";
        dialogues[1] = "Petani : apa yang kamu cari?";
        dialogues[2] = "Player : aku ingin membeli \nlabumu";
        dialogues[3] = "Petani : labu ini seharga 6 gold";
        dialogues[4] = "Player : aku akan membelinya";
        dialogues[5] = "Petani : uangmu kurang!";
        dialogues[6] = "Player : oh iya deng!";
        dialogues[7] = "Petani : Silakan kembali \nkalau uangmu cukup!";
        }

    @Override
    public void drawDialogueScreen(Graphics2D g2) {
        //WINDOW
        int x = gp.screenWidth / 2 + gp.tileSize;
        int y = 0;
        int width = gp.screenWidth / 2 - gp.tileSize * 5;
        int height = gp.tileSize * 3;

        gp.ui.drawSubWindow(g2, x, y, width, height);

        // Display Dialogue
        int textX = x + gp.tileSize;
        int textY = y + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(java.awt.Font.PLAIN, 20F));
        for (String line : dialogues[dialogueIndex].split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }
    }
}
