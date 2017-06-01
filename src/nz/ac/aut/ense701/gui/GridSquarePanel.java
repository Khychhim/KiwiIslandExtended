package nz.ac.aut.ense701.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.Terrain;

/*
 * Panel for representing a single GridSquare of the island on the GUI.
 * 
 * @author AS
 * @version 1.0 - created
 */

public class GridSquarePanel extends javax.swing.JLayeredPane
{
    /** 
     * Creates new GridSquarePanel.
     * @param game the game to represent
     * @param row the row to represent
     * @param column the column to represent
     * @param tileset the tile set being used
     */
    public GridSquarePanel(Game game, int row, int column, Tileset tileset)
    {
        this.game   = game;
        this.row    = row;
        this.column = column;
        this.tileset = tileset;
        initComponents();
    }
    
    /**
     * Updates the representation of the grid square panel.
     * @param tileSize
     */
    public void update(Dimension tileSize)
    {
        // get the GridSquare object from the world
        Terrain terrain   = game.getTerrain(row, column);
        boolean squareVisible = game.isVisible(row, column);
        boolean squareExplored = game.isExplored(row, column);
        int tileWidth = tileSize.width/game.getViewSizeOfMap();
        int tileHeight = tileSize.height/game.getViewSizeOfMap();
        
        Image image;
        
        switch ( terrain )
        {
            case SAND     : image = tileset.getSingleTile(Tileset.SAND, 
                        tileWidth, tileHeight); break;
            case FOREST   : image = tileset.getSingleTile(Tileset.FOREST, 
                        tileWidth, tileHeight); break;
            case WETLAND  : image = tileset.getSingleTile(Tileset.WETLAND, 
                        tileWidth, tileHeight); break;
            case SCRUB    : image = tileset.getSingleTile(Tileset.SCRUB, 
                        tileWidth, tileHeight); break;
            case WATER    : image = tileset.getSingleTile(Tileset.WATER, 
                        tileWidth, tileHeight); break;
            default  : image = tileset.getSingleTile(Tileset.BLANK, 
                        tileWidth, tileHeight); break;
        }
        
        if ( squareExplored || squareVisible )
        {
            // Set the text of the JLabel according to the occupant
            Image occupantsImage = getOccupantIcon(tileWidth, tileHeight);
            if(occupantsImage != null)
                lblForeground.setIcon(new ImageIcon(occupantsImage));
            // set border colour according to 
            // whether the player is in the grid square or not
            setBorder(game.hasPlayer(row,column) ? activeBorder : normalBorder);
        }
        else
        {
            image = tileset.getSingleTile(Tileset.BLANK, tileWidth, tileHeight);
            setBorder(normalBorder);
        }
        lblBackground.setIcon(new ImageIcon(image));
    }
    
    private Image getOccupantIcon(int tileWidth, int tileHeight) {
        String occupants = game.getOccupantStringRepresentation(row, column);
        int numOccupants = occupants.length() == 1 ? 2 : occupants.length();
        int imWidth = (tileWidth/3);
        int imHeight = tileHeight/3;
        if(numOccupants > 0) {
            BufferedImage image = new BufferedImage(
                                   imWidth*numOccupants, imHeight,
                                   BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) image.getGraphics();
            int x = 0, y = 0;
            if(occupants.length() == 1) {
                g.drawImage(tileset.getSingleTile(Tileset.EMPTY, imWidth, imHeight), x, y, null);
                x += imWidth;
            }
            if(occupants.contains("F")) {
                g.drawImage(tileset.getSingleTile(Tileset.FAUNA, imWidth, imHeight), x, y, null);
                x += imWidth;
            }
            if(occupants.contains("E")) {
                g.drawImage(tileset.getSingleTile(Tileset.FOOD, imWidth, imHeight), x, y, null);
                x += imWidth;
            }
            if(occupants.contains("P")) {
                g.drawImage(tileset.getSingleTile(Tileset.PREDATOR, imWidth, imHeight), x, y, null);
                x += imWidth;
            }
            if(occupants.contains("T")) {
                g.drawImage(tileset.getSingleTile(Tileset.TOOL, imWidth, imHeight), x, y, null);
                x += imWidth;
            }
            if(occupants.contains("K")) {
                g.drawImage(tileset.getSingleTile(Tileset.KIWI, imWidth, imHeight), x, y, null);
                x += imWidth;
            }
            if(occupants.contains("H")) {
                g.drawImage(tileset.getSingleTile(Tileset.HAZARD, imWidth, imHeight), x, y, null);
                x += imWidth;
            }
            if(occupants.contains("Q")) {
                g.drawImage(tileset.getSingleTile(Tileset.TRIGGER, imWidth, imHeight), x, y, null);
                x += imWidth;
            }
            return image;
        }
        return null;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblBackground = new javax.swing.JLabel();
        lblForeground = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setLayout(new javax.swing.OverlayLayout(this));

        lblBackground.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(lblBackground);

        lblForeground.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblForeground.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblForeground.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        setLayer(lblForeground, 1);
        add(lblForeground);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblBackground;
    private javax.swing.JLabel lblForeground;
    // End of variables declaration//GEN-END:variables
    
    private Game game;
    private Tileset tileset;
    public int row, column;
    
    private static final Border normalBorder = new LineBorder(Color.BLACK, 1);
    private static final Border activeBorder = new LineBorder(Color.RED, 3);
}
