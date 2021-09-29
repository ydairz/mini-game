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

    public RoomOne(GameStateManager gsm) {
        super(gsm);
        this.player = new Player(25, 25, 25, 25);
        // Generate walls.
        wall[0] = new Wall(0, 75, 250, 50);
        wall[1] = new Wall(200, 125, 50, 175);
        wall[2] = new Wall(325, 0, 50, 300);
        wall[3] = new Wall(0, 375, 700, 50);
        wall[4] = new Wall(75, 200, 50, 100);
        wall[5] = new Wall(450, 75, 250, 50);
        wall[6] = new Wall(450, 125, 50, 250);
        wall[7] = new Wall(575, 200, 225, 50);
        wall[8] = new Wall(650, 512, 50, 50);
        wall[9] = new Wall(525, 425, 50, 50);
    }

    @Override
    public void next() {
        this.player.move();
        collision(this.player, this.wall);
    }

    private static void collision(Player player, Wall[] wall) {
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

    @Override
    public void draw(Graphics g) {
        // Draws a custom background image of the level (in the resources folder).
        BufferedImage bufferedImage = getBackgroundImage();
        g.drawImage(bufferedImage, 0, 0, null);
        // Draws the walls.
        for (int i = 0; i < this.wall.length; i++) {
            this.wall[i].draw(g);
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
