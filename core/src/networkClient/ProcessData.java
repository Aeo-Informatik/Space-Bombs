/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.gdx.bomberman.Constants;
import gui.entity.EnemyPlayer;
import gui.entity.EntityManager;


/**
 *
 * @author qubasa
 */
public class ProcessData
{
    private EntityManager entityManager;
    
    //Constructor
    public ProcessData(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }
    
    /**
     * Renders incoming server calls has to be between the SpriteBatch.begin() tag.
     */
    public void start(SpriteBatch renderObject)
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

            //Check if it targets this device with playerId or astrix
            if (parameters[parameters.length - 1].equals(Integer.toString(Constants.PLAYERID)) || parameters[parameters.length - 1].equals("*"))
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
                    //General: registerAmountPlayers|amount|target
                    case "registerAmountPlayers":
                        if(parameters.length == 3)
                        {
                            Constants.AMOUNTPLAYERS = Integer.parseInt(parameters[1]);

                            //DEBUG
                            if(Constants.PROCESSDATADEBUG)
                                System.out.println("Total number of players: " + Constants.AMOUNTPLAYERS);
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
                            for(int i=1; i <= Constants.AMOUNTPLAYERS ; i++)
                            {
                                if(i == Constants.PLAYERID)
                                {
                                    continue;
                                }

                                if(Constants.PROCESSDATADEBUG)
                                    System.out.println("Spawning enemy with player id: " + i);
                                
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
                            for(EnemyPlayer enemy : entityManager.getEnemyArray())
                            {
                                if(enemy.getPlayerId() == Integer.parseInt(parameters[1]))
                                {
                                    enemy.movePlayer(parameters[2], renderObject);
                                }
                            }
                            
                        }else 
                            System.err.println("ERROR: moveEnemyPlayer wrong number of parameters");
                        break;


                    /**------------------STOP ENEMY PLAYER------------------**/
                    //General: stopEnemyPlayer|playerId|x|y|target
                    case "stopEnemyPlayer":
                        if(parameters.length == 5)
                        {
                            for(EnemyPlayer enemy : entityManager.getEnemyArray())
                            {
                                if(enemy.getPlayerId() == Integer.parseInt(parameters[1]))
                                {
                                    enemy.stopPlayer(Float.parseFloat(parameters[2]), Float.parseFloat(parameters[3]), renderObject);
                                }
                            }
                            
                        }else
                            System.err.println("ERROR: stopEnemyPlayer wrong number of parameters");
                        break;

                        
                    /**------------------ENEMY PLAYER PLACES BOMB------------------**/
                    //General: placeBomb|x|y|playerId|bombType|target
                    case "placeEnemyBomb":
                        if(parameters.length == 6)
                        {   //Check if bomb is placed by own player
                            if(Integer.parseInt(parameters[3]) != Constants.PLAYERID)
                            {
                                for(EnemyPlayer enemy : entityManager.getEnemyArray())
                                {
                                    if(enemy.getPlayerId() == Integer.parseInt(parameters[3]))
                                    {
                                        //placeBomb(Vector2 pos, String bombType)
                                        enemy.placeBomb(new Vector2(Float.parseFloat(parameters[1]), Float.parseFloat(parameters[2])), parameters[4]);
                                    }
                                }
                            }
                        }else
                            System.err.println("ERROR: placeBomb wrong number of parameters");
                        break;
                        
                        
                    /**------------------ENEMY PLAYER GETS HIT------------------**/   
                    //General: enemyPlayerLife|playerId|life|target
                    case "enemyPlayerLife":
                        if(parameters.length == 4)
                        {
                            for(EnemyPlayer enemy : entityManager.getEnemyArray())
                            {
                                if(enemy.getPlayerId() == Integer.parseInt(parameters[1]))
                                {
                                    enemy.setLife(Integer.parseInt(parameters[2]));
                                }
                            }
                        
                            //DEBUG
                            if(Constants.PROCESSDATADEBUG)
                                System.out.println("Enemy player " + parameters[1] + " has been hit by bomb. Life counter: " + parameters[2]);
                            
                        }else
                            System.err.println("ERROR: playerDied wrong number of parameters");

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
            Gdx.app.exit();
        }
    }
}
    

