/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.util.Timer;
/**
 *
 * @author Logan
 */
public class NotificationPanel extends javax.swing.JPanel {

    public MiniGameQuizPanel miniGamePanel;
    public KiwiCountUI gui;
    public boolean res;
    /**
     * Creates new form NotificationPanel
       * @param miniGamePanel MiniGamePanel object to use
       * @param gui KiwiCountUI object to use
     */
    public NotificationPanel(MiniGameQuizPanel miniGamePanel, KiwiCountUI gui) {
        initComponents();
        this.miniGamePanel = miniGamePanel;
        this.gui = gui;
    }

    /**
     * Display text to panel
     */
    public void displayToPanel(){
        String display;
        int correctAnswerIndex = miniGamePanel.currentQuestion.getCorrectOptionIndex() - 1;
        
        String playerAnswer = miniGamePanel.getPlayerChosenAnswer();
        String correctAnswer = miniGamePanel.currentQuestion.getQuestionOptions()[correctAnswerIndex];

        this.jLabelQuizLevel.setText("Quiz Level: \t" + miniGamePanel.currentQuestion.getDifficulty());
        this.jLabelPlayerAnswer.setText("Your Answer: \t" + playerAnswer);
        
        if(playerAnswer.equalsIgnoreCase(correctAnswer)){
              this.jLabelAnswer.setText("Correct Answer!! \t");
        }else{
              this.jLabelAnswer.setText("Wrong Answer!! \t");
        }
        if(miniGamePanel.reward.equalsIgnoreCase("score")){
              display = "You earn a score of "+ Integer.toString(miniGamePanel.score);
        }else if(miniGamePanel.reward.equalsIgnoreCase("predator")){
              display = "The number of predator has been reduce by 1";
        }else if(miniGamePanel.reward.equalsIgnoreCase("stamina")){
              display = "Your stamina has been increaseto max";
        }else if(miniGamePanel.reward.equalsIgnoreCase("food")){
              display = "A food has magically spawn";
        }else{
              display = "You have earn nothing!";
        }
        this.jLabelScoreEarn.setText(display);    
       
        miniGamePanel.setVisible(false);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
      // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
      private void initComponents() {

            jLabelScoreEarn = new javax.swing.JLabel();
            jLabelAnswer = new javax.swing.JLabel();
            jButtonExit = new javax.swing.JButton();
            jLabelQuizLevel = new javax.swing.JLabel();
            jLabelPlayerAnswer = new javax.swing.JLabel();

            setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

            jLabelScoreEarn.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
            jLabelScoreEarn.setText("Earn");

            jLabelAnswer.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
            jLabelAnswer.setText("Correct Answer:");

            jButtonExit.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
            jButtonExit.setText("Exit");
            jButtonExit.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jButtonExitActionPerformed(evt);
                  }
            });

            jLabelQuizLevel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
            jLabelQuizLevel.setText("Quiz Level: ");

            jLabelPlayerAnswer.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
            jLabelPlayerAnswer.setText("Your Answer: ");

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addComponent(jLabelPlayerAnswer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                              .addComponent(jLabelAnswer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                              .addComponent(jLabelScoreEarn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                              .addComponent(jLabelQuizLevel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                  .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(154, 154, 154))
            );
            layout.setVerticalGroup(
                  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabelQuizLevel)
                        .addGap(52, 52, 52)
                        .addComponent(jLabelPlayerAnswer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelAnswer)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelScoreEarn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                        .addComponent(jButtonExit)
                        .addContainerGap())
            );
      }// </editor-fold>//GEN-END:initComponents

    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExitActionPerformed
        //Close the Notification Panel
        miniGamePanel.resultFrame.dispose();
        gui.setEnabled(true);
        gui.toFront();
        gui.game.timer =  new Timer();
        gui.game.startTimer();        
    }//GEN-LAST:event_jButtonExitActionPerformed


      // Variables declaration - do not modify//GEN-BEGIN:variables
      private javax.swing.JButton jButtonExit;
      private javax.swing.JLabel jLabelAnswer;
      private javax.swing.JLabel jLabelPlayerAnswer;
      private javax.swing.JLabel jLabelQuizLevel;
      private javax.swing.JLabel jLabelScoreEarn;
      // End of variables declaration//GEN-END:variables
}
