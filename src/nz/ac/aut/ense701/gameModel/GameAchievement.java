/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;



import java.io.File;
import java.io.IOException;
import java.io.Serializable;
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
import org.w3c.dom.Attr;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *Achievement class uses the logic for assigning achievements to a player by
 * writing and reading through an achievement xml document and stores required
 * count information in a file called AwardCounts.xml. This is used to assign
 * players achievements.
 * @author Nishan
 */

public class GameAchievement implements Serializable{

    private Element Achievement;
    private  boolean won3gamesinrow;
    private boolean walked;
    private boolean  savedKiwis;
    private boolean walkingGUI;
    private boolean savedGUI;
    private boolean wonGUI;
    public static final int AMOUNTKIWISAVED = 3;
    public static final int AMOUNTSTEPS = 1000;
    public static final int AMOUNTWON= 3;
    public static final String ACHIEVEMENTS = "Achievements.xml";
    public static final String AWARDCOUNTS = "AwardCounts.xml";
    
    //Empty constructor.
    public GameAchievement(){
        //empty constructor so that class can be accessed
        //in kiwiCountGUI.
        this.Achievement = null;
    }
    
    
    //Constructor for Achievement element.
    public GameAchievement(Element achieve){
        this.Achievement = achieve;
    }
    
    
    /**
     * creates file through the Achievements.xml file and returns the document
     * using documentBuilder
     * @return document
     */
    public Document readAchievementXML(){
        Document xml = null;
        try{
         
            File fXmlFile = new File(ACHIEVEMENTS);
            DocumentBuilderFactory dbFactory = 
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            xml = dBuilder.parse(fXmlFile);
            xml.getDocumentElement().normalize();
        }
        catch(SAXException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
        catch(IOException e){
           Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
        catch(ParserConfigurationException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
        return xml;
    }
    
    
    /**
     * gets called when a player looses a game, it reads the value stored in
     * amount of games won and resets it back to zero so that player does not
     * receive award based on winning three games in a row.
     */
    public void lossGameResetCounter(){
        Document xml = null;
        try{
            File File = new File(AWARDCOUNTS);
            DocumentBuilderFactory dbFactory = 
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            xml = dBuilder.parse(File);
            Node main = xml.getElementsByTagName("Player1").item(0);
            NodeList list = main.getChildNodes();
            for(int i=0; i<list.getLength(); i++){
                Node node = list.item(i);
                if("gamesWon".equals(node.getNodeName())){
                    int gameCounter = 0;
                    node.setTextContent(Integer.toString(gameCounter));
                }
            }
            // write the content into xml file
		TransformerFactory transformerFactory = 
                        TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(xml);
		StreamResult result = new StreamResult(File);
		transformer.transform(source, result);
        }
        catch(SAXException e){
          Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);      
        }
        catch(IOException e){
           Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
        catch(ParserConfigurationException e){
          Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
        catch(TransformerException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    
    /**
     * reads through the awardCounts xml and if user wins a game 
     * increments
     * the amount of games the player wins. if won 3 in a row it 
     * will assign
     * a new achievement by calling set achievement and give the players 
     * achievement.
     */
    public void Won3Games(){
        Document xml = null;
        try{
            //read through the AwardCounts.xml already created.
            File File = new File(AWARDCOUNTS);
            DocumentBuilderFactory dbFactory = 
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            xml = dBuilder.parse(File);
            //start from the parent node.
            Node main = xml.getElementsByTagName("Player1").item(0);
            NodeList list = main.getChildNodes();
            //loop through all nodes in the xml dom parser.
            for(int i=0; i<list.getLength(); i++){
                Node node = list.item(i);
                if("gamesWon".equals(node.getNodeName())){
                    //check if number of games won is three.
                    if("3".equals(node.getTextContent())){
                        //user testing check to see if game won.
                        won3gamesinrow = true;     
                    }
                    else{
                        //create counter for counting how many games 
                        //have been won.
                        int gameCounter;
                         //convert from string to integer then integer 
                         //to string.
                        gameCounter = Integer.parseInt(node.getTextContent());
                        gameCounter++; //increment number of games won.
                         //set node value to new count value.
                        node.setTextContent(Integer.toString(gameCounter));
                    }
                }
                if(check_if_wonaward() && won3gamesinrow && 
                        "game3won".equals(node.getNodeName())){
                        node.setTextContent("t");
                        wonGUI = true;
                        setAchievements(readAchievementXML(), true,
                                false, false);             
                     
                }
            }
            //write the content into xml file
		TransformerFactory transformerFactory = 
                        TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(xml);
		StreamResult result = new StreamResult(File);
		transformer.transform(source, result);
        }
        catch(SAXException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);     
        }
        catch(IOException e){
           Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
        catch(ParserConfigurationException e){
           Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
        catch(TransformerException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    
    /**
     * assign award value depending on achievement and boolean value.
     * writes to the achievement xml document the achievement that has been 
     * given to the player.
     * @param doc
     * @param won3games 
     * @param walked_distance 
     */
    public void setAchievements(Document doc, Boolean won3games,
            Boolean walked_distance, Boolean kiwi){
        
        if(won3games){ //if player has won 3 games in a row.
        try{     
            Element root = doc.getDocumentElement();
            //access root node.
            Element achievement = doc.createElement("Achievement");
            root.appendChild(achievement);
            Attr attr = doc.createAttribute("id");
            attr.setValue("1");
            achievement.setAttributeNode(attr);
            Element name = doc.createElement("Award");
            name.appendChild(doc.createTextNode("Survivor"));
            achievement.appendChild(name);
            Element description = doc.createElement("Description");
            description.appendChild(doc.createTextNode("Won"
                    + " three games in a row!"));
            achievement.appendChild(description);
            TransformerFactory transformerFactory = 
                    TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = 
                    new StreamResult(new File(ACHIEVEMENTS));
            transformer.transform(source, result);
            } catch (TransformerException ex) {
                Logger.getLogger(GameAchievement.
                        class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        else if(walked_distance){ //if walking distance 
            //award assign it to the achievement.
        try{     
            Element root = doc.getDocumentElement();
            //access root node.
            Element achievement = doc.createElement("Achievement");
            root.appendChild(achievement);
            Attr attr = doc.createAttribute("id");
            attr.setValue("2");
            achievement.setAttributeNode(attr);
            Element name = doc.createElement("Award");
            name.appendChild(doc.createTextNode("Traveller"));
            achievement.appendChild(name);
            Element description = doc.createElement("Description");
            description.appendChild(doc.
                    createTextNode("You have walked over 1000 steps!!"));
            achievement.appendChild(description);
            TransformerFactory transformerFactory = 
                    TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = 
                    new StreamResult(new File(ACHIEVEMENTS));
            transformer.transform(source, result);
        } catch (TransformerException ex) {
                Logger.getLogger(GameAchievement.
                        class.getName()).log(Level.SEVERE, null, ex);
        }
     }
        else if(kiwi){ //check selection is kiwi to start assigning achievement.
         try{     
            Element root = doc.getDocumentElement();
            //access root node.
            Element achievement = doc.createElement("Achievement");
            root.appendChild(achievement);
            Attr attr = doc.createAttribute("id");
            attr.setValue("3");
            achievement.setAttributeNode(attr);
            Element name = doc.createElement("Award");
            name.appendChild(doc.createTextNode("Hero"));
            achievement.appendChild(name);
            Element description = doc.createElement("Description");
            description.appendChild(doc.
                    createTextNode("You have saved allot of kiwis!!"));
            achievement.appendChild(description);
            TransformerFactory transformerFactory = 
                    TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = 
                    new StreamResult(new File(ACHIEVEMENTS));
            transformer.transform(source, result);            
        } catch (TransformerException ex) {
                Logger.getLogger(GameAchievement.
                        class.getName()).log(Level.SEVERE, null, ex);
        }      
      }        
    }
    
    
    /**
     * Reads the value in saved kiwi in the AwardCounts.xml document then
     * checks to see if player has saved a certain amount of kiwis,
     * if so assigns true to the boolean savedKiwis and sets the required
     * achievement to be stored in the Achievements.xml
     * @param kiwi 
     */
    public void read_kiwiCount(int kiwi){
        Document xml = null;
        try{
            File File = new File(AWARDCOUNTS);
            DocumentBuilderFactory dbFactory = 
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            xml = dBuilder.parse(File);
            Node main = xml.getElementsByTagName("Player1").item(0);
            NodeList list = main.getChildNodes();
            for(int i=0; i<list.getLength(); i++){
                Node node = list.item(i);
                if("savedKiwis".equals(node.getNodeName())){
                    node.setTextContent(Integer.toString(kiwi));
                    if(kiwi >=AMOUNTKIWISAVED){ 
                            //This is if player saves x amount of kiwi. 
                        savedKiwis = true;
                    }
                }
                if(check_if_kiwiaward() && savedKiwis 
                        && "hero".equals(node.getNodeName())){
                        node.setTextContent("t");
                        savedGUI = true;
                        setAchievements(readAchievementXML()
                                , false, false, true); 
                }
            }       
            // write the content into xml file
		TransformerFactory transformerFactory = 
                        TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(xml);
		StreamResult result = new StreamResult(File);
		transformer.transform(source, result);
	
        }
        catch(SAXException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);     
        }
        catch(IOException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
        catch(ParserConfigurationException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
        catch(TransformerException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    
    /**
     * Write to the count of steps for the travel award.
     * @param count_of_step 
     */
    public void write_to_count(int count_of_step){
         Document xml = null;
        try{
            File File = new File(AWARDCOUNTS);
            DocumentBuilderFactory dbFactory = 
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            xml = dBuilder.parse(File);
            Node main = xml.getElementsByTagName("Player1").item(0);
            NodeList list = main.getChildNodes();
            for(int i=0; i<list.getLength(); i++){
                Node node = list.item(i);
                if("squares".equals(node.getNodeName())){
                    node.setTextContent(Integer.toString(count_of_step));
                    walked = false;
                    if(count_of_step >=AMOUNTSTEPS){ 
                        //This isif player walks over 1000 squares.
                            walked = true;
                    }
                }
                if(check_if_tavelaward() && walked &&
                        "traveller".equals(node.getNodeName())){
                        node.setTextContent("t");
                        walkingGUI = true;
                        setAchievements(readAchievementXML(),
                                false, true, false);
                }
            }
            // write the content into xml file
		TransformerFactory transformerFactory = 
                        TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(xml);
		StreamResult result = new StreamResult(File);
		transformer.transform(source, result);
        }
        catch(SAXException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);      
        }
        catch(IOException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
        catch(ParserConfigurationException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
        catch(TransformerException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }

    }
    
    
    /**
     * read count award in the xml document AwardCounts.xml to retrieve the old
     * value in the xml document squares tag.
     * @return int.
     */
    public int readCount(){
        int count_of_steps=0;
         Document xml = null;
        try{
            File File = new File(AWARDCOUNTS);
            DocumentBuilderFactory dbFactory = 
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            xml = dBuilder.parse(File);
            Node main = xml.getElementsByTagName("Player1").item(0);
            NodeList list = main.getChildNodes();
            for(int i=0; i<list.getLength(); i++){
                Node node = list.item(i);
                if("squares".equals(node.getNodeName())){
                    System.out.println("True");
                    System.out.println("Counting steps read");              
                    count_of_steps = Integer.parseInt(node.getTextContent()); 
                    //convert number of steps
                }
            }
            // write the content into xml file
		TransformerFactory transformerFactory = 
                        TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(xml);
		StreamResult result = new StreamResult(File);
		transformer.transform(source, result);
        }
        catch(SAXException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);       
        }
        catch(IOException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
        catch(ParserConfigurationException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
        catch(TransformerException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
        return count_of_steps; 
    }

    
    /**
     * Checks if kiwi award is unlocked or locked for player achievement.
     * @return boolean if unlocked then returns true.
     */
    public boolean check_if_kiwiaward(){
         Document xml = null;
        try{
            File File = new File(AWARDCOUNTS);
            DocumentBuilderFactory dbFactory = 
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            xml = dBuilder.parse(File);
            Node main = xml.getElementsByTagName("Player1").item(0);
            NodeList list = main.getChildNodes(); 
            for(int i=0; i<list.getLength(); i++){
                Node node = list.item(i);
                if("hero".equals(node.getNodeName())){
                    if( "f".equals(node.getTextContent())){
                        return true;
                    }
                }     
            }
            
            // write the content into xml file
		TransformerFactory transformerFactory = 
                        TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(xml);
		StreamResult result = new StreamResult(File);
		transformer.transform(source, result);
        }
        catch(SAXException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);     
        }
        catch(IOException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
        catch(ParserConfigurationException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
        catch(TransformerException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
    
    
     /**
     * Checks if travel award is unlocked or locked for player achievement.
     * @return boolean if unlocked then returns true.
     */
    public boolean check_if_tavelaward(){
         Document xml = null;
        try{
            File File = new File(AWARDCOUNTS);
            DocumentBuilderFactory dbFactory = 
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            xml = dBuilder.parse(File);
            Node main = xml.getElementsByTagName("Player1").item(0);
            NodeList list = main.getChildNodes();
            for(int i=0; i<list.getLength(); i++){
                Node node = list.item(i);
                if("traveller".equals(node.getNodeName())){
                    if( "f".equals(node.getTextContent())){
                        return true;
                    }
                }               
            }         
            // write the content into xml file
		TransformerFactory transformerFactory = 
                        TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(xml);
		StreamResult result = new StreamResult(File);
		transformer.transform(source, result);
        }
        catch(SAXException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);      
        }
        catch(IOException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
        catch(ParserConfigurationException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
        catch(TransformerException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
    
    
     /**
     * Checks if travel award is unlocked or locked for player achievement.
     * @return boolean if unlocked then returns true.
     */
    public boolean check_if_wonaward(){
         Document xml = null;
        try{
            File File = new File(AWARDCOUNTS);
            DocumentBuilderFactory dbFactory = 
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            xml = dBuilder.parse(File);
            Node main = xml.getElementsByTagName("Player1").item(0);
            NodeList list = main.getChildNodes();     
            for(int i=0; i<list.getLength(); i++){
                Node node = list.item(i);
                if("game3won".equals(node.getNodeName())){
                    if( "f".equals(node.getTextContent())){
                        System.out.println("won achievement is false");
                        return true;
                    }
                } 
            }
            // write the content into xml file
		TransformerFactory transformerFactory = 
                        TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(xml);
		StreamResult result = new StreamResult(File);
		transformer.transform(source, result);
        }
        catch(SAXException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);    
        }
        catch(IOException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
        catch(ParserConfigurationException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
        catch(TransformerException e){
            Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
    
    
    public String getWon3Achievement(){
          return "Achievement unlocked: **SURVIVOR** - "
                  + " You have won 3 games in a row!!";      
      }
      
    
    public String getWalkingAchievement(){
          return "Achievement unlocked: **TRAVELLER** - "
                  + " You have travelled far!!";
      }
      
    
    public String getSavedKiwiAchievement(){
             return "Achievement unlocked: **HERO** - "
                  + " You have saved allot of kiwis well done!!!";
    }
    
    public boolean get_won3gamesinrow(){
        return this.won3gamesinrow;
    }
    
    public void set_won3gamesinrow(Boolean b){
        this.won3gamesinrow =b;
        
    }
    
    public boolean get_walked(){
        return this.walked;
    }
    
    public void set_walked(Boolean b){
        this.walked = b;
    }
    
    public boolean get_savedkiwis(){
        return this.savedKiwis;
    }
    
    public void set_savedkiwis(Boolean b){
        this.savedKiwis = b;
        
    }
    
    public Boolean get_walkingGUI(){
        return this.walkingGUI;
    }
    
    public void set_walkingGUI(Boolean b){
        this.walkingGUI =b;
    }
    
    public Boolean get_savedGUI(){
        return this.savedGUI;
    }
    
    public void set_savedGUI(Boolean b){
        this.savedGUI = b;
    }
    
    public Boolean get_wonGUI(){
        return this.wonGUI;
    }
    
    public void set_wonGUI(Boolean b){
        this.wonGUI = b;
    }
}
