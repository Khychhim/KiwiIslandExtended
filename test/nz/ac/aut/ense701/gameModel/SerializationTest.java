package nz.ac.aut.ense701.gameModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
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

    private Game game;
    private Game loadedGame;

    /**
     * Setup a load a Save_Data.dat file
     */
    public void Setup_load() {
        try {
            //Import & load Game object
            FileInputStream fstream = new FileInputStream("Save_Data.dat");
            ObjectInputStream objectStream = new ObjectInputStream(fstream);
            loadedGame = (Game) objectStream.readObject();

            //Close FileStream and ObjectOutputStream
            objectStream.close();
            fstream.close();
        } catch (IOException ex) {
            System.out.println("Deserialization: IO Exception.");
        } catch (ClassNotFoundException ex) {
            System.out.println("Deserialization: Class Not Found Exception.");
        }
    }


    /*
     * Test Game object is serializable
     */
    @Test
    public void test_GameIsSerializable() {
        boolean expResult = true;
        boolean isSerialbizable = false;
        try {
            Game game = new Game();
            new ObjectOutputStream(new ByteArrayOutputStream()).writeObject(game);
            isSerialbizable = true;
        } catch (IOException ex) {
            Logger.getLogger(Serialization.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(expResult, isSerialbizable);
    }

    @Test
    public void testGameIsSaved() {
        boolean expResult = true;
        boolean isSaved = false;

        //Save game
        game = new Game();
        Serialization ser = new Serialization(game);
        ser.Save();

        Setup_load();

        if (loadedGame != null) {
            isSaved = true;
        }

        assertEquals(expResult, isSaved);
    }

    @Test
    public void testHasSameNumberOfRow() {
        boolean expResult = true;
        boolean result = false;

        //Save game
        game = new Game();
        Serialization ser = new Serialization(game);
        ser.Save();

        Setup_load();

        int row = game.getIsland().getNumRows();
        int loadedRow = loadedGame.getIsland().getNumRows();

        if (row == loadedRow) {
            result = true;
        }

        assertEquals(expResult, result);
    }

    @Test
    public void testHasSameNumberOfColumn() {
        boolean expResult = true;
        boolean result = false;

        //Save game
        game = new Game();
        Serialization ser = new Serialization(game);
        ser.Save();

        Setup_load();

        //get columns
        int column = game.getIsland().getNumColumns();
        int loadedColumn = loadedGame.getIsland().getNumColumns();

        if (column == loadedColumn) {
            result = true;
        }
        assertEquals(expResult, result);
    }

    @Test
    public void testGameHasSameTerrains() {
        boolean expResult = true;
        boolean isSame = true;

        //Save game
        game = new Game();
        Serialization ser = new Serialization(game);
        ser.Save();

        Setup_load();

        int col = game.getIsland().getNumColumns();
        int row = game.getIsland().getNumRows();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Terrain terrain = game.getTerrain(i, j);
                Terrain loadedTerrain = loadedGame.getTerrain(i, j);
                int result = terrain.compareTo(loadedTerrain);
                if (result != 0) {//if not equal
                    isSame = false;
                }
            }
        }
        assertEquals(expResult, isSame);
    }

    @Test
    public void testGameHasSameOccupants() {
        boolean expResult = true;
        boolean isSame = true;

        //Save game
        game = new Game();
        Serialization ser = new Serialization(game);
        ser.Save();

        Setup_load();

        //get Occupants
        int col = game.getIsland().getNumColumns();
        int row = game.getIsland().getNumRows();
        Island island = game.getIsland();
        Island loadedIsland = loadedGame.getIsland();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {

                Occupant[] occupants = island.getOccupants(new Position(island, i, j));
                Occupant[] loadedOccupants = island.getOccupants(new Position(loadedIsland, i, j));

                //compare Occupants
                for (int k = 0; k < occupants.length; k++) {
                    boolean result = occupants[k].equals(loadedOccupants[k]);

                    if (!result) {//if not equal
                        isSame = false;
                    }
                }
            }
        }
        assertEquals(expResult, isSame);
    }

    @Test
    public void testGameHasSamePlayerInventory() {
        boolean expResult = true;
        boolean isSame = false;

        //Save game
        game = new Game();
        Serialization ser = new Serialization(game);
        ser.Save();

        Setup_load();

        //compare player
        if (Arrays.equals(game.getPlayerInventory(), loadedGame.getPlayerInventory())) {
            isSame = true;
        }

        assertEquals(expResult, isSame);
    }

    @Test
    public void testGameHasSamePlayerMessage() {
        boolean expResult = true;
        boolean isSame = true;

        //Save game
        game = new Game();
        Serialization ser = new Serialization(game);
        ser.Save();

        Setup_load();

        //compare player
        String playerMsg = game.getPlayerMessage();
        String loadedPlayerMsg = loadedGame.getPlayerMessage();
        if (playerMsg.compareTo(loadedPlayerMsg) != 0) {//if not equal
            isSame = false;
        }

        assertEquals(expResult, isSame);
    }

    @Test
    public void testGameHasSamePlayerPosition() {
        boolean expResult = true;
        boolean isSame = true;

        //Save game
        game = new Game();
        Serialization ser = new Serialization(game);
        ser.Save();

        Setup_load();

        //compare player
        Position position = game.getPlayer().getPosition();
        Position loadedPosition = loadedGame.getPlayer().getPosition();
        if (position.getRow() != loadedPosition.getRow()) {//if not equal
            isSame = false;
        } else if (position.getColumn() != loadedPosition.getColumn()) {
            isSame = false;

        }

        assertEquals(expResult, isSame);
    }

    @Test
    public void testGameHasSamePlayerName() {
        boolean expResult = true;
        boolean isSame = true;

        //Save game
        game = new Game();
        Serialization ser = new Serialization(game);
        ser.Save();

        Setup_load();

        //compare player
        String name = game.getPlayer().getName();
        String loadedName = loadedGame.getPlayer().getName();

        if (name.compareTo(loadedName) != 0) {//if not equal
            isSame = false;
        }

        assertEquals(expResult, isSame);
    }

    @Test
    public void testGameHasSamePlayerStamina() {
        boolean expResult = true;
        boolean isSame = true;

        //Save game
        game = new Game();
        Serialization ser = new Serialization(game);
        ser.Save();

        Setup_load();
        
        //compare player
        double stamina = game.getPlayer().getStaminaLevel();
        double loadedStamina = game.getPlayer().getStaminaLevel();

        if (stamina != loadedStamina) {//if not equal
            isSame = false;
        }

        assertEquals(expResult, isSame);
    }
}
