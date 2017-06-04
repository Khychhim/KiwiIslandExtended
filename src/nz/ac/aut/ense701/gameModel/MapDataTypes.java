package nz.ac.aut.ense701.gameModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author joshl
 */
public final class MapDataTypes implements Serializable{
    public ArrayList<String> tileTypes = new ArrayList<String>();
    public ArrayList<Food> foodTypes = new ArrayList<Food>();
    public ArrayList<Tool> toolTypes = new ArrayList<Tool>();
    public ArrayList<Fauna> faunaTypes = new ArrayList<Fauna>();
    public ArrayList<Kiwi> kiwiTypes = new ArrayList<Kiwi>();
    public ArrayList<Predator> predatorTypes = new ArrayList<Predator>();
    public ArrayList<Hazard> hazardTypes = new ArrayList<Hazard>();
    public ArrayList<Trigger> triggerTypes = new ArrayList<Trigger>();
    
    private static final String FILE_NAME = "MapDataTypes.txt";
    
    public MapDataTypes() {}
    
    public void loadTypesFromFile() {
        try {
            Scanner file = new Scanner(new File(FILE_NAME));
            file.useLocale(Locale.US);
            file.useDelimiter("\\s*,\\s*");
            
            loadTileTypes(file);
            
            loadOccupantTypes(file);
            
            file.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find file : " + FILE_NAME + ", " + ex.getMessage());
        }
    }
    
    private void loadTileTypes(Scanner file) {
        int numTileTypes = file.nextInt();
        
        for(int i = 0; i < numTileTypes; i++) {
            tileTypes.add(file.next());
        }
    }
    
    private void loadOccupantTypes(Scanner file) {
        int numOccupants = file.nextInt();
        
        for(int i = 0 ; i < numOccupants ; i++) {
            String occType = file.next();
            String occName = file.next(); 
            String occDesc = file.next();

            if(occType.equals("T")) {
                double weight = file.nextDouble();
                double size   = file.nextDouble();
                toolTypes.add(new Tool(null, occName, occDesc, weight, size));
            } else if(occType.equals("E")) {
                double weight = file.nextDouble();
                double size   = file.nextDouble();
                double energy = file.nextDouble();
                foodTypes.add(new Food(null, occName, occDesc, weight, size, energy));
            } else if(occType.equals("H")) {
                double impact = file.nextDouble();
                hazardTypes.add(new Hazard(null, occName, occDesc,impact));
            } else if(occType.equals("K")) {
                kiwiTypes.add(new Kiwi(null, occName, occDesc));
            } else if(occType.equals("P")) {
                predatorTypes.add(new Predator(null, occName, occDesc));
            } else if(occType.equals("F")) {
                faunaTypes.add(new Fauna(null, occName, occDesc));
            } else if(occType.equals("Q")) {
                triggerTypes.add(new Trigger(null, occName, occDesc));
            }
        }
    }
}
