/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Sean
 */
public class ReadQuizXML {

    private ArrayList<QuizQuestion> questionArrayList;

    public static void main(String[] args) {
        new ReadQuizXML();
    }

    /**
     * This is will return a ArrayList of Questions
     *
     * @return ArrayList<QuizQuestion>
     */
    public ArrayList<QuizQuestion> getQuestionArrayList() {
        return questionArrayList;
    }

    /**
     * ReadQuizXML method read quizXML.xml and store in questionArrayList
     */
    public ReadQuizXML() {
        File quizFile = new File("quizQuestions.xml");
        Document doc = null;

        DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = docBuildFactory.newDocumentBuilder();
            doc = docBuilder.parse(quizFile);
            doc.normalize();

        } catch (ParserConfigurationException a) {
            Logger.getLogger(ReadQuizXML.class.getName()).log(Level.SEVERE, null, a);
        } catch (SAXException b) {
            Logger.getLogger(ReadQuizXML.class.getName()).log(Level.SEVERE, null, b);
        } catch (IOException c) {
            Logger.getLogger(ReadQuizXML.class.getName()).log(Level.SEVERE, null, c);
        }

        //get Quiz
        NodeList quizList = doc.getElementsByTagName("quiz");
        Node quizRootNode = quizList.item(0);
        Element quizElement = (Element) quizRootNode;

        //get questions
        NodeList questionsList = quizElement.getElementsByTagName("questions");
        Node questionsNode = questionsList.item(0);
        Element questionsElement = (Element) questionsNode;

        //get question
        NodeList questionList = questionsElement.getElementsByTagName("question");

        //getting question contents
        for (int i = 0; i < questionList.getLength(); i++) {
            String difficulty;
            String quizQuestion;
            String[] answers;
            String correct;

            Node questionNode = questionList.item(i);
            Element questionElement = (Element) questionNode;

            //get difficulty content
            Node difficultyNode = questionElement.getElementsByTagName("difficulty").item(0);
            Element difficultyElement = (Element) difficultyNode;
            difficulty = difficultyElement.getTextContent();

            //get Quiz Question content
            Node quizquestionNode = questionElement.getElementsByTagName("quizquestion").item(0);
            Element quizquestionElement = (Element) quizquestionNode;
            quizQuestion = quizquestionElement.getTextContent();

            //get aswers contents
            NodeList answerList = questionElement.getElementsByTagName("answer");
            answers = new String[answerList.getLength()];
            for (int j = 0; j < answerList.getLength(); j++) {
                Node aswerNode = answerList.item(j);
                Element answerElement = (Element) aswerNode;
                answers[j] = answerElement.getTextContent();
            }

            //get correct answer content
            Node correctNode = questionElement.getElementsByTagName("correct").item(0);
            Element correctElement = (Element) correctNode;
            correct = correctElement.getTextContent();

            //setQuestion
            QuizQuestion question = new QuizQuestion(Integer.parseInt(difficulty), quizQuestion, answers, Integer.parseInt(correct));
            questionArrayList.add(question);

            //Debug - print all questions
            StringBuilder builder = new StringBuilder();
            for (String s : answers) {
                builder.append(s + ", ");
            }
            System.out.println("Difficulty: " + difficulty + "\nQuizQuestion: "
                    + "\nAnswers: " + builder.toString() + "\nCorrect: " + correct);

        }

    }

}
