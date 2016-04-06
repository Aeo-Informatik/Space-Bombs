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
public class StartClientTest 
{
    
    public static void main(String[] args) 
    {
        Client client = new Client(Constants.HOST, Constants.PORT);
        
        Client.DEBUG = true;
        client.receiveData();
        client.sendData("TEST");
    }
    
}
