package main;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

public class RoomOne extends GameState {

    private Player player;
    private Wall[] wall = new Wall[10];

    public RoomOne(GameStateManager gsm) {
        super(gsm);
        this.player = new Player(25, 25, 25, 25);
        // Generate walls.
        wall[0] = new Wall(0, 75, 250, 50);
        wall[1] = new Wall(200, 125, 50, 175);
        wall[2] = new Wall(325, 0, 50, 300);
        wall[3] = new Wall(0, 375, 700, 50);
        wall[4] = new Wall(75, 200, 50, 100);
        wall[5] = new Wall(450, 75, 250, 50);
        wall[6] = new Wall(450, 125, 50, 250);
        wall[7] = new Wall(575, 200, 225, 50);
        wall[8] = new Wall(650, 512, 50, 50);
        wall[9] = new Wall(525, 425, 50, 50);
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
