package nz.ac.aut.ense701.gameModel;

/**
 * Predator represents a predator on the island.
 * If more specific behaviour is required for particular predators, descendants 
 * for this class should be created
 * @author AS
 * @version July 2011
 */
public class Predator extends Fauna
{
      private boolean isKiwisInRange;
      private int rowAwayFromKiwi;
      private int coloumnAwayFromKiwi;
      
    /**
     * Constructor for objects of class Predator
     * @param pos the position of the predator object
     * @param name the name of the predator object
     * @param description a longer description of the predator object
     */
    public Predator(Position pos, String name, String description) 
    {
        super(pos, name, description);
        this.isKiwisInRange = false;
        this.coloumnAwayFromKiwi = 0;
        this.rowAwayFromKiwi = 0;
    } 
    
    public Predator(Position pos, String name, String description,boolean isKiwisInRange,int column,int row){
           super(pos, name, description);
           this.isKiwisInRange = isKiwisInRange;
           this.coloumnAwayFromKiwi = column;
           this.rowAwayFromKiwi = row;
    }
    
    public void setRowAwayFromKiwi(int row){
          this.rowAwayFromKiwi = row;
    }
    
    public int getRowAwayFromKiwi(){
          return this.rowAwayFromKiwi;
    }
    
        public void setcoloumnAwayFromKiwi(int column){
          this.coloumnAwayFromKiwi = column;
    }
    
    public int getColoumnAwayFromKiwi(){
          return this.coloumnAwayFromKiwi;
    }
    
    
    public void setKiwisInRange(Boolean isKiwisInRange){
          this.isKiwisInRange = isKiwisInRange;
    }
    
    public boolean isKiwisInRange(){
          return this.isKiwisInRange;
    }
    
    @Override
    public String getStringRepresentation() 
    {
        return "P";
    }    
}
