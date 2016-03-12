/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import gui.Constants;
import gui.entity.EnemyPlayer;
import gui.entity.EntityManager;
import java.util.Arrays;

/**
 *
 * @author qubasa
 */
public class AnalyseData{
    

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
                        if(parameters.length == 3)
                        {
                            Constants.PLAYERID = Integer.parseInt(parameters[1]);

                            //DEBUG
                            if(Client.DEBUG)
                                System.out.println("-----Player id is now: " + Constants.PLAYERID);
                            
                        }else
                            System.err.println("ERROR: registerMainPlayerId wrong number of parameters");
                        break;
                       
                        
                    //Message to receive: registerEnemyPlayers|3|1
                    //General: registerEnemyPlayers|amount|target
                    case "registerEnemyPlayers":
                        if(parameters.length == 3)
                        {
                            Constants.AMOUNTENEMYPLAYERS = Integer.parseInt(parameters[1]);

                            //DEBUG
                            if(Client.DEBUG)
                                System.out.println("Amount of enemy Players: " + Constants.AMOUNTENEMYPLAYERS);
                        }else
                            System.err.println("ERROR: registerEnemyPlayers wrong number of parameters");
                        break;
                    
                        
                    //Message to receive: spawnPlayers|1
                    //General: spawnPlayers|target
                    case "spawnPlayers":
                        //Spawn main player
                        EntityManager.spawnMainPlayer(50, 0, Constants.PLAYERID);
                        
                        //Spawn enemy players
                        for(int i=1; i <= Constants.AMOUNTENEMYPLAYERS; i++)
                        {
                            if(i == Constants.PLAYERID)
                            {
                                i++;
                            }
                                
                            EntityManager.spawnEnemyPlayer(50 + i*10, 0, i);
                        }
                        
                        //DEBUG
                        if(Client.DEBUG)
                            System.out.println("Spawned all players!");
                        break;
                        
                    
                    //Message to receive: moveEnemyPlayer|2|LEFT|*
                    //General: moveEnemyPlayer|playerId|direction|target
                    case "moveEnemyPlayer":
                        if(parameters.length == 4)
                        {
                            for(EnemyPlayer enemy : EntityManager.enemies)
                            {
                                if(enemy.getPlayerId() == Integer.parseInt(parameters[1]))
                                {
                                    enemy.movePlayer(parameters[2]);
                                }
                            }
                        }else 
                            System.err.println("ERROR: moveEnemyPlayer wrong number of parameters");
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
