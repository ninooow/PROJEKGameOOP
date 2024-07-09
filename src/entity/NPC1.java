package entity;

import main.GamePanel;
import main.UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class NPC1 extends Entity implements IDialogue{

    public NPC1(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        getImage();
        setDialogues();

    }

    public void getImage() {
        try {
            down1 = ImageIO.read(getClass().getResource("/res/npc/npc1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDialogues(){
        if(gp.player.gold<1){
            dialogues[0] = "Kepala Desa : hai anak muda, \nsepertinya kau nampak \nkebingunggan." ;
            dialogues[1] = "PLayer : aku berada dimana ini?";
            dialogues[2] = "Kepala Desa : kamu berada di \nLembah Djuang, sebuah \ndesa yang damai dan indah.";
            dialogues[3] = "Player : apakah kamu tau dimana \naku bisa mendapatkan gold?";
            dialogues[4] = "Kepala Desa : kamu bisa \nmendapatkan gold dengan \nmenyelesaikan permasalahan";
            dialogues[5] = "Player : sepertinya cukup \nmenantang, aku akan mencoba!";
            dialogues[6] = "Player : dimana aku bisa \nmenemukan gold nya?";
            dialogues[7] = "Kepala Desa : kamu bisa \nmenemukan gold di \nsekitar desa ini";
            dialogues[8] = "Player : baik, terima kasih atas \nbantuannya!";
            dialogues[9] = "Kepala Desa : semoga berhasil!";
        }else{
            dialogues[1] = "Player : terimakasi, aku sudah \nmenemukan gold nya!";
            dialogues[0] = "Kepala Desa : dengan senang hati!";
        }
    }
    public void drawDialogueScreen(Graphics2D g2) {
        //WINDOW
        int x = gp.screenWidth / 2 + gp.tileSize;
        int y = 0;
        int width = gp.screenWidth / 2 - gp.tileSize * 5;
        int height = gp.tileSize * 3;

        gp.ui.drawSubWindow(g2, x, y, width, height);

        // Display Dialogue
        int textX = x+gp.tileSize;
        int textY = y+gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(java.awt.Font.PLAIN, 18F));
        for (String line : dialogues[dialogueIndex].split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }
    }
}
