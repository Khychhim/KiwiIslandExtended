package nz.ac.aut.ense701.gameModel;

import nz.ac.aut.ense701.gui.GlossaryMenu;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

/**
 * The test class GlossaryMenu.
 *
 * @author  Josh
 * @version 2017
 */
public class GlossaryTest extends junit.framework.TestCase {
    GlossaryMenu glossary;
    
    public GlossaryTest() {}

    @Override
    protected void setUp() {
        glossary = new GlossaryMenu();
    }

    @Override
    protected void tearDown() {
        glossary = null;
    }

    @Test
    public void testImageLoad() {
        //Image should not be null
        assertNotEquals(glossary.getGlossaryImages()[0], null);
    }
    
    @Test
    public void testAnimalsLoaded() {
        //Name should be loaded
        assertNotEquals(glossary.getAnimals()[0].name, "Name not loaded");
    }
}
