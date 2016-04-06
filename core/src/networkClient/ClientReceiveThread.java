/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import com.gdx.bomberman.ProcessData;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author qubasa
 */
public class ClientReceiveThread implements Runnable {

    private Socket socket;
    
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
                        if(networkClient.Client.DEBUG)
                            System.out.println("Received from server: " + dataReceived);

                        //Start to analyse received data and execute apropriate functions
                       new ProcessData().processReceivedData(dataReceived);
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
