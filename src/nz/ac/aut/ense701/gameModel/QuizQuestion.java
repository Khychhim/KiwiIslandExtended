package nz.ac.aut.ense701.gameModel;

/**
 *
 * @author Sean Chang
 */
public class QuizQuestion {

    private int difficulty;
    private String question;
    private String questionOptions[];
    private int correctOptionIndex;

    //Score Value for different level of quiz
    public static final int VALUE_LEVEL1_QUIZ = 1;
    public static final int VALUE_LEVEL2_QUIZ = 2;
    public static final int VALUE_LEVEL3_QUIZ = 3;

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

}
