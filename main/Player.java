package main;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class Player {
    
    public boolean keyUp;
    public boolean keyDown;
    public boolean keyLeft;
    public boolean keyRight;
    // The position of the player relative to the GamePanel.
    private int x;
    private int y;
    // Changes in horizontal and vertical positions (horizontal and vertical speed).
    private int dx;
    private int dy;
    // Player hitbox variables - hitbox is used for collision detection.
    private Rectangle hitbox;
    private int width;
    private int height;

    public Player(int x, int y, int width, int height) {
        this.keyUp = false;
        this.keyDown = false;
        this.keyLeft = false;
        this.keyRight = false;
        this.x = x;
        this.y = y;
        this.dx = 0;
        this.dy = 0;
        // The dimensions of the player hitbox.
        this.width = width;
        this.height = height;
        this.hitbox = new Rectangle(this.x, this.y, this.width, this.height);
    }

    public void tick() {

    }

    public void move() {
        // If the player isn't moving horizontally or if both horizontal buttons are pressed,
        // the player decelerates, eventually becoming stationary.
        if (!this.keyLeft && !this.keyRight || this.keyLeft && this.keyRight) {
            this.dx *= 0.825;
        }
        // Otherwise the player moves in the corresponding horizontal direction.
        else if (this.keyLeft && !this.keyRight) {
            this.dx -= 1;
        }
        else if (!this.keyLeft && this.keyRight) {
            this.dx += 1;
        }
        // If the player isn't moving vertically or if both vertical buttons are pressed,
        // the player decelerates, eventually becoming stationary.
        if (!this.keyUp && !this.keyDown || this.keyUp && this.keyDown) {
            this.dy *= 0.825;
        }
        // Otherwise the player moves in the corresponding vertical direction.
        else if (this.keyUp && !this.keyDown) {
            this.dy -= 1;
        }
        else if (!this.keyUp && this.keyDown) {
            this.dy += 1;
        }
        // To prevent the player from sliding, a minimum threshold is put in place to set the speed to 0.
        if (this.dx > 0 && this.dx < 1) {
            this.dx = 0;
        }
        else if (this.dx < 0 && this.dx > -1) {
            this.dx = 0;
        }
        if (this.dy > 0 && this.dy < 1) {
            this.dy = 0;
        }
        else if (this.dy < 0 && this.dy > -1) {
            this.dy = 0;
        }
        // The player cannot go beyond the maximum velocity threshold.
        if (this.dx > 5) {
            this.dx = 5;
        }
        else if (this.dx < -5) {
            this.dx = -5;
        }
        if (this.dy > 5) {
            this.dy = 5;
        }
        else if (this.dy < -5) {
            this.dy = -5;
        }
        // Updating the position of the player.
        this.x += this.dx;
        this.y += this.dy;
        // Updating the position of the player's hitbox.
        this.hitbox.x = this.x;
        this.hitbox.y = this.y;
        checkCollisions();
    }

    private void checkCollisions() {
        // Prevent the player from going out of bound.
        if (this.x < 0) {
            this.x = 0;
            this.hitbox.x = this.x;
        }
        if (this.x > 761) {
            this.x = 761;
            this.hitbox.x = this.x;
        }
        if (this.y < 0) {
            this.y = 0;
            this.hitbox.y = this.y;
        }
        if (this.y > 537) {
            this.y = 537;
            this.hitbox.y = this.y;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(this.x, this.y, this.width, this.height);
        g.setColor(Color.BLUE);
        g.drawRect(this.x, this.y, this.width, this.height);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'w') {
            this.keyUp = true;
        }
        if (e.getKeyChar() == 'a') {
            this.keyLeft = true;
        }
        if (e.getKeyChar() == 's') {
            this.keyDown = true;
        }
        if (e.getKeyChar() == 'd') {
            this.keyRight = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'w') {
            this.keyUp = false;
        }
        if (e.getKeyChar() == 'a') {
            this.keyLeft = false;
        }
        if (e.getKeyChar() == 's') {
            this.keyDown = false;
        }
        if (e.getKeyChar() == 'd') {
            this.keyRight = false;
        }
    }
}
