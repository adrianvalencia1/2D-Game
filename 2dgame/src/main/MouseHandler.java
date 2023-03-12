package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

public class MouseHandler implements MouseListener {
    
    public boolean click;
    public MouseEvent c;

    public int x, y;

    @Override
    public void mouseClicked(MouseEvent e) {
        /*if(oneClick) {
            oneClick = false;
            click = true;   
            c = e;
        } else {
            oneClick = true;
            Timer t = new Timer("clicktimer", false);
            t.schedule(new TimerTask() {
                
                @Override
                public void run() {
                    oneClick = false;
                    
                    c=null;
                }
            }, 100);
            
        }*/
        
        x = e.getX();
        y = e.getY();

        c = e;
        click = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //if(oneClick == false) {
        //click = true;   
        //c = e;
        //}
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        click = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
}
