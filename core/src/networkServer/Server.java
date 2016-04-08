package networkServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;



public class Server {
    
    public static boolean DEBUG = false;
    private ServerSocket serverSocket;
    private int minConnections;
    private int maxConnections;
    
    public Server(int port, int minConnections, int maxConnections) throws Exception
    {
        try 
        {
            this.serverSocket = new ServerSocket(port);
            this.minConnections = minConnections;
            this.maxConnections = maxConnections;
            
        }catch(Exception e) 
        {
            System.err.println("ERROR: Something went wrong on creating the server: " +e);
            throw e;
        }
    }
    
    /**
     * Starts the forward thread which will receive & send back the data. 
     * Before that happens every client gets a unique player id like player1 or player2.
     * @param socketList 
     */
    public void startGame(ArrayList<Socket> socketList)
    {
        try
        {
            //Gets connected client list as input and iterates over them
            for(int i =0; i < socketList.size(); i++)
            {
                //Debug
                if(DEBUG)
                    System.out.println("------Start Setup Game------");

                String playerId = Integer.toString(i +1);     

                /*-------------------SETUP GAME---------------------*/
                //Bind every client to their playerId (1, 2, 3, 4)
                //Message to send: registerPlayerId|playerId|*
                String registerCommand = "registerMainPlayerId|" + playerId + "|*";
                sendToOne(socketList.get(i), registerCommand);

                //Message to receive: registerEnemyPlayers|3|1
                //General: registerEnemyPlayers|amount|target //
                String registerEnemiesCommand = "registerEnemyPlayers|" + 
                Integer.toString(socketList.size()-1) + "|*";
                
                sendToAll(socketList, new ArrayList<String>(){{add(registerEnemiesCommand);add("spawnPlayers|*");}});

                /*-------------------END SETUP GAME---------------------*/
                //Open forward thread for every client
                ServerForwardThread receive = new ServerForwardThread(socketList.get(i), socketList);
                Thread thread = new Thread(receive);
                thread.start();
            }

            //Debug
            if(DEBUG)
                System.out.println("------End Setup Game------");
            
        }catch(Exception e)
        {
            System.err.println("ERROR: Unexpected error in startGame " +e);
            e.printStackTrace();
        }
    }
    
    
    /**
     * Opens a time windows where everyone can connect to the server.
     * After this time windows closed or all clients connected this method returns the connected clients list.
     * @param timeout
     * @return
     * @throws Exception 
     */
    public ArrayList<Socket> AcceptConnections(int timeout) throws Exception
    {
        try{
            //Save connection
            ArrayList<Socket> pcConnections = new ArrayList<>();
            
            System.out.println("Listening & accepting connections on port " + serverSocket.getLocalPort() + "\n");
            try
            {
                //Start accepting connections
                for(int i=0; maxConnections > i; i++)
                {       
                    //Accept connection and set timeout
                    serverSocket.setSoTimeout(timeout);
                    Socket clientSocket = serverSocket.accept();

                    //Debug
                    if(Server.DEBUG)
                        System.out.println("-----New Client-----");
                        System.out.println("Number: " + i + 1);
                        System.out.println("IP: " + clientSocket.getInetAddress().getHostAddress());

                    //Add connection object to array list
                    pcConnections.add(clientSocket);
                }
                
                //Return arraylist if the maximum number of clients are connected to the server
                return pcConnections;
                
            // If timout is reached return the arraylist
            }catch(SocketTimeoutException e)
            {
               
                if(pcConnections.size() <= 0)
                {
                    System.err.println("Timeout: No clients are connected!");
                    throw e;
                }else
                {
                    System.out.println("Timeout: Waiting aborted. A client needed too long to connect.");
                }
                    
                return pcConnections;
            }
            
        }catch(Exception e)
        {
            System.err.println("ERROR: Something went wrong in accepting connections " + e);
            throw e;
        }
    }
    
    
    /**
     * Send one message to one client.
     * @param socket
     * @param message 
     */
    public void sendToOne(Socket socket, String message)
    {
        try
        {   
            //Open send thread with the second constructor 
            SendThread send = new SendThread(socket, message);
            Thread thread = new Thread(send);
            thread.start();
            
        }catch(Exception e)
        {
            System.err.println("ERROR: Send to one had an unexcpected error " +e);
            throw e;
        }
    }
    
    
    /**
     * Send multiple messages in arraylist to everyone.
     * @param socketList
     * @param dataToSend 
     */
    public void sendToAll(ArrayList<Socket> socketList, ArrayList<String> dataToSend)
    {
        try
        { 
            SendThread send = new SendThread(socketList, dataToSend);
            Thread thread = new Thread(send);
            thread.start();
            
        }catch(Exception e)
        {
            System.err.println("ERROR: Send to all had an unexpected error " +e);
            throw e;
        }
    }
    
}
