package main;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;

public class Stair {
    
    // The position of the stair relative to the GamePanel.
    private int x;
    private int y;
    // The stair's hitbox variables - hitbox is used for collision detection.
    public Rectangle hitbox;
    // The dimensions of the stair.
    private int width;
    private int height;

    public Stair(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        // The dimensions of the stair's hitbox.
        this.width = width;
        this.height = height;
        this.hitbox = new Rectangle(this.x, this.y, this.width, this.height);
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(this.x, this.y, this.width, this.height);
        g.setColor(Color.BLUE);
        g.drawRect(this.x, this.y, this.width, this.height);
    }
}
