package main;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class RoomOne extends LevelState {

    public RoomOne(GameStateManager gsm, Player player, Key key, Door door, Stair stair, int[][] wallDimensions) {
        super(gsm, player, key, door, stair, wallDimensions);
    }

    @Override
    public void playerStairCollision() {
        // The player moves to the next level, once the player intersects with stairs.
        if (super.player.hitbox.intersects(super.stair.hitbox)) {
            // Remove the current level from the stack.
            super.gsm.state.pop();
            // Add the next level to the stack.
            Key key = new Key(162, 150, 25, 25);
            Door door = new Door(355, 0, 50, 75);
            Stair stair = new Stair(710, 25, 50, 50);
            int[][] wallDimensions = new int[][] {
                {0, 375, 275, 50},
                {355, 75, 50, 487},
                {225, 75, 50, 300},
                {75, 75, 150, 50},
                {75, 125, 50, 150},
                {405, 75, 155, 50},
                {635, 0, 50, 487},
                {480, 200, 155, 50},
                {405, 437, 155, 50}
            };
            super.gsm.state.push(new RoomTwo(super.gsm, super.player, key, door, stair, wallDimensions));
        }
    }

    @Override
    public void draw(Graphics g) {
        // Draws a custom background image of the level (in the resources folder).
        BufferedImage bufferedImage = getBackgroundImage("./resources/background.png");
        g.drawImage(bufferedImage, 0, 0, null);
        // Then draws all other entities (except lava).
        super.draw(g);
    }
}
