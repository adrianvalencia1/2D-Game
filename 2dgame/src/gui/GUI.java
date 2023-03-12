package gui;

import main.GamePanel;

import java.awt.image.BufferedImage;

import entity.Entity;

import java.awt.Graphics2D;
import java.awt.Color;

public class GUI extends Entity {
    
    GamePanel gp;

    public BufferedImage texture;
    int x;
    int y;
    String text;
    public int type; // 0 = image, 1 = 3DRect, 2 = 3DFill
    public GUIEvent guiEvent;
    public boolean eventCalled = false;
    public Color color;

    public GUI(GamePanel gp) {
        this.gp = gp;


    }

    private void setImage() {
        
    }

    public void setGUIEvent(GUIEvent guiEvent) { this.guiEvent = guiEvent; }
    public void setType(int type) {this.type = type;}

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void setDimension(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public void draw(Graphics2D g2) {
        
        if(type == 0) {
            //g2.draw(null);
        } else if(type == 1) {
            g2.draw3DRect(x, y, width, height, true);
        } else if(type == 2) {
            g2.setColor(color);
            g2.fill3DRect(x, y, width, height, true);
            
        }
        
    }



}
