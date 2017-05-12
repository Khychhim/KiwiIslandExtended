/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

import java.util.TimerTask;
import static nz.ac.aut.ense701.gameModel.Game.PREDATOR_TIME;

/**
 *
 * @author Logan
 */
public class PredatorTimerTask extends TimerTask {

    private boolean hasStarted = false;
    private Game game;

    public PredatorTimerTask(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        this.hasStarted = true;
        game.movePredators();
    }

    public boolean hasRunStarted() {
        return this.hasStarted;
    }
}
