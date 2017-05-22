package nz.ac.aut.ense701.gameModel;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nishan
 */
public class FacebookAPI {
    String accessToken ="EAACEdEose0cBALoULQD6MsDIgrngFrZCg7ZAVyyTUS5W5Y6ZBHcY8qsjFfFRt7oAuNgTldWp4RVGRPPyS9C8AnIEZBzXEZB6P3dSi3kBSHuwWziiCJ4FzWJdpMwJAE3T6eE2nJkRH73il5XZAXmC5R38WMW0khHZArQe3PLeoZCsibZBnXEXxIodk9xK2adRluF8ZD";
    FacebookClient fbclient = new DefaultFacebookClient(accessToken);
    String message ="";
    
    public void authinticate(){
        String domain = "http://www.google.co.nz/";
        String AppId ="153319715206825";
        String authUrl = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id="+AppId+"&redirect_uri="+domain+"&scope=user_about_me,"
                + "user_actions.books,user_actions.fitness,user_actions.music,user_actions.news,user_actions.video,user_activities,user_birthday,user_education_history,"
                + "user_events,user_photos,user_friends,user_games_activity,user_groups,user_hometown,user_interests,user_likes,user_location,user_photos,user_relationship_details,"
                + "user_relationships,user_religion_politics,user_status,user_tagged_places,user_videos,user_website,user_work_history,ads_management,ads_read,email,"
                + "manage_notifications,manage_pages,publish_actions,read_friendlists,read_insights,read_mailbox,read_page_mailboxes,read_stream,rsvp_event";
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get(authUrl);
        String accessToken;
        while(true){
            if(!driver.getCurrentUrl().contains("facebook.com")){
                String url = driver.getCurrentUrl();
                 accessToken = url.replaceAll(".*#access_token=(.+)&.*", "$1");
                 driver.quit();
                 FacebookClient fbClient = new DefaultFacebookClient(accessToken);
                User user = fbClient.fetchObject("me",User.class);
               
                message = user.getName();
                System.out.println(message);
                
            }
            
        }

    } 
    
    
    
}
