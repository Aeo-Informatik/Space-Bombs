/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkServer2;


import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;


/**
 *
 * @author qubasa
 */
public class ServerListenThread implements Runnable 
{
    private ServerSocket serverSocket;
    private int maxConnections;
    public static ArrayList<Socket> clientConnections = new ArrayList<>();
    
    public ServerListenThread(ServerSocket serverSocket, int maxConnections)
    {
        this.maxConnections = maxConnections;
        this.serverSocket = serverSocket;
    }
    
    
    @Override
    public void run() 
    {
       try
       {
            System.out.println("Listening & accepting connections...");
            
            while(!Thread.currentThread().isInterrupted())
            {
                try
                {
                    if(maxConnections > clientConnections.size())
                    {
                        //Listens for incoming connection
                        serverSocket.setSoTimeout(7000); //7 Seconds
                        Socket clientSocket = serverSocket.accept();
                        
                        
                        //Gets ip address from client
                        String ipAddress = clientSocket.getInetAddress().getHostAddress();

                        //Add client to connected client list
                        clientConnections.add(clientSocket);

                        //Debug
                        if(Server.DEBUG)
                        {
                            System.out.println("-----New Client-----");
                            System.out.println("Number: " + clientConnections.size());
                            System.out.println("IP: " + ipAddress);
                        }
                             
                    }else
                        //Stop listening thread
                        System.out.println("NOTE: Max connections reached so server stopped listening.");
                        Thread.currentThread().interrupt();
                    
                }catch(SocketTimeoutException e)
                {
                    System.out.println("Nobody connected in 7 seconds. Retry...");
                }
            } 
            
       }catch(Exception e)
       {
           System.err.println("ERROR: Server stopped listening for connections " + e);
           e.printStackTrace();
           System.exit(1);
       }
    }
    
    

}