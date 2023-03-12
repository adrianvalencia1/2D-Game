package enemy;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import entity.Camera;
import entity.Enemy;
import entity.Player;
import main.GamePanel;
import navmesh.NavMesh;

import java.awt.Graphics2D;

public class EnemyManager {
    
    GamePanel gp;
    Camera camera;
    Player player;
    NavMesh navMesh;
    
    public ArrayList<Enemy> enemies;
    String levelID;

    public EnemyManager(GamePanel gp, Camera camera, Player player, NavMesh navMesh) {
        this.gp = gp;
        this.camera = camera;
        this.player = player;
        this.navMesh = navMesh;
        enemies = new ArrayList<Enemy>();

        setLevelProps("map1");
        loadProps();
    }

    public void setLevelProps(String levelID) { // TODO: integrate levelID
        this.levelID = "/maps/" + levelID + "/enemies.txt";
        System.out.println(getClass());
    }

    public void loadProps() {
        try {
            InputStream is = getClass().getResourceAsStream(levelID);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String enemyArgs;
            enemyArgs = br.readLine();

            while(enemyArgs != null) {

                String args[] = enemyArgs.split(" "); // regex split
                int x = Integer.parseInt(args[0]);
                int y = Integer.parseInt(args[1]);
                int width = Integer.parseInt(args[2]);
                int height = Integer.parseInt(args[3]);
                int health = Integer.parseInt(args[4]);
                int speed = Integer.parseInt(args[5]);
                int aggroRange = Integer.parseInt(args[6]);
                boolean collision = Boolean.parseBoolean(args[7]);
                String type = args[8];

                System.out.print(x + " " + y + " " + width + " " + height + " " + aggroRange);

                Enemy temp = new Enemy(this.gp, x, y, width, height, health, speed, aggroRange, collision, type);

                enemies.add(temp);

                enemyArgs = br.readLine();
            }
            br.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        for(int i = 0; i < enemies.size(); i++) {
            
           /*if(enemies.get(i).aggroed) {
            
                //System.out.println("aggroed");

                enemies.get(i).setHitbox(
                enemies.get(i).x,
                enemies.get(i).y,
                enemies.get(i).width,
                enemies.get(i).height);

                g2.drawImage(
                enemies.get(i).getImage(),
                enemies.get(i).x,
                enemies.get(i).y,
                enemies.get(i).width,
                enemies.get(i).height,
                null);

           *///} else {
        
                enemies.get(i).setHitbox(
                enemies.get(i).x - camera.getCameraX(),
                enemies.get(i).y - camera.getCameraY(),
                enemies.get(i).width,
                enemies.get(i).height);
        
                g2.drawImage(
                enemies.get(i).getImage(),
                enemies.get(i).x - camera.getCameraX(),
                enemies.get(i).y - camera.getCameraY(),
                enemies.get(i).width,
                enemies.get(i).height,
                null);  
            //}

        }
    }
}
