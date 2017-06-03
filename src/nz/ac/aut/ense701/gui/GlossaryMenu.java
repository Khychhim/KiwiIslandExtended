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
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
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
public class GlossaryMenu extends SubMenu implements ComponentListener {
    private final AnimalIcon[] animals;
    private final Image[] glossaryImages;
    private final JLabel infoBox;
    private final JLabel selected;
    private final JLabel selectedAnimal;
    private final JTextArea animalInfo;
    private final JScrollPane infoScrollPane;
    private int selectedIndex;
    
    private static final int NUMBER_OF_IMAGES = 15;
    private static final int NUMBER_OF_ANIMALS = 12;
    private static final int ANIMAL_START_INDEX = 3;
    
    private static final int HIDDEN     = 0;
    private static final int SELECTION  = 1;
    private static final int INFO_BOX   = 2;
    private static final int CAT        = 3; //Animal start Index
    private static final int KIORE      = 4;
    private static final int POSSUM     = 5;
    private static final int RAT        = 6;
    private static final int STOAT      = 7;
    private static final int BROWN_KIWI = 8;
    private static final int SPOT_KIWI  = 9;
    private static final int FERNBIRD   = 10;
    private static final int OYSTER_CTCR= 11;
    private static final int HERON      = 12;
    private static final int TUI        = 13;
    private static final int ROBIN      = 14;
    
    //Percentages of the gui used for displaying the animals icons
    private static final float ICON_AREA_START_WIDTH_RATIO  = 0.10f;
    private static final float ICON_AREA_FINISH_WIDTH_RATIO = 0.50f;
    private static final float ICON_AREA_START_HIEGHT_RATIO = 0.10f;
    //Number of animal icons that can be displayed in a single row
    private static final int ICONS_PER_ROW = 4;
    //Percentages of the gui used for displaying the Info Box
    private static final float INFO_BOX_WIDTH_RATIO  = 0.35f;
    private static final float INFO_BOX_HEIGHT_RATIO = 0.68f;
    private static final float INFO_BOX_X_POS_RATIO  = 0.55f;
    private static final float INFO_BOX_Y_POS_RATIO  = 0.15f;
    //Height ratio for the animal picture in the info box
    private static final float SELECTED_ANIMAL_HEIGHT_RATIO = 0.45f;
    //Percentages of the gui used for the info boxs text area
    private static final float TEXT_AREA_WIDTH_RATIO  = 0.33f;
    private static final float TEXT_AREA_HEIGHT_RATIO = 0.215f;
    private static final float TEXT_AREA_X_POS_RATIO  = 0.56f;
    private static final float TEXT_AREA_Y_POS_RATIO  = 0.60f;
    
    private static final Logger LOG = Logger.getLogger(GlossaryMenu.class.getName());
    
    public GlossaryMenu() {
        super("Kiwi Island - Glossary");
        animals = new AnimalIcon[NUMBER_OF_ANIMALS];
        glossaryImages = new Image[NUMBER_OF_IMAGES];
        infoBox = new JLabel();
        selected = new JLabel();
        selectedAnimal = new JLabel();
        animalInfo = new JTextArea();
        animalInfo.setEditable(false);
        animalInfo.setBackground(new Color(154, 165, 0));
        animalInfo.setLineWrap(true);
        animalInfo.setWrapStyleWord(true);
        infoScrollPane = new JScrollPane(animalInfo);
        infoScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        infoScrollPane.setBackground(new Color(154, 165, 0));
        loadImages();
        init();
        readingGlossary();
    }
    
    private void init() {
        menuPane.setLayer(infoBox, 2);
        menuPane.add(infoBox);
        menuPane.setLayer(selected, 2);
        menuPane.add(selected);
        menuPane.setLayer(selectedAnimal, 1);
        menuPane.add(selectedAnimal);
        menuPane.setLayer(infoScrollPane, 3);
        menuPane.add(infoScrollPane);
        selectedIndex = -1;
        
        for(int i = 0; i < NUMBER_OF_ANIMALS; i++) {
            animals[i] = new AnimalIcon(i+ANIMAL_START_INDEX);
            menuPane.setLayer(animals[i].animal, 1);
            menuPane.add(animals[i].animal);
        }
        menu.addComponentListener(this);
        menu.pack();
        menu.validate();
    }
    
    private void loadImages() {
        try {
            glossaryImages[HIDDEN] = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/glossaryImages/Hidden.png"));
            glossaryImages[SELECTION] = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/glossaryImages/Selected.png"));
            glossaryImages[INFO_BOX] = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/glossaryImages/InfoBox.png"));
            glossaryImages[CAT] = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/glossaryImages/cat.png"));
            glossaryImages[KIORE] = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/glossaryImages/kiore.png"));
            glossaryImages[POSSUM] = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/glossaryImages/possum.png"));
            glossaryImages[RAT] = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/glossaryImages/rat.png"));
            glossaryImages[STOAT] = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/glossaryImages/stoat.png"));
            glossaryImages[BROWN_KIWI] = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/glossaryImages/brown-kiwi.png"));
            glossaryImages[SPOT_KIWI] = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/glossaryImages/little-spotted-kiwi.png"));
            glossaryImages[TUI] = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/glossaryImages/tui.png"));
            glossaryImages[HERON] = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/glossaryImages/white-heron.png"));
            glossaryImages[FERNBIRD] = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/glossaryImages/stewart-island-fernbird.png"));
            glossaryImages[OYSTER_CTCR] = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/glossaryImages/oyster-catcher.png"));
            glossaryImages[ROBIN] = ImageIO.read(this.getClass().getResource("/nz/ac/aut/ense701/glossaryImages/black-robin.png"));
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Failed to load image {0}", e.getMessage());
        }
    }
    
    private void readingGlossary() {
        try {
            File file = new File("glossary.xml");
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();

            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("Creature");
            for(int i = 0; i < list.getLength(); i++) {
                Node creature = list.item(i);

                if(creature.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) creature;
                    int id = Integer.parseInt(element.getAttribute("id"));
                    animals[id].description = element.getElementsByTagName("Description").item(0).getTextContent();
                    animals[id].visible = Boolean.parseBoolean(element.getAttribute("Seen"));
                    animals[id].name = element.getElementsByTagName("Name").item(0).getTextContent();
                }
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Failed to read document {0}", e.getMessage());
        } catch (NumberFormatException e) {
            LOG.log(Level.SEVERE, "error parsing id {0}", e.getMessage());
        } catch (ParserConfigurationException e) {
            LOG.log(Level.SEVERE, "ParserConfigException {0}", e.getMessage());
        } catch (SAXException e) {
            LOG.log(Level.SEVERE, "SaxException  {0}", e.getMessage());
        }
    }
    
    @Override
    protected void resizeWindow(ComponentEvent e) {
        super.resizeWindow(e);
        int width = e.getComponent().getWidth();
        int height = e.getComponent().getHeight();
        int x = (int) (width*INFO_BOX_X_POS_RATIO);
        int y = (int) (height*INFO_BOX_Y_POS_RATIO);
        int xText = (int) (width*TEXT_AREA_X_POS_RATIO);
        int yText = (int) (height*TEXT_AREA_Y_POS_RATIO);
        int infoBoxWidth = (int) (width*INFO_BOX_WIDTH_RATIO);
        int infoBoxHeight = (int) (height*INFO_BOX_HEIGHT_RATIO);
        int textWidth = (int) (width*TEXT_AREA_WIDTH_RATIO);
        int textHeight = (int) (height*TEXT_AREA_HEIGHT_RATIO);
        int selectedAnimalHeight = (int) (height*SELECTED_ANIMAL_HEIGHT_RATIO);
        infoBox.setIcon(new ImageIcon(scaleImage(glossaryImages[INFO_BOX], infoBoxWidth, infoBoxHeight)));
        infoBox.setPreferredSize(new Dimension(infoBoxWidth, infoBoxHeight));
        infoBox.setBounds(x, y, infoBoxWidth, infoBoxHeight);
        infoScrollPane.setPreferredSize(new Dimension(textWidth, textHeight));
        infoScrollPane.setBounds(xText, yText, textWidth, textHeight);
        if(selectedIndex >= 0 && selectedIndex < NUMBER_OF_ANIMALS) {
            selectedAnimal.setIcon(new ImageIcon(scaleImage(
                    glossaryImages[selectedIndex+ANIMAL_START_INDEX], infoBoxWidth, selectedAnimalHeight)));
            selectedAnimal.setPreferredSize(new Dimension(infoBoxWidth, selectedAnimalHeight));
            selectedAnimal.setBounds(x, y, infoBoxWidth, selectedAnimalHeight);
        }
        
        for(int i = 0; i < NUMBER_OF_ANIMALS; i++) {
            animals[i].resizeIcon(width, height, selectedIndex == i);
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        //Not used
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        //Not used
    }

    @Override
    public void componentShown(ComponentEvent e) {
        readingGlossary();
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        //Not used
    }
    
    public class AnimalIcon {
        private JLabel animal;
        private boolean visible;
        private final int animalIndex;
        private String name;
        private String description;
        
        public AnimalIcon(int animalIndex) {
            animal = new JLabel();
            name = "Name not loaded";
            description = "Description not loaded";
            visible = true;
            this.animalIndex = animalIndex;
        }
        
        //Resizes and positions the icon
        private void resizeIcon(int winWidth, int winHeight, boolean isSelected) {
            int iconWidth = (int) (winWidth*
                    (ICON_AREA_FINISH_WIDTH_RATIO-ICON_AREA_START_WIDTH_RATIO)/
                    (ICONS_PER_ROW+1));
            int iconHeight = iconWidth;
            int x = 0;
            int y = 0;
            int index = animalIndex;
            //Set the icons position based on the icon number and the number of icons per row
            do {
                x = (int) ((winWidth*ICON_AREA_START_WIDTH_RATIO)+
                        (iconWidth*(index-ANIMAL_START_INDEX)+(iconWidth*(index-ANIMAL_START_INDEX)/(double)ICONS_PER_ROW)));
                y += (int) ((winHeight*ICON_AREA_START_HIEGHT_RATIO)+(iconWidth*2/(double)ICONS_PER_ROW));
                index -= ICONS_PER_ROW;
            } while(index >= ICONS_PER_ROW-1);
            int imageIndex;
            if(visible) imageIndex = animalIndex;
            else imageIndex = HIDDEN;
            //Sets the icons size and scales the image
            animal.setIcon(new ImageIcon(scaleImage(glossaryImages[imageIndex], iconWidth, iconHeight)));
            animal.setPreferredSize(new Dimension(iconWidth, iconHeight));
            animal.setBounds(x, y, iconWidth, iconHeight);
            if(isSelected) {
                selected.setIcon(new ImageIcon(scaleImage(glossaryImages[SELECTION], iconWidth, iconHeight)));
                selected.setPreferredSize(new Dimension(iconWidth, iconHeight));
                selected.setBounds(x, y, iconWidth, iconHeight);
            }
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        Point clickLoc = e.getPoint();
        //Checks if the click location was within a visible animal icon
        //Then calls resizeIcon to move the selection icon to that icon
        for(int i = 0; i < NUMBER_OF_ANIMALS; i++) {
            if(animals[i].animal.getBounds().contains(clickLoc) && animals[i].visible) {
                selectedIndex = i;
                int width = menu.getWidth();
                int height = menu.getHeight();
                animals[i].resizeIcon(width, height, true);

                int x = (int) (width*INFO_BOX_X_POS_RATIO);
                int y = (int) (height*INFO_BOX_Y_POS_RATIO);
                int selectedAnimalWidth = (int) (width*INFO_BOX_WIDTH_RATIO);
                int selectedAnimalHeight = (int) (height*SELECTED_ANIMAL_HEIGHT_RATIO);
                selectedAnimal.setIcon(new ImageIcon(scaleImage(
                        glossaryImages[selectedIndex+ANIMAL_START_INDEX], selectedAnimalWidth, selectedAnimalHeight)));
                selectedAnimal.setPreferredSize(new Dimension(selectedAnimalWidth, selectedAnimalHeight));
                selectedAnimal.setBounds(x, y, selectedAnimalWidth, selectedAnimalHeight);
                animalInfo.setText(animals[i].name + "\n" + animals[i].description);
            }
        }
    }
    
    public Image[] getGlossaryImages() {
        return glossaryImages;
    }
    
    public AnimalIcon[] getAnimals() {
        return animals;
    }
}
