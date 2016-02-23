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


class SendThread implements Runnable
{       
        //Global variables & objects
        ArrayList<Socket> connections;
	
        
        //Constructor
	public SendThread(ArrayList<Socket> connections)
	{
                this.connections = connections;
	}
        
        
        @Override
	public void run() 
        {
            try{
                    while(true)
                    {
                        for(String msgToClient : ServerInterface.DATARECEIVED)
                        {
                            //Debug
                            if(new Server().getDebug())
                                System.out.println("Send string " + msgToClient);
                            
                            for(Socket socket : connections)
                            {
                                //Debug
                                if(new Server().getDebug())
                                    System.out.println("Client to send it to: " + socket.getInetAddress().getHostAddress());
                                
                                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                                
                                //Send message to client
                                printWriter.println(msgToClient);
                                printWriter.flush();
                            }
                        }
                    }
                    
		}catch(Exception e)
                {
                    e.printStackTrace();
                }	
	}
}

