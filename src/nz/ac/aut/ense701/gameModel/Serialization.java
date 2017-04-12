package nz.ac.aut.ense701.gameModel;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * This is the class for save game purpose This class will save every
 * information in the game
 *
 * @author Sean
 */
public class Serialization {

    Serialization(Game game) {
        try {
            //get current directory location
            String currentDirectory = System.clearProperty("user.dir");
            
            //export & saving Game object
            FileOutputStream fostream = new FileOutputStream(currentDirectory + "/temp/data.dat");
            ObjectOutputStream iostream = new ObjectOutputStream(fostream);
            iostream.writeObject(game);
            
            //debug - output save successfully 
            System.out.println("Serialzed data is saved: " + currentDirectory + "/temp/data.dat");

            //close FileStream and ObjectOutputStream
            iostream.close();
            fostream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
