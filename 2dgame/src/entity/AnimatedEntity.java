package entity;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class AnimatedEntity extends Entity{

    public BufferedImage idle, up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public String lastDirection;
    
    public String Ydirection;
    public String Xdirection;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int speed;

    public int health;
    public int maxHealth;

    public boolean isColliding;
    public Rectangle hitbox;
    public Rectangle sight;

    
}
