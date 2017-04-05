package nz.ac.aut.ense701.gameModel;

import org.junit.Test;

/**
 * The test class DifferentMap.
 *
 * @author  Josh
 * @version 2017
 */

public class DifferentMapTest extends junit.framework.TestCase {
    MapDataTypes mapDataTypes;
    
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
    public void testDidMapDataTypesLoad(){
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
    }
}
