package nz.ac.aut.ense701.gameModel;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 *
 * @author joshl
 * @version 2017
 */
public class DifferentMap {
    private static final String MAP_FILE_NAME = "IslandData.txt";
    private static final String TEXT_FORMAT = "UTF-8";
    private final int mapRows;
    private final int mapCols;
    private final String playerName;
    private final MapDataTypes mapDataTypes;
    
    //Sets the minimum number of each type to be generated
    private static final int MIN_KIWIS = 5;
    private static final int MIN_PREDATORS = 3;
    private static final int MIN_FOOD = 3;
    private static final int MIN_TOOLS = 1; //Min number of each tool
    private static final int MIN_FAUNA = 3;
    private static final int MIN_HAZARDS = 5;
    //Sets the range of the random value to be added to each type
    private static final int RAND_KIWIS = 2;
    private static final int RAND_PREDATORS = 3;
    private static final int RAND_FOOD = 5;
    private static final int RAND_TOOLS = 3; //Up to an addional X of each tool
    private static final int RAND_FAUNA = 3;
    private static final int RAND_HAZARDS = 4;
    
    public DifferentMap(int mapRows, int mapCols, String playerName) {
        this.mapRows = mapRows;
        this.mapCols = mapCols;
        this.playerName = playerName;
        mapDataTypes = new MapDataTypes();
        mapDataTypes.loadTypesFromFile();
    }
    
    public void generateMap() {
        PrintWriter pw = CreateFile();
        Random rand = new Random();
        //Setup Maps rows and columns
        pw.append(mapRows + ", " + mapCols + ",");
        //Setup the maps Tiles
        for(int r = 0; r < mapRows; r++) {
            String row = "";
            for(int c = 0; c < mapCols; c++) {
                int tileToAdd = rand.nextInt(mapDataTypes.tileTypes.size());
                row += mapDataTypes.tileTypes.get(tileToAdd);
            }
            row += ",";
            pw.append(row);
        }
        //Setup player stats
        int playerStartRow = rand.nextInt(mapRows);
        int playerStartCol = rand.nextInt(mapCols);
        //Sets up the player with 100 max stamina, 10 max backpack weight and 5 max backpack size
        pw.append(playerName + ", " + playerStartRow + ", " + playerStartCol + ", "
                + " 100.0, 10.0, 5.0,");
        //Sets up the number of each occupant and the total occupants
        int totalOccupants = 0;
        int numberKiwis = rand.nextInt(RAND_KIWIS) + MIN_KIWIS + 1;
        int numberPredators = rand.nextInt(RAND_PREDATORS) + MIN_PREDATORS + 1;
        int numberFood = rand.nextInt(RAND_FOOD) + MIN_FOOD + 1;
        int[] numberTools = new int[mapDataTypes.toolTypes.size()];
        for(int i = 0; i < mapDataTypes.toolTypes.size(); i++) {
            numberTools[i] = rand.nextInt(RAND_TOOLS) + MIN_TOOLS + 1;
            totalOccupants += numberTools[i];
        }
        int numberFauna = rand.nextInt(RAND_FAUNA) + MIN_FAUNA + 1;
        int numberHazards = rand.nextInt(RAND_HAZARDS) + MIN_HAZARDS + 1;
        totalOccupants += numberKiwis + numberPredators + numberFood + numberFauna + numberHazards;
        pw.append(totalOccupants + ",");
    }
    
    public PrintWriter CreateFile() {
        PrintWriter pw = null;
        
        try {
            pw = new PrintWriter(MAP_FILE_NAME, TEXT_FORMAT);
        } catch (FileNotFoundException ex) {
            System.out.println("File not found " + ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            System.out.println("File encoding not supported " + ex.getMessage());
        }
        
        return pw;
    }
    
    
}
