package guiTests;

import java.awt.Image;
import nz.ac.aut.ense701.gui.Tileset;
import org.junit.Test;

public class TileSetTest extends junit.framework.TestCase {
    private Tileset tileset;
    
    public TileSetTest() {}

    @Override
    protected void setUp()
    {
        tileset = new Tileset();
    }
    
    @Override
    protected void tearDown() {
        tileset = null;
    }

    @Test
    public void testTilesetsLoaded() {
        boolean allLoaded = true;
        Image[] tilesets = tileset.getTileSetsImages();
        for(Image tileSet : tilesets) {
            if(tileSet == null) allLoaded = false;
        }
        
        assertTrue("All tileset images should have loaded", allLoaded);
    }   
    
    @Test
    public void testTilesLoaded() {
        boolean allLoaded = true;
        Image[][] tiles = tileset.getTiles();
        for(int tileSet = 0; tileSet < tileset.getNumTileSets(); tileSet++) {
            for(Image tile : tiles[tileSet]) {
                if(tile == null) allLoaded = false;
            }
        }
        
        assertTrue("All tile images should have loaded", allLoaded);
    }  
}
