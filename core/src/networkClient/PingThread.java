/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import gui.Constants;
import java.io.IOException;
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
            PrintWriter print = new PrintWriter(socket.getOutputStream(), true);
            
            //Debug
            if(Constants.CLIENTDEBUG)
                System.out.println("Pinging server every " + Constants.PINGDELAY + " seconds.");

            while(!Thread.currentThread().isInterrupted())
            {
                //Send ping to server
                print.println("PING");
                print.flush();
                
                Thread.sleep(Constants.PINGDELAY * 1000); // seconds
            }
            
        }catch(SocketException e)
        {
            System.err.println("ERROR: Pinging server failed because I couldn't reach the server " + e);
            System.exit(1);
            
        }catch(IOException | InterruptedException e)
        {
            System.err.println("ERROR: Something went wrong in pinging server " + e);
            System.exit(1);
        }
    }
    
}
