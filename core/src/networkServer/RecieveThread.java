/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author plonies_d
 */
class RecieveThread implements Runnable
{
	Socket clientSocket=null;
	BufferedReader brBufferedReader = null;
	ArrayList<String> receivedMessage = new ArrayList<>();
        
	public RecieveThread(Socket clientSocket)
	{
		this.clientSocket = clientSocket;
	}//end constructor
        
        
	public void run() 
        {
            try{
                    brBufferedReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));		

                    String messageString;
                    while(true)
                    {
                        while((messageString = brBufferedReader.readLine())!= null)
                        {
                            System.out.println("Client: " + messageString);
                                if(messageString.equals("EXIT"))
                                {
                                        System.out.println("Exited with command EXIT");
                                        break;
                                    
                                }

                                receivedMessage.add(messageString);
                        }

                        this.clientSocket.close();
                        System.exit(0);
                    }

                }
            catch(Exception ex)
            {
                System.out.println(ex.getMessage());
            }
        }
        
       public ArrayList<String> getReceivedMessage(){
           return receivedMessage;
       }
        
}//end class RecieveThread
