/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

/**
 *
 * @author joshl
 */
public class AchievementsMenu extends SubMenu {
    private Image titleImage;
    private Image progressBarContainerImage;
    private JLabel titleLabel;
    private final Image[] progressBarImages;
    private final JLabel[] progressBars;
    private final JLabel[] progressBarContainers;
    
    private static final int ACHIEVEMENTS = 4;
    
    //Percentages of the screen used by the progress bars
    private static final float PROGRESS_BAR_START_WIDTH_RATIO   = 0.20f;
    private static final float PROGRESS_BAR_END_WIDTH_RATIO     = 0.60f;
    
    public AchievementsMenu() {
        super("Kiwi Island - Achievements Menu");
        progressBarImages = new Image[ACHIEVEMENTS];
        progressBars = new JLabel[ACHIEVEMENTS];
        progressBarContainers = new JLabel[ACHIEVEMENTS];
        loadImages();
    }
    
    private void loadImages() {
        try {
            for(int i = 0; i < ACHIEVEMENTS; i++) {
                progressBarImages[i] = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/guiImages/ProgressBar.png"));
            }
            titleImage = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/guiImages/Achievements.png"));
            progressBarContainerImage = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/guiImages/ProgressBarContainer.png"));
        } catch (IOException e) {
            System.err.println("Unable to load Image. " + e.getMessage());
        }
    }
}
