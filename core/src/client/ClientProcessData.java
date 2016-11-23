/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.gdx.bomberman.Constants;
import gui.entity.player.EnemyPlayer;
import gui.entity.EntityManager;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;


/**
 *
 * @author qubasa
 */
public class ClientProcessData
{
    private EntityManager entityManager;
    private MapLoader mapManager;
    
    //Constructor
    public ClientProcessData(EntityManager entityManager, MapLoader mapManager)
    {
        this.entityManager = entityManager;
        this.mapManager = mapManager;
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
                    /**------------------Delete Items----------------**/
                    //General:deleteItems
                    case "deleteItems":
                        entityManager.getItemManager().clearItems();
                        break;
                    
                    /**------------------SPAWN COIN------------------**/
                    //General:spawnCoin|CellX|CellY|target
                    case "spawnCoin":
                        if(parameters.length == 4)
                        {
                            //System.out.println("Received spawnCoin command!");
                            
                            //Spawn coin
                            entityManager.getItemManager().spawnCoin(new MapCellCoordinates(Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2])), Constants.COINVALUE);
                        }else
                            System.err.println("ERROR: spawnCoin wrong number of parameters");
                        break;
                            
                    
                    /**------------------SPAWN ITEM------------------**/
                    //General:spawnItem|itemType|itemField|target
                    case "spawnItem":
                        if(parameters.length == 4)
                        { 
                            try
                            {
                                ThinGridCoordinates itemField = mapManager.getSpawnerPositionsArray().get(Integer.parseInt(parameters[2]));
                                MapCellCoordinates itemFieldCellPos = new MapCellCoordinates(itemField);
                                
                                switch(parameters[1])
                                {
                                    case "BombUp":
                                        entityManager.getItemManager().spawnBombUp(itemFieldCellPos);
                                        break;
                                        
                                    case "CoinBag":
                                        entityManager.getItemManager().spawnCoinBag(itemFieldCellPos, Constants.COINBAGVALUE);
                                        break;
                                        
                                    case "LifeUp":
                                        entityManager.getItemManager().spawnLifeUp(itemFieldCellPos);
                                        break;
                                        
                                    case "RangeUp":
                                        entityManager.getItemManager().spawnRangeUp(itemFieldCellPos);
                                        break;
                                        
                                    case "SpeedUp":
                                        entityManager.getItemManager().spawnSpeedUp(itemFieldCellPos);
                                        break;
                                        
                                    case "YellowHeart":
                                        entityManager.getItemManager().spawnYellowHeart(itemFieldCellPos);
                                        break;
                                }
                            }catch(IndexOutOfBoundsException e)
                            {
                                System.err.println("ERROR: spawnItem wrong itemField number " + e);
                            }
                        }else
                            System.err.println("ERROR: spawnItem wrong number of parameters");
                        break;
                    
                    /**------------------SET MAP TO PLAY ON------------------**/
                    //General: setGameMap|mapPath|target
                    case "setGameMap":
                        if(parameters.length == 3)
                        { 
                            try
                            {
                                mapManager.setNewMap(parameters[1]);
                                
                            }catch(Exception e)
                            {
                                System.err.println("ERROR: Something went wrong in setGameMap. Wrong path or missing layer: " + e);
                            }
                        }else
                            System.err.println("ERROR: setGameMap wrong number of parameters");
                        break;
                    
                    
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
                            entityManager.getPlayerManager().spawnMainPlayer(Constants.PLAYERID);

                            //Spawn enemy players
                            for(int i=1; i <= Constants.AMOUNTPLAYERS ; i++)
                            {
                                if(i == Constants.PLAYERID)
                                {
                                    continue;
                                }

                                if(Constants.PROCESSDATADEBUG)
                                    System.out.println("Spawning enemy with player id: " + i);
                                
                                entityManager.getPlayerManager().spawnEnemyPlayer(i);
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
                            for(EnemyPlayer enemy : entityManager.getPlayerManager().getEnemyArray())
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
                            for(EnemyPlayer enemy : entityManager.getPlayerManager().getEnemyArray())
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
                                for(EnemyPlayer enemy : entityManager.getPlayerManager().getEnemyArray())
                                {
                                    if(enemy.getPlayerId() == Integer.parseInt(parameters[3]))
                                    {
                                        //placeBomb(Vector2 pos, String bombType)
                                                enemy.placeBomb(new ThinGridCoordinates(Float.parseFloat(parameters[1]), Float.parseFloat(parameters[2])), parameters[4]);
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
                            for(EnemyPlayer enemy : entityManager.getPlayerManager().getEnemyArray())
                            {
                                if(enemy.getPlayerId() == Integer.parseInt(parameters[1]))
                                {
                                    enemy.setLife(Integer.parseInt(parameters[2]));
                                }
                            }
                        
                            //DEBUG
                            if(Constants.PROCESSDATADEBUG)
                                System.out.println("Enemy player " + parameters[1] + " set life to: " + parameters[2]);
                            
                        }else
                            System.err.println("ERROR: enemyPlayerLife wrong number of parameters");
                        break;
                        
                        
                    /**------------------ENEMY PLAYER BOMB RANGE------------------**/
                    //General: enemyPlayerSetRange|playerId|range|target
                    case "enemyPlayerSetRange":
                        if(parameters.length == 4)
                        {
                            for(EnemyPlayer enemy : entityManager.getPlayerManager().getEnemyArray())
                            {
                                if(enemy.getPlayerId() == Integer.parseInt(parameters[1]))
                                {
                                    enemy.setBombRange(Integer.parseInt(parameters[2]));
                                }
                            }
                            
                            //DEBUG
                            if(Constants.PROCESSDATADEBUG)
                                System.out.println("Enemy player " + parameters[1] + " range now: " + parameters[2]);
                            
                        }else
                            System.err.println("ERROR: enemyPlayerSetRange wrong number of parameters");
                        break;
                        
                    /**------------------ENEMY PLAYER SPEED------------------**/
                    //General: enemyPlayerSetSpeed|playerId|speed|target
                    case "enemyPlayerSetSpeed":
                        if(parameters.length == 4)
                        {
                            for(EnemyPlayer enemy : entityManager.getPlayerManager().getEnemyArray())
                            {
                                if(enemy.getPlayerId() == Integer.parseInt(parameters[1]))
                                {
                                    enemy.setEntitySpeed(Float.parseFloat(parameters[2]));
                                }
                            }
                            
                            //DEBUG
                            if(Constants.PROCESSDATADEBUG)
                                System.out.println("Enemy player " + parameters[1] + " speed now: " + parameters[2]);
                            
                        }else
                            System.err.println("ERROR: enemyPlayerSetSpeed wrong number of parameters");
                        break;
                    
                    /**------------------ENEMY PLAYER COINS------------------**/
                    //General: enemyPlayerSetCoins|playerId|coins|target
                    case "enemyPlayerSetCoins":
                        if(parameters.length == 4)
                        {
                            for(EnemyPlayer enemy : entityManager.getPlayerManager().getEnemyArray())
                            {
                                if(enemy.getPlayerId() == Integer.parseInt(parameters[1]))
                                {
                                    enemy.setCoins(Integer.parseInt(parameters[2]));
                                }
                            }
                            
                            //DEBUG
                            if(Constants.PROCESSDATADEBUG)
                                System.out.println("Enemy player " + parameters[1] + " coins now: " + parameters[2]);
                        }else
                            System.err.println("ERROR: enemyPlayerSetCoins wrong number of parameters");
                        break;
                    
                    /**------------------ENEMY PLAYER MAX BOMBS------------------**/
                    //General: enemyPlayerSetMaxBombs|playerId|maxBombs|target
                    case "enemyPlayerSetMaxBombs":
                        if(parameters.length == 4)
                        {
                            for(EnemyPlayer enemy : entityManager.getPlayerManager().getEnemyArray())
                            {
                                if(enemy.getPlayerId() == Integer.parseInt(parameters[1]))
                                {
                                    enemy.setMaxBombPlacing(Integer.parseInt(parameters[2]));
                                }
                            }
                            
                            //DEBUG
                            if(Constants.PROCESSDATADEBUG)
                                System.out.println("Enemy player " + parameters[1] + " max bombs now: " + parameters[2]);
                        }else
                            System.err.println("ERROR: enemyPlayerSetMaxBombs wrong number of parameters");
                        break;
                    
                    /**------------------SPAWN TOMB STONE/ ON ENEMY PLAYER DEATH------------------**/
                    //General: enemyPlayerDied|playerId|cellX|cellY|target
                    case "enemyPlayerDied":
                        if(parameters.length == 5)
                        {
                            for(EnemyPlayer enemy : entityManager.getPlayerManager().getEnemyArray())
                            {
                                if(enemy.getPlayerId() == Integer.parseInt(parameters[1]))
                                {
                                    enemy.onDeath(new MapCellCoordinates(Integer.parseInt(parameters[2]), Integer.parseInt(parameters[3])));
                                }
                            }
                            
                            //DEBUG
                            if(Constants.PROCESSDATADEBUG)
                                System.out.println("Enemy player " + parameters[1] + " died and spawned tomb stone at: X:" + parameters[2] + " Y:" + parameters[3]);
                        }else
                            System.err.println("ERROR: enemyPlayerSpawnTombStone wrong number of parameters");
                        break;
                        
                        
                    default:
                        System.err.println("ERROR: Command received from server is not valid");
                        System.err.println(receivedData);
                }  
            }
            
        }catch(InterruptedException | NumberFormatException e)
        {
            System.err.println("ERROR: ClientProcessData() Something went wrong " + e);
            e.printStackTrace();
            Gdx.app.exit();
        }
    }
}
    

