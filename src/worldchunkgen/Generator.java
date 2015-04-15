package worldchunkgen;

import com.olhcim.bsp_room_gen.Node;
import com.olhcim.bsp_room_gen.Tree;
import com.olhcim.bsp_room_gen.TreeItterate;
import java.util.Random;

public class Generator {

    public static byte[][] generateChunk(int chunkSize, long seed)
    {
        
        Random r = new Random(seed);
        final byte[][] tiles = new byte[chunkSize][chunkSize];
        Tree tree = new Tree(chunkSize, chunkSize, 0.35 + r.nextDouble()*0.10, 85 + r.nextDouble()*35, 15, seed);

        for (byte[] row : tiles) {
            for (byte t : row) {
                t = 0;
            }
        }

        //paint pathways
        TreeItterate itrPaths = new TreeItterate(tree) {
            @Override
            protected void step(Node n) {
                if (n.left != null && n.right != null) {
                    setRect(tiles, n.left.data.center.x, n.left.data.center.y, Math.abs(n.left.data.center.x - n.right.data.center.x) + 1, Math.abs(n.left.data.center.y - n.right.data.center.y) + 1, (byte)2);
                }
            }
        };
        itrPaths.itterateAll();

        //paint rooms
        TreeItterate itrRooms = new TreeItterate(tree) {
            @Override
            protected void step(Node n) {
                setRect(tiles, n.data.room.x, n.data.room.y, n.data.room.w, n.data.room.h, (byte)1);
            }
        };
        itrRooms.itterateEnds();

        return tiles;
    }

    public static void setTile(byte[][] tiles, int x, int y, byte t) {
        if (x >= 0 && y >= 0 && x < tiles.length && y < tiles[0].length) {
            tiles[x][y] = t;
        }
    }

    public static void setRect(byte[][] tiles, int x, int y, int w, int h, byte t) {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                setTile(tiles, x + i, y + j, t);
            }
        }
    }

}
