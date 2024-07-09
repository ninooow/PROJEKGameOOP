package main;

import entity.*;
import object.OBJ_Gold;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalSize = 16; // 20x20 tile
    final int scale = 3; // 3x scale

    public final int tileSize = originalSize * scale; // 60x60 pixels
    public final int maxScreenCol = 24; // 24 tiles
    public final int maxScreenRow = 12; // 12 tiles
    public final int screenWidth = tileSize * maxScreenCol; // 1440 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 720 pixels

    AssetSetter aSetter = new AssetSetter(this);

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    public UI ui = new UI(this);
    Thread gameThread;

    public void setPlayerAnswer(String answer) {
        this.playerAnswer = answer;
    }

    public String getPlayerAnswer() {
        return playerAnswer;
    }

    public void setShowQuestion(boolean show) {
        this.showQuestion = show;
    }

    public boolean isShowQuestion() {
        return showQuestion;
    }
    public ArrayList<Integer> countGold=new ArrayList<>();


    // Tambahkan pertanyaan dan jawaban
    public String[][] questionList = {
            {"Dalam Java, keyword class digunakan \nuntuk mendefinisikan sebuah class \nbaru.\n", "true"},
            {"Sebuah objek adalah instance dari \nsebuah class.\n", "true"},
            {"Dalam Java, keyword extends \ndigunakan untuk membuat objek \nbaru dari sebuah class.\n", "false"},
            {"Semua variabel dalam sebuah class \nharus bersifat public.\n", "false"},
            {"Sebuah class di Java dapat \nmengimplementasikan lebih dari satu \ninterface.", "true"}
    };

    // ENTITY AND OBJECT
    public Entity npc[] = new Entity[10];
    public ArrayList<OBJ_Gold> goldList = new ArrayList<>();

    public Player player = new Player(this, keyH);

    NPC1 npc1 = new NPC1(this);
    NPC2 npc2 = new NPC2(this);
    NPC3 npc3 = new NPC3(this);
    NPC4 npc4 = new NPC4(this);
    private boolean isGameRunning = false;
    private boolean isGamePaused = false;
    private int pauseOption = 0;
    private final String[] pauseOptions = {"Continue", "Exit"};
    private MusicPlayer musicPlayer;

    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    public final int questionState = 4;  // Tambahkan state baru untuk pertanyaan
    private String currentQuestion;
    private String correctAnswer;
    private boolean showQuestion;
    private String playerAnswer;


    public GamePanel() throws IOException {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
//        this.addKeyListener(new KeyHandler(this));
        musicPlayer = new MusicPlayer();
        musicPlayer.playMusic("res/music/musicGame.wav");
        musicPlayer.setVolume(0.5f);
        musicPlayer.loopMusic(); // Menambahkan loopMusic setelah playMusic
    }

    public void setupGame() {
        aSetter.setNPC();
        aSetter.setObject();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void startGame() {
        isGameRunning = true;
        this.addKeyListener(keyH);
        this.requestFocusInWindow();
        setupGame();
        gameState = playState;
        startGameThread();
    }

    public void togglePause() {
        isGamePaused = !isGamePaused;
        if (isGamePaused) {
            pauseOption = 0; // Reset to first option when paused
        }
    }

    public void navigatePauseMenu(int direction) {
        if (isGamePaused) {
            pauseOption += direction;
            if (pauseOption < 0) {
                pauseOption = pauseOptions.length - 1;
            } else if (pauseOption >= pauseOptions.length) {
                pauseOption = 0;
            }
        }
    }

    public void selectPauseOption() {
        if (isGamePaused) {
            switch (pauseOptions[pauseOption]) {
                case "Continue":
                    togglePause();
                    break;
                case "Exit":
                    System.exit(0);
                    break;
            }
        }
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; // 16.6666666667 milliseconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;
            if (delta >= 1) {
                if (isGameRunning && !isGamePaused) {
                    update();
                }
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if (gameState == playState) {
            player.update();
        }
        // No need to update anything in question state
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if (isGameRunning) {
            tileM.draw(g2);

            // Draw NPC
            for (Entity npcEntity : npc) {
                if (npcEntity != null) {
                    npcEntity.draw(g2);
                }
            }

            // Draw Gold
            for (OBJ_Gold gold : goldList) {
                int screenX = (int) (gold.worldX - player.worldX + player.screenX);
                int screenY = (int) (gold.worldY - player.worldY + player.screenY);
                if (screenX + tileSize > 0 && screenX < screenWidth && screenY + tileSize > 0 && screenY < screenHeight) {
                    g2.drawImage(gold.image, screenX, screenY, tileSize, tileSize, null);
                }
            }

            player.draw(g2);

            if (gameState == dialogueState) {
                if (player.nomorNPC >= 0 && player.nomorNPC < npc.length && npc[player.nomorNPC] != null) {
                    ((IDialogue) npc[player.nomorNPC]).drawDialogueScreen(g2);
                }
            } else if (gameState == questionState) {
                drawQuestionScreen(g2);
            } else {
                ui.draw(g2);
            }
        }

        if (isGamePaused) {
            drawPauseScreen(g2);
        }

        g2.dispose();
    }


    public void drawPauseScreen(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, screenWidth, screenHeight);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 60));
        String text = "Paused";
        int x = (screenWidth - g2.getFontMetrics().stringWidth(text)) / 2;
        int y = screenHeight / 4;
        g2.drawString(text, x, y);

        g2.setFont(new Font("Arial", Font.BOLD, 40));
        for (int i = 0; i < pauseOptions.length; i++) {
            text = pauseOptions[i];
            x = (screenWidth - g2.getFontMetrics().stringWidth(text)) / 2;
            y = screenHeight / 2 + (i * 50);
            if (i == pauseOption) {
                g2.setColor(Color.YELLOW);
            } else {
                g2.setColor(Color.WHITE);
            }
            g2.drawString(text, x, y);
        }
    }

    public TileManager getTileM() {
        return tileM;
    }

    public boolean isGamePaused() {
        return isGamePaused;
    }

    // Tambahkan metode untuk menampilkan pertanyaan
    public void showQuestion(int questionIndex) {
        currentQuestion = questionList[questionIndex][0];
        correctAnswer = questionList[questionIndex][1];
        showQuestion = true;
        playerAnswer = "";
        gameState = questionState;
    }

    public void drawQuestionScreen(Graphics2D g2) {
        int x = screenWidth / 4;
        int y = screenHeight / 4;
        int width = screenWidth / 2;
        int height = screenHeight / 2;

        ui.drawSubWindow(g2, x, y, width, height);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));

        int textX = x + 20;
        int textY = y + 40;
        for (String line : currentQuestion.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        textY += 40;
        g2.drawString("Press T for True, F for False", textX, textY);
    }

    public void checkAnswer() {
        if (playerAnswer.equalsIgnoreCase(correctAnswer)) {
            player.gold++;
            countGold.add(1);
            JOptionPane.showMessageDialog(null, "Correct! You earned a gold.", "Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Wrong answer. No gold for you.", "Result", JOptionPane.ERROR_MESSAGE);
        }
        gameState = playState;
        showQuestion = false;
    }

}
