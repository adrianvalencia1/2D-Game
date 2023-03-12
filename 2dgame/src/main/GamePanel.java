package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import enemy.EnemyManager;
import entity.Camera;
import entity.Enemy;
import entity.Player;
import entity.Prop;
import gui.GUIManager;
import navmesh.NavMesh;
import prop.PropManager;
import tile.TileManager;
import java.awt.Rectangle;

public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    final int originalTileSize = 48; // 48x48 tile, default size of tile
    public final int scale = 1; // scaling variable

    public final int tileSize = originalTileSize * scale;
    
    // 4x3 ratio
    public final int maxScreenCol = 24;
    public final int maxScreenRow = 18;
    public final int screenWidth = tileSize * maxScreenCol; // 1152 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 864 pixels
    
    public final int worldSizeWidth = screenWidth * 4; // Maximum world width
    public final int worldSizeHeight = screenHeight * 4; // Maximum world height
    
    public final int screenWidthMax = worldSizeWidth - screenWidth;
    public final int screenHeightMax = worldSizeHeight - screenHeight;
    public final int screenWidthMin = 0;
    public final int screenHeightMin = 0;

    // FPS
    int FPS = 30;

    public KeyHandler keyH = new KeyHandler();
    MouseHandler mouseH = new MouseHandler();
    Thread gameThread;
    Player player = new Player(this, keyH);
    public Camera camera = new Camera(this, player);

    TileManager tileM = new TileManager(this, camera, player);
    PropManager propM = new PropManager(this, camera, player);
    GUIManager guiM = new GUIManager(this, camera, player, mouseH);
    public NavMesh navMesh = new NavMesh(this, tileM, propM);
    EnemyManager enemyM = new EnemyManager(this, camera, player, navMesh);
    
    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
        this.setFocusable(true);
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        // game time intervals
        double drawInterval = 1000000000 / FPS; //0.033... seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        // game loop
        while(gameThread != null) {
             
            // game time
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            //initProps(); // initialize props

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                // FPS counter
                drawCount++;
            }

            System.out.print("\n");
            // FPS counter console
            if (timer >= 1000000000) {
                System.out.printf("FPS: %d\r", drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    // update player animations
    public void update() {
        player.update();
        camera.update();
        guiM.update();
        for (Enemy enemy : enemyM.enemies) {
            enemy.update(player);
        }
    }

    // TODO: move collisions to separate class
    // Collision check for player with props
    public void checkCollisions() {
        Rectangle r2 = player.getHitbox();
        for (Prop prop : propM.props) {
            prop.setHitbox(
            prop.getX() - camera.getCameraX(), 
            prop.getY()  - camera.getCameraY(),
            prop.width,
            prop.height);
            Rectangle r3 = prop.getHitbox();
            if (r2.intersects(r3)) {    
                if(player.Xdirection == "left" && player.Ydirection == "idle") {
                    player.setX(player.x + getCollisionX(r2, r3));
                    player.Xdirection = "idle";
                } else if(player.Xdirection == "right" && player.Ydirection == "idle") {
                    player.setX(player.x - getCollisionX(r2, r3));
                    player.Xdirection = "idle";
                } else if(player.Xdirection == "idle" && player.Ydirection == "up") {
                    player.setY(player.y + getCollisionY(r2, r3));
                    player.Ydirection = "idle";
                } else if(player.Xdirection == "idle" && player.Ydirection == "down") {
                    player.setY(player.y - getCollisionY(r2, r3));
                    player.Ydirection = "idle";
                } else if(player.Xdirection == "left" && player.Ydirection == "up") {
                    if (getCollisionX(r2, r3) < getCollisionY(r2, r3)) {
                        player.setX(player.x + getCollisionX(r2, r3));
                        player.Xdirection = "idle"; 
                    } else if (getCollisionX(r2, r3) > getCollisionY(r2, r3)) {
                        player.setY(player.y + getCollisionY(r2, r3));
                        player.Ydirection = "idle";
                    } else if (getCollisionX(r2, r3) == getCollisionY(r2, r3)) {
                        player.setY(player.y + getCollisionY(r2, r3));
                        player.Ydirection = "idle";
                        player.setX(player.x + getCollisionX(r2, r3));
                        player.Xdirection = "idle"; 
                    }
                } else if(player.Xdirection == "left" && player.Ydirection == "down") {
                    if (getCollisionX(r2, r3) < getCollisionY(r2, r3)) {
                        player.setX(player.x + getCollisionX(r2, r3));
                        player.Xdirection = "idle"; 
                    } else if (getCollisionX(r2, r3) > getCollisionY(r2, r3)) {
                        player.setY(player.y - getCollisionY(r2, r3));
                        player.Ydirection = "idle";
                    } else if (getCollisionX(r2, r3) == getCollisionY(r2, r3)) {
                        player.setY(player.y + getCollisionY(r2, r3));
                        player.Ydirection = "idle";
                        player.setX(player.x - getCollisionX(r2, r3));
                        player.Xdirection = "idle"; 
                    }
                } else if(player.Xdirection == "right" && player.Ydirection == "up") {
                    if (getCollisionX(r2, r3) < getCollisionY(r2, r3)) {
                        player.setX(player.x - getCollisionX(r2, r3));
                        player.Xdirection = "idle"; 
                    } else if (getCollisionX(r2, r3) > getCollisionY(r2, r3)) {
                        player.setY(player.y + getCollisionY(r2, r3));
                        player.Ydirection = "idle";
                    } else if (getCollisionX(r2, r3) == getCollisionY(r2, r3)) {
                        player.setY(player.y - getCollisionY(r2, r3));
                        player.Ydirection = "idle";
                        player.setX(player.x + getCollisionX(r2, r3));
                        player.Xdirection = "idle"; 
                    }
                } else if(player.Xdirection == "right" && player.Ydirection == "down") {
                    if (getCollisionX(r2, r3) < getCollisionY(r2, r3)) {
                        player.setX(player.x - getCollisionX(r2, r3));
                        player.Xdirection = "idle"; 
                    } else if (getCollisionX(r2, r3) > getCollisionY(r2, r3)) {
                        player.setY(player.y - getCollisionY(r2, r3));
                        player.Ydirection = "idle";
                    } else if (getCollisionX(r2, r3) == getCollisionY(r2, r3)) {
                        player.setY(player.y - getCollisionY(r2, r3));
                        player.Ydirection = "idle";
                        player.setX(player.x - getCollisionX(r2, r3));
                        player.Xdirection = "idle"; 
                    }
                }
            }
        }
    }

    public void checkCollisionsEnemy(Enemy enemy) {
        Rectangle r2 = enemy.hitbox;
        for (Prop prop : propM.props) {
            prop.setHitbox(
            prop.getX() - camera.getCameraX(), 
            prop.getY()  - camera.getCameraY(),
            prop.width,
            prop.height);
            Rectangle r3 = prop.getHitbox();
            if (r2.intersects(r3)) {    
                enemy.isColliding = true;
                if(enemy.Xdirection == "left" && enemy.Ydirection == "idle") {
                    enemy.setX(enemy.x + getCollisionX(r2, r3));
                    enemy.Xdirection = "idle";
                } else if(enemy.Xdirection == "right" && enemy.Ydirection == "idle") {
                    enemy.setX(enemy.x - getCollisionX(r2, r3));
                    enemy.Xdirection = "idle";
                } else if(enemy.Xdirection == "idle" && enemy.Ydirection == "up") {
                    enemy.setY(enemy.y + getCollisionY(r2, r3));
                    enemy.Ydirection = "idle";
                } else if(enemy.Xdirection == "idle" && enemy.Ydirection == "down") {
                    enemy.setY(enemy.y - getCollisionY(r2, r3));
                    enemy.Ydirection = "idle";
                } else if(enemy.Xdirection == "left" && enemy.Ydirection == "up") {
                    if (getCollisionX(r2, r3) < getCollisionY(r2, r3)) {
                        enemy.setX(enemy.x + getCollisionX(r2, r3));
                        enemy.Xdirection = "idle"; 
                    } else if (getCollisionX(r2, r3) > getCollisionY(r2, r3)) {
                        enemy.setY(enemy.y + getCollisionY(r2, r3));
                        enemy.Ydirection = "idle";
                    } else if (getCollisionX(r2, r3) == getCollisionY(r2, r3)) {
                        enemy.setY(enemy.y + getCollisionY(r2, r3));
                        enemy.Ydirection = "idle";
                        enemy.setX(enemy.x + getCollisionX(r2, r3));
                        enemy.Xdirection = "idle"; 
                    }
                } else if(enemy.Xdirection == "left" && enemy.Ydirection == "down") {
                    if (getCollisionX(r2, r3) < getCollisionY(r2, r3)) {
                        enemy.setX(enemy.x + getCollisionX(r2, r3));
                        enemy.Xdirection = "idle"; 
                    } else if (getCollisionX(r2, r3) > getCollisionY(r2, r3)) {
                        enemy.setY(enemy.y - getCollisionY(r2, r3));
                        enemy.Ydirection = "idle";
                    } else if (getCollisionX(r2, r3) == getCollisionY(r2, r3)) {
                        enemy.setY(enemy.y + getCollisionY(r2, r3));
                        enemy.Ydirection = "idle";
                        enemy.setX(enemy.x - getCollisionX(r2, r3));
                        enemy.Xdirection = "idle"; 
                    }
                } else if(enemy.Xdirection == "right" && enemy.Ydirection == "up") {
                    if (getCollisionX(r2, r3) < getCollisionY(r2, r3)) {
                        enemy.setX(enemy.x - getCollisionX(r2, r3));
                        enemy.Xdirection = "idle"; 
                    } else if (getCollisionX(r2, r3) > getCollisionY(r2, r3)) {
                        enemy.setY(enemy.y + getCollisionY(r2, r3));
                        enemy.Ydirection = "idle";
                    } else if (getCollisionX(r2, r3) == getCollisionY(r2, r3)) {
                        enemy.setY(enemy.y - getCollisionY(r2, r3));
                        enemy.Ydirection = "idle";
                        enemy.setX(enemy.x + getCollisionX(r2, r3));
                        enemy.Xdirection = "idle"; 
                    }
                } else if(enemy.Xdirection == "right" && enemy.Ydirection == "down") {
                    if (getCollisionX(r2, r3) < getCollisionY(r2, r3)) {
                        enemy.setX(enemy.x - getCollisionX(r2, r3));
                        enemy.Xdirection = "idle"; 
                    } else if (getCollisionX(r2, r3) > getCollisionY(r2, r3)) {
                        enemy.setY(enemy.y - getCollisionY(r2, r3));
                        enemy.Ydirection = "idle";
                    } else if (getCollisionX(r2, r3) == getCollisionY(r2, r3)) {
                        enemy.setY(enemy.y - getCollisionY(r2, r3));
                        enemy.Ydirection = "idle";
                        enemy.setX(enemy.x - getCollisionX(r2, r3));
                        enemy.Xdirection = "idle"; 
                    }
                }
            }
            enemy.isColliding = true;
        }
    }

    public int getCollisionX(Rectangle r2, Rectangle r3){
        Double x = r3.intersection(r2).getBounds().getWidth();
        return x.intValue();
    }
    public int getCollisionY(Rectangle r2, Rectangle r3){
        Double y = r3.intersection(r2).getBounds().getHeight();
        return y.intValue();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // tiles are drawn first to be in background
        tileM.draw(g2);
        player.draw(g2);
        propM.draw(g2);
        enemyM.draw(g2);
        guiM.draw(g2);
        
        // TODO: implement frame counter
        // guiM.frameCounter(g2, drawCount);

        g2.dispose();
    }
}
