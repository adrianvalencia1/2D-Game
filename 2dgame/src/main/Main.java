package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) throws Exception {
        
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Game");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        // center of the screen
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
