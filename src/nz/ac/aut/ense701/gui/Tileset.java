/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author joshl
 */
public class Tileset implements Serializable{
    
    //Tileset Identifiers
    public static final int CLIFF  = 0;
    public static final int GROUND = 1;
    public static final int ROCKS  = 2;
    public static final int TREES  = 3;
    //Cliff Tiles
    public static final int CLIFF_TOP_LEFT      = 0;
    public static final int CLIFF_TOP_MIDDLE    = 1;
    public static final int CLIFF_TOP_RIGHT     = 2;
    public static final int CLIFF_MIDDLE_LEFT   = 3;
    public static final int CLIFF_MIDDLE_RIGHT  = 5;
    public static final int CLIFF_BOTTOM_LEFT   = 6;
    public static final int CLIFF_BOTTOM_MIDDLE = 7;
    public static final int CLIFF_BOTTOM_RIGHT  = 8;
    public static final int CLIFF_BASE_LEFT     = 9;
    public static final int CLIFF_BASE_MIDDLE   = 10;
    public static final int CLIFF_BASE_RIGHT    = 11;
    //Ground Tiles
    //Plain Grass
    public static final int GRASS_TOP_LEFT      = 0;
    public static final int GRASS_TOP_RIGHT     = 1;
    public static final int GRASS_BOTTOM_LEFT   = 10;
    public static final int GRASS_BOTTOM_RIGHT  = 11;
    //Plain Sand
    public static final int SAND_TOP_LEFT       = 2;
    public static final int SAND_TOP_MIDDLE     = 3;
    public static final int SAND_TOP_RIGHT      = 4;
    public static final int SAND_MIDDLE_LEFT    = 12;
    public static final int SAND_MIDDLE_MIDDLE  = 13;
    public static final int SAND_MIDDLE_RIGHT   = 14;
    public static final int SAND_BOTTOM_LEFT    = 22;
    public static final int SAND_BOTTOM_MIDDLE  = 23;
    public static final int SAND_BOTTOM_RIGHT   = 24;
    //Grass Next to Water
    public static final int G_AND_W_TOP_LEFT        = 5;
    public static final int G_AND_W_TOP_MIDDLE      = 6;
    public static final int G_AND_W_TOP_RIGHT       = 7;
    public static final int G_AND_W_MIDDLE_LEFT     = 15;
    public static final int G_AND_W_MIDDLE_RIGHT    = 17;
    public static final int G_AND_W_BOTTOM_LEFT     = 25;
    public static final int G_AND_W_BOTTOM_MIDDLE   = 26;
    public static final int G_AND_W_BOTTOM_RIGHT    = 27;
    public static final int G_AND_W_CORNER_TL       = 8;
    public static final int G_AND_W_CORNER_TR       = 9;
    public static final int G_AND_W_CORNER_BL       = 18;
    public static final int G_AND_W_CORNER_BR       = 19;
    //Single Tile Images
    public static final int BLANK   = 0;
    public static final int WATER   = 1;
    public static final int FOREST  = 2;
    public static final int SAND    = 3;
    public static final int WETLAND = 4;
    public static final int SCRUB   = 5;
    public static final int FOOD    = 6;
    public static final int FAUNA   = 7;
    public static final int KIWI    = 8;
    public static final int PREDATOR= 9;
    public static final int TOOL    = 10;
    public static final int TRIGGER = 11;
    public static final int HAZARD  = 12;
    public static final int EMPTY   = 13;
    
    
    //Array containing paths to tilesets
    private static final String[] TILE_SETS = {
        "/nz/ac/aut/ense701/gameImages/Cliff_tileset_smaller.png",
        "/nz/ac/aut/ense701/gameImages/ground_tiles.png",
        "/nz/ac/aut/ense701/gameImages/rocks.png",
        "/nz/ac/aut/ense701/gameImages/trees.png"
    };
    
    //Array containing paths to single tiles
    private static final String[] SINGLE_TILE_PATHS = {
        "/nz/ac/aut/ense701/gameImages/BlankTile.png",
        "/nz/ac/aut/ense701/gameImages/waterTile.png",
        "/nz/ac/aut/ense701/gameImages/forestTile.png",
        "/nz/ac/aut/ense701/gameImages/sandTile.png",
        "/nz/ac/aut/ense701/gameImages/wetlandTile.png",
        "/nz/ac/aut/ense701/gameImages/scrubTile.png",
        "/nz/ac/aut/ense701/gameImages/apple-silhouette.png",
        "/nz/ac/aut/ense701/gameImages/bird-silhouette.png",
        "/nz/ac/aut/ense701/gameImages/kiwi-silhouette.png",
        "/nz/ac/aut/ense701/gameImages/rat-silhouette.png",
        "/nz/ac/aut/ense701/gameImages/trap-silhouette.png",
        "/nz/ac/aut/ense701/gameImages/question.png",
        "/nz/ac/aut/ense701/gameImages/hazard.png",
        "/nz/ac/aut/ense701/gameImages/empty.png"
    };
    
    //Array of tileset Images
    private transient  BufferedImage[] tileSetsImages;
    
    //Array of single Tile Images
    private transient  BufferedImage[] singleTileImages;
    
    //Array of the tiles in each tileset
    private transient Image[][] tiles;
    
    //The width and heights of the tiles in pixels
    private static final int TILE_WIDTH  = 32;
    private static final int TILE_HEIGHT = 32;
    
    private static final Logger LOG = Logger.getLogger(Tileset.class.getName());
    
    public Tileset() {
        tiles = new Image[TILE_SETS.length][];
        tileSetsImages = new BufferedImage[TILE_SETS.length];
        singleTileImages = new BufferedImage[SINGLE_TILE_PATHS.length];
        loadTileSets();
        setupTiles();
    }
    
    //Loads the tileset images
    private void loadTileSets() {
        for(int i = 0; i < TILE_SETS.length; i++) {
            try {
                tileSetsImages[i] = ImageIO.read(this.getClass().getResource(TILE_SETS[i]));
            } catch(IOException ex) {
                LOG.log(Level.SEVERE, "Failed to load {0}, {1}", new Object[]{TILE_SETS[i], ex.getMessage()});
            }
        }
        for(int i = 0; i < SINGLE_TILE_PATHS.length; i++) {
            try {
                singleTileImages[i] = ImageIO.read(this.getClass().getResource(SINGLE_TILE_PATHS[i]));
            } catch(IOException ex) {
                LOG.log(Level.SEVERE, "Failed to load {0}, {1}", new Object[]{SINGLE_TILE_PATHS[i], ex.getMessage()});
            }
        }
    }
    
    //Setup the tiles array
    private void setupTiles() {
        for(int i = 0; i < TILE_SETS.length; i++) {
            //The width/height of the tileset over the tiles size
            int imageHeight = tileSetsImages[i].getHeight(null);
            int tilesHorizontal = (tileSetsImages[i].getWidth(null)/TILE_WIDTH);
            int tilesVertical = (imageHeight/TILE_HEIGHT);
            int totalTiles = tilesHorizontal*tilesVertical;
            tiles[i] = new Image[totalTiles];
            //Split tileset Image into tiles
            int tileCount = 0;
            for(int y = 0; y < tilesVertical; y++) {
                for(int x = 0; x < tilesHorizontal; x++) {
                    tiles[i][tileCount] = tileSetsImages[i].getSubimage(
                            x*TILE_WIDTH, y*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
                    tileCount++;
                }
            }
        }
    }
    
    public Image getTile(int tileSet, int tile, int width, int height) {
        return scaleImage(tiles[tileSet][tile], width, height);
    }
    
    public Image getSingleTile(int tile, int width, int height) {
        return scaleImage(singleTileImages[tile], width, height);
    }
    
    //Creates a new Image is a resized version of the given Image
    private Image scaleImage(Image image, int width, int height) {
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        
        return resized;
    }
    
    public BufferedImage[] getTileSetsImages() {
        return tileSetsImages;
    }

    public Image[][] getTiles() {
        return tiles;
    }
    
    public int getNumTileSets() {
        return TILE_SETS.length;
    }
}
