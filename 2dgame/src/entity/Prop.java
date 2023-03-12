// Entity super class
package entity;

import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Prop extends StaticProp {

    protected String texture = ""; //default invisible
    String textureFilePath = "/props/";

    public Prop() {

    }

    public Prop(int x, int y, String texture, int width, int height, boolean collision) { // texture should be texture file path
        // coordinates
        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        this.texture = texture;
        this.collision = collision;
        isVisible = true;
        if(this.texture.equals("")) { // set invisible is texture is blank
            setIsVisible(false);
        } 

        setPropImage();
        setImageDimensions();
    }
    public Prop(int x, int y, String texture) { // texture should be texture file path
        // coordinates
        this.x = x;
        this.y = y;
        
        this.texture = texture;
        isVisible = true;
        if(this.texture.equals("")) { // set invisible is texture is blank
            setIsVisible(false);
        } 

        setPropImage();
        setImageDimensions();
    }

    // Setters
    public void setIsVisible(boolean isVisible) {this.isVisible = isVisible;}
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    public void setIdle(String texture) {this.texture = texture;}
    protected void setImageDimensions() {
        if (texture.equals("")) {
            width = 100;
            height = 100;
        } else {
            // TODO: create new subclass for invisible hitboxes (bounds)
            width = idle.getWidth(null);
            height = idle.getHeight(null);
        }
    }
    protected void setImageDimensions(int width, int height) { // for custom hitboxes
        this.width = width;
        this.height = height;
    }
    public void setPropImage() {
        String path = textureFilePath + texture;
        try { idle = ImageIO.read(getClass().getResourceAsStream(path));} 
        catch(IOException e) {e.printStackTrace();}
    }
    public void setHitbox(int x, int y, int width, int height) {
        hitbox = new Rectangle(x, y, width, height);
    }

    // Getters
    public int getX() {return x;}
    public int getY() {return y;}
    public boolean getIsVisible() {return isVisible;}
    public Rectangle getHitbox() {
        if(collision == false) { // returns "empty" hitbox is collision is false
            return new Rectangle(-1,-1,0,0);
        } else {
            return hitbox;
        }
    }
    public String getTexture() {return texture;}
    public BufferedImage getImage() {return idle;}

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        image = idle;
        g2.drawImage(image, x, y, width, height, null);
    }
}
