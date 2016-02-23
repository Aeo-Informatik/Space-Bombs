/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import java.io.PrintWriter;
import java.net.ConnectException;
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
                        
                        //Debug
                        if(Client.getDebug())
                            System.out.println("Send to server: " + msgToServer);
                        
                        //Send string to server
			print.println(msgToServer);
			print.flush();
                    }
                    
                    //Clears the arraylist 
                    ClientInterface.DATASEND.clear();
                }
                
                System.err.println("Socket is not connected to server thats why SendThread also closed.");
                
            }catch(NullPointerException e){
                System.err.println("Error: SendThread() Socket is not defined");
                
            }catch(Exception e){
                e.printStackTrace();
            }
	}
}
