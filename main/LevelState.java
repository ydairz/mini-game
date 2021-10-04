package main;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

public class LevelState extends GameState {
    
    protected Player player;
    private Key key;
    private Door door;
    protected Stair stair;
    protected Wall[] wall;

    public LevelState(GameStateManager gsm, Player player, Key key, Door door, Stair stair, int[][] wallDimensions) {
        super(gsm);
        this.player = player;
        this.key = key;
        this.door = door;
        this.stair = stair;
        this.wall = new Wall[wallDimensions.length];
        for (int i = 0; i < wallDimensions.length; i++) {
            this.wall[i] = new Wall(wallDimensions[i][0], wallDimensions[i][1], wallDimensions[i][2], wallDimensions[i][3]);
        }
    }

    @Override
    public void next() {
        if (this.player.hitpoints > 0) {
            // Moves the player model (updates the player model according to its movement).
            this.player.move();
            playerWallCollision();
            // Collision detection between the player and the key is only necessary, if the player hasn't yet picked up the key.
            if (this.key != null) {
                playerKeyCollision();
            }
            // Collision detection between the player and the door is only necessary, if the player hasn't yet opened the door.
            if (this.door != null) {
                playerDoorCollision();
            }
            playerStairCollision();
        }
        else {
            gsm.state.pop();
            gsm.state.push(new LossState(gsm));
        }
    }

    private void playerWallCollision() {
        // Horizontal collision detection. The player's hitbox moves with the player model.
        this.player.hitbox.x += this.player.dx;
        for (Wall block : this.wall) {
            // Collision detected - prevent the player from going into the wall.
            if (this.player.hitbox.intersects(block.hitbox)) {
                // Move the player to the position prior to the collision, so that the player and the wall are no longer colliding.
                this.player.hitbox.x -= this.player.dx;
                // However, if there is no collision, the player can move as close to the wall as possible.
                while(!block.hitbox.intersects(this.player.hitbox)) {
                    this.player.hitbox.x += Math.signum(this.player.dx);
                }
                // Once the player collides with the wall, the player is moved back a step
                // to make sure that the player and the wall aren't intersecting.
                this.player.hitbox.x -= Math.signum(this.player.dx);
                this.player.dx = 0;
                this.player.x = this.player.hitbox.x;
            }
        }
        // Vertical collision detection. The player's hitbox moves with the player model.
        this.player.hitbox.y += this.player.dy;
        for (Wall block : this.wall) {
            // Collision detected - prevent the player from going into the wall.
            if (this.player.hitbox.intersects(block.hitbox)) {
                // Move the player so that the player and the wall are no longer colliding.
                this.player.hitbox.y -= this.player.dy;
                // However, if the is no collision, the player can move as close to the wall as possible.
                while(!block.hitbox.intersects(this.player.hitbox)) {
                    this.player.hitbox.y += Math.signum(this.player.dy);
                }
                // Once the player collides with the wall, the player is moved back a step
                // to make sure that the player and the wall aren't intersecting.
                this.player.hitbox.y -= Math.signum(this.player.dy);
                this.player.dy = 0;
                this.player.y = this.player.hitbox.y;
            }
        }
    }

    private void playerKeyCollision() {
        // Collision detected - "delete" the key, by removing its reference.
        if (this.player.hitbox.intersects(this.key.hitbox)) {
            // Change door's variable into unlockable and get rid of key.
            this.key = null;
            this.door.unlockable = true;
        }
    }

    private void playerDoorCollision() {
        // The door can be opened, only if the key for it has been picked up.
        if (this.door.unlockable == true) {
            if (this.player.hitbox.intersects(this.door.hitbox)) {
                // Get's rid of the door, opening up the path.
                this.door = null;
            }
        }
        // Otherwise, the door acts like a wall.
        else {
            // Collision detected (only horizontal detection) - prevent the player from going into the door.
            if (this.player.hitbox.intersects(this.door.hitbox)) {
                // Move the player to the position prior to the collision, so that the player and the door are no longer colliding.
                this.player.hitbox.x -= this.player.dx;
                // However, if there is no collision, the player can move as close to the door as possible.
                while(!this.door.hitbox.intersects(this.player.hitbox)) {
                    this.player.hitbox.x += Math.signum(this.player.dx);
                }
                // Once the player collides with the door, the player is moved back a step
                // to make sure that the player and the wall aren't intersecting.
                this.player.hitbox.x -= Math.signum(this.player.dx);
                this.player.dx = 0;
                this.player.x = player.hitbox.x;
            }
        }
    }

    public void playerStairCollision() {
        // The player moves to the next level, once the player intersects with stairs.
        if (this.player.hitbox.intersects(this.stair.hitbox)) {
            // Remove the current level from the stack.
            gsm.state.pop();
            // Add the next level to the stack.
            gsm.state.push(new FinalState(gsm));
        }
    }

    @Override
    public void draw(Graphics g) {
        // Draws the walls.
        for (int i = 0; i < this.wall.length; i++) {
            this.wall[i].draw(g);
        }
        // Draw key.
        if (this.key != null) {
            this.key.draw(g);
        }
        // Draw door.
        if (this.door != null) {
            this.door.draw(g);
        }
        // Draw stairs.
        this.stair.draw(g);
        // Update the player object's position.
        this.player.draw(g);
    }

    protected BufferedImage getBackgroundImage(String resource) {
        try {
            return ImageIO.read(new File(resource));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gsm.state.push(new PauseState(gsm));
        }
        else {
            this.player.keyPressed(e);
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        this.player.keyReleased(e);
    }
}
