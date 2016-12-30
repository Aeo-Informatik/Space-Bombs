package server;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
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
    private static ArrayList<ClientConnection> clientConnections = new ArrayList<>();
    private ServerProcessData processData = new ServerProcessData(this);
    
    // Persistent Threads
    private Thread lobbyThread; //Till maxConnection has been reached or thread closed
    private ArrayList<Thread> forwardThreadList = new ArrayList<>(); // Till client disconnected or thread closed
    
    // Variables
    private static int  maxConnections = Constants.MAXPLAYERS;
    private int port = Constants.LISTENINGPORT;
    private boolean isLobbyOpen = false;
            
    //Ingame
    private String mapPath = "";
    private int startCoins = Constants.STARTCOINS;
    private int startLives = Constants.DEFAULTLIFE;
    private int coinBonus = Constants.COINBONUS;
    private int startRange = Constants.DEFAULTBOMBRANGE;
    private int startBombPlace = Constants.DEFAULTBOMBPLACE;
    private int startSpeed = Constants.DEFAULTENTITYSPEED;
    private int gameTimer = 0;
    
    //Constructor
    public Server(int port, int maxConnections)
    {
        try 
        {
            this.serverSocket = new ServerSocket();
            Server.maxConnections = maxConnections;
            this.port = port;
            
        }catch(Exception e) 
        {
            System.err.println("ERROR: Something went wrong on creating the server: " +e);
            e.printStackTrace();
            System.exit(1);
        }
    }
    

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

                activateGameTimer();
                
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
                    ServerForwardThread receive = new ServerForwardThread(Server.getClient(i), this);
                    Thread forwardThread = new Thread(receive);
                    forwardThread.start();

                    //Add forward thread to arraylist
                    forwardThreadList.add(forwardThread);
                }

                /**-----------------SETUP GAME COMMANDS-----------------**/
                ArrayList<String> preGameCommands = new ArrayList<>();
                
                // Necessary code block
                preGameCommands.add("setGameMap|" + mapPath + "|*");
                preGameCommands.add("registerAmountPlayers|" + Integer.toString(Server.getClientList().size()) + "|*");
                preGameCommands.add("spawnPlayers|*");
                
                // Player settings
                preGameCommands.add("registerStartCoins|" + getStartCoins() + "|*");
                preGameCommands.add("registerStartLives|" + getStartLives() + "|*");
                preGameCommands.add("registerCoinBonus|" + getCoinBonus() + "|*");
                
                preGameCommands.add("registerStartSpeed|" + startSpeed + "|*");
                preGameCommands.add("registerStartBombPlace|" + startBombPlace + "|*");
                preGameCommands.add("registerStartRange|" + startRange + "|*");
                
                sendToAll(preGameCommands);
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
    
    private void activateGameTimer()
    {
        Timer.schedule(new Task()
        {
            @Override
            public void run() 
            {
                gameTimer += 1;
            }
        }
                , 1 // first execute delay
                ,1  // delay between executes     
        );
    }
    
    private void deactivateTimer()
    {
        Timer.instance().clear();
        gameTimer = 0;
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
            
            for(int i=0; i < Server.getClientConnectionArraySize(); i++)
            {
                ClientConnection client = Server.getClient(i);
                
                client.getSocket().close();
            }

            forwardThreadList.clear();
            
            //Empty connected client list
            Server.clearClients();
            deactivateTimer();
            
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
    
    public boolean startServer()
    {
        try 
        {
            if(!serverSocket.isBound())
            {
                this.serverSocket.bind(new InetSocketAddress("0.0.0.0", getPort()), maxConnections);
                return true;
            }
        } catch (IOException ex) 
        {
            System.out.println("ERROR: Something went wrong in startServer() " + ex);
            ex.printStackTrace();
            System.exit(1);
        }
        
        return false;
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
        
        isLobbyOpen = false;
    }
    
    
    /**
     * Lets everyone connect to the server till maxConnections has been 
     * reached or startGame has been executed.
     * Persistent thread.
     */
    public boolean OpenLobby()
    {
        if(isIsLobbyOpen() == false)
        {
            //Create thread
            ServerLobby lobby = new ServerLobby(serverSocket);
            lobbyThread = new Thread(lobby);
            lobbyThread.start();
            isLobbyOpen = true;
            return true;
        }else
        {
            System.out.println("Opening lobby failed reason: Lobby already opened!");
        }
        
        return false;
    }
    
    
    /**
     * Send one message to one client. Temporary thread.
     * @param message 
     */
    public synchronized void sendToOne(ClientConnection clientconnection, String message)
    {
        try
        {   
            //Open send thread with the second constructor 
            ServerSendThread send = new ServerSendThread(clientconnection, message);
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
     * @param dataToSend 
     */
    public synchronized void sendToAll(ArrayList<String> dataToSend)
    {
        try
        { 
            ServerSendThread send = new ServerSendThread(dataToSend);
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
    private static synchronized ClientConnection addClient(Socket socket, int playerId)
    {
        ClientConnection clientConnection = null;
        
        if(getClientConnectionArraySize() < maxConnections)
        {
            clientConnection = new ClientConnection(socket, playerId);
            Server.getClientList().add(clientConnection);
        }else
        {
            System.err.println("addClient(): Max number of clients reached on server!");
        }
        return clientConnection;
    }
    
    public static synchronized void clearClients()
    {
        Server.getClientList().clear();
    }
    
    public static synchronized ClientConnection getClient(int i)
    {
        return Server.getClientList().get(i);
    }
    
    private static synchronized void removeClient(int i)
    {
        Server.getClientList().remove(i);
    }
    
    public static synchronized ClientConnection addClientAutomatePlayerId(Socket socket)
    {
        ClientConnection clientconnection = null;
        
        if(getClientConnectionArraySize() < maxConnections)
        {
            for(int i=1; i < maxConnections+1; i++)
            {
                clientconnection = getClientByPlayerId(i);

                if(clientconnection == null)
                {
                    clientconnection = Server.addClient(socket, i);
                    break;
                }
            }
        }
        return clientconnection;
    }
    
    public static synchronized ClientConnection getClientByPlayerId(int playerId)
    {
        // Iterate through all clients
        for(int i=0; i < Server.getClientConnectionArraySize(); i++)
        {
            // Check if one client matches the playerId
            if(Server.getClient(i).getPlayerId() == playerId)
            {
                return Server.getClient(i);
            }
        }
        
        return null;
    }
    
    public static synchronized void removeClientByPlayerId(int playerId)
    {
        // Iterate through all clients
        for(int i=0; i < Server.getClientConnectionArraySize(); i++)
        {
            // Check if one client matches the playerId
            if(Server.getClient(i).getPlayerId() == playerId)
            {
                Server.removeClient(i);
            }
        }
    }
    
    private static synchronized ArrayList<ClientConnection> getClientList()
    {
        return clientConnections;
    }
    
    public synchronized ServerProcessData getServerProcessData()
    {
        return processData;
    }

    public static synchronized  int getClientConnectionArraySize()
    {
        return Server.getClientList().size();
    }
    
    /**
     * @param startCoins the startCoins to set
     */
    public void setStartCoins(int startCoins) {
        this.startCoins = startCoins;
    }

    /**
     * @return the startCoins
     */
    public int getStartCoins() {
        return startCoins;
    }
    
    /**
     * @return the maxConnections
     */
    public int getMaxConnections() {
        return maxConnections;
    }

    /**
     * @param maxConnections the maxConnections to set
     */
    public void setMaxConnections(int maxConnections) {
        Server.maxConnections = maxConnections;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the startLives
     */
    public int getStartLives() {
        return startLives;
    }

    /**
     * @param startLives the startLives to set
     */
    public void setStartLives(int startLives) {
        this.startLives = startLives;
    }

    /**
     * @return the coinBonus
     */
    public int getCoinBonus() {
        return coinBonus;
    }

    /**
     * @param coinBonus the coinBonus to set
     */
    public void setCoinBonus(int coinBonus) {
        this.coinBonus = coinBonus;
    }

    /**
     * @return the gameTimer
     */
    public int getGameTimer() {
        return gameTimer;
    }

    /**
     * @return the startRange
     */
    public int getStartRange() {
        return startRange;
    }

    /**
     * @param startRange the startRange to set
     */
    public void setStartRange(int startRange) {
        this.startRange = startRange;
    }

    /**
     * @return the startBombPlace
     */
    public int getStartBombPlace() {
        return startBombPlace;
    }

    /**
     * @param startBombPlace the startBombPlace to set
     */
    public void setStartBombPlace(int startBombPlace) {
        this.startBombPlace = startBombPlace;
    }

    /**
     * @return the startSpeed
     */
    public int getStartSpeed() {
        return startSpeed;
    }

    /**
     * @param startSpeed the startSpeed to set
     */
    public void setStartSpeed(int startSpeed) {
        this.startSpeed = startSpeed;
    }

    /**
     * @return the isLobbyOpen
     */
    public boolean isIsLobbyOpen() {
        return isLobbyOpen;
    }

}
