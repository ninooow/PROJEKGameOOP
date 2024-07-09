package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = true;
            if (gp.isGamePaused()) {
                gp.navigatePauseMenu(-1);
            }
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
            if (gp.isGamePaused()) {
                gp.navigatePauseMenu(1);
            }
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gp.togglePause();
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.isGamePaused()) {
                gp.selectPauseOption();
            }
        }


        if (gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
        } else if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.npc[gp.player.nomorNPC].dialogueIndex++;
                if (gp.npc[gp.player.nomorNPC].dialogueIndex >= gp.npc[gp.player.nomorNPC].dialogues.length || gp.npc[gp.player.nomorNPC].dialogues[gp.npc[gp.player.nomorNPC].dialogueIndex] == null) {
                    gp.gameState = gp.playState;
                    gp.npc[gp.player.nomorNPC].dialogueIndex = 0;
                }
            }
        } else if (gp.gameState == gp.questionState) {
            if (code == KeyEvent.VK_T) {
                gp.setPlayerAnswer("true");
                gp.checkAnswer();
            } else if (code == KeyEvent.VK_F) {
                gp.setPlayerAnswer("false");
                gp.checkAnswer();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
