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

public class ScoreTest extends junit.framework.TestCase {
    private Score score;
    
    public ScoreTest() {
    }


    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Override
    protected void setUp() {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @Override
    protected void tearDown() {
        score = null;
    }
    
    @Test
    public void testCanAddScore() {
        score = new Score(); //Initialize score to 0
        score.addScore(5);
        assertTrue("Score should be 5", score.getScore() == 5);
    }
    
    @Test
    public void testCanSubtractScore() {
        score = new Score(10); //Initialize score to 10
        score.subtractScore(5);
        assertTrue("Score should be 5", score.getScore() == 5);
    }
    
    @Test
    public void testCanSetScore() {
        score = new Score(); //Initialize score to 5
        score.setScore(15);
        assertTrue("Score should be 15", score.getScore() == 15);
    }
        
}
