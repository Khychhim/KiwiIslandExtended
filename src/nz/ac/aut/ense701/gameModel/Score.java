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
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
    private int currentScore;
    
    //Score Values for each event
    public static final int VALUE_HARARD_DEATH = 100;
    public static final int VALUE_HAZARD_BREAK_TRAP = 40;
    public static final int VALUE_HAZARD_NON_FATAL = 30;
    public static final int VALUE_KIWI_COUNTED = 50;
    public static final int VALUE_PREDATOR_TRAPPED = 40;
    public static final int VALUE_KIWI_EATEN = 50;
    public static final int VALUE_GUESS_CORRECT = 15;
    //Score Values for end game multiplied by percentage completed
    public static final int SURVIVED = 100;
    public static final int KIWIS_COUNTED = 150;
    public static final int PREDATORS_TRAPPED = 150;
    public static final int REMAINING_STAMINA = 100;
    //public static final int TRASH_COLLECTED = 200; //Not yet implemented
    
    private static final int SCORES_RECORDED = 100;
    
    private static final String POINTS_TAG = "points";
    
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    
    private static final Logger LOG = Logger.getLogger(Score.class.getName());
    
    public Score() {
        currentScore = 0;
    }
    
    public Score(int score) {
        currentScore = score;
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
            df = new SimpleDateFormat("dd/MM/yyyy");
            
            for(int i = 0; i < numScores; i++) {
                Node highScore = list.item(i);

                Element element = (Element) highScore;
                highScores[i] = new HighScore(
                    element.getElementsByTagName("name").item(0).getTextContent(),
                    Integer.parseInt(element.getElementsByTagName(POINTS_TAG).item(0).getTextContent()),
                    df.parse(element.getElementsByTagName("date").item(0).getTextContent())
                );
            }
            boolean scoreIsHigher = false;
            for(HighScore hs : highScores) {
                if(currentScore > hs.value) scoreIsHigher = true;
            }
            if(numScores < SCORES_RECORDED || scoreIsHigher) {
                Element root = doc.getDocumentElement();
                if(scoreIsHigher && numScores >= SCORES_RECORDED) {
                    int lowestScore = Integer.MAX_VALUE;
                    for(int i = 0; i < numScores; i++) {
                        if(highScores[i].value < lowestScore) {
                            lowestScore = highScores[i].value;
                        }
                    }
                    for(int i = 0; i < numScores; i++) {
                        Node highScore = list.item(i);

                        if(highScore.getNodeType() == Node.ELEMENT_NODE) {
                            Element element = (Element) highScore;
                            int points = Integer.parseInt(element.
                                    getElementsByTagName(POINTS_TAG).item(0).getTextContent());
                            if(points == lowestScore) {
                                root.removeChild(highScore);
                                break;
                            }
                        }
                    }
                } 
                Element newScore = doc.createElement("Score");
                Element playerName = doc.createElement("name");
                playerName.setTextContent(name);
                Element playerScore = doc.createElement(POINTS_TAG);
                playerScore.setTextContent(Integer.toString(currentScore));
                Element currentDate = doc.createElement("date");
                currentDate.setTextContent(df.format(new Date()));
                newScore.appendChild(playerName);
                newScore.appendChild(playerScore);
                newScore.appendChild(currentDate);
                root.appendChild(newScore);
                
                TransformerFactory transformerFactory = 
                    TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                DOMSource source = new DOMSource(doc);
                StreamResult result = 
                        new StreamResult(new File("highScores.xml"));
                transformer.transform(source, result);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "IO error: {0}", e.getMessage());
        } catch (NumberFormatException e) {
            LOG.log(Level.SEVERE, "Number Format Exception {0}", e.getMessage());
        } catch (ParserConfigurationException e) {
            LOG.log(Level.SEVERE, "Parser error: {0}", e.getMessage());
        } catch (SAXException e) {
            LOG.log(Level.SEVERE, "SAXException error: {0}", e.getMessage());
        } catch (ParseException e) {
            LOG.log(Level.SEVERE, "Document Parsing error: {0}", e.getMessage());
        } catch (TransformerException e) {
            LOG.log(Level.SEVERE, "Transformer error: {0}", e.getMessage());
        }
    }
    
    public int getScore() {
        return currentScore;
    }
    
    public void setScore(int score) {
        this.currentScore = score;
    }
    
    public void addScore(int add) {
        currentScore += Math.abs(add);
    }
    
    public void subtractScore(int subtract) {
        currentScore -= Math.abs(subtract);
    }
    
    public class HighScore implements Comparable<HighScore> {
        String name;
        int value;
        Date date;
        
        public HighScore(String name, int value, Date date) {
            this.name = name;
            this.value = value;
            this.date = date;
        }
        
        @Override
        public String toString() {
            return name + " scored " + value + " on " + df.format(date);
        }

        @Override
        public int compareTo(HighScore hsToCompare) {
            if(hsToCompare.value > value) return 1;
            if(hsToCompare.value < value) return -1;
            return 0;
        }
    }
}
