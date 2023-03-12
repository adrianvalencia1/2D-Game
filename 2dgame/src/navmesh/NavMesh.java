package navmesh;

import main.GamePanel;
import physics.Vertex;
import prop.PropManager;
import tile.TileManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import entity.Prop;

public class NavMesh {
    
    GamePanel gp;
    TileManager tileM;
    PropManager propM;

    public List<Node> nodes;
    List<Edge> edges;
    Node[][] adjList;

    HashMap<Node, Edge> map;

    public NavMesh(GamePanel gp, TileManager tileM, PropManager propM) {
        this.gp = gp;
        this.tileM = tileM;
        this.propM = propM;

        initNavMesh();

    }

    public void createMap() {
        
        List<Node> nodes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        // double for loop, changing TileManager to use some iterable
        // object would be more efficient, TODO
        boolean deleteNode = false;
        Node prev = new Node(0, 0, 0, new Vertex(0,0));
        for (int i = 0; i < gp.worldSizeWidth; i += gp.tileSize) {
            for (int j = 0; j < gp.worldSizeHeight; j += gp.tileSize) {
                
                Node n = new Node(
                    i, j, 0,
                    new Vertex(
                        i + gp.tileSize/2, // to center the node's position on the tile
                        j + gp.tileSize/2
                    )
                );
                nodes.add(n);
                // TODO: insanely inefficient, also not dynamic
                for(Prop p : propM.props) {
                    if(p.x==n.nodeX && p.y==n.nodeY) {
                        deleteNode = true;
                        break;
                    }
                }

                Edge e = new Edge(n, prev, 1);
                edges.add(e);

                if(deleteNode == false) {
                    nodes.add(n);
                    adjList[i][j] = n;
                }

                deleteNode = false;
                prev = n;
            }
        }
        /*map = new HashMap<Node, Edge>();
        for(Node n : nodes) {
            map.keySet();
        }*/
    }
    public void initNavMesh() {
        // every tile as a node,
        // overlay props
        // if prop is on top of node,
        // remove node

        List<Node> nodes = new ArrayList<>();
        
        // Adjacency List for NavMesh Nodes
        adjList = new Node[gp.worldSizeWidth][gp.worldSizeHeight];

        boolean deleteNode = false;

        for (int i = 0; i < gp.worldSizeWidth; i += gp.tileSize) {
            for (int j = 0; j < gp.worldSizeHeight; j += gp.tileSize) {
                
                Node n = new Node(
                    i, j, 0,
                    new Vertex(
                        i + gp.tileSize/2, // to center the node's position on the tile
                        j + gp.tileSize/2
                    )
                );
                
                for(Prop p : propM.props) {
                    if(p.x==n.nodeX && p.y==n.nodeY) {
                        deleteNode = true;
                        break;
                    }
                }

                if(deleteNode == false) {
                    nodes.add(n);
                    adjList[i][j] = n;
                }

                deleteNode = false;

            }
        }
    }
}
