package networkServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;


public class Server implements ServerInterface {
    
    private static boolean DEBUG = false;
    private static boolean STOP = false;
    
    
    /**
     * Accepts all connections made to the server for a specific amount of time
     * @param ss
     * @param maxConnections
     * @param timeout
     * @return ArrayList<Socket>
     * @throws Exception 
     */
    @Override
    public ArrayList<Socket> AcceptConnections(ServerSocket ss,  int maxConnections, int timeout) throws Exception
    {
        try{
            
            //Save IP
            ArrayList<String> ipTable = new ArrayList<>();
            
            //Save connection
            ArrayList<Socket> pcConnections = new ArrayList<>();
            
            //Debug
            if(Server.DEBUG)
                System.out.println("Listening & accepting connections on port " + ss.getLocalPort());
            
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
                            System.out.println("Connection: " + i + "\n" + "Intercepted Ip: " + ip);
                        
                        //Get ips already saved in array list if entry matches delete variable content
                        for(int e=0; e < ipTable.size(); e++){
                            String savedIp = ipTable.get(e);    

                            if(savedIp.equals(ip)){
                                ip = "";
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
                if(Server.DEBUG)
                    System.err.println("Timeout: A client needs too long to connect.");
                
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
