/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;


/**
 *
 * @author plonies_d
 */
class RecieveThread implements Runnable
{       
        //Global Variables & Objects
	Socket socket;
        BufferedReader receive;
	
        //Constructor
	public RecieveThread(Socket sock) {
		this.socket = sock;
	}
        
        //Starts automatically when creating this object
        @Override
	public void run() 
        {
            try 
            {
                //Runs the whole time
                while(true)
                {
                    //Get data from socket and parse it into an Object BufferedReader
                    receive = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                    String msgRecieved;

                    //Read all lines received from socket
                    while((msgRecieved = receive.readLine())!= null)
                    {   
                        //Decode string to normal utf-8
                        String decodedMsg = new String(Base64.decode(msgRecieved));
                        
                        //Debug
                        System.out.println("Client received from Server: " + msgRecieved);

                        //Add received data to static arraylist
                        ClientInterface.DATARECEIVED.add(msgRecieved);
                    }
                }
                
            }catch(Exception e)
            {
                
            }
	}
}

