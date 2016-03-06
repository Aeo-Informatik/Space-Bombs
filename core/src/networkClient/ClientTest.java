/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import java.util.ArrayList;

/**
 *
 * @author qubasa
 */
public class ClientTest {
    
    public static void main(String[] args) {
        try{
            Client client = new Client("127.0.0.1", 13199);
            Client.DEBUG = true;

            client.receiveData();

            ArrayList<String> sendData = new ArrayList<>();
            
            
            for(int i=0; i < 10; i++)
            {
                sendData.add("Test " +i);
            }
            client.sendData(sendData);
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
