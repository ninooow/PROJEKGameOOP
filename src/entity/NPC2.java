package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class NPC2 extends Entity implements IDialogue {
    public NPC2(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        getImage();
        setDialogues();
    }

    public void getImage() {
        try {
            down1 = ImageIO.read(getClass().getResource("/res/npc/npc2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDialogues(){
        dialogues[0] = "Penduduk : apa yang kau cari " +
                "\ndisini anak muda?";
        dialogues[1] = "Player : aku sedang mencari \ngold disekitar sini, \napakah kamu melihatnya?";
        dialogues[2] = "Penduduk : maaf, tapi aku \ntidak pernah keluar dari \npulau ini.";
        dialogues[3] = "Player : bikelah kalaw bgtu";
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
