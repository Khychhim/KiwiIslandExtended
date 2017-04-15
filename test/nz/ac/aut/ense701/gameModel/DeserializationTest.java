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

    /**
     * Default constructor for test class DeserializationTest
     */
    public DeserializationTest() {

    }

    /*
     * Test Game object is serializable
     */
    @Test
    public void testSavedGameIsLoaded() {
        boolean expResult = true;

        Game game = new Game();
        Deserialization deserialization = new Deserialization();

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

        //CHECK FILE EXISTS
        Game loaded = deserialization.deserialize();

        boolean isloaded;
        isloaded = (loaded != null);
        
        assertEquals(expResult, isloaded);
    }
}
