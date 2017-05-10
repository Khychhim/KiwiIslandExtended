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
        for (int i = 0; i < questionList.getLength(); i++) {
            Node questionNode = questionList.item(i);
            Element questionElement = (Element) questionNode;

            //get difficulty
            Node difficultyNode = questionElement.getElementsByTagName("difficulty").item(0);
            Element difficultyElement = (Element) difficultyNode;
            System.out.println("Difficulty: " + difficultyElement.getTextContent());

            //get quizquestion
            Node quizquestionNode = questionElement.getElementsByTagName("quizquestion").item(0);
            Element quizquestionElement = (Element) quizquestionNode;
            System.out.println(quizquestionElement.getTextContent());

            //get aswers (There are 4 answers)
            for (int j = 0; j < 4; j++) {
                Node aswerNode = questionElement.getElementsByTagName("answer").item(j);
                Element answerElement = (Element) aswerNode;
                System.out.println("\tAnswer: " + answerElement.getTextContent());
            }

            //get correct answer
            Node correctNode = questionElement.getElementsByTagName("correct").item(0);
            Element correctElement = (Element) correctNode;
            System.out.println("Correct: " + correctElement.getTextContent() + "\n");

        }

    }

}
