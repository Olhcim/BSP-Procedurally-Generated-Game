
package com.olhcim.bsp_room_gen;

import com.olhcim.bsp_room_gen.frame.Frame;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Stack;

public class Main {

    public static final int W = 1600, H = 800, L = 20;
    public static final long seed = (long)(Math.random()*Long.MAX_VALUE);
    public static int minArea = 100, maxLevels = 18;
    public static double ratio = 0.45;
    public static Tree tree;
    public static Frame frame;

    public static void main(String[] args) {
        tree = new Tree(W, H, ratio, minArea, maxLevels, seed);
        
        frame = new Frame(W,H,1);
        frame.setVisible(true);
        
        frame.getCanvas().addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {}

            @Override
            public void mouseMoved(MouseEvent e) {
                Node node = tree.getNodeAt(e.getX(), e.getY());
                
                final Graphics2D g = frame.getCanvas().getImageGraphics();
                    
                g.setColor(new Color(128,128,128));
                g.fillRect(0, 0, W, H);

                tree.node.paint(g);
                    
                if(node != null)
                {
                    g.setColor(new Color(0,0,0,50));
                    g.fillRect(node.data.x, node.data.y, node.data.w, node.data.h);
                    g.setColor(new Color(0,0,0,200));
                    
                    Node current = node;
                    while(current.parent != null)
                    {
                        g.setColor(new Color(0,0,0,200));
                        g.drawLine((int)current.data.center.x, (int)current.data.center.y, (int)current.parent.data.center.x, (int)current.parent.data.center.y);
                        current = current.parent;
                    }
                }
                
                frame.getCanvas().repaint();
            }
        });
        
        frame.addMouseWheelListener( new MouseWheelListener() {

            private long lastScroll = System.nanoTime();
            
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                
                long time = System.nanoTime();
                if(System.nanoTime() - lastScroll < 100000000)
                    return;
                lastScroll = time;

                
                int scroll = -1*e.getWheelRotation();
                
                if(!e.isControlDown() && !e.isShiftDown())
                {
                    maxLevels += scroll;
                    maxLevels = (maxLevels < 0) ? 0 : maxLevels;
                    maxLevels = (maxLevels > 18) ? 18 : maxLevels;
                    tree = new Tree(W, H, ratio, minArea, maxLevels, seed);
                }
                
                if(e.isControlDown() && !e.isShiftDown())
                {
                    ratio += scroll/100.0;
                    ratio = (ratio < 0.1) ? 0.1 : ratio;
                    ratio = (ratio > 0.45) ? 0.45 : ratio;
                    tree = new Tree(W, H, ratio, minArea, maxLevels, seed);
                }
                
                if(e.isShiftDown() && !e.isControlDown())
                {
                    minArea += scroll*100;
                    minArea = (minArea < 100) ? 100 : minArea;
                    minArea = (minArea > 10000) ? 10000 : minArea;
                    tree = new Tree(W, H, ratio, minArea, maxLevels, seed);
                }
                
                System.out.println( " Max Levels: " + maxLevels + " Ratio: " + ratio + " Min Area: " + minArea);
                
                Graphics2D g = frame.getCanvas().getImageGraphics();
                g.setColor(new Color(128,128,128));
                g.fillRect(0, 0, W, H);
                tree.node.paint(g);
                frame.getCanvas().repaint();
            }
        } );
        
        
        
        
        
        
        
        
    }
    
}
