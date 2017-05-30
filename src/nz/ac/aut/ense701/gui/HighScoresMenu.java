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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import nz.ac.aut.ense701.gameModel.Score;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author joshl
 */
public class HighScoresMenu extends SubMenu {
    private static final int TOTAL_SCORES_SHOWN = 10;
    private Image titleImage;
    private Image highScoreLabelImage;
    
    private final JLabel[] highScoreLabels;
    private final JLabel[] highScoreLabelContainers;
    private final JLabel titleLabel;
    private ArrayList<Score.HighScore> highScores;
    
    private static final int HIGHSCORE_COLUMNS = 2;
    private static final int HIGHSCORE_ITEMS_PER_COLUMN = 5;
    
    //Percentages of the screen used by the title
    private static final float TITLE_START_WIDTH_RATIO  = 0.25f;
    private static final float TITLE_END_WIDTH_RATIO    = 0.65f;
    private static final float TITLE_START_HEIGHT_RATIO = 0.075f;
    private static final float TITLE_HEIGHT_RATIO       = 0.20f;
    //Percentages of the screen used by the title
    private static final float HIGHSCORE_WIDTH_RATIO    = 0.3f;
    private static final float HIGHSCORE_HEIGHT_RATIO   = 0.10f;
    private static final float HIGHSCORE_START_HEIGHT   = 0.25f;
    private static final float HIGHSCORE_END_HEIGHT     = 0.15f;
    private static final float HIGHSCORE_TEXT_OFFSET    = 0.02f;
    
    public HighScoresMenu() {
        super("Kiwi Island - High Scores Menu");
        highScoreLabels = new JLabel[TOTAL_SCORES_SHOWN];
        highScoreLabelContainers = new JLabel[TOTAL_SCORES_SHOWN];
        titleLabel = new JLabel();
        loadImages();
        loadHighScores();
        init();
    }
    
    private void loadImages() {
        try {
            titleImage = ImageIO.read(this.getClass().
                    getResource("/nz/ac/aut/ense701/guiImages/HighScores.png"));
            highScoreLabelImage = ImageIO.read(this.getClass().
                    getResource("/nz/ac/aut/ense701/guiImages/highScoreContainer.png"));
        } catch (IOException e) {
            System.err.println("Unable to load Image. " + e.getMessage());
        }
    }
    
    private void init() {
        menuPane.setLayer(titleLabel, 1);
        menuPane.add(titleLabel);
        
        for(int i = 0; i < TOTAL_SCORES_SHOWN; i++) {
            highScoreLabels[i] = new JLabel();
            highScoreLabelContainers[i] = new JLabel();
            highScoreLabels[i].setText(highScores.get(i).toString());
            menuPane.setLayer(highScoreLabels[i], 2);
            menuPane.add(highScoreLabels[i]);
            menuPane.setLayer(highScoreLabelContainers[i], 1);
            menuPane.add(highScoreLabelContainers[i]);
            if(i+1 == highScores.size()) break;
        }
        
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
        int widthHS = (int) (width*HIGHSCORE_WIDTH_RATIO);
        int heightHS = (int) (height*HIGHSCORE_HEIGHT_RATIO);
        int xGap = (width-(widthHS*HIGHSCORE_COLUMNS))/(HIGHSCORE_COLUMNS+1);
        int xHS = xGap;
        int yHSStart = (int) (height*HIGHSCORE_START_HEIGHT);
        int yHSEnd = (int) (height*HIGHSCORE_END_HEIGHT);
        int yGap = (height-yHSStart-yHSEnd-(heightHS*HIGHSCORE_ITEMS_PER_COLUMN))
                /(HIGHSCORE_ITEMS_PER_COLUMN+1);
        int yHS = yHSStart;
        for(int i = 0; i < TOTAL_SCORES_SHOWN; i++) {
            int textOffset = (int) (width*HIGHSCORE_TEXT_OFFSET);
            if(i == HIGHSCORE_ITEMS_PER_COLUMN) {
                xHS += (xGap+widthHS);
                yHS = yHSStart;
            }
            highScoreLabelContainers[i].setIcon(new ImageIcon(
                    scaleImage(highScoreLabelImage, widthHS, heightHS)));
            highScoreLabelContainers[i].setPreferredSize(new Dimension(widthHS, heightHS));
            highScoreLabelContainers[i].setBounds(xHS, yHS, widthHS, heightHS);
            highScoreLabels[i].setPreferredSize(new Dimension(widthHS-(2*textOffset), heightHS));
            highScoreLabels[i].setBounds(xHS+textOffset, yHS, widthHS-(2*textOffset), heightHS);
            yHS += (yGap+heightHS);
            if(i+1 == highScores.size()) break;
        }
    }

    private void loadHighScores() {
        Score score = new Score();
        try {
            File file = new File("highScores.xml");
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();

            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("Score");
            int numScores = list.getLength();
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            highScores = new ArrayList<Score.HighScore>();
            
            for(int i = 0; i < numScores; i++) {
                Node highScore = list.item(i);

                if(highScore.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) highScore;
                    highScores.add(score.new HighScore(
                            element.getElementsByTagName("name").item(0).getTextContent(),
                            Integer.parseInt(element.getElementsByTagName("points").item(0).getTextContent()),
                            df.parse(element.getElementsByTagName("date").item(0).getTextContent())
                    ));
                }
            }
            Collections.sort(highScores);
        } catch (SAXException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (ParserConfigurationException e) {
            System.err.println(e.getMessage());
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
    }
}
