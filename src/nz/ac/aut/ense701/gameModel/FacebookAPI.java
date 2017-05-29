package nz.ac.aut.ense701.gameModel;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.restfb.types.FacebookType;
import javax.swing.JOptionPane;

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
    String accessToken ="EAACEdEose0cBAGrnNC2Hh2MBm5LuV6IxFsp8O9ZBLLAxZCPeza8LBsyPqbVmRol5jKav5F23pLZC3uO3cxVE3lAyc5uqJ0omXuMgPOsP6ml3HCRkFVkdMaM95u0qc11ecVW7naLzZCnQgk05yMUZC436ajHCUOZBd7CFGhg3fk8Sdj5xs2OZBeErxW6iYa5YHcZD";
    String message ="";
    
    public void authinticate(){
        String domain = "http://www.google.co.nz/";
        String AppId ="153319715206825";
        String authUrl = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id="+AppId+"&redirect_uri="+domain+"&scope=user_about_me";

        
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get(authUrl);
        String accessToken;
        while(true){
            try{
                if(!driver.getCurrentUrl().contains("facebook.com")){
                String url = driver.getCurrentUrl();
                 accessToken = url.replaceAll(".*#access_token=(.+)&.*", "$1");
                 
                 
                 
                 driver.close();
                String FbMessage = JOptionPane.showInputDialog("Enter to update status to facebook");
                
                 
                 Object[] options = {"OK"};
                  
                int option  = JOptionPane.showOptionDialog(null, 
                    null, "Game over!", 
                    JOptionPane.PLAIN_MESSAGE,JOptionPane.
                            INFORMATION_MESSAGE, null, options, options[0]);
                if(option == JOptionPane.OK_OPTION){
                    break;
                    
                }
            
                FacebookClient fbClient = new DefaultFacebookClient(accessToken);
                FacebookType publishMessageResponse = fbClient.publish("me/feed", FacebookType.class, Parameter.with("message", "Testing application version 1.0"));
                 User user = fbClient.fetchObject("me",User.class);

                //message = user.getName();
                //System.out.println(message);
                }       
            }
            catch(org.openqa.selenium.WebDriverException e){
                //System.out.println("Closed facebook down");
            }
//            catch(com.restfb.exception.FacebookOAuthException e){
//                
//            }

        }
      

    } 
    public void post(){
         FacebookClient fbClient = new DefaultFacebookClient(accessToken);
         fbClient.publish("me/finsh", FacebookType.class, Parameter.with("message", "score is: "));
    }
      
}
