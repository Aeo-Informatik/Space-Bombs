/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import com.badlogic.gdx.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

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
            while(socket.isConnected())
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
                    new AnalyseData(socket).analyse(dataReceived);
                }
            }
            
            System.err.println("NOTE: Receive thread closed because there is no connection to the server.");
            
        }catch(Exception e)
        {
            System.err.println("ERROR: Something went wrong in receiving data " + e);
        }
    }
    
}
