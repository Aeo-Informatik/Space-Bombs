/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 *
 * @author qubasa
 */
public class Lobby implements Runnable
{
    //Variables
    private int i = 0;
    private int maxConnections = -1;
    private ServerSocket serverSocket;
    
    public Lobby(int maxConnections, ServerSocket serverSocket)
    {
        this.maxConnections = maxConnections;
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
            for(; maxConnections > getIvalue(); incrementI())
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

                        //Accept connection and set timeout
                        serverSocket.setSoTimeout(100); //milliseconds
                        clientSocket = serverSocket.accept();

                    }catch(SocketTimeoutException e)
                    {
                        System.out.println("Timeout");
                    }
                }

                //Stops thread
                if(Thread.currentThread().isInterrupted())
                {
                    break;
                }

                //Connection announcement
                System.out.println("-----New Client-----");
                int y = getIvalue() +1;
                System.out.println("Number: " + y);
                System.out.println("IP: " + clientSocket.getInetAddress().getHostAddress());
                System.out.println("--------------------");

                //Add connection object to array list
                Server.addClient(clientSocket);
                
                //Check if client disconnects
                new Thread()
                {
                    @Override
                    public void run() 
                    {
                        try
                        {
                            BufferedReader receive = new BufferedReader(new InputStreamReader(Server.getClient(getIvalue()).getInputStream()));
                            String dataReceived;

                            if((dataReceived = receive.readLine()) == null)
                            {
                                System.out.println("SERVER: Client " + Server.getClient(getIvalue()).getInetAddress().getHostAddress() + " disconnected from Lobby");
                                Server.removeClient(getIvalue());
                                decrementI();
                            }else
                            {
                                System.out.println("Nobody disconnected");
                            }
                            
                        }catch(Exception e)
                        {
                            System.err.println("ERROR: " + e);
                            e.printStackTrace();
                        }
                    }
                }.start();
            }

        }catch(Exception e)
        {
            System.err.println("ERROR: Something went wrong in accepting connections " + e);
            e.printStackTrace();
            System.exit(1);
        }
    }
        
    /**----------------------THREAD SAFE FUNCTIONS----------------------**/
    private synchronized void incrementI() 
    {
        i++;
    }

    private synchronized void decrementI() 
    {
        i--;
    }

    private synchronized int getIvalue() 
    {
        return i;
    }
}


    

