package nz.ac.aut.ense701.gameModel;

/**
 * This class that store question information
 *
 * @author Sean Chang
 */
public class QuizQuestion {

    private int difficulty;
    private String question;
    private String questionOptions[];
    private int correctOptionIndex;
    private boolean complete;

    //Score Value for different level of quiz
    public static final int VALUE_LEVEL1_QUIZ = 10;
    public static final int VALUE_LEVEL2_QUIZ = 20;
    public static final int VALUE_LEVEL3_QUIZ = 30;

    public QuizQuestion(int difficulty, String question, String[] questionOptions, int correctOptionIndex) {
        this.difficulty = difficulty;
        this.question = question;
        this.questionOptions = questionOptions;
        this.correctOptionIndex = correctOptionIndex;
        this.complete = false;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setQuestionOptions(String[] options) {
        this.questionOptions = options;
    }

    public void setCorrectOptionIndex(int correctOptionIndex) {
        this.correctOptionIndex = correctOptionIndex;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getQuestion() {
        return question;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String[] getQuestionOptions() {
        return questionOptions;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    public boolean isComplete() {
        return this.complete;
    }

    /**
     * get score point you can earn from this question
     * @return score
     */
    public int getPointGain() {
        int score;
        if (this.difficulty == 1) {
            score = VALUE_LEVEL1_QUIZ;
        } else if (this.difficulty == 2) {
            score = VALUE_LEVEL2_QUIZ;
        } else {
            score = VALUE_LEVEL3_QUIZ;
        }
        return score;
    }

}
