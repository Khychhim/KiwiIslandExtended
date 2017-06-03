package nz.ac.aut.ense701.gameModel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Deserialization {

    private String FILE_NAME = "Save_Data.dat";
    private Game game;

    public static void main(String[] args) {
        Deserialization loadObject = new Deserialization();

    }

    public Deserialization() {
//        createAWindow();
    }

    /**
     * This method create a window with a button of Save
     */
    public void createAWindow() {
        JFrame frame = new JFrame("Load Game");

        //create panel
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        //create button
        JButton button = new JButton("Load Game");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                Load();
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
     * This method check if the save file is exist
     * @return 
     */
    public boolean isSaveExist() {
        boolean isExist = false;

        File file = new File(FILE_NAME);
        if (file.exists()) {
            isExist = true;
        }
        return isExist;
    }

    /**
     * This method load game data(FILE_NAME)
     *
     * @return Game
     */
    public Game Load() {
        Game game = null;
        try {
            //get current directory location
            String currentDirectory = System.getProperty("user.dir");

            //Import & load Game object
            FileInputStream fstream = new FileInputStream(FILE_NAME);
            ObjectInputStream objectStream = new ObjectInputStream(fstream);
            game = (Game) objectStream.readObject();

            //Debug - print of loading path
            System.out.println("Game loaded from: " + currentDirectory + "\\" + FILE_NAME);

            //Close FileStream and ObjectOutputStream
            objectStream.close();
            fstream.close();
        } catch (IOException ex) {
            System.out.println("Deserialization: IO Exception.");
            System.out.println(ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("Deserialization: Class Not Found Exception.");
        }
        return game;
    }

}
