/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;


/**
 *
 * @author plonies_d
 */
class ReceiveThread implements Runnable
{       
        //Global Variables & Objects
	Socket socket;
        BufferedReader receive;
	
        
        //Constructor
	public ReceiveThread(Socket sock) {
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
                    //Get data from server and parse it into an Object BufferedReader
                    receive = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                    String msgRecieved;

                    //Read all lines received from server
                    while((msgRecieved = receive.readLine())!= null)
                    {   
                        //Debug
                        if(Client.DEBUG)
                            System.out.println("Received from server: " + msgRecieved);

                        //Start to analyse received data and execute apropriate functions
                        new AnalyseData(socket).analyse(msgRecieved);
                    }
                }
                
            }catch(NullPointerException e)
            {
                System.err.println("Error: ReceiveThread() Socket is not defined" );
                
            }catch(Exception e)
            {
                e.printStackTrace();
            }
	}
}

