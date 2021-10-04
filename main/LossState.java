package main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class LossState extends MenuState {

    private String[] text;

    public LossState(GameStateManager gsm) {
        super(gsm);
        this.option = new String[] {"Main Menu", "Quit"};
        this.text = new String[] {"Game Over!", "You Lose :("};
    }

    @Override
    public void next() {
        // No functionality required...
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setFont(new Font("Arial", Font.BOLD, 64));
        g.setColor(Color.RED);
        // Centre the text horizontally.
        for (int j = 0; j < this.text.length; j++) {
            g.drawString(this.text[j], 210+(j*5), 100+(j*80));
        }
        // Each option is defaulted to a specific size (and centred horizontally).
        // The selected option will be coloured green, whereas the remaining options will remain black.
        g.setFont(new Font("Arial", Font.PLAIN, 64));
        for (int i = 0; i < this.option.length; i++) {
            if (i == this.currentOption) {
                g.setColor(Color.GREEN);
            }
            else {
                g.setColor(Color.BLACK);
            }
            g.drawString(this.option[i], 240+(i*90), 300+(i*80));
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        // Pressing enter will select the current highlighted option, triggering the appropriate response.
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (this.currentOption == 0) {
                // Back to main menu.
                gsm.state.pop();
            }
            else {
                // Terminates the game
                System.exit(0);
            }
        }
    }
}
