package main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Menu extends GameState {
    
    protected String[] option;
    protected int currentOption;
    // Used to ensure that a value of obtained once when pressing a key (the array elements correspond to {up and down}),
    // meaning that holding down on a key will won't register the same key multiple times.
    private boolean[] key = {false, false};

    public Menu(GameStateManager gsm) {
        super(gsm);
        this.currentOption = 0;
    }

    @Override
    public void next() {

    }

    @Override
    public void draw(Graphics g) {
        // Creates a light blue background.
        g.setColor(new Color(150, 225, 250));
        g.fillRect(0, 0, 800, 600);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Move the current selection according to the key pressed.
        // The current selection can move from the first to the last option and vice versa.
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (this.key[0] == true) {
                // The key has already been pressed, therefore prevent the registering of the same key whilst it's being held down.
                return;
            }
            if (this.currentOption == 0) {
                this.currentOption = this.option.length - 1;
            }
            else {
                this.currentOption -= 1;
            }
            this.key[0] = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (this.key[1] == true) {
                // The key has already been pressed, therefore prevent the registering of the same key whilst it's being held down.
                return;
            }
            if (this.currentOption == this.option.length - 1) {
                this.currentOption = 0;
            }
            else {
                this.currentOption += 1;
            }
            this.key[1] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Change the value of the keys to false, so that the released keys can be registered once again
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            this.key[0] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            this.key[1] = false;
        }
    }
}
