package entity;

import main.GamePanel;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class NPC3 extends Entity implements IDialogue{
    public NPC3(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;
//        collisionOn=true;

        getImage();
        setDialogues();
    }

    public void getImage() {
        try {
            down1 = ImageIO.read(getClass().getResource("/res/npc/npc3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDialogues(){
        dialogues[0] = "Penjaga : sedang apa kamu \ndi sini anak muda?";
        dialogues[1] = "Player : aku sedang mencari \ngold disekitar sini, \napakah kamu melihatnya?";
        dialogues[2] = "Penjaga : sebrangilah jembatan \nini, disana ada lebih \n banyak gold.";
        dialogues[3] = "Player : zangkyu zomach brok!";
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
