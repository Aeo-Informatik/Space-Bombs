/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import gui.Constants;
import gui.entity.EntityManager;
import java.net.Socket;

/**
 *
 * @author qubasa
 */
public class AnalyseData{
    
    private Socket socket;
    private EntityManager entityManager;
    
    public AnalyseData(Socket socket){
        this.socket = socket;
    }
    
    
    protected void analyse(String receivedData){
        
        try
        {
            /*
            Syntax for receiving data: funktion|arguments|player
            */
            String[] parameters = receivedData.split("\\|");
            String playerIdString = Integer.toString(Constants.PLAYERID);

            
            //Check if the minimum arguments are given
            if (parameters.length < 2)
            {
                System.err.println("ERROR: received data with invalid length " + receivedData);
                
            //Check if it targets this device with playerId or astrix
            } else if (parameters[parameters.length - 1].equals(playerIdString) || parameters[parameters.length - 1].equals("*"))
            {

                switch (parameters[0]) 
                {
                    //Message to receive: registerPlayerId|1|*
                    //General: registerPlayerId|id|target
                    case "registerMainPlayerId":
                        Constants.PLAYERID = Integer.parseInt(parameters[1]);
                        
                        //DEBUG
                        if(Client.DEBUG)
                            System.out.println("-----Player id is now: " + Constants.PLAYERID);
                        break;
                        
                    //Message to receive: registerEnemyPlayers|3|1
                    //General: registerEnemyPlayers|amount|target
                    case "registerEnemyPlayers":
                        Constants.AMOUNTENEMYPLAYERS = Integer.parseInt(parameters[1]);
                        
                        //DEBUG
                        if(Client.DEBUG)
                            System.out.println("Amount of enemy Players: " + Constants.AMOUNTENEMYPLAYERS);
                        break;
                    
                    //Message to receive: spawnPlayers|1
                    //General: spawnPlayers|target
                    case "spawnPlayers":
                        //Spawn main player
                        EntityManager.spawnMainPlayer(50, 0, Constants.PLAYERID);
                        
                        //Spawn enemy players
                        for(int i=0; i < Constants.AMOUNTENEMYPLAYERS; i++)
                        {
                            if(i != Constants.PLAYERID)
                                EntityManager.spawnEnemyPlayer(50 + i*10, 0, i);
                        }
                        
                        //DEBUG
                        if(Client.DEBUG)
                            System.out.println("Spawend all players!");
                        break;
                        
                        
                    default:
                        System.err.println("ERROR: Command received from server is not valid");
                }  
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
