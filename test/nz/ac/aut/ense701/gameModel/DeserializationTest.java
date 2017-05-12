package nz.ac.aut.ense701.gameModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import static junit.framework.TestCase.assertEquals;
import org.junit.Test;

public class DeserializationTest {

    private Game game;
    private Game loadGame;

    /**
     * Default constructor for test class DeserializationTest
     */
    public DeserializationTest() {
    }

    /**
     * Setup a save data file and ready for testing
     */
    public void setup() {
        Game game = new Game();

        //CREATE SAVE DATA
        try {
            if (game.getPlayer().isAlive()) {
                FileOutputStream fstream = new FileOutputStream("data.dat");
                ObjectOutputStream objSteram = new ObjectOutputStream(fstream);
                objSteram.writeObject(game);

                //CLOSE OBJECTSTREAM AND FILEOUTPUTSTREAM
                objSteram.close();
                fstream.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(SerializationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testGameIsLoaded() {
        boolean expResult = true;
        boolean isLoaded = false;
        Deserialization load = new Deserialization();

        loadGame = load.Load();

        if (loadGame != null) {
            isLoaded = true;
        }

        assertEquals(expResult, isLoaded);
    }
}
