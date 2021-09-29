package main;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;

public class Door {

    // The position of the door relative to the GamePanel.
    private int x;
    private int y;
    // The door's hitbox variables - hitbox is used for collision detection.
    public Rectangle hitbox;
    // The dimensions of the door.
    private int width;
    private int height;
    // Unlockable door's disappear which opens up a path.
    public boolean unlockable;

    public Door(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        // The dimensions of the door's hitbox.
        this.width = width;
        this.height = height;
        this.hitbox = new Rectangle(this.x, this.y, this.width, this.height);
        // Doors can only be unlocked with a key. The state will only change, once the player has picked up a key.
        this.unlockable = false;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(this.x, this.y, this.width, this.height);
        g.setColor(Color.BLUE);
        g.drawRect(this.x, this.y, this.width, this.height);
    }
}
