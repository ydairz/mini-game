package main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class PauseState extends MenuState {

    public PauseState(GameStateManager gsm) {
        super(gsm);
        this.option = new String[] {"Resume", "Help", "Main Menu"};
    }

    @Override
    public void next() {
        // No functionality required...
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        // Each option is defaulted to a specific size. The selected option will be coloured green,
        // whereas the remaining options will remain black.
        g.setFont(new Font("Arial", Font.PLAIN, 64));
        for (int i = 0; i < this.option.length; i++) {
            if (i == this.currentOption) {
                g.setColor(Color.GREEN);
            }
            else {
                g.setColor(Color.BLACK);
            }
            // Centres the options horizontally.
            if (i % 2 == 0) {
                g.drawString(option[i], 280-(i*20), 200+(i*80));
            }
            else {
                g.drawString(option[i], 330, 200+(i*80));   
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        // Pressing enter will select the current highlighted option, triggering the appropriate response.
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (this.currentOption == 0) {
                // Resume the game.
                gsm.state.pop();
            }
            else if (this.currentOption == 1) {
                // Display help screen.
            }
            else {
                // Quits to the main menu (progress is not saved), by removing the pauseState at the top of the stack.
                gsm.state.pop();
                gsm.state.pop();
            }
        }
        // Pressing escape will resume the game.
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gsm.state.pop();
        }
    }
}
