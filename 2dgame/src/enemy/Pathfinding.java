package enemy;

import java.util.List;
import java.util.Random;

import entity.Enemy;
import main.GamePanel;
import navmesh.NavMesh;
import navmesh.Node;

public class Pathfinding {
    
    Enemy enemy;
    GamePanel gp;
    Node nearestNode;
    NavMesh navMesh;

    String[] cardinalDirections = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
    Random random = new Random();
    String preferredDirection;

    Node next;

    // TODO: reimplement navmesh
    public Pathfinding(GamePanel gp, NavMesh navMesh, Enemy enemy) {
        this.gp = gp;
        this.navMesh = navMesh;
        this.enemy = enemy;
    }

    public void chase(Enemy enemy) {
        // Find nearest node
        nearestNode(navMesh.nodes, enemy);
        Node curr = nearestNode;
    }
    //public void chase(Enemy enemy)
    public void wander(Enemy enemy) {

        // choosing random direction
        int randomDirection = random.nextInt(cardinalDirections.length);
        preferredDirection = cardinalDirections[randomDirection];

        switch(preferredDirection) {
            case "NW":
                enemy.y += enemy.speed;
                enemy.x += enemy.speed;
            case "N":
                enemy.y += enemy.speed;
            case "NE":
                enemy.y += enemy.speed;
                enemy.x -= enemy.speed;
            case "E":
                enemy.x -= enemy.speed;
            case "SE":
                enemy.y -= enemy.speed;
                enemy.x -= enemy.speed;
            case "S":
                enemy.y -= enemy.speed;
            case "SW":
                enemy.y -= enemy.speed;
                enemy.x += enemy.speed;
            case "W":
                enemy.x += enemy.speed; 

        }
    }

    /*public void findNearestNode() {
        // divide x y
        // round
        int x = Math.round(enemy.x / gp.tileSize);
        int y = Math.round(enemy.y / gp.tileSize);
        // find nearest node node
        for(Node node : navMesh.nodes) {
            if(node.nodeX == x && node.nodeY == y) {
                nearestNode = node;
                break;
            }
        }
    }

    public void moveToNearestNode() {
        findNearestNode();
        if(enemy.x < nearestNode.nodeX) {
            while(enemy.x + enemy.speed < nearestNode.nodeX) {
                enemy.x += enemy.speed;
            }
        } else if(enemy.x > nearestNode.nodeX) {
            while(enemy.x + enemy.speed < nearestNode.nodeX) {
                enemy.x -= enemy.speed;
            }
        }

        if(enemy.y < nearestNode.nodeY) {
            while(enemy.y + enemy.speed < nearestNode.nodeY) {
                enemy.y += enemy.speed;
            }
        } else if(enemy.y > nearestNode.nodeY) {
            while(enemy.y + enemy.speed < nearestNode.nodeY) {
                enemy.y -= enemy.speed;
            }
        }

    }*/

    // Finds closest node to enemy
    public void nearestNode(List<Node> nodes, Enemy enemy) {
        for(Node n : nodes) {
            if((n.nodeX <= enemy.x+gp.tileSize && n.nodeX >= enemy.x) 
            && (n.nodeY <= enemy.y+gp.tileSize && n.nodeY >= enemy.y)) {
                nearestNode = n;
                break;
            }
        }
    }

    // Finds closest node to player
    public void findPlayerNode(List<Node> nodes, Enemy enemy) {
        for(Node n : nodes) {
            if((n.nodeX <= enemy.player.x+gp.tileSize && n.nodeX >= enemy.player.x) 
            && (n.nodeY <= enemy.player.y+gp.tileSize && n.nodeY >= enemy.player.y)) {
                nearestNode = n;
                break;
            }
        }
    }

}
