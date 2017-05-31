package nz.ac.aut.ense701.gameModel;

import nz.ac.aut.ense701.gui.KiwiCountUI;
import nz.ac.aut.ense701.gui.MiniGameGuess;
import org.junit.Test;

/**
 *
 * @author Logan
 */
public class MiniGameGuessTest extends junit.framework.TestCase {

    @Test
    public void testImageIsLoaded() {
        Game game = new Game();
        KiwiCountUI gui = new KiwiCountUI(game);
        MiniGameGuess guessGame = new MiniGameGuess(gui, game);

        //loadImage
        guessGame.loadImage();

        assertTrue(guessGame.getImageHashMap() != null);
    }

    @Test
    public void testImageIsNotLoaded() {
        Game game = new Game();
        KiwiCountUI gui = new KiwiCountUI(game);
        MiniGameGuess guessGame = new MiniGameGuess(gui, game);

        assertFalse(guessGame.getImageHashMap() != null);
    }

}
