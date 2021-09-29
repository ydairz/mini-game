package main;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

public class RoomOne extends GameState {

    private Player player;
    private Wall[] wall = new Wall[10];
    private Key key;
    private Door door;

    public RoomOne(GameStateManager gsm) {
        super(gsm);
        this.player = new Player(25, 25, 25, 25);
        // Generate walls.
        this.wall[0] = new Wall(0, 75, 250, 50);
        this.wall[1] = new Wall(200, 125, 50, 175);
        this.wall[2] = new Wall(325, 0, 50, 300);
        this.wall[3] = new Wall(0, 375, 700, 50);
        this.wall[4] = new Wall(75, 200, 50, 100);
        this.wall[5] = new Wall(450, 75, 250, 50);
        this.wall[6] = new Wall(450, 125, 50, 250);
        this.wall[7] = new Wall(575, 200, 225, 50);
        this.wall[8] = new Wall(650, 512, 50, 50);
        this.wall[9] = new Wall(525, 425, 50, 50);
        // Generate key.
        this.key = new Key(88, 150, 25, 25);
        // Generate door.
        this.door = new Door(325, 300, 50, 75);
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

    @Override
    public void draw(Graphics g) {
        // Draws a custom background image of the level (in the resources folder).
        BufferedImage bufferedImage = getBackgroundImage();
        g.drawImage(bufferedImage, 0, 0, null);
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
