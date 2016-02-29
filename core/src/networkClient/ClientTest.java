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
            Client client = new Client("10.5.34", 4444);
            client.setDebug(true);

            client.receiveData();
            if(Sockets.isSocketConnected())
            {
                for(int i=0; i < 10; i++)
                {
                    ClientInterface.DATASEND.add("Test" +i);
                }
                client.sendData();
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
