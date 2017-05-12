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
    private int playerRow;
    private int playerCol;
    private final String playerName;
    private final MapDataTypes mapDataTypes;
    
    //Sets the minimum number of each type to be generated
    private static final int MIN_KIWIS = 5;
    private static final int MIN_PREDATORS = 3;
    private static final int MIN_FOOD = 3;
    private static final int MIN_TOOLS = 1; //Min number of each tool
    private static final int MIN_FAUNA = 3;
    private static final int MIN_HAZARDS = 5;
    private static final int MIN_TRIGGERS = 3;
    //Sets the range of the random value to be added to each type
    private static final int RAND_KIWIS = 2;
    private static final int RAND_PREDATORS = 3;
    private static final int RAND_FOOD = 5;
    private static final int RAND_TOOLS = 3; //Up to an addional X of each tool
    private static final int RAND_FAUNA = 3;
    private static final int RAND_HAZARDS = 4;
    private static final int RAND_TRIGGERS = 2;
    //Minimum distances between spawns
    private static final int PRED_MIN_DIST = 3; //Min Distance between predator and kiwi
    private static final int HAZARD_MIN_DIST = 1; //Min Distance between other hazards
    
    //For testing map Generation
    public static void main(String args[]) {
        DifferentMap test = new DifferentMap(20, 20, "Cheese");
        test.generateMap();
    }     
    
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
        pw.println(mapRows + ", " + mapCols + ",");
        //Setup the maps Tiles
        for(int r = 0; r < mapRows; r++) {
            String row = "";
            for(int c = 0; c < mapCols; c++) {
                int tileToAdd = rand.nextInt(mapDataTypes.tileTypes.size());
                row += mapDataTypes.tileTypes.get(tileToAdd);
            }
            row += ",";
            pw.println(row);
        }
        //Setup player stats
        playerRow = rand.nextInt(mapRows);
        playerCol = rand.nextInt(mapCols);
        //Sets up the player with 100 max stamina, 10 max backpack weight and 5 max backpack size
        pw.println(playerName + ", " + playerRow + ", " + playerCol + ", "
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
        int numberTriggers = rand.nextInt(RAND_TRIGGERS) + MIN_TRIGGERS + 1;
        totalOccupants += numberKiwis + numberPredators + numberFood + numberFauna + numberHazards + numberTriggers;
        pw.println(totalOccupants + ",");
        //Create a temporary map representation to track occupant positions and counts for viability
        String[][] tempMap = new String[mapRows][mapCols];
        //Generate the occupants any predators that cannot be generated will be replaced with extra food
        generateTools(pw, numberTools, tempMap);
        generateFauna(pw, numberFauna, tempMap);
        generateHazards(pw, numberHazards, tempMap);
        generateKiwis(pw, numberKiwis, tempMap);        
        generateTriggers(pw, numberTriggers, tempMap);
        int failedPredGens = generatePredators(pw, numberPredators, tempMap);
        generateFood(pw, numberFood+failedPredGens, tempMap);
        //Close the stream after generation is finished
        pw.close();
    }
    
    private void generateTriggers(PrintWriter pw, int numberOfTriggers, String[][] tempMap) {
        int generatedCount = 0;
        Random rand = new Random();
        while(generatedCount < numberOfTriggers) {
            int row = rand.nextInt(mapRows);
            int col = rand.nextInt(mapCols);
            int randomTrigger = rand.nextInt(mapDataTypes.triggerTypes.size());
            Trigger trigger = mapDataTypes.triggerTypes.get(randomTrigger);
            //Dont spawn a trigger in grid square more than 3, the hazard or player position
            if(tempMap[row][col] == null || 
                    (tempMap[row][col].length() < 3 && !tempMap[row][col].contains("H") && !tempMap[row][col].contains("Q") && playerRow != row && playerCol != col)) {
                pw.println("Q," + trigger.getName() + "," + trigger.getDescription() + ", " +
                        row + ", " + col + ", ");
                generatedCount++;
            
            }
        }
    }
     
    private void generateFood(PrintWriter pw, int numberOfFood, String[][] tempMap) {
        int generatedCount = 0;
        Random rand = new Random();
        while(generatedCount < numberOfFood) {
            int row = rand.nextInt(mapRows);
            int col = rand.nextInt(mapCols);
            int randomFood = rand.nextInt(mapDataTypes.foodTypes.size());
            Food food = mapDataTypes.foodTypes.get(randomFood);
            if(tempMap[row][col] == null || tempMap[row][col].length() < 3) {
                if(tempMap[row][col] == null) tempMap[row][col] = "E";
                else tempMap[row][col] += "E";
                pw.println("E," + food.getName() + "," + food.getDescription() + ", " +
                        row + ", " + col + ", " + food.getWeight() + ", " + food.getSize() + ", "
                        + food.getEnergy() + ",");
                generatedCount++;
            }
        }
    }
    
    private void generateFauna(PrintWriter pw, int numberOfFauna, String[][] tempMap) {
        int generatedCount = 0;
        Random rand = new Random();
        while(generatedCount < numberOfFauna) {
            int row = rand.nextInt(mapRows);
            int col = rand.nextInt(mapCols);
            int randomFauna = rand.nextInt(mapDataTypes.faunaTypes.size());
            Fauna fauna = mapDataTypes.faunaTypes.get(randomFauna);
            if(tempMap[row][col] == null || tempMap[row][col].length() < 3) {
                if(tempMap[row][col] == null) tempMap[row][col] = "F";
                else tempMap[row][col] += "F";
                pw.println("F," + fauna.getName() + "," + fauna.getDescription() + ", " +
                        row + ", " + col + ",");
                generatedCount++;
            }
        }
    }
    
    private void generateHazards(PrintWriter pw, int numberOfHazards, String[][] tempMap) {
        int generatedCount = 0;
        Random rand = new Random();
        while(generatedCount < numberOfHazards) {
            int row = rand.nextInt(mapRows);
            int col = rand.nextInt(mapCols);
            int randomHazard = rand.nextInt(mapDataTypes.hazardTypes.size());
            Hazard hazard = mapDataTypes.hazardTypes.get(randomHazard);
            //Dont spawn a predator within PRED_MIN_DIST spaces of a kiwi
            boolean validSpawn = true;
            for(int x = -HAZARD_MIN_DIST; x < HAZARD_MIN_DIST; x++) {
                for(int y = -HAZARD_MIN_DIST; y < HAZARD_MIN_DIST; y++) {
                    if(col+x > 0 && col+x < mapCols && row+y > 0 && row+y < mapRows) {
                        if(tempMap[row+y][col+x] != null && tempMap[row+y][col+x].contains("H")) {
                            validSpawn = false;
                        }
                    }
                }
            }
            if(tempMap[row][col] != null && row == playerRow && col == playerCol) validSpawn = false;
            if(validSpawn && (tempMap[row][col] == null || tempMap[row][col].length() < 3)) {
                if(tempMap[row][col] == null) tempMap[row][col] = "H";
                else tempMap[row][col] += "H";
                pw.println("H," + hazard.getName() + "," + hazard.getDescription() + ", " +
                        row + ", " + col + ", " + hazard.getImpact() + ",");
                generatedCount++;
            }
        }
    }
    
    private int generatePredators(PrintWriter pw, int numberOfPredators, String[][] tempMap) {
        int generatedCount = 0;
        int failedSpawns = 0;
        Random rand = new Random();
        while(generatedCount < numberOfPredators) {
            int row = rand.nextInt(mapRows);
            int col = rand.nextInt(mapCols);
            int randomPredator = rand.nextInt(mapDataTypes.predatorTypes.size());
            Predator predator = mapDataTypes.predatorTypes.get(randomPredator);
            //Dont spawn a predator within PRED_MIN_DIST spaces of a kiwi
            boolean validSpawn = true;
            for(int x = -PRED_MIN_DIST; x < PRED_MIN_DIST; x++) {
                for(int y = -PRED_MIN_DIST; y < PRED_MIN_DIST; y++) {
                    if(col+x > 0 && col+x < mapCols && row+y > 0 && row+y < mapRows) {
                        if(tempMap[row+y][col+x] != null && tempMap[row+y][col+x].contains("K")) {
                            validSpawn = false;
                        }
                    }
                }
            }
            if(tempMap[row][col] != null && tempMap[row][col].contains("H")) validSpawn = false;
            if(validSpawn && (tempMap[row][col] == null || tempMap[row][col].length() < 3)) {
                if(tempMap[row][col] == null) tempMap[row][col] = "P";
                else tempMap[row][col] += "P";
                pw.println("P," + predator.getName() + "," + predator.getDescription() + ", " +
                        row + ", " + col + ",");
                generatedCount++;
                failedSpawns = 0;
            } else {
                failedSpawns++;
            }
            if(failedSpawns > 100) {
                int failedGens = (numberOfPredators-generatedCount);
                System.err.println("Unable to generate " + failedGens + " predators.");
                return failedGens;
            }
        }
        return 0;
    }
    
    private void generateKiwis(PrintWriter pw, int numberOfKiwis, String[][] tempMap) {
        int generatedCount = 0;
        Random rand = new Random();
        while(generatedCount < numberOfKiwis) {
            int row = rand.nextInt(mapRows);
            int col = rand.nextInt(mapCols);
            int randomKiwi = rand.nextInt(mapDataTypes.kiwiTypes.size());
            Kiwi kiwi = mapDataTypes.kiwiTypes.get(randomKiwi);
            if(tempMap[row][col] == null || 
                    (tempMap[row][col].length() < 3 && !tempMap[row][col].contains("H"))) {
                if(tempMap[row][col] == null) tempMap[row][col] = "K";
                else tempMap[row][col] += "K";
                pw.println("K," + kiwi.getName() + "," + kiwi.getDescription() + ", " +
                        row + ", " + col + ",");
                generatedCount++;
            }
        }
    }
    
    private void generateTools(PrintWriter pw, int[] numberOfTools, String[][] tempMap) {
        for(int i = 0; i < numberOfTools.length; i++) {
            int generatedCount = 0;
            Random rand = new Random();
            while(generatedCount < numberOfTools[i]) {
                int row = rand.nextInt(mapRows);
                int col = rand.nextInt(mapCols);
                Tool tool = mapDataTypes.toolTypes.get(i);
                if(tempMap[row][col] == null || tempMap[row][col].length() < 3) {
                    if(tempMap[row][col] == null) tempMap[row][col] = "T";
                    else tempMap[row][col] += "T";
                    pw.println("T," + tool.getName() + "," + tool.getDescription() + ", " +
                            row + ", " + col + ", " + tool.getWeight() + ", " + tool.getSize() + ",");
                    generatedCount++;
                }
            }
        }
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
