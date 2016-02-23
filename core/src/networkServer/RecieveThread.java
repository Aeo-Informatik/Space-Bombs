/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;


class RecieveThread implements Runnable
{       
    //Global variables & objects
    Socket socket;
    ArrayList<String> receivedMessage = new ArrayList<>();
        
    //Constructor
    public RecieveThread(Socket socket)
    {
        this.socket = socket;
    }
        
        
    @Override
    public void run() 
    {
        try{
                //Receive data from connected client
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));		
                String messageString;
                          
                while(true)
                {
                    while((messageString = bufferedReader.readLine())!= null)
                    {
                        //Debug
                        if(Server.getDebug())
                            System.out.println("Received: " + messageString);
                            
                        //Add received data to arraylist
                        receivedMessage.add(messageString);
                    }
                }

            }catch(Exception e)
            {
                e.printStackTrace();
            }
    }
        
    public ArrayList<String> getReceivedMessage()
    {
        return receivedMessage;
    }     
}
