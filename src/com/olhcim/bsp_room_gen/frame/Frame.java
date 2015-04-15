
package com.olhcim.bsp_room_gen.frame;

import java.awt.Color;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Frame extends JFrame {
    
    private Canvas canvas;
    
    public Frame(int width, int height, double scale)
    {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        canvas = new Canvas(width, height, scale);
        this.setBackground(Color.BLACK);
        
        this.add( canvas );
        this.pack();
        this.setMinimumSize(this.getSize());
        this.setLocationRelativeTo(null);
    }
    
    public Canvas getCanvas()
    {
        return canvas;
    }
}