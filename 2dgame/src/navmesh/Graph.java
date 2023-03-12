package navmesh;

import java.util.List;

import main.GamePanel;

public class Graph {
    
    public Node[][] adjList;
    GamePanel gp;

    public Graph(GamePanel gp, List<Node> nodes) {
        this.gp = gp;

        adjList = new Node[gp.worldSizeWidth/gp.tileSize][gp.worldSizeHeight/gp.tileSize];

        for (int i = 0; i < gp.worldSizeWidth/gp.tileSize; i++) {
            for (int j = 0; j < gp.worldSizeHeight/gp.tileSize; j++) {
                
                for(Node n: nodes) {
                    if (n.nodeX/gp.tileSize==i && n.nodeY/gp.tileSize==j) {
                        adjList[i][j] = n;
                    }
                }

            }
        }
    }
}
