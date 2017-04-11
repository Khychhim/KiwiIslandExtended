/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

/**
 *
 * @author joshl
 */
public class Score {
    private int score;
    
    //Score Values for each event
    public static final int VALUE_HARARD_DEATH = 100;
    public static final int VALUE_HAZARD_BREAK_TRAP = 40;
    public static final int VALUE_HAZARD_NON_FATAL = 30;
    public static final int VALUE_KIWI_COUNTED = 50;
    public static final int VALUE_PREDATOR_TRAPPED = 40;
    public static final int VALUE_KIWI_EATEN = 50;
    //Score Values for end game multiplied by percentage completed
    public static final int SURVIVED = 100;
    public static final int KIWIS_COUNTED = 150;
    public static final int PREDATORS_TRAPPED = 150;
    public static final int REMAINING_STAMINA = 100;
    //public static final int TRASH_COLLECTED = 200; //Not yet implemented
    
    public Score() {
        score = 0;
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public void addScore(int add) {
        score += Math.abs(add);
    }
    
    public void subtractScore(int subtract) {
        score -= Math.abs(subtract);
    }
}
