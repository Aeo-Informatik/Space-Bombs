/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;
import gui.map.AvailableMaps;
import java.util.Scanner;

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
            Server server = new Server(ServerConstants.LISTENINGPORT, ServerConstants.MAXPLAYERS, new AvailableMaps().getTestMap());   

            server.OpenLobby();
            
            Scanner reader = new Scanner(System.in);  // Reading from System.in
            System.out.println("Commands: /startGame, /stopServer, /closeLobby, /openLobby");
            
            while(true)
            {
                String n = reader.nextLine();
                if(n.equalsIgnoreCase("/startGame"))
                {
                    System.out.println("----------GAME STARTED---------");
                    server.startGame();
                    
                }else if(n.equalsIgnoreCase("/stopServer"))
                {
                    System.out.println("----------SERVER STOPPED---------");
                    server.resetServer();
                    break;
                    
                }else if(n.equalsIgnoreCase("/closeLobby"))
                {
                    System.out.println("----------LOBBY CLOSED---------");
                    server.closeLobby();
                    
                }else if(n.equalsIgnoreCase("/openLobby"))
                {
                    System.out.println("----------NEW LOBBY OPENED----------");
                    server.OpenLobby();
                    
                }
            }
               
        }catch(Exception e)
        {
            System.err.println("ERROR: Unexpected error has been thrown in main" + e);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
