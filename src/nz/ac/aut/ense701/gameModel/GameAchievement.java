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
import org.xml.sax.SAXException;

/**
 *
 * @author Nishan
 */
public class GameAchievement {
    Element Achievement;
    
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
    
    public void setAchievements(Document doc){
        boolean won3games = true;
        if(won3games){
            try{
                
            Element root = doc.getDocumentElement();
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
        
        
    }
    
    
    
    
    
    
    
    
}
