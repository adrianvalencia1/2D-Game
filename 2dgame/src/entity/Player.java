package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.Graphics2D;
import java.io.IOException;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Player extends AnimatedEntity {
    
    GamePanel gp;
    KeyHandler keyH;
    PhysicsVector location;
    
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        location = new PhysicsVector();

        setDefaultValues(0, 0, 3);
        setPlayerImage();
        setPlayerSize(50, 50); // size of player, used for hitbox
        setHealth(100);
        setMaxHealth(100);
    }

    public void setDefaultValues(int x, int y, int speed) {
        // spawn point
        this.x = x;
        this.y = y;
        this.speed = speed;

        direction = "idle";
        lastDirection = "idle";
        isColliding = false;
    }

    protected void setPlayerSize(int width, int height) {this.width = width; this.height = height;}
    public void setLastMovement(String lastDirection) {this.lastDirection = lastDirection;}
    public void setCollision(boolean collision) {isColliding =  collision;}
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    public void setHitbox(int x, int y, int width, int height) {hitbox = new Rectangle(x, y, width, height);}
    public void setHealth(int health) {this.health = health;}
    public void setMaxHealth(int maxHealth) {this.maxHealth = maxHealth;}

    public int getHealth() { return health; }
    public int getX() {return x;}
    public int getY() {return y;}
    public Rectangle getHitbox() {return new Rectangle(getX(), getY(), width, height);}
    public String getLastMovement() { return lastDirection; }
    
    public void setPlayerImage() {
        try {
            idle = ImageIO.read(getClass().getResourceAsStream("/player/idle.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/down2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/right2.png"));
        } catch(IOException e) {e.printStackTrace();}
    }

    public void update() {

         // collision check

        // TODO: replace animations
        // TODO: diagonal speed
        gp.checkCollisions();
        
        direction = "idle";

        if(keyH.upPressed == true) {
            Ydirection = "up";
            y -= speed;
        } else if (keyH.downPressed == true) {
            Ydirection = "down";
            y += speed;
        } else {
            Ydirection = "idle";
        }
        if (keyH.leftPressed == true) {
            Xdirection = "left";
            x -= speed;
        } else if (keyH.rightPressed == true) {
            Xdirection = "right";
            x += speed;
        } else {
            Xdirection = "idle";  
        }
    
        // animation update
        spriteCounter++;
        if (spriteCounter > 20) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        
        switch(direction) {
        case "idle":
            image = idle;
            break;
        case "up":
            if(spriteNum == 1) 
                image = up1;
            if (spriteNum == 2)
                image = up2;
            break;
        case "down":
            if(spriteNum == 1)
                image = down1;
            if (spriteNum == 2) 
                image = down2;
            break;
        case "left":
            if(spriteNum == 1)
                image = left1;
            if (spriteNum == 2) 
                image = left2;
            break;
        case "right":
            if(spriteNum == 1)
                image = right1;
            if (spriteNum == 2)
                image = right2;
            break;
        case "downRight":
            if(spriteNum == 1)
                image = down1;
            if (spriteNum == 2) 
                image = down2;
            break;
        case "downLeft":
            if(spriteNum == 1)
                image = down1;
            if (spriteNum == 2) 
                image = down2;
            break;
        case "upRight":
            if(spriteNum == 1) 
                image = up1;
            if (spriteNum == 2)
                image = up2;
            break;
        case "upLeft":
            if(spriteNum == 1) 
                image = up1;
            if (spriteNum == 2)
                image = up2;
            break;
        }

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
