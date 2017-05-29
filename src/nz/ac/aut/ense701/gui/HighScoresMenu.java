/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author joshl
 */
public class HighScoresMenu extends SubMenu {
    private static final int TOTAL_SCORES_SHOWN = 10;
    private Image titleImage;
    
    private final JLabel[] highScores;
    private final JLabel titleLabel;
    
    //Percentages of the screen used by the title
    private static final float TITLE_START_WIDTH_RATIO   = 0.25f;
    private static final float TITLE_END_WIDTH_RATIO     = 0.65f;
    private static final float TITLE_START_HEIGHT_RATIO  = 0.075f;
    private static final float TITLE_HEIGHT_RATIO        = 0.20f;
    
    public HighScoresMenu() {
        super("Kiwi Island - High Scores Menu");
        highScores = new JLabel[TOTAL_SCORES_SHOWN];
        titleLabel = new JLabel();
        loadImages();
        init();
    }
    
    private void loadImages() {
        try {
            titleImage = ImageIO.read(this.getClass().
                    getResource("/nz/ac/aut/ense701/guiImages/HighScores.png"));
        } catch (IOException e) {
            System.err.println("Unable to load Image. " + e.getMessage());
        }
    }
    
    private void init() {
        menuPane.setLayer(titleLabel, 1);
        menuPane.add(titleLabel);
        
        
        
        menu.pack();
        menu.validate();
    }
    
    @Override
    protected void resizeWindow(ComponentEvent e) {
        super.resizeWindow(e);
        int width = e.getComponent().getWidth();
        int height = e.getComponent().getHeight();
        
        int xTitle = (int) (width*TITLE_START_WIDTH_RATIO);
        int widthTitle = (int) (width*TITLE_END_WIDTH_RATIO) - xTitle;
        int heightTitle = (int) (height*TITLE_HEIGHT_RATIO);
        int yTitle = (int) (height*TITLE_START_HEIGHT_RATIO);
        titleLabel.setIcon(new ImageIcon(
                    scaleImage(titleImage, widthTitle, heightTitle)));
        titleLabel.setPreferredSize(new Dimension(widthTitle, heightTitle));
        titleLabel.setBounds(xTitle, yTitle, widthTitle, heightTitle);
    }
}
