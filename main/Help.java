package main;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;

public class Help extends MenuState {

    public Help(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void next() {
        // No functionality required...
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        // Draws the help screen (in the resources folder).
        BufferedImage bufferedImage = getBackgroundImage("./resources/help.png");
        g.drawImage(bufferedImage, 0, 0, null);
        
    }

    private BufferedImage getBackgroundImage(String resource) {
        try {
            return ImageIO.read(new File(resource));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Pressing enter will select the current highlighted option, triggering the appropriate response.
        if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (this.currentOption == 0) {
                // Back to main menu.
                gsm.state.pop();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Do nothing...
    }
}
