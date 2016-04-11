/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkServer;
import gui.Constants;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author qubasa
 */
public class ServerStart implements Runnable
{

    public static void main(String [] args)
    {
        startServer();
    }
    
    @Override
    public void run() 
    {
        startServer();
        Server.DEBUG = false;
    }
    
    public static void startServer()
    {
        try
        {
            
            //Initialise server object
            Server server = new Server(Constants.SERVERPORT, Constants.MINPLAYERS, Constants.MAXPLAYERS);   
               
            //Set debug output to true
            Server.DEBUG = true;
                    
            
            //Accept all client connections and get them as socket object
            ArrayList<Socket> socketList = server.AcceptConnections(Constants.SERVERTIMEOUT);
            
            //Starts the game
            server.startGame(socketList);
               
        }catch(Exception e)
        {
            System.err.println("ERROR: Unexpected error has been thrown in main" + e);
            System.exit(0); 
        }
    }
    
}
