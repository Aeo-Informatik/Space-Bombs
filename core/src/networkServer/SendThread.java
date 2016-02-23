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
class SendThread implements Runnable
{
        ArrayList<Socket> connections;
	
	public SendThread(ArrayList<Socket> connections)
	{
                this.connections = connections;
	}
        
	public void run() {
		try{
                    while(true)
                    {
                        System.out.println("Please enter something to send back to client..");
                        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));//get userinput
                        String msgToClientString = input.readLine();//get message to send to client
                            
                        for(Socket c : connections)
                        {
                            PrintWriter pwPrintWriter = new PrintWriter(new OutputStreamWriter(c.getOutputStream()));//get outputstream
                            
                            pwPrintWriter.println(msgToClientString);//send message to client with PrintWriter
                            pwPrintWriter.flush();//flush the PrintWriter
                        }
                    }
		}
		catch(Exception ex){System.out.println(ex.getMessage());}	
	}//end run
}//end class SendThread

