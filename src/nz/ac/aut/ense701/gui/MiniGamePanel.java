/*
 * This is a panel for mini game
 */
package nz.ac.aut.ense701.gui;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.QuizQuestion;

/**
 * @param Game game
 * @author Sean Chang
 */
public final class MiniGamePanel extends javax.swing.JPanel {

    private ArrayList<QuizQuestion> quizQuestionList;
    private final Game game;

    /**
     * Creates new form MiniGamePanel
     */
    public MiniGamePanel(Game game) {
        initComponents();
        this.game = game;
        QuizQuestion theQuestion = pickRandomQuestion();
        setQuestionLabel(theQuestion.getQuestion());
    }

    /**
     * This method set the Question Label
     *
     * @param question
     */
    public void setQuestionLabel(String question) {
        jLabelQuestion.setText("Question: " + question);
    }

    /**
     * pick up a new question randomly which is not completed
     *
     * @param QuizQuestion
     */
    public QuizQuestion pickRandomQuestion() {
        Random rnd = new Random();
        int questionSize = game.quizQuestionList.size();
        QuizQuestion theQuestion;
        do {
            theQuestion = game.quizQuestionList.get(rnd.nextInt(questionSize));
        } while (theQuestion.isComplete());
        return theQuestion;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabelQuestion = new javax.swing.JLabel();
        jPanelRadionButtons = new javax.swing.JPanel();
        jButtonEnter = new javax.swing.JButton();

        jLabel1.setText("Quiz");

        jLabelQuestion.setText("Question: ");

        javax.swing.GroupLayout jPanelRadionButtonsLayout = new javax.swing.GroupLayout(jPanelRadionButtons);
        jPanelRadionButtons.setLayout(jPanelRadionButtonsLayout);
        jPanelRadionButtonsLayout.setHorizontalGroup(
            jPanelRadionButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelRadionButtonsLayout.setVerticalGroup(
            jPanelRadionButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 149, Short.MAX_VALUE)
        );

        jButtonEnter.setText("Enter");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelRadionButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabelQuestion))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addComponent(jButtonEnter)
                .addContainerGap(190, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelQuestion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelRadionButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonEnter)
                .addContainerGap(42, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonEnter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelQuestion;
    private javax.swing.JPanel jPanelRadionButtons;
    // End of variables declaration//GEN-END:variables
}
