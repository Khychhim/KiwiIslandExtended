package nz.ac.aut.ense701.gameModel;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Deserialization {

    public Deserialization() {
    }

    public Game deserialize() {
        Game game = null;
        try {
            //get current directory location
            String currentDirectory = System.clearProperty("user.dir");

            //Import & load Game object
            FileInputStream fstream = new FileInputStream(currentDirectory + "/temp/data.dat");
            ObjectInputStream objectStream = new ObjectInputStream(fstream);
            game = (Game) objectStream.readObject();

            //Close FileStream and ObjectOutputStream
            objectStream.close();
            fstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return game;
    }

}
