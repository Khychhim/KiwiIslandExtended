package nz.ac.aut.ense701.gameModel;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
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
    
    public static void main(String args[]) throws TwitterException{
 
        ConfigurationBuilder cb  =new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("GgtfYC6RjQqWW6YkXKSqT8baV")
                .setOAuthConsumerSecret("9ebwhD6zbQ7VI9BYeO4S3gtqnYfXpqjzMhtqKgalfJkhqMb88B")
                
                .setOAuthAccessToken("867915375500906496-BPkjFRJ7UexsiFGoZA6NvAI41y3LpZp")
                .setOAuthAccessTokenSecret("STY05KWxxShtZeuCPyp73GVehpeVi7tkpTDdvTz1tq7XQ");
        
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter4j.Twitter tw = tf.getInstance();
        

        
        //Posting on twitter
        
       Status stat = tw.updateStatus("Kiwi Island nishanjk");
       
       System.out.println("Twtiteer updated");        
               
    }
    
    
    
    
}
