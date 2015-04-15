package worldchunkgen;

import com.olhcim.util.Map2D;
import java.util.ArrayList;

public class World {
    
    public final long SEED;
    public final int VIEW_RADIUS = 3;
    
    private Map2D<Chunk> chunks; 
    public ArrayList<Chunk> loadedChunks; 
    private ArrayList<Player> players;
    
    private final double loadedChunksUpdateTime = 0.5;
    private double loadedChunksUpdateTimePassed = 0;
    
    /**
     * creates a world with a random seed.
     */
    public World()
    {
        this( System.nanoTime() );
    }
    
    /**
     * creates a world with the specified seed.
     * 
     * Worlds with the same seed will always be identical.
     * 
     * @param seed.
     */
    public World(long seed)
    {
        SEED = seed;
        chunks = new Map2D<>();
        loadedChunks = new ArrayList<>();
        players = new ArrayList<>();
    }
    
    
    
    public void update(double delta)
    {
        if( (loadedChunksUpdateTimePassed += delta) > loadedChunksUpdateTime)
        {
            loadedChunksUpdateTimePassed = 0;
            updateLoadedChunks();
        }
        
        updateVisitedChunks();
    }
    
    /**
     * Adds a player to the world.
     * 
     * @param p The player.
     */
    public void addPlayer(Player p)
    {
        players.add(p);
    }
    
    private void updateVisitedChunks()
    {
        for(Player p : players)
        {
            Chunk c = getChunkAt((int)p.x, (int)p.y);
            c.visited = true;
            
//            if(isChunkSurrounded(c.cx + 1, c.cy)) { getChunk(c.cx + 1, c.cy).visited = true; }
//            if(isChunkSurrounded(c.cx - 1, c.cy)) { getChunk(c.cx - 1, c.cy).visited = true; }
//            if(isChunkSurrounded(c.cx, c.cy + 1)) { getChunk(c.cx, c.cy + 1).visited = true; }
//            if(isChunkSurrounded(c.cx, c.cy - 1)) { getChunk(c.cx, c.cy - 1).visited = true; }
            
        }
    }
    
//    private boolean isChunkSurrounded(int cx, int cy)
//    {
//        return getChunk(cx + 1, cy).visited
//                && getChunk(cx - 1, cy).visited
//                && getChunk(cx, cy + 1).visited
//                && getChunk(cx, cy - 1).visited;
//                
//    }
    
    private void updateLoadedChunks()
    {
        for(Player p : players)
        {
            loadedChunks.clear();
            
            //find chunks to mark as loaded
            for(int i = -VIEW_RADIUS; i <= VIEW_RADIUS; i++)
            {
                for(int j = -VIEW_RADIUS; j <= VIEW_RADIUS; j++)
                {
                    int cx = i + Chunk.toChunkCoord((int)p.x);
                    int cy = j + Chunk.toChunkCoord((int)p.y);
                    loadedChunks.add(getChunk(cx, cy));
                }
            }
        }
    }
    
    /**
     * Returns the chunk at the specified chunk coordinate.
     * If the chunk does not exist, it is generated.
     * 
     * @param cx the X chunk coordinate. 
     * @param cy the Y chunk coordinate.
     * @return the chunk;
     */
    public Chunk getChunk(int cx, int cy)
    {
        generateMissingChunk(cx,cy);
        return chunks.get(cx, cy);
    }
    
    /**
     * Returns the chunk at the specified worldly coordinate.
     * If the chunk does not exist, it is generated.
     * 
     * @param x the X worldly coordinate. 
     * @param y the Y worldly coordinate.
     * @return the chunk;
     */
    public Chunk getChunkAt(int x, int y)
    {
        int cx = Chunk.toChunkCoord(x), cy = Chunk.toChunkCoord(y);
        generateMissingChunk(cx,cy);
        return chunks.get(cx, cy);
    }
    
    /**
     * Generates the chunk at the specified CHUNK Coordinates if it is missing.
     * 
     * @param cx the X chunk coordinate. 
     * @param cy the Y chunk coordinate.
     * @return 
     */
    private void generateMissingChunk(int cx, int cy)
    {
        if(!chunks.containsKey(cx, cy))
        {
            chunks.put(cx, cy, new Chunk(cx, cy, SEED));
        }
    }
    
    /**
     * returns the tile at the specified worldly coordinates.
     * 
     * @param x the X worldly coordinate. 
     * @param y the Y worldly coordinate.
     * @return 
     */
    public byte getTile(int x, int y)
    {
        int cx = Chunk.toChunkCoord(x), cy = Chunk.toChunkCoord(y);
        int rx = Chunk.toRelativeCoord(x), ry = Chunk.toRelativeCoord(y);
        generateMissingChunk(cx,cy);
        return chunks.get(cx, cy).getTile(rx,ry);
    }
}
