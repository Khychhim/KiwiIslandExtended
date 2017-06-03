package nz.ac.aut.ense701.gameModel;

import java.io.Serializable;

public class Trigger extends Occupant implements Serializable{

    /**
     * Create a Trigger on the map
     *
     * @param pos the position of the trigger
     * @param name the name of the trigger
     * @param description a more detailed description of trigger
     */
    public Trigger(Position position, String name, String description) {
        super(position, name, description);
    }

    
    @Override
    public String getStringRepresentation() {
        return "Q";
    }
}
