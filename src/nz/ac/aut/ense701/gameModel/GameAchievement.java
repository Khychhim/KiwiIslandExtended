/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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

public class GameAchievement {
    public Element Achievement;
    public boolean won3gamesinrow;
    public boolean walked;
    public boolean savedKiwis;
    
    //Empty constructor.
    public GameAchievement(){
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
    public Document ReadAchievementXML(){
        Document xml = null;
        try{
         
            File fXmlFile = new File("Achievements.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            xml = dBuilder.parse(fXmlFile);
        }
        catch(SAXException e){
            System.out.println("SAXException error");
                
        }
        catch(IOException e){
            System.out.println("IOException error");
        }
        catch(ParserConfigurationException e){
            System.out.println("Parser Error");
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
         
            File File = new File("AwardCounts.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            xml = dBuilder.parse(File);
            Node main = xml.getElementsByTagName("Player1").item(0);
            NodeList list = main.getChildNodes();
            
            for(int i=0; i<list.getLength(); i++){
                Node node = list.item(i);
                if("gamesWon".equals(node.getNodeName())){
                    System.out.println("True");
                    System.out.println("Lost your chance at winning 3 games in a row.");
                    int gameCounter = 0;
                    node.setTextContent(Integer.toString(gameCounter));
                }
            }
            
            // write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(xml);
		StreamResult result = new StreamResult((File));
		transformer.transform(source, result);

		System.out.println("Done"); 
        }
        catch(SAXException e){
            System.out.println("SAXException error");
                
        }
        catch(IOException e){
            System.out.println("IOException error");
        }
        catch(ParserConfigurationException e){
            System.out.println("Parser Error");
        }
        catch(TransformerException e){
            System.out.println("Transformer Excpetion");
        }
    }
    
    
    /**
     * reads through the awardCounts xml and if user wins a game increments
     * the amount of games the player wins. if won 3 in a row it will assign
     * a new achievement by calling set achievement and give the players achievement.
     */
    public void Won3Games(){
        Document xml = null;
        try{
            //read through the AwardCounts.xml already created.
            File File = new File("AwardCounts.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            xml = dBuilder.parse(File);
            //start from the parent node.
            Node main = xml.getElementsByTagName("Player1").item(0);
            NodeList list = main.getChildNodes();
            //loop through all nodes in the xml dom parser.
            for(int i=0; i<list.getLength(); i++){
                Node node = list.item(i);
               won3gamesinrow = false;
                if("gamesWon".equals(node.getNodeName())){
                    System.out.println("True");
                   
                    //check if number of games won is three.
                    if(node.getTextContent().equals("3")){
                        //user testing check to see if game won.
                        System.out.println("Won 3 games in a row");
                        //if number of games won is 3 set achievement.
                          won3gamesinrow = true;
         
                        GameAchievement game = new GameAchievement();
                        boolean won3games = true;
                        //create object game for achievement and write to 
                        //game achievement class.
               
                        setAchievements(game.ReadAchievementXML(), won3games,
                                false, false);
                        //send a message to achievement xml. 
                    }
                    else{
                        //create counter for counting how many games have been won.
                        int gameCounter;
                         //convert from string to integer then integer to string.
                        gameCounter = Integer.parseInt(node.getTextContent());
                        gameCounter++; //increment number of games won.
                         //set node value to new count value.
                        node.setTextContent(Integer.toString(gameCounter));
                    }
                }
            }

            //write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(xml);
		StreamResult result = new StreamResult((File));
		transformer.transform(source, result);

		System.out.println("Done"); 
        }
        catch(SAXException e){
            System.out.println("SAXException error");
                
        }
        catch(IOException e){
            System.out.println("IOException error");
        }
        catch(ParserConfigurationException e){
            System.out.println("Parser Error");
        }
        catch(TransformerException e){
            System.out.println("Transformer Excpetion");
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
            description.appendChild(doc.createTextNode("Won three games in a row!"));
            achievement.appendChild(description);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("Achievements.xml"));
        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);
            System.out.println("Achievement file saved with survivor award added!");
                
            } catch (TransformerException ex) {
                Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        else if(walked_distance){
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
            description.appendChild(doc.createTextNode("You have walked over 1000 steps!!"));
            achievement.appendChild(description);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("Achievements.xml"));
        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);
            System.out.println("Achievement file saved with survivor award added!");
                
        } catch (TransformerException ex) {
                Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, ex);
        }
            
     }
        else if(kiwi){
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
            description.appendChild(doc.createTextNode("You have saved allot of kiwis!!"));
            achievement.appendChild(description);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("Achievements.xml"));
        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);
            System.out.println("Achievement file saved with survivor award added!");
                
        } catch (TransformerException ex) {
                Logger.getLogger(GameAchievement.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        }
               
    }
    
    
    public void read_kiwiCount(int kiwi){
        Document xml = null;
        try{
            File File = new File("AwardCounts.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            xml = dBuilder.parse(File);
            Node main = xml.getElementsByTagName("Player1").item(0);
            NodeList list = main.getChildNodes();
            
            for(int i=0; i<list.getLength(); i++){
                Node node = list.item(i);
                if("savedKiwis".equals(node.getNodeName())){
                    System.out.println("True");
                    System.out.println("Counting kiwis saved added to the xml..");
                    node.setTextContent(Integer.toString(kiwi));
                    if(kiwi >=1){
                        savedKiwis = true;
                        setAchievements(ReadAchievementXML(), false, false, true);
                    }
                }
                
            }
            
            // write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(xml);
		StreamResult result = new StreamResult((File));
		transformer.transform(source, result);

		System.out.println("Done"); 
        }
        catch(SAXException e){
            System.out.println("SAXException error");
                
        }
        catch(IOException e){
            System.out.println("IOException error");
        }
        catch(ParserConfigurationException e){
            System.out.println("Parser Error");
        }
        catch(TransformerException e){
            System.out.println("Transformer Excpetion");
        }
    }
    
    
    public void write_to_count(int count_of_step){
         Document xml = null;
        try{
         
            File File = new File("AwardCounts.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            xml = dBuilder.parse(File);
            Node main = xml.getElementsByTagName("Player1").item(0);
            NodeList list = main.getChildNodes();
            
            for(int i=0; i<list.getLength(); i++){
                Node node = list.item(i);
                if("squares".equals(node.getNodeName())){
                    System.out.println("True");
                    System.out.println("Counting steps added to the xml..");
                    node.setTextContent(Integer.toString(count_of_step));
                    walked = false;
                    if(count_of_step >=1000){ //if player walks over 1000 squares.
                        walked = true;
                        setAchievements(ReadAchievementXML(),false, true, false);
       
                    }
                }
            }
            
            // write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(xml);
		StreamResult result = new StreamResult((File));
		transformer.transform(source, result);

		System.out.println("Done"); 
        }
        catch(SAXException e){
            System.out.println("SAXException error");
                
        }
        catch(IOException e){
            System.out.println("IOException error");
        }
        catch(ParserConfigurationException e){
            System.out.println("Parser Error");
        }
        catch(TransformerException e){
            System.out.println("Transformer Excpetion");
        }

    }
    
    
    public int readCount(){
        int count_of_steps=0;
         Document xml = null;
        try{
         
            File File = new File("AwardCounts.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            xml = dBuilder.parse(File);
            Node main = xml.getElementsByTagName("Player1").item(0);
            NodeList list = main.getChildNodes();
            
            for(int i=0; i<list.getLength(); i++){
                Node node = list.item(i);
                if("squares".equals(node.getNodeName())){
                    System.out.println("True");
                    System.out.println("Counting steps read");              
                    count_of_steps = Integer.parseInt(node.getTextContent()); //convert number of steps
                }
            }
            
            // write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(xml);
		StreamResult result = new StreamResult((File));
		transformer.transform(source, result);

		System.out.println("Done"); 
        }
        catch(SAXException e){
            System.out.println("SAXException error");
                
        }
        catch(IOException e){
            System.out.println("IOException error");
        }
        catch(ParserConfigurationException e){
            System.out.println("Parser Error");
        }
        catch(TransformerException e){
            System.out.println("Transformer Excpetion");
        }
        return count_of_steps;
        
    }
    
    
    
    
    public String getWon3Achievement(){
          return "Achievement unlocked: **SURVIVOR** - "
                  + " You have won 3 games in a row!!";
          
      }
      
    public String getWalkingAchievement(){
          return "Achievement unlocked: **TRAVELLER** - "
                  + " You have travelled over 1000 steps!!";
      }
      
      
    public String getSavedKiwiAchievement(){
             return "Achievement unlocked: **HERO** - "
                  + " You have saved allot of kiwis well done!!!";
          
      }


}
