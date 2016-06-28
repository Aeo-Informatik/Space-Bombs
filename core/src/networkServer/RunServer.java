/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkServer;
import com.gdx.bomberman.Constants;

/**
 *
 * @author qubasa
 */
public class RunServer 
{
    public static void main(String [] args)
    {
        try
        {
            
            //Initialise server object
            Server server = new Server(Constants.SERVERPORT, Constants.MAXPLAYERS);   

            server.OpenLobby();
            server.startGame();
               
        }catch(Exception e)
        {
            System.err.println("ERROR: Unexpected error has been thrown in main" + e);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
