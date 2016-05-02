/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import gui.Constants;
import gui.entity.EnemyPlayer;
import gui.entity.EntityManager;


/**
 *
 * @author qubasa
 */
public class ProcessData
{
    private EntityManager entityManager;
    
    public ProcessData(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }
    
    /**
     * Renders incoming server calls has to be between the SpriteBatch.begin() tag.
     */
    public void start()
    {
        try
        {
            String receivedData = "";
            
            if(ClientReceiveThread.queue.size() != 0)
            {
                receivedData = ClientReceiveThread.queue.take().toString();
            }

            
            //Split received data
            String[] parameters = receivedData.split("\\|");
            String playerIdString = Integer.toString(Constants.PLAYERID);


            //Check if it targets this device with playerId or astrix
            if (parameters[parameters.length - 1].equals(playerIdString) || parameters[parameters.length - 1].equals("*"))
            {
                switch (parameters[0]) 
                {

                    /**------------------SAVE PLAYER ID------------------**/
                    //General: registerPlayerId|id|target
                    case "registerMainPlayerId":
                        if(parameters.length == 3)
                        {
                            Constants.PLAYERID = Integer.parseInt(parameters[1]);

                            //DEBUG
                            if(Constants.PROCESSDATADEBUG)
                                System.out.println("Player id is now: " + Constants.PLAYERID);

                        }else
                            System.err.println("ERROR: registerMainPlayerId wrong number of parameters");
                        break;


                    /**------------------REGISTER ENEMY PLAYERS------------------**/
                    //General: registerEnemyPlayers|amount|target
                    case "registerEnemyPlayers":
                        if(parameters.length == 3)
                        {
                            Constants.AMOUNTENEMYPLAYERS = Integer.parseInt(parameters[1]);

                            //DEBUG
                            if(Constants.PROCESSDATADEBUG)
                                System.out.println("Amount of Enemy players: " + Constants.AMOUNTENEMYPLAYERS);
                        }else
                            System.err.println("ERROR: registerEnemyPlayers wrong number of parameters");
                        break;


                    /**------------------SPAWN ALL PLAYERS------------------**/
                    //General: spawnPlayers|target
                    case "spawnPlayers":
                        if(parameters.length == 2)
                        {
                            //Spawn main player
                            entityManager.spawnMainPlayer(Constants.PLAYERID);

                            //Spawn enemy players
                            for(int i=1; i <= Constants.AMOUNTENEMYPLAYERS; i++)
                            {
                                if(i == Constants.PLAYERID)
                                {
                                    i++;
                                }

                                if(Constants.PROCESSDATADEBUG)
                                    System.out.println("Spawning enemie with player id: " + i);
                                
                                entityManager.spawnEnemyPlayer(i);
                            }

                            Constants.PLAYERSPAWNED = true;

                            //DEBUG
                            if(Constants.PROCESSDATADEBUG)
                                System.out.println("Spawned all players!");
                        }else 
                            System.err.println("ERROR: spawnPlayer received data with invalid parameter length");
                        break;


                    /**------------------MOVE ENEMY PLAYER------------------**/
                    //General: moveEnemyPlayer|playerId|direction|target
                    case "moveEnemyPlayer":
                        if(parameters.length == 4)
                        {
                            for(EnemyPlayer enemy : entityManager.getEnemieArray())
                            {
                                if(enemy.getPlayerId() == Integer.parseInt(parameters[1]))
                                {
                                    enemy.movePlayer(parameters[2]);
                                }
                            }
                            
                        }else 
                            System.err.println("ERROR: moveEnemyPlayer wrong number of parameters");
                        break;


                    /**------------------STOP ENEMY PLAYER------------------**/
                    //General: moveEnemyPlayer|playerId|x|y|target
                    case "stopEnemyPlayer":
                        if(parameters.length == 5)
                        {
                            for(EnemyPlayer enemy : entityManager.getEnemieArray())
                            {
                                if(enemy.getPlayerId() == Integer.parseInt(parameters[1]))
                                {
                                    enemy.stopPlayer(Float.parseFloat(parameters[2]), Float.parseFloat(parameters[3]));
                                }
                            }
                            
                        }else
                            System.err.println("ERROR: stopEnemyPlayer wrong number of parameters");
                        break;

                     /**------------------STOP ENEMY PLAYER------------------**/
                    //General: setBomb|playerId|x|y|bomb|target
                    case "setBomb":
                        break;
                        
                    default:
                        System.err.println("ERROR: Command received from server is not valid");
                        System.err.println(receivedData);
                }  
            }
            
        }catch(Exception e)
        {
            System.err.println("ERROR: ProcessDataThread() Something went wrong " + e);
            e.printStackTrace();
        }
    }
}
    

