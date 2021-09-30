package main;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class RoomTwo extends LevelState {

    private Lava[] lava = new Lava[6];
    // Used to alternate between the lava's operative state (alternate between active and inactive).
    private int count;

    public RoomTwo(GameStateManager gsm, Player player, Key key, Door door, Stair stair, int[][] wallDimensions) {
        super(gsm, player, key, door, stair, wallDimensions);
        this.lava = new Lava[] {
            new Lava(275, 375, 80, 50),
            new Lava(275, 225, 80, 50),
            new Lava(275, 75, 80, 50),
            new Lava(560, 75, 75, 50),
            new Lava(405, 200, 75, 50),
            new Lava(560, 437, 75, 50)
        };
        this.count = 0;
    }

    @Override
    public void next() {
        super.next();
        // Exclusive addition for checking lava collision.
        playerLavaCollision();
        // Function to make the lava pits alternate their state (active or inactive), depending on the value of count. 
        changeLava();
    }

    private void playerLavaCollision() {
        boolean onLava = false;
        // Collision detection (only vertical collision) - The player loses health, if on active lava pits.
        for (Lava block : this.lava) {
            // Collision detected - player loses health, whilst on top of lava pit.
            if (super.player.hitbox.intersects(block.hitbox) && block.active == true) {
                super.player.hitpoints -= 1;
                onLava = true;
            }
        }
        // The player also experiences slowness, whilst on top of any lava pits.
        if (onLava) {
            super.player.slow = true;
        }
        else {
            super.player.slow = false;
        }
    }

    private void changeLava() {
        // Lava pits will periodically (every minute) appear and then disappear.
        if (this.count == 60) {
            for (int i = 0; i < this.lava.length; i++) {
                if (this.lava[i].active) {
                    this.lava[i].active = false;
                }
                else {
                    this.lava[i].active = true;
                }
            }
            this.count = 0;
        }
        this.count++;
    }

    @Override
    public void playerStairCollision() {
        // The player moves to the next level, once the player intersects with stairs.
        if (super.player.hitbox.intersects(super.stair.hitbox)) {
            // Remove the current level from the stack.
            super.gsm.state.pop();
            // Add the victory menu to the stack.
            super.gsm.state.push(new FinalState(super.gsm));
        }
    }

    @Override
    public void draw(Graphics g) {
        // Draws a custom background image of the level (in the resources folder).
        BufferedImage bufferedImage = getBackgroundImage("./resources/background2.png");
        g.drawImage(bufferedImage, 0, 0, null);
        // Draws the lava.
        for (int j = 0; j < this.lava.length; j++) {
            // Lava pits periodically change from active to inactive; pits that are inactive temporarily disappear, opening up a path.
            if (this.lava[j].active == true) {
                this.lava[j].draw(g);
            }
        }
        // Then draws all other entities (except lava).
        super.draw(g);
    }
}
