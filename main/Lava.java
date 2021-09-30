package main;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;

public class Lava {
    
    // The position of the lava relative to the GamePanel.
    private int x;
    private int y;
    // Lava's hitbox variables - hitbox is used for collision detection.
    public Rectangle hitbox;
    // The dimensions of the lava.
    private int width;
    private int height;
    // Inactive lava pits disappear which opens up a path.
    public boolean active;
    
    public Lava(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        // The dimensions of the lava's hitbox.
        this.width = width;
        this.height = height;
        this.hitbox = new Rectangle(this.x, this.y, this.width, this.height);
        // Lava pits periodically change from active (true) to inactive (false).
        this.active = true;
    }

    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(this.x, this.y, this.width, this.height);
        g.setColor(Color.BLUE);
        g.drawRect(this.x, this.y, this.width, this.height);
    }
}
