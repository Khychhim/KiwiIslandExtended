package nz.ac.aut.ense701.gameModel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

public class DeserializationTest {

    private Game game;
    private Game loadedGame;

    /**
     * Default constructor for test class DeserializationTest
     */
    public DeserializationTest() {
        this.setup();
    }

    /**
     * Setup a save data file and ready for testing
     */
    public void setup() {
        game = new Game();

        //CREATE SAVE DATA
        try {
            if (game.getPlayer().isAlive()) {
                FileOutputStream fstream = new FileOutputStream("Save_Data.dat");
                ObjectOutputStream objSteram = new ObjectOutputStream(fstream);
                objSteram.writeObject(game);

                //CLOSE OBJECTSTREAM AND FILEOUTPUTSTREAM
                objSteram.close();
                fstream.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(SerializationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testGameIsLoaded() {
        boolean expResult = true;
        boolean isLoaded = false;

        //Load game
        Deserialization load = new Deserialization();
        loadedGame = load.Load();

        if (loadedGame != null) {
            isLoaded = true;
        }

        assertEquals(expResult, isLoaded);
    }

    @Test
    public void testHasSameNumberOfRow() {
        boolean expResult = true;
        boolean result = false;

        //Load game
        Deserialization load = new Deserialization();
        loadedGame = load.Load();

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

        //Load game
        Deserialization load = new Deserialization();
        loadedGame = load.Load();

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

        //Load game
        Deserialization load = new Deserialization();
        loadedGame = load.Load();

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

        //Load game
        Deserialization load = new Deserialization();
        loadedGame = load.Load();

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

        //Load game
        Deserialization load = new Deserialization();
        loadedGame = load.Load();

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

        //Load game
        Deserialization load = new Deserialization();
        loadedGame = load.Load();

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

        //Load game
        Deserialization load = new Deserialization();
        loadedGame = load.Load();

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

        //Load game
        Deserialization load = new Deserialization();
        loadedGame = load.Load();

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

        //Load game
        Deserialization load = new Deserialization();
        loadedGame = load.Load();

        //compare player
        double stamina = game.getPlayer().getStaminaLevel();
        double loadedStamina = game.getPlayer().getStaminaLevel();

        if (stamina != loadedStamina) {//if not equal
            isSame = false;
        }

        assertEquals(expResult, isSame);
    }
}
