package server;

import com.gdx.bomberman.Constants;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server 
{
    //Objects
    private ServerSocket serverSocket;
    private static ArrayList<Socket> clientConnections = new ArrayList<>();
    
    //Persistent Threads
    private Thread lobbyThread; //Till maxConnection has been reached or thread closed
    private ArrayList<Thread> forwardThreadList = new ArrayList<>(); // Till client disconnected or thread closed
    
    //Variables
    private int maxConnections;
    private String mapPath = ServerConstants.DEFAULTMAPPATH;//"maps/TestMap.tmx"
    
    //Constructor
    public Server(int port, int maxConnections)
    {
        try 
        {
            this.serverSocket = new ServerSocket(port);
            this.maxConnections = maxConnections;
            
        }catch(Exception e) 
        {
            System.err.println("ERROR: Something went wrong on creating the server: " +e);
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    
    /**
     * Starts the forward thread which will receive & send back the data. 
     * Before that happens every client gets a unique player id.
     * @param socketList 
     */
    public void startGame()
    {
        try
        {
            if(forwardThreadList.size() == 0)
            {
                //Stop accepting client connections
                closeLobby();

                //Gets connected client list as input and iterates over them
                for(int i = 0; i < Server.getClientList().size(); i++)
                {
                    //Debug
                    if(ServerConstants.SERVERDEBUG)
                        System.out.println("SERVER: ------Start Setup Game------");

                    //Player id as string
                    String playerId = Integer.toString(i +1);     

                    //Bind every client to their playerId (1, 2, 3, 4)
                    String registerCommand = "registerMainPlayerId|" + playerId + "|*";
                    sendToOne(Server.getClient(i), registerCommand);

                    //Open forward thread for every client
                    ServerForwardThread receive = new ServerForwardThread(Server.getClient(i), i +1);
                    Thread forwardThread = new Thread(receive);
                    forwardThread.start();

                    //Add forward thread to arraylist
                    forwardThreadList.add(forwardThread);
                }

                //Send to client the amount of players and the signal to spawn them
                String setMapCommand = "setGameMap|" + mapPath + "|*";
                String registerPlayersCommand = "registerAmountPlayers|" + Integer.toString(Server.getClientList().size()) + "|*";
                sendToAll(Server.getClientList(), new ArrayList<String>(){{add(setMapCommand);add(registerPlayersCommand);add("spawnPlayers|*");}});
            }else
            {
                System.err.println("Game already in progress!");
            }
        }catch(Exception e)
        {
            System.err.println("ERROR: Unexpected error in startGame " +e);
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    
    /**
     * Resets server completly.
     */
    public void resetServer()
    {
        try
        {
            closeLobby();

            //Close all forward threads
            for(Thread thread: forwardThreadList)
            {
                thread.interrupt();
            }

            for(Socket client: Server.getClientList())
            {
                client.close();
            }

            forwardThreadList.clear();
            
            //Empty connected client list
            Server.getClientList().clear();
            
        }catch(Exception e)
        {
            System.out.println("ERROR: Something went wrong in resetServer() " +e);
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    
    /**
     * Closes lobby thread. Nobody can connect anymore afterwards.
     */
    public void closeLobby()
    {
        if(lobbyThread != null && lobbyThread.isAlive())
        {
            lobbyThread.interrupt();
        }
    }
    
    
    /**
     * Lets everyone connect to the server till maxConnections has been 
     * reached or startGame has been executed.
     * Persistent thread.
     */
    public void OpenLobby()
    {
        resetServer();
        
        //Create thread
        ServerLobby lobby = new ServerLobby(maxConnections, serverSocket);
        lobbyThread = new Thread(lobby);
        lobbyThread.start();
    }
    
    
    /**
     * Send one message to one client. Temporary thread.
     * @param socket
     * @param message 
     */
    private void sendToOne(Socket socket, String message)
    {
        try
        {   
            //Open send thread with the second constructor 
            ServerSendThread send = new ServerSendThread(socket, message);
            Thread thread = new Thread(send);
            thread.start();
            
        }catch(Exception e)
        {
            System.err.println("ERROR: Send to one had an unexcpected error " +e);
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    
    /**
     * Send multiple messages in an arraylist to everyone. Temporary thread.
     * @param socketList
     * @param dataToSend 
     */
    private void sendToAll(ArrayList<Socket> socketList, ArrayList<String> dataToSend)
    {
        try
        { 
            ServerSendThread send = new ServerSendThread(socketList, dataToSend);
            Thread thread = new Thread(send);
            thread.start();
            
        }catch(Exception e)
        {
            System.err.println("ERROR: Send to all had an unexpected error " +e);
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**-----------------------GETTER & SETTER-----------------------**/
    public void setMap(String mapPath)
    {
        this.mapPath = mapPath;
    }
    
    public String getMap()
    {
        return this.mapPath;
    }
    
    
    /**-----------------------THREAD SAFE FUNCTIONS-----------------------**/
    public static synchronized void addClient(Socket client)
    {
        clientConnections.add(client);
    }
    
    public static synchronized Socket getClient(int i)
    {
        return clientConnections.get(i);
    }
     
    public static synchronized void removeClient(int i)
    {
        clientConnections.remove(i);
    }
    
    public static synchronized ArrayList<Socket> getClientList()
    {
        return clientConnections;
    }
}
