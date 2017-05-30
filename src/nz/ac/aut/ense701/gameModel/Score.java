/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author joshl
 */
public class Score {
    private int score;
    
    //Score Values for each event
    public static final int VALUE_HARARD_DEATH = 100;
    public static final int VALUE_HAZARD_BREAK_TRAP = 40;
    public static final int VALUE_HAZARD_NON_FATAL = 30;
    public static final int VALUE_KIWI_COUNTED = 50;
    public static final int VALUE_PREDATOR_TRAPPED = 40;
    public static final int VALUE_KIWI_EATEN = 50;
    //Score Values for end game multiplied by percentage completed
    public static final int SURVIVED = 100;
    public static final int KIWIS_COUNTED = 150;
    public static final int PREDATORS_TRAPPED = 150;
    public static final int REMAINING_STAMINA = 100;
    //public static final int TRASH_COLLECTED = 200; //Not yet implemented
    
    private static final int SCORES_RECORDED = 100;
    
    public Score() {
        score = 0;
    }
    
    public Score(int score) {
        this.score = score;
    }
    
    public void saveHighScore(String name) {
        try {
            File file = new File("highScores.xml");
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();

            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("Score");
            int numScores = list.getLength();
            HighScore[] highScores = new HighScore[numScores];
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            
            for(int i = 0; i < numScores; i++) {
                Node highScore = list.item(i);

                if(highScore.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) highScore;
                    highScores[i] = new HighScore(
                        element.getElementsByTagName("name").item(0).getTextContent(),
                        Integer.parseInt(element.getElementsByTagName("points").item(0).getTextContent()),
                        df.parse(element.getElementsByTagName("date").item(0).getTextContent())
                    );
                    System.out.println(highScores[i]);
                }
            }
            boolean scoreIsHigher = false;
            for(HighScore hs : highScores) {
                if(score > hs.value) scoreIsHigher = true;
            }
            if(numScores < SCORES_RECORDED || scoreIsHigher) {
                Element root = doc.getDocumentElement();
                if(scoreIsHigher && numScores >= SCORES_RECORDED) {
                    int lowestScore = Integer.MAX_VALUE;
                    int lowestIndex = 0;
                    for(int i = 0; i < numScores; i++) {
                        if(highScores[i].value < lowestScore) {
                            lowestScore = highScores[i].value;
                            lowestIndex = i;
                        }
                    }
                } else {
                    Element newScore = doc.createElement("Score");
                    Element playerName = doc.createElement("name");
                    playerName.setTextContent(name);
                    Element playerScore = doc.createElement("points");
                    playerScore.setTextContent(""+score);
                    Element currentDate = doc.createElement("date");
                    currentDate.setTextContent(df.format(new Date()));
                    newScore.appendChild(playerName);
                    newScore.appendChild(playerScore);
                    newScore.appendChild(currentDate);
                    root.appendChild(newScore);
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
        } catch (ParseException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public void addScore(int add) {
        score += Math.abs(add);
    }
    
    public void subtractScore(int subtract) {
        score -= Math.abs(subtract);
    }
    
    private class HighScore {
        String name;
        int value;
        Date date;
        
        HighScore(String name, int value, Date date) {
            this.name = name;
            this.value = value;
            this.date = date;
        }
        
        @Override
        public String toString() {
            return "Name: " + name + ", Score: " + value + ", Date: " + date.toString();
        }
    }
}
