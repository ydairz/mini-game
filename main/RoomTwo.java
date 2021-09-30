package main;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

public class RoomTwo extends GameState {

    private Player player;
    private Wall[] wall = new Wall[9];
    private Lava[] lava = new Lava[6];
    private Key key;
    private Door door;
    private Stair stair;
    // Used to alternate between the lava's operative state (alternate between active and inactive).
    private int count;

    public RoomTwo(GameStateManager gsm) {
        super(gsm);
        this.player = new Player(25, 468, 25, 25);
        // Generate walls.
        this.wall[0] = new Wall(0, 375, 275, 50);
        this.wall[1] = new Wall(355, 75, 50, 487);
        this.wall[2] = new Wall(225, 75, 50, 300);
        this.wall[3] = new Wall(75, 75, 150, 50);
        this.wall[4] = new Wall(75, 125, 50, 150);
        this.wall[5] = new Wall(405, 75, 155, 50);
        this.wall[6] = new Wall(635, 0, 50, 487);
        this.wall[7] = new Wall(480, 200, 155, 50);
        this.wall[8] = new Wall(405, 437, 155, 50);
        // Generate lava.
        this.lava[0] = new Lava(275, 375, 80, 50);
        this.lava[1] = new Lava(275, 225, 80, 50);
        this.lava[2] = new Lava(275, 75, 80, 50);
        this.lava[3] = new Lava(560, 75, 75, 50);
        this.lava[4] = new Lava(405, 200, 75, 50);
        this.lava[5] = new Lava(560, 437, 75, 50);
        // Generate key.
        this.key = new Key(162, 150, 25, 25);
        // Generate door.
        this.door = new Door(355, 0, 50, 75);
        //Generator stair.
        this.stair = new Stair(710, 25, 50, 50);
    }

    @Override
    public void next() {
        this.player.move();
        playerWallCollision(this.player, this.wall);
        // Collision detection between the player and the key is only necessary, if the player hasn't yet picked up the key.
        if (this.key != null) {
            playerKeyCollision();
        }
        if (this.door != null) {
            playerDoorCollision();
        }
        playerStairCollision();
        playerLavaCollision();
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

    private static void playerWallCollision(Player player, Wall[] wall) {
        // Horizontal collision detection. The player's hitbox moves with the player model.
        player.hitbox.x += player.dx;
        for (Wall block : wall) {
            // Collision detected - prevent the player from going into the wall.
            if (player.hitbox.intersects(block.hitbox)) {
                // Move the player to the position prior to the collision, so that the player and the wall are no longer colliding.
                player.hitbox.x -= player.dx;
                // However, if there is no collision, the player can move as close to the wall as possible.
                while(!block.hitbox.intersects(player.hitbox)) {
                    player.hitbox.x += Math.signum(player.dx);
                }
                // Once the player collides with the wall, the player is moved back a step
                // to make sure that the player and the wall aren't intersecting.
                player.hitbox.x -= Math.signum(player.dx);
                player.dx = 0;
                player.x = player.hitbox.x;
            }
        }
        // Vertical collision detection. The player's hitbox moves with the player model.
        player.hitbox.y += player.dy;
        for (Wall block : wall) {
            // Collision detected - prevent the player from going into the wall.
            if (player.hitbox.intersects(block.hitbox)) {
                // Move the player so that the player and the wall are no longer colliding.
                player.hitbox.y -= player.dy;
                // However, if the is no collision, the player can move as close to the wall as possible.
                while(!block.hitbox.intersects(player.hitbox)) {
                    player.hitbox.y += Math.signum(player.dy);
                }
                // Once the player collides with the wall, the player is moved back a step
                // to make sure that the player and the wall aren't intersecting.
                player.hitbox.y -= Math.signum(player.dy);
                player.dy = 0;
                player.y = player.hitbox.y;
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

    private void playerStairCollision() {
        // The player moves to the next level, once the player intersects with stairs.
        if (this.player.hitbox.intersects(this.stair.hitbox)) {
            // Remove the current level from the stack.
            gsm.state.pop();
            // Add the next level to the stack.
            gsm.state.push(new FinalState(gsm));
        }
    }

    private void playerLavaCollision() {
        boolean onLava = false;
        // Collision detection (only vertical collision) - The player loses health, if on active lava pits.
        for (Lava block : this.lava) {
            // Collision detected - player loses health, whilst on top of lava pit.
            if (this.player.hitbox.intersects(block.hitbox) && block.active == true) {
                this.player.hitpoints -= 1;
                onLava = true;
            }
        }
        // The player also experiences slowness, whilst on top of any lava pits.
        if (onLava) {
            this.player.slow = true;
        }
        else {
            this.player.slow = false;
        }
    }

    @Override
    public void draw(Graphics g) {
        // Draws a custom background image of the level (in the resources folder).
        BufferedImage bufferedImage = getBackgroundImage();
        g.drawImage(bufferedImage, 0, 0, null);
        // Draws the walls.
        for (int i = 0; i < this.wall.length; i++) {
            this.wall[i].draw(g);
        }
        // Draws the lava.
        for (int j = 0; j < this.lava.length; j++) {
            // Lava pits periodically change from active to inactive; pits that are inactive temporarily disappear, opening up a path.
            if (this.lava[j].active == true) {
                this.lava[j].draw(g);
            }
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

    private BufferedImage getBackgroundImage() {
        try {
            return ImageIO.read(new File("./resources/background.png"));
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
