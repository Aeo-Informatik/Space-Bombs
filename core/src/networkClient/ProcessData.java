/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import com.gdx.bomberman.Main;
import gui.Constants;
import gui.entity.EnemyPlayer;
import gui.entity.EntityManager;


/**
 *
 * @author qubasa
 */
public class ProcessData implements Runnable
{
    @Override
    public void run() 
    {
        while(!Thread.currentThread().isInterrupted())
        {
            try
            {
                //Read from BlockingQueue
                String receivedData = ClientReceiveThread.queue.take().toString();

                /*
                Syntax for receiving data: funktion|arguments|player
                */
                String[] parameters = receivedData.split("\\|");
                String playerIdString = Integer.toString(Constants.PLAYERID);


                //Check if it targets this device with playerId or astrix
                if (parameters[parameters.length - 1].equals(playerIdString) || parameters[parameters.length - 1].equals("*"))
                {

                    switch (parameters[0]) 
                    {

                        //SAVE PLAYER ID
                        //General: registerPlayerId|id|target
                        case "registerMainPlayerId":
                            if(parameters.length == 3)
                            {
                                Constants.PLAYERID = Integer.parseInt(parameters[1]);

                                //DEBUG
                                if(Client.DEBUG)
                                    System.out.println("NOTE: Player id is now: " + Constants.PLAYERID);

                            }else
                                System.err.println("ERROR: registerMainPlayerId wrong number of parameters");
                            break;


                        //REGISTER ENEMY PLAYERS
                        //General: registerEnemyPlayers|amount|target
                        case "registerEnemyPlayers":
                            if(parameters.length == 3)
                            {
                                Constants.AMOUNTENEMYPLAYERS = Integer.parseInt(parameters[1]);

                                //DEBUG
                                if(Client.DEBUG)
                                    System.out.println("NOTE: Amount of enemy Players: " + Constants.AMOUNTENEMYPLAYERS);
                            }else
                                System.err.println("ERROR: registerEnemyPlayers wrong number of parameters");
                            break;


                        //SPAWN PLAYERS
                        //General: spawnPlayers|target
                        case "spawnPlayers":
                            if(parameters.length == 2)
                            {
                                //Spawn main player
                                EntityManager.spawnMainPlayer(50, 50, Constants.PLAYERID);

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
                            }else 
                                System.err.println("ERROR: spawnPlayer received data with invalid parameter length");
                            break;


                        //MOVE ENEMY PLAYER
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

                            
                        //STOP ENEMY PLAYER
                        //General: moveEnemyPlayer|playerId|x|y|target
                        case "stopEnemyPlayer":
                            if(parameters.length == 5)
                            {
                                for(EnemyPlayer enemy : EntityManager.enemies)
                                {
                                    if(enemy.getPlayerId() == Integer.parseInt(parameters[1]))
                                    {
                                        enemy.stopPlayer(Float.parseFloat(parameters[2]), Float.parseFloat(parameters[3]));
                                    }
                                }
                            }else
                                System.err.println("ERROR: stopEnemyPlayer wrong number of parameters");
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
    
}
