package main;
import java.awt.Graphics;
import java.awt.Color;

public class Wall extends Entity {
    
    public Wall(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void draw(Graphics g) {
        super.draw(g, Color.LIGHT_GRAY, Color.BLUE);
    }
}
