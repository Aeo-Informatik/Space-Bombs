package networkServer;

import com.gdx.bomberman.Constants;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;



public class Server 
{
    
    private ServerSocket serverSocket;
    private int maxConnections;
    private boolean stopListening = false;
    private ArrayList<Socket> clientConnections = new ArrayList<>();
    
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
     * Before that happens every client gets a unique player id like player1 or player2.
     * @param socketList 
     */
    public void startGame()
    {
        try
        {
            //Gets connected client list as input and iterates over them
            for(int i = 0; i < clientConnections.size(); i++)
            {
                //Debug
                if(Constants.SERVERDEBUG)
                    System.out.println("SERVER: ------Start Setup Game------");

                String playerId = Integer.toString(i +1);     

                /*-------------------SETUP GAME---------------------*/
                //Bind every client to their playerId (1, 2, 3, 4)
                //Message to send: registerPlayerId|playerId|*
                String registerCommand = "registerMainPlayerId|" + playerId + "|*";
                sendToOne(clientConnections.get(i), registerCommand);

                /*-------------------END SETUP GAME---------------------*/
                //Open forward thread for every client
                ServerForwardThread receive = new ServerForwardThread(clientConnections.get(i), clientConnections, i +1);
                Thread thread = new Thread(receive);
                thread.start();
            }

            //Message to receive: registerEnemyPlayers|3|1
            //General: registerAmountPlayers|amount|target //
            String registerPlayersCommand = "registerAmountPlayers|" + Integer.toString(clientConnections.size()) + "|*";

            sendToAll(clientConnections, new ArrayList<String>(){{add(registerPlayersCommand);add("spawnPlayers|*");}});

        }catch(Exception e)
        {
            System.err.println("ERROR: Unexpected error in startGame " +e);
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    
    /**
     * Lets everyone connect to the server till maxConnections has been reached or stopListening is
     * set to true.
     */
    public void AcceptConnections()
    {
        try
        {
            //Debug
            System.out.println("SERVER: Listening & accepting connections on port " + serverSocket.getLocalPort() + "\n");

            //Start accepting connections till maxConnections are reached
            for(int i=0; maxConnections > i; i++)
            {   
                Socket clientSocket = null;

                //Try connecting till clientSocket has been bound to a client
                while(clientSocket == null)
                {
                    try
                    {
                        //Check if client disconnects from lobby
                        for(int b=0; b < clientConnections.size(); b++)
                        {
                            BufferedReader receive = new BufferedReader(new InputStreamReader(clientConnections.get(b).getInputStream()));
                            String dataReceived;
                            
                            if((dataReceived = receive.readLine()) == null)
                            {
                                System.out.println("SERVER: Client " + clientConnections.get(b).getInetAddress().getHostAddress() + " disconnected from Lobby");
                                clientConnections.remove(b);
                                i -= 1;
                            }
                        }
                        
                        //Stops clients from connecting
                        if(stopListening == true)
                        {
                            break;
                        }

                        //Accept connection and set timeout
                        serverSocket.setSoTimeout(100); //milliseconds
                        clientSocket = serverSocket.accept();

                    }catch(SocketTimeoutException e)
                    {

                    }
                }

                //Stops clients from connecting
                if(stopListening == true)
                {
                    break;
                }

                //Connection announcement
                System.out.println("-----New Client-----");
                int y = i +1;
                System.out.println("Number: " + y);
                System.out.println("IP: " + clientSocket.getInetAddress().getHostAddress());
                System.out.println("--------------------");

                //Add connection object to array list
                clientConnections.add(clientSocket);
            }

        }catch(Exception e)
        {
            System.err.println("ERROR: Something went wrong in accepting connections " + e);
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    //TODO
    public void closeServer()
    {
        
    }
    
    /**
     * Send one message to one client.
     * @param socket
     * @param message 
     */
    private void sendToOne(Socket socket, String message)
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
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    
    /**
     * Send multiple messages in arraylist to everyone.
     * @param socketList
     * @param dataToSend 
     */
    private void sendToAll(ArrayList<Socket> socketList, ArrayList<String> dataToSend)
    {
        try
        { 
            SendThread send = new SendThread(socketList, dataToSend);
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
    public boolean getStopListening()
    {
        return this.stopListening;
    }
    
    public void setStopListening(boolean stopListening)
    {
        this.stopListening = stopListening;
    }
    
    public ArrayList<Socket> getClientConnections()
    {
        return this.clientConnections;
    }
    
    public void setClientConnections(ArrayList<Socket> clientConnections)
    {
        this.clientConnections = clientConnections;
    }
}
