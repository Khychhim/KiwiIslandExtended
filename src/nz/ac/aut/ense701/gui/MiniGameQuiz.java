/*
 * This is a panel for mini game
 */
package nz.ac.aut.ense701.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.QuizQuestion;

/**
 *
 * @author Sean Chang
 */
public final class MiniGameQuiz extends javax.swing.JPanel {

    private ArrayList<JRadioButton> radioButtions = new ArrayList<JRadioButton>();
    public QuizQuestion currentQuestion;
    private int currentQuestionIndex;
    private final Game game;
    public JFrame resultFrame;
    public int score = 0;
    public KiwiCountUI gui;
    String reward;

    /**
     * Creates new form MiniGamePanel
     *
     * @param gui KiwiCountUI object to use
     * @param game game object to use
     */
    public MiniGameQuiz(KiwiCountUI gui, Game game) {
        initComponents();
        this.game = game;
        this.gui = gui;
        //select a random question
        currentQuestion = pickRandomQuestion();
        //set Question text label
        setQuestionLabel(currentQuestion.getQuestion());
        //set up radio buttons
        setUpRadioButtons(currentQuestion);
        start();
        reward = "none";
    }

    public boolean isOptionCorrectAnswer() {
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
            radioButtions.get(i).setVisible(true);
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
     * @return a quiz question
     */
    public QuizQuestion pickRandomQuestion() {
        Random rnd = new Random();
        int questionSize = game.quizQuestionList.size();
        QuizQuestion theQuestion;
        do {
            currentQuestionIndex = rnd.nextInt(questionSize);
            theQuestion = game.quizQuestionList.get(currentQuestionIndex);
        } while (theQuestion.isComplete());
        return theQuestion;
    }

    public void launchResult() {
        dispose();
        //setup Mini game panel
        NotificationPanel notificationPanel = new NotificationPanel(this, gui);

        //setup Frame
        resultFrame = new JFrame("Result");
        resultFrame.add(notificationPanel);
        resultFrame.setSize(notificationPanel.getSize());
        resultFrame.setVisible(true);
        resultFrame.pack();
        resultFrame.setDefaultCloseOperation(0);
        resultFrame.setLocation(dim.width / 2 - resultFrame.getWidth() / 2, dim.height / 2 - resultFrame.getHeight() / 2);

        notificationPanel.displayToPanel();
    }

    /**
     * This will return players chosen answer
     *
     * @return String
     */
    public String getPlayerChosenAnswer() {
        String optionStr = buttonGroup1.getSelection().getActionCommand();
        int selectedIndex = Integer.parseInt(optionStr);
        String playerAnswer = currentQuestion.getQuestionOptions()[selectedIndex - 1];
        return playerAnswer;
    }

    /**
     * This method will start the Quiz
     */
    public void start() {
        //setup Frame
        frame = new JFrame("Mini Game Quiz");
        frame.add(this);
        frame.setVisible(true);
        frame.pack();
        frame.setDefaultCloseOperation(0);
        frame.setLocation(dim.width / 2 - frame.getWidth() / 2, dim.height / 2 - frame.getHeight() / 2);
    }

    /**
     * This method close Quiz Mini Game
     */
    public void dispose() {
        this.frame.dispose();
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
        jLabelQuestion = new javax.swing.JLabel();
        jPanelAnswerOptions = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jButtonEnter = new javax.swing.JButton();

        jLabelQuestion.setText("Question: ");

        radioButtions.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setVisible(false);
        jRadioButton1.setText("jRadioButton1");
        jRadioButton1.setActionCommand("1");

        radioButtions.add(jRadioButton2);
        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setVisible(false);
        jRadioButton2.setText("jRadioButton2");
        jRadioButton2.setActionCommand("2");

        radioButtions.add(jRadioButton3);
        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setVisible(false);
        jRadioButton3.setText("jRadioButton3");
        jRadioButton3.setActionCommand("3");

        radioButtions.add(jRadioButton4);
        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setVisible(false);
        jRadioButton4.setText("jRadioButton4");
        jRadioButton4.setActionCommand("4");

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
                .addContainerGap()
                .addGroup(jPanelAnswerOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAnswerOptionsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonEnter)
                .addGap(22, 22, 22))
        );
        jPanelAnswerOptionsLayout.setVerticalGroup(
            jPanelAnswerOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAnswerOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton1)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton2)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton3)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton4)
                .addGap(18, 18, 18)
                .addComponent(jButtonEnter)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(jPanelAnswerOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(106, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabelQuestion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabelQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelAnswerOptions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(27, 27, 27))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonEnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEnterActionPerformed
        //set question is complete and add score
        if (isOptionCorrectAnswer()) {
            game.quizQuestionList.get(currentQuestionIndex).setComplete(true);
            // get current reward
            reward = game.quizQuestionList.get(currentQuestionIndex).getReward();            
            // get score
            this.score = currentQuestion.getPointGain();
            //set reward for game
            game.setReward(reward, score);
        }
        launchResult();
    }//GEN-LAST:event_jButtonEnterActionPerformed

    private JFrame frame;
    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonEnter;
    private javax.swing.JLabel jLabelQuestion;
    private javax.swing.JPanel jPanelAnswerOptions;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    // End of variables declaration//GEN-END:variables
}
