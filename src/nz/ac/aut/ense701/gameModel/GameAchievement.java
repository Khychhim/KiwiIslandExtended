/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

import java.io.File;
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

/**
 *
 * @author Nishan
 */
public class GameAchievement {
    Element player;
    
    public Document createAchievementXML() throws ParserConfigurationException, TransformerException{
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document AchievementDoc = docBuilder.newDocument();
        // root elements
        
        Element rootElement = AchievementDoc.createElement("Achievements");
        AchievementDoc.appendChild(rootElement);
        player = AchievementDoc.createElement("Player");
        rootElement.appendChild(player);
        // set attribute to staff element
        Attr attr = AchievementDoc.createAttribute("id");
        attr.setValue("1");
        player.setAttributeNode(attr);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(AchievementDoc);
        StreamResult result = new StreamResult(new File("Achievements.xml"));
        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);
        
        transformer.transform(source, result);
        System.out.println("Achievement file saved!");
        return AchievementDoc;

    }
    
    public void setAchievements(Document doc){
        boolean won3games = false;
        if(won3games){
            try{
                
            Element name = doc.createElement("Award");
            name.appendChild(doc.createTextNode("Survivor"));
            player.appendChild(name);
            Element description = doc.createElement("Description");
            description.appendChild(doc.createTextNode("Won three games in a row!"));
            player.appendChild(description);
            
            
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
    
    
    
    
    
    
    
    
}
