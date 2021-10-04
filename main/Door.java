package main;
import java.awt.Graphics;
import java.awt.Color;

public class Door extends Entity {

    // Unlockable door's disappear which opens up a path.
    public boolean unlockable;

    public Door(int x, int y, int width, int height) {
        super(x, y, width, height);
        // Doors can only be unlocked with a key. The state will only change, once the player has picked up a key.
        this.unlockable = false;
    }

    public void draw(Graphics g) {
        super.draw(g, Color.RED, Color.BLUE);
    }
}
