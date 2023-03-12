package navmesh;

import physics.Vertex;

public class Node {
    
    public int nodeX, nodeY;
    int weight;
    Vertex position;
    
    Node(int nodeX, int nodeY, int weight, Vertex position) {
        this.nodeX = nodeX;
        this.nodeY = nodeY;
        this.weight = weight;
        this.position = position;
    }
    
}
