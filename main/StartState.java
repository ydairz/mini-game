package main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class StartState extends MenuState {

    public StartState(GameStateManager gsm) {
        super(gsm);
        this.option = new String[] {"Start", "Help", "Quit"};
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
            g.drawString(this.option[i], 330, 200+(i*80));
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        // Pressing enter will select the current highlighted option, triggering the appropriate response.
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (this.currentOption == 0) {
                // Start the game.
                Player player = new Player(25, 25, 25, 25);
                Key opener = new Key(88, 150, 25, 25);
                Door door = new Door(325, 300, 50, 75);
                Stair stair = new Stair(25, 468, 50, 50);
                int wallDimensions[][] = new int[][] {
                    {0, 75, 250, 50},
                    {200, 125, 50, 175},
                    {325, 0, 50, 300},
                    {0, 375, 700, 50},
                    {75, 200, 50, 100},
                    {450, 75, 250, 50},
                    {450, 125, 50, 250},
                    {575, 200, 225, 50},
                    {650, 512, 50, 50},
                    {375, 425, 50, 50}
                };
                gsm.state.push(new RoomOne(gsm, player, opener, door, stair, wallDimensions));
            }
            else if (this.currentOption == 1) {
                // Display help screen.
                gsm.state.push(new Help(gsm));
            }
            else {
                // Terminates the game
                System.exit(0);
            }
        }
    }
}
