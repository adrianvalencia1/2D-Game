package prop;

import main.GamePanel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.awt.Graphics2D;

import entity.Prop;
import entity.Camera;
import entity.Player;

public class PropManager {
    
    GamePanel gp;
    Camera camera;
    Player player; 
    public ArrayList<Prop> props;
    String levelID;

    public PropManager(GamePanel gp, Camera camera, Player player) {
        this.gp = gp;
        this.camera = camera;
        this.player = player;
        
        props = new ArrayList<Prop>();

        setLevelProps("map1");
        loadProps();
    }

    public void setLevelProps(String levelID) { // TODO: integrate levelID
        this.levelID = "/maps/" + levelID + "/props.txt";
        System.out.println(getClass());
    }

    public void loadProps() {
        try {
            InputStream is = getClass().getResourceAsStream(levelID);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String propArgs;
            propArgs = br.readLine();

            while(propArgs != null) {

                String args[] = propArgs.split(" "); // regex split
                int x = Integer.parseInt(args[0]);
                int y = Integer.parseInt(args[1]);
                String texture = args[2];
                int width = Integer.parseInt(args[3]);
                int height = Integer.parseInt(args[4]);
                boolean collision = Boolean.parseBoolean(args[5]);
                // System.out.print(x + " " + y + " " + texture + " " + width + " " + height);

                Prop temp = new Prop(x, y, texture, width, height, collision);

                props.add(temp);

                propArgs = br.readLine();
            }
            br.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // drawing props
    public void draw(Graphics2D g2) {

        for(int i = 0; i < props.size(); i++) {

            props.get(i).setHitbox(
            props.get(i).getX() - camera.getCameraX(), 
            props.get(i).getY()  - camera.getCameraY(),
            props.get(i).width,
            props.get(i).height);

            g2.drawImage(
            props.get(i).getImage(), 
            props.get(i).getX() - camera.getCameraX(), 
            props.get(i).getY() - camera.getCameraY(), 
            props.get(i).getImage().getWidth(),
            props.get(i).getImage().getHeight(),
            null);
        }
    }

    // generate hitboxes
    public void generateHitboxes() {

    }

    /* PSEUDO CODE
    1. load props from text file (XML or JSON are possible too)
        save x and y coordinates, and prop texture file
        PROPMAP FORMAT: X Y TEXTURE WIDTH HEIGHT    (width and height optional)
        READER: line split " " 
    2. draw props onto screen
     */ 

    // load
    
    // draw
}
