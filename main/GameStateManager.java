package main;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Stack;

public class GameStateManager {
    
    private Stack<GameState> state;
    public GameStateManager() {
        this.state = new Stack<GameState>();
        this.state.push(new MenuState(this));
    }

    public void tick() {
        this.state.peek().tick();
    }

    public void draw(Graphics g) {
        this.state.peek().draw(g);
    }

    public void keyPressed(KeyEvent e) {
        this.state.peek().keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        this.state.peek().keyReleased(e);
    }
}
