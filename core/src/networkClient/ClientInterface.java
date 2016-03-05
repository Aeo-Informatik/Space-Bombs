
package networkClient;

import java.util.ArrayList;


public interface ClientInterface {
    
    public static ArrayList<String> DATASEND = new ArrayList<>();
   
    public static int PLAYERID = 0;
    
    public void sendData();
    
    public void receiveData();
    
    //Client.getDebug()
    
    //Client.setDebug(boolean debug)
         
}
