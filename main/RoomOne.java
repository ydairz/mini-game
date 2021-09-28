package main;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

import javax.imageio.ImageIO;

public class RoomOne extends GameState {

    private Player player;
    private Wall[] wall = new Wall[3];

    public RoomOne(GameStateManager gsm) {
        super(gsm);
        this.player = new Player(25, 25, 25, 25);
        // Generate walls.
        for (int i = 0; i < wall.length-1; i = i + 3) {
            wall[i] = new Wall(0+(i*50), 75, 250, 50);
            wall[i+1] = new Wall(200+(i*50), 125, 50, 175);
            wall[i+2] = new Wall(325+(i*50), 0, 50, 300);
        }
    }

    @Override
    public void next() {
        this.player.move();
    }

    @Override
    public void draw(Graphics g) {
        // Draws a custom background image of the level (in the resources folder).
        BufferedImage bufferedImage = getBackgroundImage();
        g.drawImage(bufferedImage, 0, 0, null);
        // Draws the walls.
        for (int i = 0; i < this.wall.length; i++) {
            this.wall[i].draw(g);
        }
        // Update the player object's position.
        this.player.draw(g);
    }

    private BufferedImage getBackgroundImage() {
        try {
            return ImageIO.read(new File("./resources/background.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        this.player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.player.keyReleased(e);
    }
}
