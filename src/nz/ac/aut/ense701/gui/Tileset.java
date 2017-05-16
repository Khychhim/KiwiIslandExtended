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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author joshl
 */
public class Tileset {
    
    //Tileset Identifiers
    public static final int CLIFF = 0;
    public static final int GROUND = 1;
    public static final int ROCKS = 2;
    public static final int TREES = 3;
    
    //Array containing paths to tilesets
    private final String[] tileSets = {
        "/nz/ac/aut/ense701/gameImages/Cliff_tileset.png",
        "/nz/ac/aut/ense701/gameImages/ground_tiles.png",
        "/nz/ac/aut/ense701/gameImages/rocks.png",
        "/nz/ac/aut/ense701/gameImages/trees.png"
    };
    
    //Array of tileset Images
    private BufferedImage[] tileSetsImages;
    
    //Array of the tiles in each tileset
    private final Image[][] tiles;
    
    //The width and heights of the tiles in pixels
    private final int TILE_WIDTH  = 32;
    private final int TILE_HEIGHT = 32;
    
    public Tileset() {
        tiles = new Image[tileSets.length][];
        loadTileSets();
        setupTiles();
    }
    
    //Loads the tileset images
    private void loadTileSets() {
        for(int i = 0; i < tileSets.length; i++) {
            try {
                tileSetsImages[i] = ImageIO.read(this.getClass().getResource(tileSets[i]));
            } catch(IOException ex) {
                System.err.println("Failed to load " + tileSets[i] + ", " + ex.getMessage());
            }
        }
    }
    
    //Setup the tiles array
    private void setupTiles() {
        for(int i = 0; i < tileSets.length; i++) {
            //The width/height of the tileset over the tiles size +1 for safety
            int tilesHorizontal = (tileSetsImages[i].getWidth(null)/TILE_WIDTH)+1;
            int tilesVertical = (tileSetsImages[i].getHeight(null)/TILE_HEIGHT)+1;
            int totalTiles = tilesHorizontal*tilesVertical;
            tiles[i] = new Image[totalTiles];
            //Split tileset Image into tiles
            int tileCount = 0;
            for(int x = 0; x < tilesHorizontal; x++) {
                for(int y = 0; y < tilesVertical; y++) {
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
        return tileSets.length;
    }
}
