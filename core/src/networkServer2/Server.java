/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkServer2;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


/**
 *
 * @author qubasa
 */
public class Server {
    
    public static boolean DEBUG = false;
    private ServerSocket serverSocket;
    private Thread listenThread;
    private int maxConnections;
    private int minConnections;
    
    
    public Server(int port, int minConnections, int maxConnections)
    {
        try
        {
            this.serverSocket = new ServerSocket(port);
            this.minConnections = minConnections;
            this.maxConnections = maxConnections;
            
        }catch(Exception e)
        {
            System.out.println("ERROR: On creating the server socket something went wrong " + e);
            e.printStackTrace();
            System.exit(0);
        }
        
    }
    
    
    /**
     * Listen for incoming connections and add the clients automatically to the connected 
     * socket list and remove them once they aren't connected anymore.
     */
    public void listenForClients()
    {
        try
        {
            ServerListenThread listenObject = new ServerListenThread(serverSocket, maxConnections);
            listenThread = new Thread(listenObject);
            listenThread.start();
            
        }catch(Exception e)
        {
            System.err.println("ERROR: Something went wrong by listening for clients " + e);
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    
    /**
     * Start sending all required commands to setup the game 
     */
    public void startGame()
    {
        try
        {
            //Check if minimum number of clients are connected
            if(ServerListenThread.clientConnections.size() >= minConnections)
            {
                //Debug
                if(Server.DEBUG)
                    System.out.println("Minimum clients are connected " + ServerListenThread.clientConnections.size());

                forwardData();
                

            }else
                System.err.println("ERROR: Not enough clients are connected required " + minConnections + " Connected: " + ServerListenThread.clientConnections.size());
        
        }catch(Exception e)
        {
            System.err.println("ERROR: Something went wrong on starting the game " +e);
            e.printStackTrace();
            System.exit(1);
        }
    }
        
    
    /**
    * Forwards all incoming data to every connected client
    */
    private void forwardData()
    {
        try
        {
            //Stop listen thread
            listenThread.interrupt();
            System.out.println("Stopped listen thread.");
            
            //Get static connected clients list
            ArrayList<Socket> socketList = ServerListenThread.clientConnections;
            
            //Open for every client a forward thread
            for(int i = 0; i < socketList.size(); i++)
            {
                System.out.println("Open forward thread for " + i+1);
                
                //Open forward thread for every client 
                ServerForwardThread forwardThread = new ServerForwardThread(socketList.get(i), socketList);
                Thread forward = new Thread(forwardThread);
                forward.start();
            }
            
        }catch(Exception e)
        {
            System.err.println("ERROR: Something went wrong by forwarding the data " +e);
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    
    /**
     * Sends a string to a specific client
     * @param socket
     * @param dataToSend 
     */
    public void sendData(Socket socket, String dataToSend)
    {
        
    }
    
}
