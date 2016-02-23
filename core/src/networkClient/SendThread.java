/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import java.io.PrintWriter;
import java.net.Socket;


class SendThread implements Runnable
{
	Socket socket;
	
	public SendThread(Socket socket)
	{
		this.socket = socket;
	}
        
        @Override
	public void run(){
            try{
                
		while(socket.isConnected())
		{
                    PrintWriter print = new PrintWriter(socket.getOutputStream(), true);	
                    
                    for(String msgToServer : ClientInterface.DATASEND){ 
                        
                        //Send string to server
			print.println(msgToServer);
			print.flush();
                    }
                    
                    //Clears the arraylist 
                    ClientInterface.DATASEND.clear();
                }
                
                //Debug
                if(socket.isConnected())
                    System.err.println("Socket closed thats why SendThread closed also.");
                
            }catch(Exception e)
            {
                
            }
	}
}
