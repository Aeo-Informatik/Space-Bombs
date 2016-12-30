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
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 *
 * @author qubasa
 */
public class ServerLobby implements Runnable
{
    //Variables
    private ServerSocket serverSocket;
    
    public ServerLobby(ServerSocket serverSocket)
    {
        this.serverSocket = serverSocket;
    }
    
    
    @Override
    public void run() 
    {
        //Debug
        System.out.println("SERVER: Listening & accepting connections on port " + serverSocket.getLocalPort() + "\n");
        
        try
        {
            //Start accepting connections till maxConnections are reached
            while(true)
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

                        // Check for clients that disconnect from lobby
                        for(int i=0; i < Server.getClientConnectionArraySize(); i++)
                        {
                            ClientConnection currentClientConnection = Server.getClient(i);
                            
                            //Stops thread
                            if(Thread.currentThread().isInterrupted())
                            {
                                break;
                            }
                            
                            try
                            {
                                currentClientConnection.getSocket().setSoTimeout(500); // Milliseconds
                                
                                BufferedReader receive = new BufferedReader(new InputStreamReader(currentClientConnection.getSocket().getInputStream()));
                                String dataReceived;

                                // Opens tcp session to client if client disconnects readLine() returns null
                                // Holds execution at this point till the SocketTimeout is reached or some data has been
                                // received
                                if((dataReceived = receive.readLine()) != null)
                                {
                                    continue;
                                }
                                
                                System.out.println("-----Client Disconnected-----");
                                System.out.println("Player Id: " + currentClientConnection.getPlayerId());
                                Server.removeClientByPlayerId(currentClientConnection.getPlayerId());
                                i = 0;
                                
                            }catch(SocketTimeoutException e)
                            {
                                
                            }
                        }
                        
                        
                        // Sets timeout for the accept method
                        serverSocket.setSoTimeout(100); //milliseconds
                        
                        // Waits for client to connect and returns socket
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

                //Add connection object to array list
                ClientConnection connectedClient = Server.addClientAutomatePlayerId(clientSocket);
                
                if(connectedClient != null)
                {
                    //Connection announcement
                    System.out.println("-----Client Connected-----");
                    System.out.println("Player Id: " + connectedClient.getPlayerId());
                    System.out.println("IP: " + connectedClient.getSocket().getInetAddress().getHostAddress());
                }else
                {
                    System.err.println("Client with IP: " + clientSocket.getInetAddress().getHostAddress() + " tried to connect but lobby is full!");
                    clientSocket.close();
                }
            }    
                
               
            
        }catch(SocketException e)
        {
            System.out.println("Lobby has been shutdown");
        }catch(Exception e)
        {
            System.err.println("ERROR: Something went wrong in ServerLobby " + e);
            e.printStackTrace();
            System.exit(1);
        }
    }
}


    

