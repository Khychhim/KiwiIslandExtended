package nz.ac.aut.ense701.gameModel;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import twitter4j.conf.ConfigurationBuilder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nishan
 */
public class TwitterAPI {
    
    
    /**
     * Method accesses the kiwiIsland account and posts scores and achievements
     * to Twitter.
     * @param tweet
     */
    public void postToTwitter(String tweet, String players_name){
            ConfigurationBuilder cb  =new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("JDGhcaILLaPmeKw4MMPWr3BLr")
                .setOAuthConsumerSecret
        ("DL9esT3xtarLqnsbdAuT5nVRQra5TnNNmZnxvuGI3UcKK9oiyv")
                .setOAuthAccessToken
        ("869360220857749508-YGdR70eV31UxJM1VzONux4sLHGRUUMb")
                .setOAuthAccessTokenSecret
        ("q6Mp0GEkUsw6Elkk8qczJReE4s6AR9oRqRgwx0qO9Wvn9");
        
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter4j.Twitter tw = tf.getInstance();
 
        String message = players_name +" just finished playing"
                + " KiwiIsland!! Their game score was: ";
        message += tweet;
        message += " and they unlocked the following achievements: ";
        
        //assigning the game achievements for tweet.
        GameAchievement game =  new GameAchievement();
        if(!game.check_if_kiwiaward()){
            message += "-HERRO-";
        }
        if(!game.check_if_tavelaward()){
            message += "-TRAVELLER-";
        }
        if(!game.check_if_wonaward()){
            message += "-SURVIVOR-";
        }
        try {
            //Posting on twitter

            Status stat = tw.updateStatus(message);
        } catch (TwitterException ex) {
            Logger.getLogger(TwitterAPI.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
       
       System.out.println("Twtiteer updated");  
        Object[] options = {"OK"};
                  
                int tweeted  = JOptionPane.showOptionDialog(null, 
                    "Your Game score and Achievements have been posted"
                            + " to KiwiIsland twitter "
                            + "page!\nhttps://twitter.com/KiwiIslandSE",
                    "Twitter", 
                    JOptionPane.PLAIN_MESSAGE,JOptionPane.
                            INFORMATION_MESSAGE, null, options, options[0]);
        
    }

  
}
