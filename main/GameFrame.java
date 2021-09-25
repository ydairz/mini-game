package main;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame implements KeyListener {
    
    private static final int frameWidth = 800;
    private static final int frameHeight = 600;
    private GamePanel gamePanel;

    public GameFrame(String title) {
        this.setSize(frameWidth, frameHeight);
        this.setResizable(false);
        this.setTitle(title);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.gamePanel = new GamePanel(frameWidth, frameHeight);
        this.add(gamePanel);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.gamePanel.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.gamePanel.keyReleased(e);
    }
}
