/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 *
 * @author joshl
 */
public class Score {
    private int score;
    
    public Score() {
        score = 0;
    }
    
    public JPanel getScoreBox(int width, int height) {
        JPanel scoreBox = new JPanel(new BorderLayout());
        scoreBox.setPreferredSize(new Dimension(width, height));
        scoreBox.setBorder(new BevelBorder(BevelBorder.RAISED));
        scoreBox.setForeground(Color.LIGHT_GRAY);
        
        JLabel scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setPreferredSize(new Dimension(width, height));
        
        scoreBox.add(scoreLabel);
        
        return scoreBox;
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
