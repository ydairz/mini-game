package main;
import java.awt.Graphics;
import java.awt.Color;

public class Key extends Entity {
    
    public Key(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void draw(Graphics g) {
        super.draw(g, Color.YELLOW, Color.BLUE);
    }
}
