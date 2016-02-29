/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkServer;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author qubasa
 */
public class ServerTest 
{
    public static void main(String[] args) 
    {
        try
        {
            //Variables & objects
            final int port = 4444;
            ServerSocket serversocket = new ServerSocket(port);
            Server server = new Server();   
               
            
            server.setDebug(true);
                    
            
            //Accept all client connections and get them as socket object
            ArrayList<Socket> socketList = server.AcceptConnections(serversocket, 4, 20000);
            server.startServer(socketList);
               
                    
        }catch(Exception e)
        {
            System.err.println("BombermanServer: " + e);
        }
    }
}
