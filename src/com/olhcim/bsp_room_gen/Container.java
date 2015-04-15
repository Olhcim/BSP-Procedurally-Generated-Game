
package com.olhcim.bsp_room_gen;

import java.awt.Color;
import java.util.Random;

public class Container {
    
    public static int attempts = 0;
    
    public int x, y, w, h, area;
    public Point center;
    
    public Container room;
    
    public Color color;

    public Container(int x, int y, int w, int h)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.area = w*h;
        center = new Point((int)(x + w/2.0), (int)(y + h/2.0));
        color = new Color(200+(int)(Math.random()*50),200+(int)(Math.random()*50),200+(int)(Math.random()*50));
    }
    
    public Container[] splitContainer(double ratio, Random r, boolean discard)
    {
        Container[] cs = new Container[2];
        double nr = r.nextDouble();
        boolean wSplit = r.nextBoolean();
        
        if (wSplit)
        {
            cs[0] = new Container(x, y, w, (int)(h * nr));
            cs[1] = new Container(x, y + cs[0].h, w, h - cs[0].h);
        } else {
            cs[0] = new Container(x, y, (int)(w * nr), h);
            cs[1] = new Container(x + cs[0].w, y, w - cs[0].w, h);
        }
        
        if (discard)
        {
            double c0hr = (wSplit) ? (double)cs[0].h / cs[0].w : (double)cs[0].w / cs[0].h;
            double c1hr = (wSplit) ? (double)cs[1].h / cs[1].w : (double)cs[1].w / cs[1].h;
            if (c0hr < ratio || c1hr < ratio) { return splitContainer(ratio, r, discard); }
        }
        
        return cs;
    }
    
    public void createSubRoom(Random r)
    {
        double x = this.x + (r.nextDouble()*Math.floor(this.w / 5)) + 1;
        double y = this.y + (r.nextDouble()*Math.floor(this.h / 5)) + 1;
        double w = this.w - (x - this.x);
        double h = this.h - (y - this.y);
        w -= r.nextDouble() * (w / 7) + 1;
        h -= r.nextDouble() * (w / 7) + 1;
        
        room = new Container((int)x,(int)y,(int)w,(int)h);
    }
    
    public boolean isPointInside(int x, int y)
    {
        return x >= this.x && y >= this.y && x < this.x + w && y < this.y + h;
    }
}