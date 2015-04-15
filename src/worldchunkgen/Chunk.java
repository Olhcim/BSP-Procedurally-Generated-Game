package worldchunkgen;

public class Chunk {

    public static final int CHUNK_SIZE = 64;

    private final byte[][] tiles;
    private final long relativeSeed;
    public final int cx, cy;
    public boolean visited;

    /**
     * Generates a chunk at the specified X and Y chunk coordinates.
     * 
     * @param cx The X chunk coordinate of this chunk.
     * @param cy The Y chunk coordinate of this chunk.
     * @param seed preferably the seed of the world that this chunk belongs to.
     */
    public Chunk(int cx, int cy, long seed) {
        relativeSeed = seed + cx * 113 + cy * 1151;
        tiles = Generator.generateChunk(CHUNK_SIZE, relativeSeed);
        this.cx = cx;
        this.cy = cy;
        this.visited = false;
    }
    
    /**
     * returns the tile at the specified relative X and Y coordinates to this chunk.
     * 
     * @param x The X coordinate relative to this chunk.
     * @param y The Y coordinate relative to this chunk.
     * @return The tile at the specified location.
     */
    public byte getTile(int x, int y)
    {
        return tiles[toRelativeCoord(x)][toRelativeCoord(y)];
    }
    
    /**
     * returns the tile at the specified relative X and Y coordinates to this chunk.
     * 
     * @param x The X coordinate relative to this chunk.
     * @param y The Y coordinate relative to this chunk.
     * @param tile The type of tile to be set at the specified location.
     */
    public void setTile(int x, int y, byte tile)
    {
        tiles[toRelativeCoord(x)][toRelativeCoord(y)] = tile;
    }
    
     /**
     * Converts a worldly coordinate relative to a chunk.
     * 
     * @param a An X or Y worldly coordinate.
     * @return  A coordinate that is relative to a chunk. ( a % ChunkSize )
     */
    public static int toRelativeCoord(int a)
    {
        return (int)(a % Chunk.CHUNK_SIZE);
    }
    
    /**
     * Converts a worldly coordinate into chunk coordinates
     * 
     * @param a An X or Y worldly coordinate.
     * @return A coordinate that encompasses the location of a chunk. ( (int) (a / ChunkSize) + ((a < 0) ? -1 : 0) )
     */
    public static int toChunkCoord(int a)
    {
        return (int)(a / Chunk.CHUNK_SIZE) + ((a < 0) ? -1 : 0);
    }
}
