
package networkClient;

import java.util.ArrayList;


public interface ClientInterface {
    
    public static ArrayList<String> DATARECEIVED = new ArrayList<>();
    
    public static ArrayList<String> DATASEND = new ArrayList<>();
   
    public void sendData();
    
    public void receiveData();
    
    //Client.getDebug()
    
    //Client.setDebug(boolean debug)
         
}
