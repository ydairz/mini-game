package main;
import java.awt.Graphics;
import java.awt.Color;

public class Lava extends Entity {
    
    // Inactive lava pits disappear which opens up a path.
    public boolean active;
    
    public Lava(int x, int y, int width, int height) {
        super(x, y, width, height);
        // Lava pits periodically change from active (true) to inactive (false).
        this.active = true;
    }

    public void draw(Graphics g) {
        super.draw(g, Color.ORANGE, Color.BLUE);
    }
}
