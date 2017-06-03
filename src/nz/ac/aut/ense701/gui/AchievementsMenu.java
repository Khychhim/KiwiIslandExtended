/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import nz.ac.aut.ense701.gameModel.GameAchievement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author joshl
 */
public class AchievementsMenu extends SubMenu {
    private Image titleImage;
    private Image progressBarContainerImage;
    private final JLabel titleLabel;
    private final Image[] progressBarImages;
    private final JLabel[] progressBars;
    private final JLabel[] progressBarContainers;
    private final JLabel[] achievementTitles;
    
    private static final int ACHIEVEMENT_COUNT      = 3;
    private static final int MIN_PROGRESS_LENGTH    = 15;
    
    private static final int GAMES_WON      = 0;
    private static final int TRAVELLER      = 1;
    private static final int HERO           = 2;
    
    private final Achievement[] achievements;
    
    //Percentages of the screen used by the progress bars
    private static final float PROGRESS_BAR_START_WIDTH_RATIO   = 0.20f;
    private static final float PROGRESS_BAR_END_WIDTH_RATIO     = 0.60f;
    private static final float PROGRESS_BAR_START_HEIGHT_RATIO  = 0.30f;
    private static final float PROGRESS_BAR_HEIGHT_RATIO        = 0.10f;
    private static final float PROGRESS_BAR_ADD_TO_CONTAINTER_X = 0.005f;
    //Percentages of the screen used by the title
    private static final float TITLE_START_WIDTH_RATIO   = 0.30f;
    private static final float TITLE_END_WIDTH_RATIO     = 0.70f;
    private static final float TITLE_START_HEIGHT_RATIO  = 0.10f;
    private static final float TITLE_HEIGHT_RATIO        = 0.20f;
    //Percentages of the screen used by the achievement titles
    private static final float ADD_TO_PROGRESS_BAR_X = 0.125f;
    
    public AchievementsMenu() {
        super("Kiwi Island - Achievements Menu");
        achievements = new Achievement[ACHIEVEMENT_COUNT];
        progressBarImages = new Image[ACHIEVEMENT_COUNT];
        progressBars = new JLabel[ACHIEVEMENT_COUNT];
        progressBarContainers = new JLabel[ACHIEVEMENT_COUNT];
        achievementTitles = new JLabel[ACHIEVEMENT_COUNT];
        titleLabel = new JLabel();
        loadImages();
        readingAchievements();
        init();
    }
    
    private void init() {
        menuPane.setLayer(titleLabel, 1);
        menuPane.add(titleLabel);
        
        for(int i = 0; i < ACHIEVEMENT_COUNT; i++) {
            progressBarContainers[i] = new JLabel();
            progressBars[i] = new JLabel();
            achievementTitles[i] = new JLabel();
            if(!achievements[i].achieved || 
                    achievements[i].progress > achievements[i].goal) {
                achievementTitles[i].setText(
                        achievements[i].title + " " + achievements[i].progress 
                        + "/" + achievements[i].goal);
            } else {
                achievementTitles[i].setText(
                        achievements[i].title + " " + achievements[i].goal 
                        + "/" + achievements[i].goal);
            }
            
            menuPane.setLayer(progressBarContainers[i], 1);
            menuPane.add(progressBarContainers[i]);
            menuPane.setLayer(progressBars[i], 2);
            menuPane.add(progressBars[i]);
            menuPane.setLayer(achievementTitles[i], 3);
            menuPane.add(achievementTitles[i]);
        }
        
        menu.pack();
        menu.validate();
    }
    
    private void loadImages() {
        try {
            for(int i = 0; i < ACHIEVEMENT_COUNT; i++) {
                progressBarImages[i] = ImageIO.read(this.getClass().
                        getResource("/nz/ac/aut/ense701/guiImages/ProgressBar.png"));
            }
            titleImage = ImageIO.read(this.getClass().
                    getResource("/nz/ac/aut/ense701/guiImages/Achievements.png"));
            progressBarContainerImage = ImageIO.read(this.getClass().
                    getResource("/nz/ac/aut/ense701/guiImages/ProgressBarContainer.png"));
        } catch (IOException e) {
            System.err.println("Unable to load Image. " + e.getMessage());
        }
    }
    
    private void readingAchievements() {
        try {
            File file = new File("AwardCounts.xml");
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();

            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("Player1");
            for(int i = 0; i < list.getLength(); i++) {
                Node achievement = list.item(i);

                if(achievement.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) achievement;
                    achievements[GAMES_WON] = new Achievement( 
                            "Won " + GameAchievement.AMOUNTWON + " games in a row.",
                            element.getElementsByTagName("game3won")
                                    .item(0).getTextContent().equalsIgnoreCase("t"),
                            Integer.parseInt(element.
                            getElementsByTagName("gamesWon").item(0).getTextContent()),
                            GameAchievement.AMOUNTWON
                    );
                    achievements[HERO] = new Achievement(
                            "Hero",
                            element.getElementsByTagName("hero")
                                    .item(0).getTextContent().equalsIgnoreCase("t"),
                            Integer.parseInt(element.
                            getElementsByTagName("savedKiwis").item(0).getTextContent()),
                            GameAchievement.AMOUNTKIWISAVED
                    );
                    achievements[TRAVELLER] = new Achievement(
                            "Traveller",
                            element.getElementsByTagName("traveller")
                                    .item(0).getTextContent().equalsIgnoreCase("t"),
                            Integer.parseInt(element.
                            getElementsByTagName("squares").item(0).getTextContent()),
                            GameAchievement.AMOUNTSTEPS
                    );
                }
            }
        } catch (IOException e) {
            System.err.println("IO error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("NumberFormatException error: " + e.getMessage());
        } catch (ParserConfigurationException e) {
            System.err.println("Parser error: " + e.getMessage());
        } catch (SAXException e) {
            System.err.println("SAXException error: " + e.getMessage());
        }
    }
    
    @Override
    protected void resizeWindow(ComponentEvent e) {
        super.resizeWindow(e);
        int width = e.getComponent().getWidth();
        int height = e.getComponent().getHeight();
        int xBar = (int) (width*PROGRESS_BAR_START_WIDTH_RATIO);
        int widthBar = (int) (width*PROGRESS_BAR_END_WIDTH_RATIO) - xBar;
        int heightBar = (int) (height*PROGRESS_BAR_HEIGHT_RATIO);
        int yBar = (int) (height*PROGRESS_BAR_START_HEIGHT_RATIO);
        int heightBetweenBars = (((height-(heightBar*ACHIEVEMENT_COUNT))-yBar)/ACHIEVEMENT_COUNT);
        int progressWidth, progressX;
        
        for(int i = 0; i < ACHIEVEMENT_COUNT; i++) {
            progressX = (int)(width*PROGRESS_BAR_ADD_TO_CONTAINTER_X);
            if(achievements[i].achieved) progressWidth = widthBar-(progressX*2);
            else progressWidth = (int) (widthBar*((float)achievements[i].progress/
                    (float)achievements[i].goal))-(progressX*2);
            if(progressWidth < MIN_PROGRESS_LENGTH) progressWidth = MIN_PROGRESS_LENGTH;
            progressBars[i].setIcon(new ImageIcon(
                    scaleImage(progressBarImages[i], progressWidth, heightBar)));
            progressBars[i].setPreferredSize(new Dimension(progressWidth, heightBar));
            progressBars[i].setBounds(progressX+xBar, yBar, progressWidth, heightBar);
            progressBarContainers[i].setIcon(new ImageIcon(
                    scaleImage(progressBarContainerImage, widthBar, heightBar)));
            progressBarContainers[i].setPreferredSize(new Dimension(widthBar, heightBar));
            progressBarContainers[i].setBounds(xBar, yBar, widthBar, heightBar);
            achievementTitles[i].setPreferredSize(new Dimension(widthBar, heightBar));
            achievementTitles[i].setBounds(
                    xBar+(int)(width*ADD_TO_PROGRESS_BAR_X), 
                    yBar, widthBar, heightBar);
            yBar += heightBetweenBars;
        }
        
        int xTitle = (int) (width*TITLE_START_WIDTH_RATIO);
        int widthTitle = (int) (width*TITLE_END_WIDTH_RATIO) - xTitle;
        int heightTitle = (int) (height*TITLE_HEIGHT_RATIO);
        int yTitle = (int) (height*TITLE_START_HEIGHT_RATIO);
        titleLabel.setIcon(new ImageIcon(
                    scaleImage(titleImage, widthTitle, heightTitle)));
        titleLabel.setPreferredSize(new Dimension(widthTitle, heightTitle));
        titleLabel.setBounds(xTitle, yTitle, widthTitle, heightTitle);
    }
    
    private class Achievement {
        String title;
        boolean achieved;
        int progress;
        int goal;
        
        Achievement(String title, boolean achieved, int progress, int goal) {
            this.title = title;
            this.achieved = achieved;
            this.progress = progress;
            this.goal = goal;
        }
    }
}
