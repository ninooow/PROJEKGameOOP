package main;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Goldean Adventure");

        GamePanel gamePanel = new GamePanel();
        MainMenuPanel mainMenuPanel = new MainMenuPanel(gamePanel, window);
        window.add(mainMenuPanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
