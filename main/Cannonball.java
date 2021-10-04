package main;
import java.awt.Graphics;
import java.awt.Color;

public class Cannonball extends Entity {

    private int dx;
    
    public Cannonball(int x, int y, String direction) {
        // Dimension (width and height) of cannonballs remains consistent.
        super(x, y, 5, 5);
        if (direction == "Left") {
            this.dx = -5;
        }
        else if (direction == "Right") {
            this.dx = 5;
        }
    }

    public void move() {
        // Updating the position of the cannonball.
        this.updateX(this.dx);
        // Updating the position of the cannonball's hitbox.
        this.hitbox.x = this.getX();
    }

    public void draw(Graphics g) {
        super.draw(g, Color.WHITE, Color.BLUE);
    }
}
