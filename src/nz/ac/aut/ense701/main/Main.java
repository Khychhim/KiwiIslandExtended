package nz.ac.aut.ense701.main;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gui.KiwiCountUI;

/**
 * Kiwi Count Project
 * 
 * @author AS
 * @version 2011
 */
public class Main 
{
    /**
     * Main method of Kiwi Count.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParserConfigurationException, TransformerException 
    {
        //Create the main menu
        final MainMenu mainMenu  = new MainMenu();
        //make the Main menu visible
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                mainMenu.setVisible(true);
            }
        });
    }

}
