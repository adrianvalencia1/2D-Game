package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import entity.Camera;
import entity.Player;

import java.awt.Graphics2D;

public class TileManager {
    
    GamePanel gp;
    Camera camera;
    Player player;

    Tile[] tile;
    int mapTileNum[][];

    public TileManager(GamePanel gp, Camera camera, Player player) {

        this.gp = gp;
        this.camera = camera;
        this.player = player;

        tile = new Tile[10]; // num of tiles
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];

        getTileImage();
        loadMap();
    }

    public void getTileImage () {
        try {
            // dirt
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt.png"));
            // stone
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stone.png"));
        
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap() {

        try {

            InputStream is = getClass().getResourceAsStream("/maps/map1/map.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            
            while(col < gp.maxScreenCol && row < gp.maxScreenRow) {

                String line = br.readLine();

                while(col < gp.maxScreenCol) {
                    
                    String numbers[] = line.split(" ");
                    // System.out.println(numbers);
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxScreenCol) {
                    col = 0;
                    row++;
                }
               
            }
            br.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
    
    public void draw(Graphics2D g2) {

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow) {

            int tileNum = mapTileNum[col][row];

            //System.out.print(tileNum);
            g2.drawImage(
                tile[tileNum].image, 
                x - camera.getCameraX(), 
                y - camera.getCameraY(), 
                gp.tileSize, 
                gp.tileSize, 
                null
            );

            col++;
            x += gp.tileSize;

            if(col == gp.maxScreenCol) {
                col = 0;
                 x = 0;
                 row++;
                 y += gp.tileSize;
             }
        }

    }
}
