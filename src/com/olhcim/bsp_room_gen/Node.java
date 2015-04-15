
package com.olhcim.bsp_room_gen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Node {
    public Node parent;
    public Node left;
    public Node right;
    public Container data;
    
    public Node(int width, int height)
    {
        parent = null;
        left = null;
        right = null;
        this.data = new Container(0,0,width,height);
    }
    
    public Node(Node parent, Container data)
    {
        this.parent = parent;
        left = null;
        right = null;
        this.data = data;
    }

    
    public void paint(Graphics2D g)
    {
        if (left == null && right == null)
        {
            data.paint(g);
        } else {
            if (left != null)
            {
                left.paint(g);
            }
            if (right != null)
            {
                right.paint(g);
            }
            if(left != null && right != null)
            {
                g.setColor(Color.WHITE);
                int pathSize = (int)Math.ceil( Math.sqrt(Math.sqrt(data.area)) / 4 );
                
                
                    int x = (int) (Math.min(left.data.center.x, right.data.center.x) - pathSize/2.0);
                    int y = (int) (Math.min(left.data.center.y, right.data.center.y) - pathSize/2.0);
                    int w = (int) Math.abs(left.data.center.x - right.data.center.x) + pathSize;
                    int h = (int) Math.abs(left.data.center.y - right.data.center.y) + pathSize;
                    
                    g.fillRect(x,y,w,h);
            }
        }
    }
    
    public Node splitNode(double ratio, Random r)
    {
        if(left == null && right == null)
        {
            Container[] cs = data.splitContainer(ratio, r, true);
            left  = new Node(this, cs[0]);
            right = new Node(this, cs[1]);
        }
        
        return this;
    }
    
    public void splitNode(double ratio, double minArea, Random r, int itr) {
        if (itr > 0 && data.area * ratio > minArea) {
            splitNode(ratio, r);
            left.splitNode(ratio, minArea, r, itr-1);
            right.splitNode(ratio, minArea, r, itr-1);
        }
    }
}
