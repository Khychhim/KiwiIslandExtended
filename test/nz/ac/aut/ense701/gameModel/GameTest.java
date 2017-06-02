package nz.ac.aut.ense701.gameModel;

import java.io.File;
import java.io.IOException;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * The test class GameTest.
 *
 * @author  AS
 * @version S2 2011
 */
public class GameTest extends junit.framework.TestCase
{
    Game       game;
    Player     player;
    Position   playerPosition;
    Island island ;
    /**
     * Default constructor for test class GameTest
     */
    public GameTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Override
    protected void setUp()
    {
            // Create a new game from the data file.
            // Player is in position 2,0 & has 100 units of stamina
        game           = new Game(GameDifficulty.EASY);
        
        playerPosition = game.getPlayer().getPosition();
        player         = game.getPlayer();
        island = game.getIsland();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @Override
    protected void tearDown()
    {
        game = null;
        player = null;
        playerPosition = null;
    }

    /**
     * Tests for Accessor methods of Game, excluding those which are wrappers for accessors in other classes.
     * Other class accessors are tested in their test classes.
     */
    
    @Test
    public void testGetNumRows(){
        assertEquals("Check row number", game.getNumRows(), game.getMapSize());
    }
    
    @Test
    public void testGetNumColumns(){
        assertEquals("Check column number", game.getNumColumns(), game.getMapSize());
    }
    
    @Test
    public void testGetPlayer(){
        String name = player.getName();
        String checkName = "River Song";
        assertTrue("Check player name", name.equals(checkName) );
    } 

    @Test
    public void testGetInitialState(){
        assertEquals("Wrong initial state", game.getState(), GameState.PLAYING);
    }
    
    @Test
    public void testGetPlayerValues(){
        int[] values = game.getPlayerValues();
        assertEquals("Check Max backpack size.", values[Game.MAXSIZE_INDEX], 5);    
        assertEquals("Check max stamina.", values[Game.MAXSTAMINA_INDEX], 100);
        assertEquals("Check max backpack weight.", values[Game.MAXWEIGHT_INDEX], 10);
        assertEquals("Check initialstamina", values[Game.STAMINA_INDEX], 100);
        assertEquals("Check initial backpack weight.", values[Game.WEIGHT_INDEX], 0);
        assertEquals("Check initial backp[ack size.", values[Game.SIZE_INDEX], 0);
    }
    
    @Test
    public void testGetRandomDirection(){
          MoveDirection direction = game.getRandomDirection();
          assertTrue(direction instanceof MoveDirection);         
    }
    
    @Test
    public void countreset(){
        int zero =0;
        assertEquals(zero, game.count_of_steps);
    }
    @Test
    public void achievementWonGames(){
        assertTrue(game.achievement.won3gamesinrow);
    }
    @Test
    public void achievementTraveller(){
        assertTrue(game.achievement.walked);
    }
    
    @Test
    public void achievementHero(){
        assertTrue(game.achievement.savedKiwis);
    }
    
    @Test
    public void AchievementXMLDocument(){
        
        try{
            Document xml = null;
            File fXmlFile = new File("Achievements.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            xml = (Document) dBuilder.parse(fXmlFile);
            assertEquals(xml, game.achievement.ReadAchievementXML());
        }
        catch(SAXException e){
            System.out.println("SAXException error");
                
        }
        catch(IOException e){
            System.out.println("IOException error");
        }
        catch(ParserConfigurationException e){
            System.out.println("Parser Error");
        }
    }
    
    @Test
    public void testEasyDifficultyEnvironment(){
          if(game.getGameDifficulty() == GameDifficulty.EASY){
                assertEquals(game.getMapSize(),10);
                assertEquals(game.getPlayer().getCurrentBackpackSize(), 0.0);
                assertEquals(game.getPlayer().getCurrentBackpackWeight(), 0.0);
                assertEquals(game.getPlayer().getStaminaLevel(), 100.0);
                assertEquals(game.getPredatorsRemaining(), 3);
          }
    }    
        
    @Test
    public void testNormalDifficultyEnvironment(){
            game = new Game(GameDifficulty.NORMAL);
            assertEquals(game.getMapSize(),15);
            assertEquals(game.getPlayer().getCurrentBackpackSize(), 0.0);
            assertEquals(game.getPlayer().getCurrentBackpackWeight(), 2.0);
            assertEquals(game.getPlayer().getStaminaLevel(), 80.0);  
            assertEquals(game.getPredatorsRemaining(), 5);
    }
    
    @Test
    public void testHardDifficultyEnvironment(){
            game = new Game(GameDifficulty.HARD);
            assertEquals(game.getMapSize(),15);
            assertEquals(game.getPlayer().getCurrentBackpackSize(), 1.0);
            assertEquals(game.getPlayer().getCurrentBackpackWeight(), 4.0);
            assertEquals(game.getPlayer().getStaminaLevel(), 50.0); 
            assertEquals(game.getPredatorsRemaining(), 7);
    }
    
    @Test
    public void testIsKiwiNearPredator(){          
          Position positionPredator = new Position(island, 0,2);
          Position positionKiwi = new Position(island,0,0);
          Predator predator;
          Kiwi kiwi;
          if(!island.hasPredator(positionPredator)){
                predator = new Predator(positionPredator,"Cat","Danger");
                island.addOccupant(positionPredator, predator);
          }else{
                predator = island.getPredator(positionPredator);
          }
          
          if(!island.hasKiwi(positionKiwi)){
                kiwi = new Kiwi(positionKiwi,"Kiwi","Cute");
                island.addOccupant(positionKiwi, kiwi);
          }
          assertTrue("Kiwi should be near predator",game.isKiwiNearPredator(predator));
    }
    
    @Test
    public void testPredatorMoveToKiwi(){
          
          Position positionPredator = new Position(island, 0,2);
          Position positionKiwi = new Position(island,0,0);
          Predator predator;
          Kiwi kiwi;
          if(!island.hasPredator(positionPredator)){
                predator = new Predator(positionPredator,"Cat","Danger",0,-2);
                island.addOccupant(positionPredator, predator);
          }else{
                predator = island.getPredator(positionPredator);
          }
          
          if(!island.hasKiwi(positionKiwi)){
                kiwi = new Kiwi(positionKiwi,"Kiwi","Cute");
                island.addOccupant(positionKiwi, kiwi);
          }
          
          game.predatorMoveToKiwi(predator);
          
          assertFalse("Predator should not be in this position",island.hasPredator(positionPredator));
    }
    
        @Test
    public void testQuizLaunchStopPredatorTimer(){
        boolean isPlayerMoveSuccess = false;
        boolean isValid = false;

        //set up create testIsland 
        //create trigger position and Player position where 
        //position of trigger is one below the player's position
        Island testIsland = new Island(2,1);
        Position playerPosition = new Position(testIsland,0,0);
        Position triggerPosition = new Position(testIsland,1,0);
        
        //place trigger in the island
        Trigger trigger = new Trigger(triggerPosition, "Trigger", "quiz");
        testIsland.addOccupant(triggerPosition, trigger);

        //set test Island , player position 
        game.setIsland(testIsland);
        game.getPlayer().setPosition(playerPosition);
        //--------------------------------------------------------------------
        
        //player move
        if (game.getPlayer().isAlive()) {
            isPlayerMoveSuccess = game.playerMove(MoveDirection.SOUTH);
        }
        
        isValid = isPlayerMoveSuccess & game.predatorTimerTask.hasRunStarted();
        
        assertEquals(false, isValid);
    }
    
    @Test
    public void testIsPlayerMovePossibleValidMove(){
          Position pos = new Position(island, 0,0);
          game.player = new Player(pos, "JOH",50,50,100);
        assertTrue("Move should be valid", game.isPlayerMovePossible(MoveDirection.SOUTH));
    }
    
    @Test
    public void testIsPlayerMovePossibleInvalidMove(){
           Position pos = new Position(island, 0,0);
          game.player = new Player(pos, "JOH",50,50,100);
        //At start of game player has valid moves EAST, West & South
        assertFalse("Move should not be valid", game.isPlayerMovePossible(MoveDirection.NORTH));
    }
    
    @Test
    public void testCanCollectCollectable(){
        //Items that are collectable and fit in backpack
        Item valid = new Food(playerPosition,"Sandwich", "Yummy",1.0, 1.0,1.0);
        assertTrue("Should be able to collect", game.canCollect(valid));
    }
    
    @Test    
    public void testCanCollectNotCollectable(){
        //Items with size of '0' cannot be carried
        Item notCollectable = new Food(playerPosition,"Sandwich", "Very Heavy Sandwich",10.0, 0.0,1.0);
        assertFalse("Should not be able to collect", game.canCollect(notCollectable));
    }
    
    @Test
    public void testCanUseFoodValid(){
        //Food can always be used
        Item valid = new Food(playerPosition,"Sandwich", "Yummy",1.0, 1.0,1.0);
        assertTrue("Should be able to use", game.canUse(valid));
    }
    
    @Test
    public void testCanUseTrapValid(){
        //Trap can be used if there is a predator here
        Item valid = new Tool(playerPosition,"Trap", "A predator trap",1.0, 1.0);
        //Add predator
        Predator rat = new Predator(playerPosition,"Rat", "A norway rat");
        island.addOccupant(playerPosition, rat);
        assertTrue("Should be able to use", game.canUse(valid));
    }
    
    @Test
    public void testCanUseTrapNoPredator(){
        //Trap can be used if there is a predator here
        Item tool = new Tool(playerPosition,"Trap", "A predator trap",1.0, 1.0);

        assertFalse("Should not be able to use", game.canUse(tool));
    }
    
    @Test
    public void testCanUseTool(){
        //Screwdriver can be used if player has a broken trap
        Item tool = new Tool(playerPosition,"Screwdriver", "A good tool to fix a trap",1.0, 1.0);
        Tool trap = new Tool(playerPosition,"Trap", "A predator trap",1.0, 1.0);
        trap.setBroken();
        player.collect(trap);

        assertTrue("Should be able to use", game.canUse(tool));
    }
    
    @Test
    public void testCanUseToolNoTrap(){
        //Screwdriver can be used if player has a broken trap
        Item tool = new Tool(playerPosition,"Screwdriver", "A good tool to fix a trap",1.0, 1.0);
        Tool trap = new Tool(playerPosition,"Trap", "A predator trap",1.0, 1.0);
        trap.setBroken();

        assertFalse("Should not be able to use", game.canUse(tool));
    }
    
    @Test
    public void testCanUseToolTrapNotBroken(){
        //Screwdriver can be used if player has a broken trap
        Item tool = new Tool(playerPosition,"Screwdriver", "A good tool to fix a trap",1.0, 1.0);
        Tool trap = new Tool(playerPosition,"Trap", "A predator trap",1.0, 1.0);
        player.collect(trap);

        assertFalse("Should not be able to use", game.canUse(tool));
    }
    
    @Test
    public void testGetKiwiCountInitial()
    {
       assertEquals("Shouldn't have counted any kiwis yet",game.getKiwiCount(),0); 
    }
    /**
     * Test for mutator methods
     */
    
    @Test
    public void testCollectValid(){
        Item food = new Food(playerPosition,"Sandwich", "Yummy",1.0, 1.0,1.0);
        island.addOccupant(playerPosition, food);
        assertTrue("Food now on island", island.hasOccupant(playerPosition, food));
        game.collectItem(food);
        
        assertTrue("Player should have food",player.hasItem(food));
        assertFalse("Food should no longer be on island", island.hasOccupant(playerPosition, food));
    }
    
    @Test
    public void testCollectNotCollectable(){
        Item notCollectable = new Food(playerPosition,"House", "Can't collect",1.0, 0.0,1.0);
        island.addOccupant(playerPosition, notCollectable);
        assertTrue("House now on island", island.hasOccupant(playerPosition, notCollectable));
        game.collectItem(notCollectable);
        
        assertFalse("Player should not have house",player.hasItem(notCollectable));
        assertTrue("House should be on island", island.hasOccupant(playerPosition, notCollectable));
    }
    
    @Test    
    public void testDropValid(){
        Item food = new Food(playerPosition,"Sandwich", "Yummy",1.0, 1.0,1.0);
        island.addOccupant(playerPosition, food);
        game.collectItem(food);
        assertTrue("Player should have food",player.hasItem(food));
        
        game.dropItem(food);
        assertFalse("Player should no longer have food",player.hasItem(food));
        assertTrue("Food should be on island", island.hasOccupant(playerPosition, food));
    }
    
    @Test
    public void testDropNotValidPositionFull(){
        Item food = new Food(playerPosition,"Sandwich", "Yummy",1.0, 1.0,1.0);
        island.addOccupant(playerPosition, food);
        game.collectItem(food);
        assertTrue("Player should have food",player.hasItem(food));
        
        //Positions can have at most three occupants
        Item dummy = new Tool(playerPosition,"Trap", "An extra occupant", 1.0, 1.0);
        Item dummy2 = new Tool(playerPosition,"Trap", "An extra occupant", 1.0, 1.0);
        Item dummy3 = new Tool(playerPosition,"Trap", "An extra occupant", 1.0, 1.0);
        island.addOccupant(playerPosition, dummy);
        island.addOccupant(playerPosition, dummy2);
        island.addOccupant(playerPosition, dummy3);
        
        game.dropItem(food);
        assertTrue("Player should have food",player.hasItem(food));
        assertFalse("Food should not be on island", island.hasOccupant(playerPosition, food));
    }
    
    @Test
    public void testUseItemFoodCausesIncrease(){
        Item food = new Food(playerPosition,"Sandwich", "Yummy",1.0, 1.0,1.3);
        player.collect(food);
        assertTrue("Player should have food",player.hasItem(food));
        
        // Will only get a stamina increase if player has less than max stamina
        player.reduceStamina(5.0);
        game.useItem(food);
        assertFalse("Player should no longer have food",player.hasItem(food));
        assertEquals("Wrong stamina level", player.getStaminaLevel(), 96.3);
    }
 
    public void testUseItemFoodNoIncrease(){
        Item food = new Food(playerPosition,"Sandwich", "Yummy",1.0, 1.0,1.3);
        player.collect(food);
        assertTrue("Player should have food",player.hasItem(food));
        
        // Will only get a stamina increase if player has less than max stamina
        game.useItem(food);
        assertFalse("Player should no longer have food",player.hasItem(food));
        assertEquals("Wrong stamina level", player.getStaminaLevel(), 100.0);
    }  
    
    @Test
    public void testUseItemTrap(){
        Item trap = new Tool(playerPosition,"Trap", "Rat trap",1.0, 1.0);
        player.collect(trap);
        assertTrue("Player should have trap",player.hasItem(trap));
        
        // Can only use trap if there is a predator.
        Predator predator = new Predator(playerPosition,"Rat", "Norway rat");
        if(!island.hasPredator(playerPosition)){
              island.addOccupant(playerPosition, predator);
        }
        game.useItem(trap);
        assertTrue("Player should still have trap",player.hasItem(trap));
        assertFalse("Predator should be gone.", island.hasPredator(playerPosition));
    }
    
    @Test
    public void testUseItemBrokenTrap(){
        Tool trap = new Tool(playerPosition,"Trap", "Rat trap",1.0, 1.0);
        player.collect(trap);
        assertTrue("Player should have trap",player.hasItem(trap));
        
        // Can only use trap if there is a predator.
        Predator predator = new Predator(playerPosition,"Rat", "Norway rat");
        island.addOccupant(playerPosition, predator);
        trap.setBroken();
        game.useItem(trap);
        assertTrue("Player should still have trap",player.hasItem(trap));
        assertTrue("Predator should still be there as trap broken.", island.hasPredator(playerPosition));
    }
    
    @Test
    public void testUseItemToolFixTrap(){
        Tool trap = new Tool(playerPosition,"Trap", "Rat trap",1.0, 1.0);
        trap.setBroken();
        player.collect(trap);
        assertTrue("Player should have trap",player.hasItem(trap));
        Tool screwdriver = new Tool(playerPosition,"Screwdriver", "Useful screwdriver",1.0, 1.0);
        player.collect(screwdriver);
        assertTrue("Player should have screwdriver",player.hasItem(screwdriver));
        
        game.useItem(screwdriver);
        assertFalse("Trap should be fixed", trap.isBroken());
    }
   
    @Test
    public void testPlayerMoveToInvalidPosition(){
           Position pos = new Position(island, 0,0);
          game.player = new Player(pos, "JOH",50,50,100);
        //A move NORTH would be invalid from player's start position
        assertFalse("Move not valid", game.playerMove(MoveDirection.NORTH));
    }
    
    @Test
    public void testPlayerMoveDeadPlayer(){
        player.kill();
        assertFalse(game.playerMove(MoveDirection.SOUTH));
    }
    
    @Test
    public void testPlayerMoveNotEnoughStamina(){
        // Reduce player's stamina to less than is needed for the most challenging move
        //Most challenging move is WEST as Terrain is water
        player.reduceStamina(100);
        assertFalse("Player should not have required stamina", game.playerMove(MoveDirection.WEST));
        //Game not over as there other moves player has enough stamina for
        assertTrue("Game should not be over", game.getState()== GameState.PLAYING);
    }
    
    @Test
    public void testCountKiwi()
    {
        Island testIsland = new Island(2,1);
        Position playerPosition = new Position(testIsland,0,0);        
        Occupant kiwi = new Kiwi(playerPosition, "Kiwi","Kiwi");
        testIsland.addOccupant(playerPosition, kiwi);
        //set test Island , player position 
        game.setIsland(testIsland);
        game.getPlayer().setPosition(playerPosition);
 
        game.countKiwi();
        assertEquals("Wrong count", game.getKiwiCount(), 1);
    }
}
