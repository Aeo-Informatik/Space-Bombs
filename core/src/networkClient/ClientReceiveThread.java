/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import gui.Constants;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author qubasa
 */
public class ClientReceiveThread implements Runnable {

    private Socket socket;
    
    //To be able to communicate between Threads
    protected static BlockingQueue queue = new ArrayBlockingQueue(4080);
    
    public ClientReceiveThread(Socket socket)
    {
        this.socket = socket;
    }
    
    @Override
    public void run() 
    {
        try
        {
            //Runs the whole time
            while(!Thread.currentThread().isInterrupted())
            {
                try
                {
                    //Get data from server and parse it into an Object BufferedReader
                    BufferedReader receive = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                    String dataReceived;

                    //Read all lines received from server
                    while((dataReceived = receive.readLine())!= null)
                    {   
                        //Debug
                        if(Constants.CLIENTDEBUG)
                            System.out.println("Received from server: " + dataReceived);
                        
                       //Adds the data to the BlockingQueue
                       queue.add(dataReceived);
                       Thread.sleep(100);
                    }
                    
                }catch(NullPointerException e)
                {
                    System.err.println("ERROR: ReceiveThread() Client disconnected closing receive thread");
                    Thread.currentThread().interrupt();
                }
            }
            
        }catch(SocketException e)
        {
            System.err.println("ERROR: Receiving data failed because I couldn't reach the server " + e);
            System.exit(1);
            
        }catch(Exception e)
        {
            System.err.println("ERROR: Something went wrong in receiving data " + e);
            e.printStackTrace();
            System.exit(1);
        }
    }
    
}
