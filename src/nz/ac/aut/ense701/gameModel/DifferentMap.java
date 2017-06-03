package nz.ac.aut.ense701.gameModel;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joshl
 * @version 2017
 */
public class DifferentMap {
    private static final String MAP_FILE_NAME = "IslandData.txt";
    private static final String TEXT_FORMAT = "UTF-8";
    //Difficulty : EASY
    private static final int MAP_SIZE_EASY = 10;
    private static final int NUMBER_PREDATOR_EASY = 3;
    private static final int MIN_HAZARD_EASY = 3;
    private static final int PREDATOR_MOVE_TIME_EASY = 60;
    
    //Difficulty: NORMAL
    private static final int MAP_SIZE_NORMAL_HARD = 15;
    private static final int NUMBER_PREDATOR_NORMAL = 5;
    private static final int MIN_HAZARD_NORMAL = 7;
    private static final int PREDATOR_MOVE_TIME_NORMAL = 40;

    //Difficulty: HARD
    private static final int NUMBER_PREDATOR_HARD = 7;
    private static final int MIN_HAZARD_HARD = 10;
    private static final int PREDATOR_MOVE_TIME_HARD = 20;
    
    private int mapRows;
    private int mapCols;
    private int playerRow;
    private int playerCol;
    private final String playerName;
    private final MapDataTypes mapDataTypes;
    private int predatorMoveTime;
    private int minHazards;
    private int numPredators;
    
    private static final Logger LOG = Logger.getLogger(DifferentMap.class.getName());
    
    private static final int NUM_TRIGGERS = 5;
    //Sets the minimum number of each type to be generated
    private static final int MIN_KIWIS = 5;
    private static final int MIN_FOOD = 4;
    private static final int MIN_TOOLS = 1; //Min number of each tool
    private static final int MIN_FAUNA = 3;
    //Sets the range of the random value to be added to each type
    private static final int RAND_KIWIS = 2;
    private static final int RAND_FOOD = 4;
    private static final int RAND_TOOLS = 3; //Up to an addional X of each tool
    private static final int RAND_FAUNA = 3;
    private static final int RAND_HAZARDS = 2;
    //Minimum distances between spawns
    private static final int PRED_MIN_DIST = 3; //Min Distance between predator and kiwi
    private static final int HAZARD_MIN_DIST = 1; //Min Distance between other hazards
    //Chance for generated tile to be the same as adjacent tile (1-10)
    private static final int RE_GEN = 5;
    private final GameDifficulty gameDifficulty;     
    
    public DifferentMap(GameDifficulty difficulty, String playerName) {
        this.playerName = playerName;
        gameDifficulty = difficulty;
        mapDataTypes = new MapDataTypes();
        mapDataTypes.loadTypesFromFile();
    }
    
    public void generateMap() {
        //setup map difficulty
        generateDifficulty();
        int[][] mapGen = new int[mapRows][mapCols];
        
        PrintWriter pw = createFile();
        Random rand = new Random();
        //Setup Maps rows and columns
        pw.println(getMapRows() + ", " + getMapCols() + ",");
        //Setup the maps Tiles
        for(int r = 0; r < getMapRows(); r++) {
            StringBuilder row = new StringBuilder();
            for(int c = 0; c < mapCols; c++) {
                int tileToAdd;
                //Gives a chance to group tiles that are the same
                if(r > 0 || c > 0) {
                    if(rand.nextInt(10)+1 < RE_GEN && r > 0) tileToAdd = mapGen[r-1][c];
                    else if(rand.nextInt(10)+1 < RE_GEN && c > 0) tileToAdd = mapGen[r][c-1];
                    else tileToAdd = rand.nextInt(mapDataTypes.tileTypes.size());
                } else tileToAdd = rand.nextInt(mapDataTypes.tileTypes.size());
                mapGen[r][c] = tileToAdd;
                row.append(mapDataTypes.tileTypes.get(tileToAdd));
            }
            row.append(",");
            pw.println(row);
        }
        //Setup player stats
        playerRow = rand.nextInt(getMapRows());
        playerCol = rand.nextInt(getMapCols());
        //Sets up the player with 100 max stamina, 10 max backpack weight and 5 max backpack size
        pw.println(playerName + ", " + playerRow + ", " + playerCol + ", "
                + " 100.0, 10.0, 5.0,");
        
        //Sets up the number of each occupant and the total occupants
        int totalOccupants = 0;
        int numberKiwis = rand.nextInt(RAND_KIWIS) + MIN_KIWIS + 1;
        int numberPredators = numPredators;
        int numberFood = rand.nextInt(RAND_FOOD) + MIN_FOOD + 1;
        int[] numberTools = new int[mapDataTypes.toolTypes.size()];
        for(int i = 0; i < mapDataTypes.toolTypes.size(); i++) {
            numberTools[i] = rand.nextInt(RAND_TOOLS) + MIN_TOOLS + 1;
            totalOccupants += numberTools[i];
        }
        int numberFauna = rand.nextInt(RAND_FAUNA) + MIN_FAUNA + 1;
        int numberHazards = rand.nextInt(RAND_HAZARDS) + minHazards + 1;
        int numberTriggers = NUM_TRIGGERS;
        totalOccupants += numberKiwis + numberPredators + numberFood + numberFauna + numberHazards + numberTriggers;
        pw.println(totalOccupants + ",");
        //Create a temporary map representation to track occupant positions and counts for viability
        String[][] tempMap = new String[getMapRows()][getMapCols()];
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
    
    private void generateDifficulty(){
          switch(gameDifficulty){
                case EASY:
                      mapRows = MAP_SIZE_EASY;
                      mapCols = MAP_SIZE_EASY;
                      numPredators = NUMBER_PREDATOR_EASY;
                      minHazards = MIN_HAZARD_EASY;
                      this.predatorMoveTime = PREDATOR_MOVE_TIME_EASY;                      
                break;
                
                case NORMAL:
                      mapRows = MAP_SIZE_NORMAL_HARD;
                      mapCols = MAP_SIZE_NORMAL_HARD;
                      numPredators = NUMBER_PREDATOR_NORMAL;
                      minHazards = MIN_HAZARD_NORMAL;
                      this.predatorMoveTime = PREDATOR_MOVE_TIME_NORMAL;       
                break;
                
                case HARD:
                      mapRows = MAP_SIZE_NORMAL_HARD;
                      mapCols = MAP_SIZE_NORMAL_HARD;
                      numPredators = NUMBER_PREDATOR_HARD;
                      minHazards = MIN_HAZARD_HARD;
                      this.predatorMoveTime = PREDATOR_MOVE_TIME_HARD;       
                break;
                
                default : break;
          }
    }
    
    private void generateTriggers(PrintWriter pw, int numberOfTriggers, String[][] tempMap) {
        int generatedCount = 0;
        Random rand = new Random();
        while(generatedCount < numberOfTriggers) {
            int row = rand.nextInt(getMapRows());
            int col = rand.nextInt(getMapCols());
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
            int row = rand.nextInt(getMapRows());
            int col = rand.nextInt(getMapCols());
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
            int row = rand.nextInt(getMapRows());
            int col = rand.nextInt(getMapCols());
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
            int row = rand.nextInt(getMapRows());
            int col = rand.nextInt(getMapCols());
            int randomHazard = rand.nextInt(mapDataTypes.hazardTypes.size());
            Hazard hazard = mapDataTypes.hazardTypes.get(randomHazard);
            //Dont spawn a predator within PRED_MIN_DIST spaces of a kiwi
            boolean validSpawn = true;
            for(int x = -HAZARD_MIN_DIST; x < HAZARD_MIN_DIST; x++) {
                for(int y = -HAZARD_MIN_DIST; y < HAZARD_MIN_DIST; y++) {
                    if(col+x > 0 && col+x < getMapCols() && row+y > 0 && row+y < getMapRows() &&
                            tempMap[row+y][col+x] != null && tempMap[row+y][col+x].contains("H")) {
                        validSpawn = false;
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
            int row = rand.nextInt(getMapRows());
            int col = rand.nextInt(getMapCols());
            int randomPredator = rand.nextInt(mapDataTypes.predatorTypes.size());
            Predator predator = mapDataTypes.predatorTypes.get(randomPredator);
            //Dont spawn a predator within PRED_MIN_DIST spaces of a kiwi
            boolean validSpawn = true;
            for(int x = -PRED_MIN_DIST; x < PRED_MIN_DIST; x++) {
                for(int y = -PRED_MIN_DIST; y < PRED_MIN_DIST; y++) {
                    if(col+x > 0 && col+x < getMapCols() && row+y > 0 && row+y < getMapRows() &&
                            tempMap[row+y][col+x] != null && tempMap[row+y][col+x].contains("K")) {
                        validSpawn = false;
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
                LOG.log(Level.FINE, "Unable to generate {0} predators.", failedGens);
                return failedGens;
            }
        }
        return 0;
    }
    
    private void generateKiwis(PrintWriter pw, int numberOfKiwis, String[][] tempMap) {
        int generatedCount = 0;
        Random rand = new Random();
        while(generatedCount < numberOfKiwis) {
            int row = rand.nextInt(getMapRows());
            int col = rand.nextInt(getMapCols());
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
                int row = rand.nextInt(getMapRows());
                int col = rand.nextInt(getMapCols());
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
    
    public PrintWriter createFile() {
        PrintWriter pw = null;
        
        try {
            pw = new PrintWriter(MAP_FILE_NAME, TEXT_FORMAT);
        } catch (FileNotFoundException ex) {
            LOG.log(Level.SEVERE, "File not found {0}", ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            LOG.log(Level.SEVERE, "File encoding not supported {0}", ex.getMessage());
        }
        
        return pw;
    }
    
      /**
       * @return the mapRows
       */
      public int getMapRows() {
            return mapRows;
      }

      /**
       * @return the mapCols
       */
      public int getMapCols() {
            return mapCols;
      }
      
      /**
       * @return the predatorMoveTime
       */
      public int getPredatorMoveTime() {
            return predatorMoveTime;
      }

      /**
       * @param predatorMoveTime the predatorMoveTime to set
       */
      public void setPredatorMoveTime(int predatorMoveTime) {
            this.predatorMoveTime = predatorMoveTime;
      }
}
