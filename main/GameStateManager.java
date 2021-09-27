package main;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Stack;

public class GameStateManager {
    // The stack is required to be accessible by the GameState's child instances.
    public Stack<GameState> state;

    public GameStateManager() {
        this.state = new Stack<GameState>();
        this.state.push(new MenuState(this));
    }

    public void next() {
        this.state.peek().next();
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
