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


class ReceiveThread implements Runnable
{       
    //Global variables & objects
    ArrayList<Socket> sock;
    ArrayList<String> receivedData = new ArrayList<>();
        
    
    //Constructor
    public ReceiveThread(ArrayList<Socket> sock)
    {
        this.sock = sock;
    }
        
        
    @Override
    public void run() 
    {
        try{
            while(true)
            {
                //Iterate through the connected clients list
                for(int i=0; i < sock.size(); i++)
                {   
                    //Check if the client is connected
                    if(sock.get(i).isConnected())
                    {
                        //Receive data from connected client
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(sock.get(i).getInputStream()));		
                        String receivedMsg = null;
                            
                            //Read the data from the buffer and add it to the arraylist
                            while((receivedMsg = bufferedReader.readLine())!= null)
                            {
                                //Debug
                                if(Server.getDebug())
                                    System.out.println("Received: " + receivedMsg + " --from: " + sock.get(i).getInetAddress().getHostAddress());

                                //Add received data to arraylist
                                receivedData.add(receivedMsg);
                                System.out.println("test1");
                            }

                            
                            System.out.println("Open send thread");
                            //Opens one thread to send back the data to every client
                            SendThread send = new SendThread(sock, receivedData);
                            Thread thread = new Thread(send);
                            thread.start();
                    
                    System.out.println("Proceed with receive");
                            
                    }else
                    {
                        //Debug
                        if(Server.getDebug())
                            System.err.println("ReceiveThread(): Client disconnected with ip: " + sock.get(i).getInetAddress().getHostAddress());
                    
                    }
                    
                }
            }

            }catch(Exception e)
            {
                e.printStackTrace();
            }
    }
        
}
