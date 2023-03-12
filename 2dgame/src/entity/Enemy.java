package entity;

import main.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import enemy.Pathfinding;

import java.awt.Rectangle;

public class Enemy extends AnimatedEntity {
    
    GamePanel gp;
    public Player player;

    public int aggroRange;
    String sightDirection;
    String sightLastDirection;
    String moveDirection;

    String type; 
    boolean collision;
    public boolean aggroed;
    public BufferedImage image;
    
    // Pathfinding and AI
    Pathfinding pathfinding;

    public Enemy(GamePanel gp) {
        this.gp = gp;
    }

    public Enemy(GamePanel gp, int x, int y, int width, int height, int health, int speed, int aggroRange, boolean collision, String type) {
        this.gp = gp;
        this.pathfinding = new Pathfinding(gp, gp.navMesh, this);
        this.x = x; this.y = y; this.width = width; this.height = height;
        this.health = health; this.speed = speed; this.aggroRange = aggroRange;
        this.collision = collision; this.type = type;

        setHitbox(x, y, width, height);
        direction = "N";

        setImage();
    }

    public void setHitbox(int x, int y, int width, int height) {
        hitbox = new Rectangle(x, y, width, height);
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }


    public int getX() { return x;}
    public int getY() { return y;}
    public int getAggroRange() {return aggroRange;}
    public void setDirection(String direction) {this.direction = direction;}
    public void setImage() {
        String idleDir = "/EnemyRes/" + type + "/idle.png";
        try {
            idle = ImageIO.read(getClass().getResourceAsStream(idleDir));
            up1 = ImageIO.read(getClass().getResourceAsStream(idleDir));
            up2 = ImageIO.read(getClass().getResourceAsStream(idleDir));
            down1 = ImageIO.read(getClass().getResourceAsStream(idleDir));
            down2 = ImageIO.read(getClass().getResourceAsStream(idleDir));
            left1 = ImageIO.read(getClass().getResourceAsStream(idleDir));
            left2 = ImageIO.read(getClass().getResourceAsStream(idleDir));
            right1 = ImageIO.read(getClass().getResourceAsStream(idleDir));
            right2 = ImageIO.read(getClass().getResourceAsStream(idleDir));
        } catch(IOException e) {e.printStackTrace();}
    }

    public BufferedImage getImage() {
        /*switch(direction) {
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
        }*/
        image = idle;
        return image;
    }

    public void movementAI(Player entity) {
        
        if (y+height/2 <= entity.y+entity.height/2) {
            Ydirection = "up";
            y += speed;
        } else if (y+height/2 >= entity.y+entity.height/2) {
            Ydirection = "down";
            y -= speed;
        } else {
            Ydirection = "idle";
        }
        if (x+width/2 <= entity.x+entity.width/2) {
            Xdirection = "left";
            x += speed;
        } else if (x+width/2 >= entity.x+entity.width/2) {
            Xdirection = "right";
            x -= speed;
        } else {
            Xdirection = "idle";
        }
    }


    public boolean aggroUpdate(Player entity) {
        
        if(entity.y+aggroRange <= y) {
            aggroed = true;
            
        } else if(entity.x-aggroRange <= x) {
            aggroed = true;
            
        } else {
            aggroed = false;
        }
        
        return aggroed;

    }

    public void directionUpdate() {
        if(Ydirection=="up") {
            if(Xdirection=="right") {
                direction = "NE";
            } else if(Xdirection=="left") {
                direction = "NW";
            } else {
                direction = "N";
            }
        } else if (Ydirection=="down") {
            if(Xdirection=="right") {
                direction = "SE";
            } else if(Xdirection=="left") {
                direction = "SW";
            } else {
                direction = "S";
            }
        } else if(Xdirection=="right") {
            direction = "E";
        } else if (Xdirection=="left") {
            direction = "W";
        }
    }
    public void update(Player player) {
        // TODO: check collisions
        
        gp.checkCollisionsEnemy(this);

        /*if(aggroUpdate(player)) {
           movementAI(player);
        }*/
        //if (isColliding == false) {
            //movementAI(player);

        //}
        //pathfinding.wander(this);
        //pathfinding.moveToNearestNode();
        gp.checkCollisionsEnemy(this);

        /*if(Ydirection == "up") {
            y += speed;
        } else if (Ydirection == "down") {
            y -= speed;
        }

        if (Xdirection == "left") {
            x += speed;
        } else if (Xdirection == "right") {
            x -= speed;
        }*/

        //directionUpdate();
        
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

        image = idle;
        /*switch(direction) {
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
        }*/
        
        g2.drawImage(image, x, y, width, height, null);
    }

    public Rectangle getHitbox() {
        if(collision == false) { // returns "empty" hitbox is collision is false
            return new Rectangle(-1,-1,0,0);
        } else {
            return hitbox;
        }
    }
}
