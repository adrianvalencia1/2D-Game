package entity;

import main.GamePanel;

public class Camera extends Entity {
    
    Player player;
    GamePanel gp;

    public Camera(GamePanel gp, Player player) {

        this.gp = gp;
        this.player = player;

        this.x = player.x;
        this.y = player.y;

        this.width = gp.screenWidth;
        this.height = gp.screenHeight;
    }
    public void setCameraX(int playerX) {this.x = player.x;}
    public void setCameraY(int playerY) {this.y = player.y;}

    public int getCameraX(){return x;}
    public int getCameraY(){return y;}

    public int getWidth(){return width;}
    public int getHeight(){return height;}

    public void update() {
        setCameraX(x);
        setCameraY(y);
    }

}
