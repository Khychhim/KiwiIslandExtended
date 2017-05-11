/*
 * This is a panel for mini game
 */
package nz.ac.aut.ense701.gui;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JRadioButton;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.QuizQuestion;

/**
 * @param Game game
 * @author Sean Chang
 */
public final class MiniGamePanel extends javax.swing.JPanel {

    private ArrayList<JRadioButton> radioButtions = new ArrayList<JRadioButton>();
    private QuizQuestion currentQuestion;
    private final Game game;

    /**
     * Creates new form MiniGamePanel
     */
    public MiniGamePanel(Game game) {
        initComponents();
        this.game = game;
        //select a random question
        currentQuestion = pickRandomQuestion();
        //set Question text label
        setQuestionLabel(currentQuestion.getQuestion());
        //set up radio buttons
        setUpRadioButtons(currentQuestion);
    }

    public boolean isCorrectAnswerOption() {
        boolean isCorrectAnswer = false;

        //get selected option index
        String optionStr = buttonGroup1.getSelection().getActionCommand();
        int selectedIndex = Integer.parseInt(optionStr);

        //Current question index
        int correctIndex = currentQuestion.getCorrectOptionIndex();
        if (selectedIndex == correctIndex) {
            isCorrectAnswer = true;
        }
        return isCorrectAnswer;
    }

    /**
     * set up Radio buttons radio buttons depends on how many options answer the
     * question has
     *
     * @param quizquestion
     */
    public void setUpRadioButtons(QuizQuestion quizquestion) {
        //group for radiobuttons

        int sizeOfAnswerOption = quizquestion.getQuestionOptions().length;
        String[] questions = quizquestion.getQuestionOptions();

        //set up radio button text
        for (int i = 0; i < sizeOfAnswerOption; i++) {
            radioButtions.get(i).setText(questions[i]);
        }
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabelQuestion = new javax.swing.JLabel();
        jPanelAnswerOptions = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jButtonEnter = new javax.swing.JButton();

        jLabel1.setText("Quiz");

        jLabelQuestion.setText("Question: ");

        radioButtions.add(jRadioButton1);
        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("jRadioButton1");
        jRadioButton1.setActionCommand("1");

        radioButtions.add(jRadioButton3);
        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("jRadioButton3");
        jRadioButton3.setActionCommand("3");

        radioButtions.add(jRadioButton4);
        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setText("jRadioButton4");
        jRadioButton4.setActionCommand("4");

        radioButtions.add(jRadioButton2);
        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("jRadioButton2");
        jRadioButton2.setActionCommand("2");

        jButtonEnter.setText("Enter");
        jButtonEnter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEnterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelAnswerOptionsLayout = new javax.swing.GroupLayout(jPanelAnswerOptions);
        jPanelAnswerOptions.setLayout(jPanelAnswerOptionsLayout);
        jPanelAnswerOptionsLayout.setHorizontalGroup(
            jPanelAnswerOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAnswerOptionsLayout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addGroup(jPanelAnswerOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4)
                    .addGroup(jPanelAnswerOptionsLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jButtonEnter)))
                .addContainerGap(146, Short.MAX_VALUE))
        );
        jPanelAnswerOptionsLayout.setVerticalGroup(
            jPanelAnswerOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAnswerOptionsLayout.createSequentialGroup()
                .addComponent(jRadioButton1)
                .addGap(6, 6, 6)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addComponent(jButtonEnter)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelAnswerOptions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelQuestion)
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(21, 21, 21)
                .addComponent(jLabelQuestion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelAnswerOptions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonEnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEnterActionPerformed
        System.out.println(isCorrectAnswerOption());
    }//GEN-LAST:event_jButtonEnterActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonEnter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelQuestion;
    private javax.swing.JPanel jPanelAnswerOptions;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    // End of variables declaration//GEN-END:variables
}
