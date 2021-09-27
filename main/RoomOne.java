package main;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

import javax.imageio.ImageIO;

public class RoomOne extends GameState {

    private Player player;

    public RoomOne(GameStateManager gsm) {
        super(gsm);
        this.player = new Player(0, 0, 25, 25);
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
