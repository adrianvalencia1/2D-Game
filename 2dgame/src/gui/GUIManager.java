package gui;

import main.GamePanel;
import main.MouseHandler;

import java.awt.Graphics2D;

import java.awt.Color;
import entity.Camera;
import entity.Player;

public class GUIManager {
    
    // TODO: implement GUI elements: healthbar,
    GamePanel gp;
    Camera camera;
    Player player;
    MouseHandler mouseH;

    GUI[] gui;
    
    public GUIManager(GamePanel gp, Camera camera, Player player, MouseHandler mouseH) {
        this.gp = gp;
        this.camera = camera;
        this.player = player;
        this.mouseH = mouseH;

        gui = new GUI[3]; // num of GUI elements, arbitrary limit

        loadGUI();
    }

    //TODO: config file for scalars
    public void loadGUI() {
        try {
            gui[0] = new GUI(gp); gui[0].desc = "healthbarmax"; gui[0].type = 1;
            gui[0].x = 10; gui[0].y = gp.screenHeight - 30;
            gui[0].width = player.maxHealth; gui[0].height = 20;

            gui[1] = new GUI(gp); gui[1].desc = "health"; gui[1].type = 2;
            gui[1].x = gui[0].x; gui[1].y = gui[0].y;
            gui[1].width = player.health; gui[1].height = gui[0].height;
            gui[1].color = Color.red;

            // Inventory button
            gui[2] = new GUI(gp); gui[2].desc = "inventory"; gui[2].type = 2;
            gui[2].x = gp.screenWidth-300; gui[2].y = gp.screenHeight-30;
            gui[2].width = 300; gui[2].height = 30;
            gui[2].color = Color.blue;
            gui[2].text = "inventory";
            gui[2].guiEvent = new Inventory(gui[2], gp);
            gui[2].eventCalled = false;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void frameCounter(Graphics2D g2, int drawCount) {
        String fps = "FPS: " + drawCount;
        g2.drawString(fps, 10, 20);
    }
    public void update() {

        if(gp.keyH.escPressed) {
            gui[2].eventCalled = false;
        } else if(gui[2].eventCalled == false) {
            if(mouseH.x >= gui[2].x &&
            mouseH.x <= gui[2].x+gui[2].width &&
            mouseH.y >= gui[2].y &&
            mouseH.y <= gui[2].y+gui[2].height) {
                
                gui[2].eventCalled = true;
                mouseH.x = 0;
                mouseH.y = 0;
            }
        } 
    }
    public void draw(Graphics2D g2) {
        for(int i = 0; i < gui.length; i++) {
            gui[i].draw(g2);

            if(gui[i].eventCalled == true) {  
                gui[i].guiEvent.draw(g2);
            }

        }
    }
}
