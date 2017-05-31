/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;
import java.util.Timer;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.GameState;
import nz.ac.aut.ense701.gameModel.Score;

/**
 *
 * @author Logan
 */
public class MiniGameGuess extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    private HashMap<String, ImageIcon> imageMap;
    private final String path = "src/nz/ac/aut/ense701/SilohuetteImages/";
    private final String[] animalNames = {"black-robin", "brown-kiwi", "cat", "kiore", "little-spotted-kiwi", "oyster-catcher", "possum",
        "rat", "stewart-island-fernbird", "stoat", "tui", "white-heron"};
    private final Random random;
    private ArrayList<String> animalArrayList;
    private ArrayList<String> questions;
    private String answer;
    private Game game;
    private KiwiCountUI gui;

    /**
     * Creates new form MiniGameGuess
     */
    public MiniGameGuess(KiwiCountUI gui, Game game) {
        initComponents();
        //Initailize
        this.game = game;
        this.gui = gui;
        random = new Random();
        questions = new ArrayList<String>();
        animalArrayList = new ArrayList<String>();
        animalArrayList.addAll(Arrays.asList(animalNames));
    }

    public MiniGameGuess() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method will start the Guess Game
     */
    public void start() {
        //load Images
        loadImage();
        //select one animal as answer
        pickAnswer();
        //randomly pick 2 question
        selectQuestions();
        //shuffle
        Collections.shuffle(questions);

        //set Question label
        lblQustion.setText("Which one is " + answer + "?");
        //set images
        lblImage1.setIcon(imageMap.get(questions.get(0) + "-silhouette"));
        lblImage2.setIcon(imageMap.get(questions.get(1) + "-silhouette"));
        lblImage3.setIcon(imageMap.get(questions.get(2) + "-silhouette"));
        //set text as reference
        lblImage1.setText(questions.get(0));
        lblImage2.setText(questions.get(1));
        lblImage3.setText(questions.get(2));

        //setup Frame
        setTitle("Mini Game Guess");
        setDefaultCloseOperation(0);
        setVisible(true);
        pack();
    }

    /**
     * This method load image to ImageMap
     */
    public void loadImage() {
        //initialize HashMap
        if (imageMap == null) {
            Hashtable<String, ImageIcon> table = new Hashtable<String, ImageIcon>();
            imageMap = new HashMap(table);
        }
        //add images
        for (int i = 0; i < animalNames.length; i++) {
            //original image
            String name = animalNames[i];
            Image image = getImage(name);
            //shillhouette image
            String silhouetteName = animalNames[i] + "-silhouette";
            Image silhouetteImage = getImage(silhouetteName);
            //add to hash map
            imageMap.put(name, new ImageIcon(image));
            imageMap.put(silhouetteName, new ImageIcon(silhouetteImage));
        }
    }

    /**
     * This method gets image
     *
     * @param fileName
     * @return image
     */
    private Image getImage(String fileName) {
        Image image = null;

        //load image
        try {
            image = ImageIO.read(new File(path + fileName + ".png"));
        } catch (IOException ex) {
            System.out.println("Image Not Found." + ex);
        }
        return image;
    }

    /**
     * This method randomly select different names from animalNames and it's not
     * duplicate
     *
     */
    private void selectQuestions() {
        do {
            //get animal name randomly
            String name = animalNames[random.nextInt(animalNames.length)];
            if (!questions.contains(name)) {
                questions.add(name);
            }
        } while (questions.size() < 3);
    }

    /**
     * This method select a random animal name from animalArraylist, And add to
     * questionsArraylist, And set answer
     */
    private void pickAnswer() {
        int index = random.nextInt(animalArrayList.size());
        String animalName = animalArrayList.get(index);
        questions.add(animalName);
        this.answer = animalName;
    }

    /**
     * This method compare the input parameter against answer
     *
     * @param selected
     * @return isTheSame with answer
     */
    private boolean checkAnswer(String selected) {
        return selected.equals(answer);
    }

    /**
     * This method show message
     *
     * @param isCorrect
     * @param animal
     */
    private void showMessage(boolean isCorrect, String animal) {
        if (isCorrect) {
            JOptionPane.showMessageDialog(this, "Great, You have a right guess.\n\nYou have select: " + animal + "\n\n You have eran: " + Score.VALUE_GUESS_CORRECT);
        } else {
            JOptionPane.showMessageDialog(this, "Sorry, You have a wrong guess.\n\nYou have select: " + animal);
        }
    }

    /**
     * This method add score when have a correct guess
     *
     * @param isCorrect - boolean
     */
    private void addScore(boolean isCorrect) {
        if (isCorrect) {
            game.score.addScore(Score.VALUE_GUESS_CORRECT);
        }
    }

    /**
     * This method remove answer from arrayList when play have a right guess
     */
    private void remvoeAnswer(boolean isCorrect) {
        if (isCorrect) {
            //remove answer from arraylist
            animalArrayList.remove(answer);
        }
    }

    /**
     * This method clear current guess game Set game status notify all listeners
     */
    private void exit() {
        //clear questions
        questions.clear();
        answer = null;

        //set invisible
        this.setVisible(false);

        //set game status
        game.setGameState(GameState.PLAYING);

        //start timer
        gui.setEnabled(true);
        gui.toFront();
        game.timer = new Timer();
        game.startTimer();

    }

    /**
     * This method return a HashMap of image
     *
     * @return imageMape
     */
    public HashMap<String, ImageIcon> getImageHashMap() {
        return imageMap;
    }

    /**
     * This method set the label border
     *
     * @param label
     */
    public void mouseEnter(JLabel label) {
        Border border = BorderFactory.createLineBorder(Color.YELLOW, 5);
        label.setBorder(border);
    }

    /**
     * This method set the border to null
     *
     * @param label
     */
    public void mouseExit(JLabel label) {
        label.setBorder(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblImage1 = new javax.swing.JLabel();
        lblImage2 = new javax.swing.JLabel();
        lblImage3 = new javax.swing.JLabel();
        lblQustion = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(780, 350));
        setResizable(false);
        setSize(new java.awt.Dimension(0, 0));

        lblImage1.setText("Image 1");
        lblImage1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImage1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblImage1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblImage1MouseExited(evt);
            }
        });

        lblImage2.setText("Image 2");
        lblImage2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImage2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblImage2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblImage2MouseExited(evt);
            }
        });

        lblImage3.setText("Image 3");
        lblImage3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImage3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblImage3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblImage3MouseExited(evt);
            }
        });

        lblQustion.setFont(new java.awt.Font("DFKai-SB", 3, 18)); // NOI18N
        lblQustion.setText("Which one is the Kiwi?");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblQustion, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblImage2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblImage3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(lblQustion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImage3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblImage2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblImage1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImage1MouseClicked
        //check answer and show message
        boolean isCorrect = checkAnswer(lblImage1.getText());
        addScore(isCorrect);
        showMessage(isCorrect, lblImage1.getText());
        remvoeAnswer(isCorrect);
        exit();
    }//GEN-LAST:event_lblImage1MouseClicked

    private void lblImage2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImage2MouseClicked
        //check answer and show message
        boolean isCorrect = checkAnswer(lblImage2.getText());
        addScore(isCorrect);
        showMessage(isCorrect, lblImage2.getText());
        remvoeAnswer(isCorrect);
        exit();
    }//GEN-LAST:event_lblImage2MouseClicked

    private void lblImage3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImage3MouseClicked
        //check answer and show message
        boolean isCorrect = checkAnswer(lblImage3.getText());
        addScore(isCorrect);
        showMessage(isCorrect, lblImage3.getText());
        remvoeAnswer(isCorrect);
        exit();
    }//GEN-LAST:event_lblImage3MouseClicked

    private void lblImage1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImage1MouseEntered
        mouseEnter(lblImage1);
    }//GEN-LAST:event_lblImage1MouseEntered

    private void lblImage1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImage1MouseExited
        mouseExit(lblImage1);
    }//GEN-LAST:event_lblImage1MouseExited

    private void lblImage2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImage2MouseEntered
        mouseEnter(lblImage2);
    }//GEN-LAST:event_lblImage2MouseEntered

    private void lblImage2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImage2MouseExited
        mouseExit(lblImage2);
    }//GEN-LAST:event_lblImage2MouseExited

    private void lblImage3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImage3MouseEntered
        mouseEnter(lblImage3);
    }//GEN-LAST:event_lblImage3MouseEntered

    private void lblImage3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImage3MouseExited
        mouseExit(lblImage3);
    }//GEN-LAST:event_lblImage3MouseExited

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblImage1;
    private javax.swing.JLabel lblImage2;
    private javax.swing.JLabel lblImage3;
    private javax.swing.JLabel lblQustion;
    // End of variables declaration//GEN-END:variables
}
