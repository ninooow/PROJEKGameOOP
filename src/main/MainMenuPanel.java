package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class MainMenuPanel extends JPanel implements KeyListener {
    private int currentSelection = 0;
    private String[] options = {"Start", "Exit"};
    private GamePanel gp;
    private JFrame window;
    private String title = "Goldean Adventure";
    private MusicPlayer musicPlayer;

    public MainMenuPanel(GamePanel gp, JFrame window) {
        this.gp = gp;
        this.window = window;
        this.musicPlayer = new MusicPlayer();
        this.setPreferredSize(new Dimension(gp.screenWidth, gp.screenHeight));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 60));

        // Menggambar judul di tengah
        FontMetrics fm = g2.getFontMetrics();
        int titleX = (gp.screenWidth - fm.stringWidth(title)) / 2;
        int titleY = gp.screenHeight / 4;
        g2.drawString(title, titleX, titleY);

        g2.setFont(new Font("Arial", Font.BOLD, 50));

        // Menggambar opsi di tengah
        for (int i = 0; i < options.length; i++) {
            if (i == currentSelection) {
                g2.setColor(Color.YELLOW);
            } else {
                g2.setColor(Color.WHITE);
            }
            int x = (gp.screenWidth - fm.stringWidth(options[i])) / 2;
            int y = (gp.screenHeight / 2) + (i * 60);
            g2.drawString(options[i], x, y);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            currentSelection--;
            if (currentSelection < 0) {
                currentSelection = options.length - 1;
            }
        }
        if (code == KeyEvent.VK_S) {
            currentSelection++;
            if (currentSelection >= options.length) {
                currentSelection = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            if (currentSelection == 0) {
                // Start the game
                startGame();
            } else if (currentSelection == 1) {
                // Exit the game
                System.exit(0);
            }
        }
        repaint();
    }

    private void startGame() {
        window.remove(this);
        window.add(gp);
        window.revalidate();
        gp.startGame();
        // Memulai musik saat game dimulai
        musicPlayer.playMusic("src/res/music/musicGame.wav");
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
