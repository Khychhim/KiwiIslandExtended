package nz.ac.aut.ense701.gameModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import static junit.framework.TestCase.assertEquals;
import org.junit.Test;

/**
 * The test class SearilzationTest..
 *
 * @author SeanChang
 */
public class SerializationTest {

    /**
     * Default constructor for test class SerializationTest
     */
    public SerializationTest() {

    }

    /*
     * Test Game object is serializable
     */
    @Test
    public void test_GameIsSerializable() {
        boolean expResult = true;
        boolean isSerialbizable;
        try {
            new ObjectOutputStream(new ByteArrayOutputStream()).writeObject(new Game());
            isSerialbizable = true;
        } catch (IOException ex) {
            Logger.getLogger(Serialization.class.getName()).log(Level.SEVERE, null, ex);
            isSerialbizable = false;
        }
        assertEquals(expResult, isSerialbizable);
    }

    /*
     * Test save file is created
     */
    @Test
    public void test_SaveFileIsCreated() {

        boolean expResult = true;
        boolean isFileCreated = false;

        //CREATE SAVE DATA
        Game game = new Game();
        Serialization serialization = new Serialization(game);

        //CHECK FILE EXISTS
        File file = new File("data.dat");
        if (file.exists() && !file.isDirectory()) {
            isFileCreated = true;
        }

        assertEquals(expResult, isFileCreated);
    }

    /*
     * Test save file named "data.dat"
     */
    @Test
    public void testSaveFileIsName() {
        boolean expResult = true;
        boolean isFileNameSame = false;
        String fileName = "data.dat";

        //CREATE SAVE DATA
        Game game = new Game();
        Serialization serialization = new Serialization(new Game());

        //CHECK FILE named "data.dat" exists
        File file = new File(fileName);
        if (file.exists() && !file.isDirectory()) {
            isFileNameSame = true;
        }

        assertEquals(expResult, isFileNameSame);
    }

    /*
     * Test Island information -  Save row
     */
    @Test
    public void test_IslandRowsIsSaved() {
        int expResult;
        int loadedResult;

        //CREATE SAVE DATA
        Game game = new Game();
        Serialization serialization = new Serialization(new Game());

        //Load saved game
        Deserialization des = new Deserialization();
        Game loadedgame = des.deserialize();

        expResult = game.getIsland().getNumRows();
        loadedResult = loadedgame.getIsland().getNumRows();

        assertEquals(expResult, loadedResult);
    }

    /*
     * Test Island information -  Saved column
     */
    @Test
    public void test_IslandColumnIsSaved() {
        int expResult;
        int loadedResult;

        //CREATE SAVE DATA
        Game game = new Game();
        Serialization serialization = new Serialization(new Game());

        //Load saved game
        Deserialization des = new Deserialization();
        Game loadedgame = des.deserialize();

        expResult = game.getIsland().getNumColumns();
        loadedResult = loadedgame.getIsland().getNumColumns();

        assertEquals(expResult, loadedResult);
    }
}
