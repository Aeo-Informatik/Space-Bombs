package networkServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;


public class Server {
    
    public static boolean DEBUG = false;
    private static boolean STOP = false;
    
    
    /**
     * Starts the receive thread which will receive & send back the data. 
     * Before that happens every client gets a unique player id like player1 but only 1 is transmitted.
     * @param socketList 
     */
    public void startServer(ArrayList<Socket> socketList)
    {
        //Gets connected client list as input and iterates over them
        for(int i =0; i < socketList.size(); i++)
        {
            //DEBUG
            if(DEBUG)
                System.out.println("IP: " + socketList.get(i).getInetAddress().getHostAddress());
                System.out.println("-----------End IP List-----------");
            
            setupGame(i, socketList);
                
            //Open receive thread for every client
            ReceiveThread receive = new ReceiveThread(socketList.get(i), socketList);
            Thread thread = new Thread(receive);
            thread.start();
        }
    }
    
    
    private void setupGame(int i, ArrayList<Socket> socketList)
    {
        String playerId = Integer.toString(i +1);     
                
        //Bind every client to their playerId (1, 2, 3, 4)
        //Message to send: registerPlayerId|playerId|*
        String registerCommand = "registerMainPlayerId|" + playerId + "|*";
        sendToOne(socketList.get(i), registerCommand);
            
        //Message to receive: registerEnemyPlayers|3|1
        //General: registerEnemyPlayers|amount|target //
        String registerEnemiesCommand = "registerEnemyPlayers|" + 
            Integer.toString(socketList.size()-1) + "|*";
        sendToAll(socketList, new ArrayList<String>(){{add(registerEnemiesCommand);add("spawnPlayers|*");}});
        
        
    }
    
    /**
     * Accepts all connections made to the server for a specific amount of time
     * @param ss
     * @param maxConnections
     * @param timeout
     * @return ArrayList<Socket>
     * @throws Exception 
     */
    public ArrayList<Socket> AcceptConnections(ServerSocket ss,  int maxConnections, int timeout) throws Exception
    {
        try{
            
            //Save IP
            ArrayList<String> ipTable = new ArrayList<>();
            
            //Save connection
            ArrayList<Socket> pcConnections = new ArrayList<>();
            

            System.out.println("Listening & accepting connections on port " + ss.getLocalPort() + "\n");
            
            try{
                //Start accepting connections
                for(int i=0; STOP == false && maxConnections > i; i++)
                {       

                        //Accept connection and set timeout
                        ss.setSoTimeout(timeout);
                        Socket clientSocket = ss.accept();

                        //Get IP address of connected pc
                        String ip = clientSocket.getInetAddress().getHostAddress();

                        //Debug
                        if(Server.DEBUG)
                            System.out.println("Connection: " + i + 1);
                            System.out.println("Intercepted Ip: " + ip);
                        
                        //Get ips already saved in array list if entry matches delete variable content
                        for(int e=0; e < ipTable.size(); e++){
                            String savedIp = ipTable.get(e);    

                            if(savedIp.equals(ip)){
                                ip = "";
                                if(Server.DEBUG)
                                    System.out.println("Ip has been already saved!");
                            }
                        }

                        //If variable has been delted do nothing
                        if(ip.isEmpty())
                        {
                            //Do nothing
                            if(Server.DEBUG)
                                System.out.println("Ip variable is empty!");
                            
                        }else 
                        {   
                            //Add ip to array list
                            ipTable.add(ip);

                            //Add connection object to array list
                            pcConnections.add(clientSocket);

                            //Debug
                            if(Server.DEBUG)
                                System.out.println("Saved Ip:" + ip );
                            
                        }
                }
                
                //Return arraylist if the maximum number of clients are connected to the server
                return pcConnections;
                
            // If timout is reached return the arraylist
            }catch(SocketTimeoutException e)
            {
               
                if(pcConnections.size() <= 0)
                {
                    System.err.println("Timeout: No clients are connected!");
                    
                }else
                {
                    System.out.println("Timeout: Waiting aborted. A client needed too long to connect.");
                }
                    
                return pcConnections;
            }
            
        }catch(Exception e)
        {
            throw e;
        }
    }
    

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
            throw e;
        }
    }
    
    public void sendToAll(ArrayList<Socket> socketList, ArrayList<String> dataToSend)
    {
        SendThread send = new SendThread(socketList, dataToSend);
        Thread thread = new Thread(send);
        thread.start();
    }
    
    
    public static void setStopConnection(boolean stop){
        Server.STOP = stop; 
    }
    
}
