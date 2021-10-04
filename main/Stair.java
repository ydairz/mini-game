package main;
import java.awt.Graphics;
import java.awt.Color;

public class Stair extends Entity {

    public Stair(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void draw(Graphics g) {
        super.draw(g, Color.GREEN, Color.BLUE);
    }
}
