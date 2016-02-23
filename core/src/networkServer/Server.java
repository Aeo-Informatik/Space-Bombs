package networkServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;


public class Server {
    
    private static boolean debug = false;
    private static boolean stop = false;
    
    public static ArrayList<Socket> AcceptConnections(ServerSocket ss,  int maxConnections, int timeout) throws Exception
    {
        try{    
            //If minConnections is higher as maxConnections throw exception
            
            //Save IP
            ArrayList<String> ipTable = new ArrayList<>();
            
            //Save connection
            ArrayList<Socket> pcConnections = new ArrayList<>();
            
            try{
                //Start accepting connections
                for(int i=0; stop == false && maxConnections > i; i++){       
                        //Accept connection
                        ss.setSoTimeout(timeout);
                        Socket clientSocket = ss.accept();

                        //Get IP address of connected pc
                        String interceptedIp = clientSocket.getInetAddress().getHostAddress();

                        //Debug
                        if(debug){
                            System.out.println("Connection: " + i);
                            System.out.println("Intercepted Ip:" + interceptedIp);
                        }

                        //Get IPs already saved in array list if entry matches delete variable content
                        for(int e=0; e < ipTable.size(); e++){
                            String savedIp = ipTable.get(e);    

                            if(savedIp.equals(interceptedIp)){
                                interceptedIp = "";
                                System.out.println("Ip has been already saved!");
                            }
                        }

                        //If variable has been delted do nothing
                        if(interceptedIp.isEmpty()){
                            //Do nothing

                            if(debug){
                                System.out.println("Ip variable is empty!");
                            }

                        }else {   
                            //Add ip to array list
                            ipTable.add(interceptedIp);

                            //Add connection object to array list
                            pcConnections.add(clientSocket);

                            if(debug)
                            {
                                System.out.println("Saved Ip:" + interceptedIp );
                            }
                        }
                }
            
            // If timout is reached return the arraylist
            }catch(SocketTimeoutException e){
                if(debug){
                    System.err.println("Timout a pc needed to long to connect.");
                }
                
                return pcConnections;
            }
            
            return pcConnections;
            
        }catch(Exception e)
        {
            throw e;
        }
    }
    
    public static void setDebug(boolean debug)
    {
        Server.debug = debug;
    }
    
    public static void stopConnection(boolean stop){
        Server.stop = stop; 
    }
    
}
