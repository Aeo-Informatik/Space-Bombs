/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkServer;
import com.badlogic.gdx.Gdx;
import com.gdx.bomberman.Constants;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author qubasa
 */
public class ServerStart implements Runnable
{

    @Override
    public void run() 
    {
        try
        {
            //Initialise server object
            Server server = new Server(Constants.SERVERPORT, 1, 1);   

            //Accept all client connections and get them as socket object
            ArrayList<Socket> socketList = server.AcceptConnections(Constants.SERVERLOBBYWAIT);

            //Starts the game
            server.startGame(socketList);
            
        }catch(Exception e)
        {
            System.err.println("ERROR: Unexpected error has been thrown in main" + e);
            Gdx.app.exit();
        }
    }
    
    
    public static void main(String [] args)
    {
        try
        {
            
            //Initialise server object
            Server server = new Server(Constants.SERVERPORT, Constants.MINPLAYERS, Constants.MAXPLAYERS);   
               
            //Accept all client connections and get them as socket object
            ArrayList<Socket> socketList = server.AcceptConnections(Constants.SERVERLOBBYWAIT);
            
            //Starts the game
            server.startGame(socketList);
               
        }catch(Exception e)
        {
            System.err.println("ERROR: Unexpected error has been thrown in main" + e);
            Gdx.app.exit();
        }
    }
}
