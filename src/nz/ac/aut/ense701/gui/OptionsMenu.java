/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
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
import org.xml.sax.SAXException;

/**
 *
 * @author joshl
 */
public class OptionsMenu extends SubMenu {
    private final JTextField nameInput;
    private final JTextArea helpText;
    private final JScrollPane helpScrollPane;
    private final JLabel nameLabel;
    private final JLabel titleLabel;
    private Image titleImage;
    
    //Percentages of the screen used by the title
    private static final float TITLE_START_WIDTH_RATIO   = 0.25f;
    private static final float TITLE_END_WIDTH_RATIO     = 0.65f;
    private static final float TITLE_START_HEIGHT_RATIO  = 0.07f;
    private static final float TITLE_HEIGHT_RATIO        = 0.20f;
    //Percentages of the screen used by the name input
    private static final float NAME_INPUT_START_WIDTH_RATIO   = 0.10f;
    private static final float NAME_INPUT_END_WIDTH_RATIO     = 0.60f;
    private static final float NAME_INPUT_START_HEIGHT_RATIO  = 0.30f;
    private static final float NAME_INPUT_HEIGHT_RATIO        = 0.03f;
    //Percentages of the screen used by the help text
    private static final float HELP_START_WIDTH_RATIO   = 0.50f;
    private static final float HELP_END_WIDTH_RATIO     = 0.65f;
    private static final float HELP_START_HEIGHT_RATIO  = 0.25f;
    private static final float HELP_HEIGHT_RATIO        = 0.60f;
    
    public OptionsMenu() {
        super("Kiwi Island - Options");
        nameInput = new JTextField();
        nameInput.setBackground(new Color(154, 165, 0));
        helpText = new JTextArea();
        helpText.setEditable(false);
        helpText.setBackground(new Color(154, 165, 0));
        helpText.setLineWrap(true);
        helpText.setWrapStyleWord(true);
        helpScrollPane = new JScrollPane(helpText);
        helpScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        helpScrollPane.setBackground(new Color(154, 165, 0));
        nameLabel = new JLabel("User name:");
        nameLabel.setForeground(Color.WHITE);
        titleLabel = new JLabel();
        loadImages();
        init();
        loadOptions();
    }
    
    private void init() {
        menuPane.setLayer(nameInput, 1);
        menuPane.add(nameInput);
        menuPane.setLayer(nameLabel, 1);
        menuPane.add(nameLabel);
        menuPane.setLayer(titleLabel, 1);
        menuPane.add(titleLabel);
        menuPane.setLayer(helpScrollPane, 1);
        menuPane.add(helpScrollPane);
        
        menu.pack();
        menu.validate();
    }
    
    private void loadImages() {
        try {
            titleImage = ImageIO.read(this.getClass().
                    getResource("/nz/ac/aut/ense701/guiImages/Options.png"));
        } catch (IOException e) {
            System.err.println("Unable to load Image. " + e.getMessage());
        }
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
        
        int xName = (int) (width*NAME_INPUT_START_WIDTH_RATIO);
        int widthName = (int) (width*NAME_INPUT_END_WIDTH_RATIO) - xTitle;
        int heightName = (int) (height*NAME_INPUT_HEIGHT_RATIO);
        int yName = (int) (height*NAME_INPUT_START_HEIGHT_RATIO);
        
        nameInput.setPreferredSize(new Dimension(widthName, heightName));
        nameInput.setBounds(xName, yName, widthName, heightName);
        nameLabel.setPreferredSize(new Dimension(widthName, heightName));
        nameLabel.setBounds(xName, yName-heightName, widthName, heightName);
        
        int xHelp = (int) (width*HELP_START_WIDTH_RATIO);
        int widthHelp = (int) (width*HELP_END_WIDTH_RATIO) - xTitle;
        int heightHelp = (int) (height*HELP_HEIGHT_RATIO);
        int yHelp = (int) (height*HELP_START_HEIGHT_RATIO);
        
        helpScrollPane.setPreferredSize(new Dimension(widthHelp, heightHelp));
        helpScrollPane.setBounds(xHelp, yHelp, widthHelp, heightHelp);
    }
    
    public String getUserName() {
        return nameInput.getText();
    }
    
    private void loadOptions() {
        try {
            File file = new File("options.xml");
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();

            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            Element name = (Element) doc.getElementsByTagName("Name").item(0);
            nameInput.setText(name.getTextContent());
            Element help = (Element) doc.getElementsByTagName("Help").item(0);
            helpText.setText(help.getTextContent());
        } catch (SAXException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (ParserConfigurationException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public void saveOptions() {
        try {
            File file = new File("options.xml");
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();

            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            Element userName = (Element) doc.getElementsByTagName("Name").item(0);
            userName.setTextContent(nameInput.getText());
                
            TransformerFactory transformerFactory = 
                TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = 
                    new StreamResult(new File("options.xml"));
            transformer.transform(source, result);
        } catch (IOException e) {
            System.err.println("IO error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("NumberFormatException error: " + e.getMessage());
        } catch (ParserConfigurationException e) {
            System.err.println("Parser error: " + e.getMessage());
        } catch (SAXException e) {
            System.err.println("SAXException error: " + e.getMessage());
        } catch (TransformerException e) {
            System.err.println("Transformer error: " + e.getMessage());
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        Point clickLoc = e.getPoint();
        if(getBackButton().getBounds().contains(clickLoc)) {
            saveOptions();
        }
    }
}
