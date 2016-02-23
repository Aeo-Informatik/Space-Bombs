/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

/**
 *
 * @author qubasa
 */
public class ClientTest {
    
    public static void main(String[] args) {
        try{
            Client client = new Client("127.0.0.1", 4444);
            client.setDebug(true);

            //Send data to server
            ClientInterface.DATASEND.add("Hello and ");
            ClientInterface.DATASEND.add("welcome");
            client.sendData();

            //Receive data from server
            client.receiveData();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
