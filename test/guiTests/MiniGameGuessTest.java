package guiTests;

import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.GameDifficulty;
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
        Game game = new Game(GameDifficulty.EASY,"test");
        KiwiCountUI gui = new KiwiCountUI(game);
        MiniGameGuess guessGame = new MiniGameGuess(gui, game);

        //loadImage
        guessGame.loadImage();
        
        assertTrue(guessGame.getImageHashMap() != null);
    }

}
