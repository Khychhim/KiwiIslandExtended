package nz.ac.aut.ense701.gameModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Deserialization {

    public Deserialization() {
    }

    public Game deserialize() {
        Game game = null;
        try {
            //get current directory location
            String currentDirectory = System.getProperty("java.io.tmpdir");

            //Import & load Game object
            FileInputStream fstream = new FileInputStream("data.dat");
            ObjectInputStream objectStream = new ObjectInputStream(fstream);
            game = (Game) objectStream.readObject();

            //Debug - print of loading path
            System.out.println("Deserialize loaded from: " + currentDirectory + "data.dat");

            //Close FileStream and ObjectOutputStream
            objectStream.close();
            fstream.close();
        } catch (IOException ex) {
            System.out.println("Deserialization: IO Exception.");
        } catch (ClassNotFoundException ex) {
            System.out.println("Deserialization: Class Not Found Exception.");
        }
        return game;
    }

}
