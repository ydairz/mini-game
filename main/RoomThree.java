package main;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class RoomThree extends LevelState {

    private Cannon[] cannon;
    private Cannon[] cannon2;
    // Used to alternate between the lava's operative state (alternate between active and inactive).
    private int count;

    public RoomThree(GameStateManager gsm, Player player, Key key, Door door, Stair stair, int[][] wallDimensions) {
        super(gsm, player, key, door, stair, wallDimensions);
        this.cannon = new Cannon[] {
            new Cannon(100, 125, 50, 15, "Left"),
            new Cannon(636, 235, 50, 15, "Right"),
            new Cannon(636, 260, 50, 15, "Right"),
            new Cannon(200, 355, 50, 15, "Left"),
            new Cannon(200, 395, 50, 15, "Left"),
            new Cannon(550, 462, 50, 15, "Left")
        };
        this.cannon2 = new Cannon[] {
            new Cannon(200, 375, 50, 15, "Left"),
            new Cannon(375, 462, 50, 15, "Left"),
            new Cannon(375, 462, 50, 15, "Right"),
            new Cannon(550, 500, 50, 15, "Left")
        };

        this.count = 0;
    }

    @Override
    public void next() {
        super.next();
        // Function to determine whether the cannons will shoot (occurs periodically) or not, depending on the value of count.
        loadCannon();
        // Exclusive addition for checking for cannonball collision. 
        playerBulletCollision();
        wallBulletCollision();
        // Moves the cannonballs' models (updates the models according to their movement).
        for (int i = 0; i < this.cannon.length; i++) {
            if (this.cannon[i].cannonball != null) {
                this.cannon[i].cannonball.move();
            }
        }
        for (int j = 0; j < this.cannon2.length; j++) {
            if (this.cannon2[j].cannonball != null) {
                this.cannon2[j].cannonball.move();
            }
        }
    }

    private void playerBulletCollision() {
        // Collision detection with the player - The player loses health, if cannonballs hit the player.
        for (Cannon block : this.cannon) {
            // Check if cannonball's are present.
            if (block.cannonball != null) {
                // Collision detected - player loses health and the intersecting cannonball disappears.
                if (super.player.hitbox.intersects(block.cannonball.hitbox)) {
                    block.cannonball = null;
                    super.player.hitpoints -= 10;
                }
            }
        }
        for (Cannon block : this.cannon2) {
            if (block.cannonball != null) {
                if (super.player.hitbox.intersects(block.cannonball.hitbox)) {
                    block.cannonball = null;
                    super.player.hitpoints -= 10;
                }
            }
        }
    }

    private void wallBulletCollision() {
        // Collision detection with the wall - the bullet simply disappears.
        for (Cannon block : this.cannon) {
            // Check if cannonball's are present.
            if (block.cannonball != null) {
                // Collision detected - the intersecting cannonball disappears.
                for (Wall sheet : this.wall) {
                    if (sheet.hitbox.intersects(block.cannonball.hitbox)) {
                        block.cannonball = null;
                        // No need to continue the loop, as the cannonball has disappeared.
                        break;
                    }
                }
            }
        }
        for (Cannon block : this.cannon2) {
            if (block.cannonball != null) {
                for (Wall sheet : this.wall) {
                    if (sheet.hitbox.intersects(block.cannonball.hitbox)) {
                        block.cannonball = null;
                        break;
                    }
                }
            }
        }
    }

    private void loadCannon() {
        // Cannons shoot at a periodic rate. Some cannon shoot at different time intervals.
        if (this.count == 60) {
            for (int i = 0; i < this.cannon.length; i++) {
                this.cannon[i].shoot();
            }
            this.count = 0;
        }
        else if (this.count == 30) {
            for (int i = 0; i < this.cannon2.length; i++) {
                this.cannon2[i].shoot();
            }
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
        BufferedImage bufferedImage = getBackgroundImage("./resources/background3.png");
        g.drawImage(bufferedImage, 0, 0, null);
        // Then draws all other entities (except lava).
        super.draw(g);
        // Draws the cannons and their cannonballs.
        for (int i = 0; i < this.cannon.length; i++) {
            this.cannon[i].draw(g);
            // Cannonballs that intersect with the player or wall disappear.
            if (this.cannon[i].cannonball != null) {
                this.cannon[i].cannonball.draw(g);
            }
        }
        for (int i = 0; i < this.cannon2.length; i++) {
            this.cannon2[i].draw(g);
            if (this.cannon2[i].cannonball != null) {
                this.cannon2[i].cannonball.draw(g);
            }
        }
    }
}
