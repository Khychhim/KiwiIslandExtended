package nz.ac.aut.ense701.gameModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    public void isSerializable() {
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
}
