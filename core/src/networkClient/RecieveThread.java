/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author plonies_d
 */
class RecieveThread implements Runnable
{       
        //Global Variable
	Socket socket;
        ArrayList<String> receivedData = new ArrayList<>();
	
        //Constructor
	public RecieveThread(Socket sock) {
		this.socket = sock;
	}
        
        //Starts automatically when creating this object
	public void run() 
        {
            try 
            {
                //Get data from socket and parse it into an Object BufferedReader
		BufferedReader recieve = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		String msgRecieved;
                
                //Read all lines received from socket
		while((msgRecieved = recieve.readLine())!= null)
		{   
                    //Debug
                    System.out.println("From Server: " + msgRecieved);
                    
		}
                
            }catch(Exception e)
            {
                e.printStackTrace();
            }
	}
}

