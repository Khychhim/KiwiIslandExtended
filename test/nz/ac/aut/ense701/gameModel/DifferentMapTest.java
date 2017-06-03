package nz.ac.aut.ense701.gameModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.Test;

/**
 * The test class DifferentMap.
 *
 * @author  Josh
 * @version 2017
 */

public class DifferentMapTest extends junit.framework.TestCase {
    MapDataTypes mapDataTypes;
    DifferentMap differentMap;
    
    public DifferentMapTest() {
    }


    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Override
    protected void setUp() {
        mapDataTypes = new MapDataTypes();
        differentMap = new DifferentMap(GameDifficulty.EASY, "River Song");
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @Override
    protected void tearDown() {
        mapDataTypes = null;
    }
    
    @Test
    public void testDidMapDataTypesLoad() {
        mapDataTypes.loadTypesFromFile();
        //None of them should be null or empty
        assertFalse("Fauna should not be null", mapDataTypes.faunaTypes == null);
        assertFalse("Fauna should not be empty", mapDataTypes.faunaTypes.isEmpty());
        assertFalse("Food should not be null", mapDataTypes.foodTypes == null);
        assertFalse("Food should not be empty", mapDataTypes.foodTypes.isEmpty());
        assertFalse("Hazards should not be null", mapDataTypes.hazardTypes == null);
        assertFalse("Hazards should not be empty", mapDataTypes.hazardTypes.isEmpty());
        assertFalse("Kiwi should not be null", mapDataTypes.kiwiTypes == null);
        assertFalse("Kiwi should not be empty", mapDataTypes.kiwiTypes.isEmpty());
        assertFalse("Tiles should not be null", mapDataTypes.tileTypes == null);
        assertFalse("Tiles should not be empty", mapDataTypes.tileTypes.isEmpty());
        assertFalse("Tool should not be null", mapDataTypes.toolTypes == null);
        assertFalse("Tool should not be empty", mapDataTypes.toolTypes.isEmpty());
        assertFalse("Trigger should not be empty", mapDataTypes.triggerTypes.isEmpty());
    }
    
    @Test
    public void testDidPrintWriterAccessFile() {
        PrintWriter pw = differentMap.createFile();
        assertFalse("Printwriter should not be null", pw == null);
        assertFalse("The printwriter should not have an error", pw.checkError());
        pw.close();
    }
    
    @Test
    public void testWasMapCreated() {
        BufferedReader br = null;
        try {
            differentMap.generateMap();
            File file = new File("IslandData.txt");
            assertTrue("Map file should exist under the name IslandData.txt", file.exists());
            
            br = new BufferedReader(new FileReader("IslandData.txt"));
            boolean fileIsEmpty = false;
            if(br.readLine() == null) fileIsEmpty = true;
            
            assertTrue("Map file should not be empty", !fileIsEmpty);
        } catch (IOException ex) {
            assertTrue("Error " + ex, false);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                assertTrue("Error " + ex, false);
            }
        }
    }
        
}
