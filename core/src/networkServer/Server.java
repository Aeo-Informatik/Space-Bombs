package networkServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;


public class Server {
    
    private static boolean DEBUG = false;
    private static boolean STOP = false;
    
    
    /**
     * Starts the receive thread which will receive & send back the data. 
     * The receive thread and will automatically start a send thread.
     * @param socketList 
     */
    public void startServer(ArrayList<Socket> socketList)
    {
        
        for(Socket socket : socketList)
        {
            if(DEBUG)
                System.out.println("IP: " + socket.getInetAddress().getHostAddress());
                System.out.println("-----------End IP-----------");
                      

            //Open receive thread for every client
            ReceiveThread receive = new ReceiveThread(socket, socketList);
            Thread thread = new Thread(receive);
            thread.start();
        }
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
    

    //Getter & setter
    public static void setDebug(boolean debug)
    {
        Server.DEBUG = debug;
    }
    
    public static boolean getDebug()
    {
        return Server.DEBUG;
    }
    
    public static void setStopConnection(boolean stop){
        Server.STOP = stop; 
    }
    
}
