package main;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements Runnable {
    
    private Thread thread;
    private boolean running;
    // Frames are updated once every 16ms, to ensure that the game runs smoothly on 60fps. 
    private long targetTime = 1000 / 60;
    // The width and the height of the panel stay consistent with the frame's width and height.
    private int panelWidth;
    private int panelHeight;
    private GameStateManager gsm;
    private Player player;

    public GamePanel(int panelWidth, int panelHeight) {
        this.setLocation(0, 0);
        this.setSize(panelWidth, panelHeight);
        this.setFocusable(true);
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        // Creating the player object. GamePanel object is passed on to ensure that collision detection works as intended.
        // The player object must know when it is about to intersect with the walls.
        this.player = new Player(this);
        this.gsm = new GameStateManager();
        if (this.thread == null) {
            this.thread = new Thread(this);
            this.thread.start();
        }
    }

    public void run() {
        this.running = true;
        long start;
        long elapsed;
        long wait;
        // Game loop...
        while(this.running) {
            start = System.nanoTime();
            // The player's position is to be update every frame. If the player's position has changed,
            // The position of the player model is updated.
            this.player.move();
            repaint();
            elapsed = System.nanoTime() - start;
            wait = this.targetTime - (elapsed / 1000000);
            // There is no need to make the thread sleep if a frame takes more than 16ms to load (at the cost of frame rate).
            if (wait >= 0) {
                try {
                    // The sleep fuction is used to prevent the game from exceeding 60fps.
                    Thread.sleep(wait);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ???
    public void tick() {
        this.gsm.tick();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, this.panelWidth, this.panelHeight);
        this.gsm.draw(g);
    }

    public void keyPressed(KeyEvent e) {
        this.gsm.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        this.gsm.keyReleased(e);
    }
}
