/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multithreading.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author plonies_d
 */
class RecieveThread implements Runnable
{
	Socket sock=null;
	BufferedReader recieve=null;
	
	public RecieveThread(Socket sock) {
		this.sock = sock;
	}//end constructor
	public void run() {
		try{
		recieve = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));//get inputstream
		String msgRecieved = null;
		while((msgRecieved = recieve.readLine())!= null)
		{
			System.out.println("From Server: " + msgRecieved);
			System.out.println("Please enter something to send to server..");
		}
		}catch(Exception e){System.out.println(e.getMessage());}
	}//end run
}//end class recievethread

