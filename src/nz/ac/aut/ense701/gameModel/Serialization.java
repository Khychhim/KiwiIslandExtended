package nz.ac.aut.ense701.gameModel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This is the class for save game purpose This class will save every
 * information in the game
 *
 * @author Sean
 */
public class Serialization {

    private String FILE_NAME = "Save_Data.dat";
    private Game game;

    public static void main(String[] args) {
        Game game = new Game();
        Serialization saveObject = new Serialization(game);
    }

    public Serialization(Game game) {
        this.game = game;
        createAWindow();
    }

    /**
     * This method create a window with a button of Save
     */
    public void createAWindow() {
        JFrame frame = new JFrame("Save Game");

        //create panel
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        //create button
        JButton button = new JButton("Save Game");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                Save();
            }
        });

        panel.add(button);

        //setting for frame
        frame.add(panel);
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    /**
     * This method execute save game data as file .dat
     */
    public void Save() {
        try {
            //get current directory location
            String currentDirectory = System.getProperty("user.dir");

            //export & saving Game object
            FileOutputStream fostream = new FileOutputStream(FILE_NAME);
            ObjectOutputStream iostream = new ObjectOutputStream(fostream);
            iostream.writeObject(game);

            //debug - output save successfully 
            System.out.println("Data is saved: " + currentDirectory + "\\" + FILE_NAME);

            //close FileStream and ObjectOutputStream
            iostream.close();
            fostream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
