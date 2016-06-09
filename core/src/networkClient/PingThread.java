/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import gui.Constants;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author qubasa
 */
public class PingThread implements Runnable {

    private Socket socket;
    
    public PingThread(Socket socket)
    {
        this.socket = socket;
    }
    
    @Override
    public void run() 
    {
        try
        {
            while(!Thread.currentThread().isInterrupted())
            {

                PrintWriter print = new PrintWriter(socket.getOutputStream(), true);	

                //Debug
                if(Constants.CLIENTDEBUG)
                    System.out.println("Pinging server.");

                //Send ping to server
                print.println("PING");
                print.flush();
                
                Thread.sleep(5 * 1000); // seconds
            }
//        }catch(SocketException e)
//        {
//            System.err.println("ERROR: Receiving data failed because I couldn't reach the server " + e);
//            System.exit(1);
//            
        }catch(Exception e)
        {
            System.err.println("ERROR: Something went wrong in receiving data " + e);
            e.printStackTrace();
            System.exit(1);
        }
    }
    
}
