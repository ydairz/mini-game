package main;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;

public class Entity {

    // The position of the entity relative to the GamePanel.
    private int x;
    private int y;
    // The entity's hitbox variables - hitbox is used for collision detection.
    public Rectangle hitbox;
    // The dimensions of the entity.
    private int width;
    private int height;
    
    public Entity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hitbox = new Rectangle(this.x, this.y, this.width, this.height);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void updateX(int x) {
        this.x += x;
    }

    public void draw(Graphics g, Color primary, Color secondary) {
        g.setColor(primary);
        g.fillRect(this.x, this.y, this.width, this.height);
        g.setColor(secondary);
        g.drawRect(this.x, this.y, this.width, this.height);
    }
}
