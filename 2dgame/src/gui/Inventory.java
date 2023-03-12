package gui;

import java.awt.Graphics2D;

import item.Item;
import main.GamePanel;

import java.awt.Color;

public class Inventory extends GUIEvent{
    
    GUI gui;
    GamePanel gp;

    public Color invColor = new Color(0f, 0f, 0f, 0.5f);
    public Color itemColor = new Color(1f, 1f, 1f, 1f);
    public Item[][] inventory = new Item[5][5];

    private int invWidth = 0;
    private int invHeight = 0;
    private int invX = 0;
    private int invY = 0;
    private final int itemSpacing;
    private final int itemSlotSize;

    public Inventory(GUI gui, GamePanel gp) {
        this.gui = gui;
        this.gp = gp;

        itemSlotSize = 40;
        
        itemSpacing = 5;

        initInventory();
    }

    private void initInventory() {

        for(int i = 0; i < inventory.length; i++) {

            invWidth += itemSlotSize;
            invHeight += itemSlotSize;

            for(int j = 0; j < inventory[0].length; j++) {

                Item item = new Item(0, false);
                inventory[i][j] = item;

            }
        }

        invX = gp.screenWidth - invWidth;
        invY = gp.screenHeight - invHeight;
        
    }

    public void draw(Graphics2D g2) {

        g2.setColor(invColor);
        g2.fill3DRect(invX, invY, invWidth, invHeight, true);

        g2.setColor(itemColor);
        for(int i = 0; i < inventory.length; i++) {
            for(int j = 0; j < inventory[0].length; j++) {
                g2.fill3DRect(invX + (i * itemSlotSize), invY + (j * itemSlotSize), itemSlotSize, itemSlotSize, true);
            }
        }
    }
}
