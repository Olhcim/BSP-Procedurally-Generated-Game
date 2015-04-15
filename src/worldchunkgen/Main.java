
package worldchunkgen;

import com.olhcim.bsp_room_gen.frame.Frame;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {
    
    public static final double scale = 4;
    public static final int W = (int)(1280/scale), H = (int)(720/scale);
    public static final Frame frame = new Frame(W,H,scale);
    
    public static final World world = new World();
    
    public static Player player = new Player(Chunk.CHUNK_SIZE/2,Chunk.CHUNK_SIZE/2);
    
    public static long oldTime = System.nanoTime();
    

    
    public static void main(String[] args) {

        world.addPlayer(player);
        
        frame.setVisible(true);
        
        frame.addKeyListener( new KeyListener() {

            @Override public void keyTyped(KeyEvent e) {}
            @Override public void keyReleased(KeyEvent e) {}
            @Override public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode())
                {
                    case KeyEvent.VK_UP :
                    case KeyEvent.VK_W :
                        player.y -= 5;
                        break;
                    case KeyEvent.VK_DOWN :
                    case KeyEvent.VK_S :
                        player.y += 5;
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        player.x -= 5;
                        break;
                    case KeyEvent.VK_RIGHT :
                    case KeyEvent.VK_D :
                        player.x += 5;
                        break;
                }
                
                
                paintAll();
                frame.getCanvas().repaint();
                
            }
        });
        
    }
    
    
    public static void paintAll()
    {
        Graphics2D g = frame.getCanvas().getImageGraphics();
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, frame.getCanvas().getImageWidth(), frame.getCanvas().getImageHeight());
        
        long time = System.nanoTime();
        world.update((time - oldTime)/1000000000.0);
        oldTime = time;

        for (Chunk c : world.loadedChunks) {
            
            
            int sx = c.cx * Chunk.CHUNK_SIZE - (int)player.x + frame.getCanvas().getImageWidth()/2;
            int sy = c.cy * Chunk.CHUNK_SIZE - (int)player.y + frame.getCanvas().getImageHeight()/2;

            for(int x = 0; x < Chunk.CHUNK_SIZE; x++)
            {
                for(int y = 0; y < Chunk.CHUNK_SIZE; y++)
                {
                    switch(c.getTile(x, y))
                    {
                        case 1:
                        case 2:
                            g.setColor( (!c.visited) ? new Color(200,200,200) : new Color(100,100,100) );
                            break;
                        default:
                            g.setColor(Color.BLACK);
                            break;
                    }
                    g.fillRect(sx + x, sy + y, 1, 1);
                }
            }
            
//            g.setColor(new Color(255,255,255,100));
//            g.drawRect(sx, sy, Chunk.CHUNK_SIZE, Chunk.CHUNK_SIZE);
//            g.drawString(c.cx + " " + c.cy, sx + Chunk.CHUNK_SIZE/2, sy + Chunk.CHUNK_SIZE/2);
        }
        
        g.setColor(Color.WHITE);
        g.fillRect(frame.getCanvas().getImageWidth()/2 - 1, frame.getCanvas().getImageHeight()/2 - 1, 3, 3);
    }
}
