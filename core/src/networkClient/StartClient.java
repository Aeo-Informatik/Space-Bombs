/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import gui.Constants;

/**
 *
 * @author qubasa
 */
public class StartClient {
    
    public static void main(String[] args) 
    {
        try
        {
            Client client = new Client(Constants.HOST, Constants.PORT);
            
            Client.DEBUG = true;
            client.receiveData();
            client.sendData("TEST");
            
            
        }catch(Exception e)
        {
            System.err.println("ERROR: Something went wrong in the StartClient class " + e);
            e.printStackTrace();
            System.exit(1);
        }
    }
    
}
