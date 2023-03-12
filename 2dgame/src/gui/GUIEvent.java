package gui;

import java.awt.Graphics2D;
import java.awt.Color;
public class GUIEvent {

    public Color color = new Color(0f, 0f, 0f, 0.5f);
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fill3DRect(500, 500, 200, 200, true);
    }
    
}
