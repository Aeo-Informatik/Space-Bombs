
package networkClient;

import java.util.ArrayList;

public interface ClientInterface {
    
    //Add data to send like that Client.DATASEND("yourdata");
    public void sendData(ArrayList<String> dataToSend);
    
    public void receiveData();
      
}
