/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 *
 * @author qubasa
 */
public class ServerLobby implements Runnable
{
    //Variables
    private int maxConnections = -1;
    private ServerSocket serverSocket;
    
    public ServerLobby(int maxConnections, ServerSocket serverSocket)
    {
        this.maxConnections = maxConnections;
        this.serverSocket = serverSocket;
    }
    
    
    @Override
    @SuppressWarnings("null")
    public void run() 
    {
        //Debug
        System.out.println("SERVER: Listening & accepting connections on port " + serverSocket.getLocalPort() + "\n");
        
        try
        {
            //Start accepting connections till maxConnections are reached
            for(int i = 0; maxConnections > i; i++)
            {   
                Socket clientSocket = null;

                //Try connecting till clientSocket has been bound to a client
                while(clientSocket == null)
                {
                    try
                    {
                        //Stops thread
                        if(Thread.currentThread().isInterrupted())
                        {
                            break;
                        }

                         //Check for clients that disconnect from lobby
                        for(int b=0;Server.getClientList().size() > b; b++)
                        {
                            try
                            {
                                Socket checkSocket = Server.getClient(b);

                                checkSocket.setSoTimeout(500); // Milliseconds
                                
                                BufferedReader receive = new BufferedReader(new InputStreamReader(checkSocket.getInputStream()));
                                String dataReceived;

                                //Opens tcp session to client if client disconnects readLine() returns null
                                if((dataReceived = receive.readLine())!= null)
                                {
                                    continue;
                                }
                                
                                System.out.println("-----Client Disconnected-----");
                                System.out.println("Player Id: " + (b+1));
                                System.out.println("IP: " + checkSocket.getInetAddress().getHostAddress());
                                Server.removeClient(b);
                                i -= 1;
                                b = 0;
                                
                            }catch(SocketTimeoutException e)
                            {
                                
                            }

                        }
                        
                        
                        //Accept connection and set timeout
                        serverSocket.setSoTimeout(100); //milliseconds
                        clientSocket = serverSocket.accept();

                    }catch(SocketTimeoutException e)
                    {
                        
                    }
                }

                //Stops thread
                if(Thread.currentThread().isInterrupted())
                {
                    break;
                }

                //Connection announcement
                System.out.println("-----Client Connected-----");
                System.out.println("Player Id: " + (i+1));
                System.out.println("IP: " + clientSocket.getInetAddress().getHostAddress());

                //Add connection object to array list
                Server.addClient(clientSocket);
            }    
                
               
            
        }catch(Exception e)
        {
            System.err.println("ERROR: Something went wrong in accepting connections " + e);
            e.printStackTrace();
            System.exit(1);
        }
    }
}


    

