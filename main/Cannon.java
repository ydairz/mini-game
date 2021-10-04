package main;
import java.awt.Graphics;
import java.awt.Color;

public class Cannon extends Entity {
    
    public Cannonball cannonball;
    private String direction;

    public Cannon(int x, int y, int width, int height, String direction) {
        super(x, y, width, height);
        this.direction = direction;
    }

    public void shoot() {
        // Bullets appear 5 pixels in front of the cannon.
        if (this.direction == "Left") {
            this.cannonball = new Cannonball(this.getX()-5, this.getY()+5, this.direction);
        }
        else {
            this.cannonball = new Cannonball(this.getX()+55, this.getY()+5, this.direction);
        }
    }

    public void draw(Graphics g) {
        super.draw(g, Color.GRAY, Color.BLUE);
    }
}
