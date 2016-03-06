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
    Socket socket;
    ArrayList<String> receivedData = new ArrayList<>();
    ArrayList<Socket> socketList;
        
    
    //Constructor
    public ReceiveThread(Socket socket, ArrayList<Socket> socketList)
    {
        this.socket = socket;
        this.socketList = socketList;
    }
        
        
    @Override
    public void run() 
    {
        try{
            while(true)
            {
                 //Check if the client is connected
                if(socket.isConnected())
                {
                        //Receive data from connected client
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));		
                        String receivedMsg = null;
                            
                            //Read the data from the buffer and add it to the arraylist
                            while(!(receivedMsg = bufferedReader.readLine()).equals("EXIT"))
                            {
                                //Debug
                                if(Server.getDebug())
                                    System.out.println("From: " + socket.getInetAddress().getHostAddress());
                                    System.out.println("Received: " + receivedMsg);

                                //Add received data to arraylist
                                receivedData.add(receivedMsg);
                            }

                            
                            //Opens one thread to send back the data to every client
                            SendThread send = new SendThread(socketList, receivedData);
                            Thread thread = new Thread(send);
                            thread.start();
                            
                }else
                {
                    System.err.println("ReceiveThread(): Client disconnected with ip: " + socket.getInetAddress().getHostAddress());
                }
            }

        }catch(Exception e)
        {
            try 
                {
                    throw e;
                    
                } catch (Exception ex) 
                {
                    System.err.println("Couldnt throw exception in ReceiveThread printed it instead.");
                    ex.printStackTrace();
                }
        }
    }
        
}
