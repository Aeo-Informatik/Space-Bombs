package server;

import com.gdx.bomberman.Constants;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server 
{
    //Objects
    private ServerSocket serverSocket;
    private static ArrayList<Socket> clientConnections = new ArrayList<>();
    private ServerProcessData processData = new ServerProcessData(this);
    
    //Persistent Threads
    private Thread lobbyThread; //Till maxConnection has been reached or thread closed
    private ArrayList<Thread> forwardThreadList = new ArrayList<>(); // Till client disconnected or thread closed
    
    //Variables
    private int maxConnections;
    private String mapPath = "";
    private int port = -1;
    
    //Constructor
    public Server(int port, int maxConnections)
    {
        try 
        {
            this.serverSocket = new ServerSocket();
            this.maxConnections = maxConnections;
            this.port = port;
            
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
        if(mapPath.equals(""))
        {
            System.err.println("ERROR: Something went wrong on creating the server: No map set!");
            System.exit(1);
        }
        
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
                    if(Constants.SERVERDEBUG)
                        System.out.println("SERVER: ------Start Setup Game------");

                    //Player id as string
                    String playerId = Integer.toString(i +1);     

                    //Bind every client to their playerId (1, 2, 3, 4)
                    String registerCommand = "registerMainPlayerId|" + playerId + "|*";
                    sendToOne(Server.getClient(i), registerCommand);

                    //Open forward thread for every client
                    ServerForwardThread receive = new ServerForwardThread(Server.getClient(i), i +1, this);
                    Thread forwardThread = new Thread(receive);
                    forwardThread.start();

                    //Add forward thread to arraylist
                    forwardThreadList.add(forwardThread);
                }

                //Send to client the amount of players and the signal to spawn them
                String setMapCommand = "setGameMap|" + mapPath + "|*";
                String registerPlayersCommand = "registerAmountPlayers|" + Integer.toString(Server.getClientList().size()) + "|*";
                sendToAll(new ArrayList<String>(){{add(setMapCommand);add(registerPlayersCommand);add("spawnPlayers|*");}});
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
     * Resets server completly. New lobby can be started from this point on.
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

            getServerProcessData().stopSpawnItemThread();
            
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
    
    //Shuts down the server and unbinds port. Server objects should be set to null.
    public void stopServer()
    {
        resetServer();
        try 
        {
            this.serverSocket.close();
            this.serverSocket = new ServerSocket();
        } catch (IOException ex) 
        {
            System.out.println("ERROR: Something went wrong in stopServer() " + ex);
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    public void startServer()
    {
        try 
        {
            if(!serverSocket.isBound())
            {
                this.serverSocket.bind(new InetSocketAddress("127.0.0.1", port), maxConnections);
            }
        } catch (IOException ex) 
        {
            System.out.println("ERROR: Something went wrong in startServer() " + ex);
            ex.printStackTrace();
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
    public synchronized void sendToOne(Socket socket, String message)
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
    public synchronized void sendToAll(ArrayList<String> dataToSend)
    {
        try
        { 
            ServerSendThread send = new ServerSendThread(getClientList(), dataToSend);
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
    
    public synchronized ServerProcessData getServerProcessData()
    {
        return processData;
    }

}
