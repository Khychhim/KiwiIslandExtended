package nz.ac.aut.ense701.main;

import nz.ac.aut.ense701.gui.MainMenu;

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
    public static void main(String[] args)
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
