/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.badlogic.gdx.Gdx;
import com.gdx.bomberman.Constants;

import gui.entity.EntityManager;
import gui.entity.bomb.Dynamite;
import gui.entity.bomb.Infinity;
import gui.entity.bomb.X3;
import gui.entity.player.EnemyPlayer;
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

            //Check that command is not send from this device and command is not empty
            if (!parameters[parameters.length - 1].equals(Integer.toString(Constants.PLAYERID)) && !receivedData.isEmpty())
            {
                switch (parameters[0]) 
                {
                    /**------------------Delete Items----------------**/
                    //General: deleteItems|senderId
                    case "deleteItems":
                        entityManager.getItemManager().clearItems();
                        break;
                    
                    /**------------------SPAWN COIN------------------**/
                    //General: "spawnCoin|posX|posY|senderId"
                    case "spawnCoin":
                        if(parameters.length == 4)
                        {
                            //System.out.println("Received spawnCoin command!");
                            
                            //Spawn coin
                            entityManager.getItemManager().spawnCoin(new MapCellCoordinates(Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2])), Constants.COINVALUE);
                        }else
                            System.err.println("ERROR: spawnCoin wrong number of parameters");
                        break;
                            
                    /**------------------START COINS------------------**/
                    //General: "registerStartCoins|amount|senderId"    
                    case "registerStartCoins":
                        if(parameters.length == 3)
                        {
                            //Set start coins
                            entityManager.getPlayerManager().getMainPlayer().setCoins(Integer.parseInt(parameters[1]));
                        
                            for(EnemyPlayer enemy : entityManager.getPlayerManager().getEnemyArray())
                            {
                                enemy.setCoins(Integer.parseInt(parameters[1]));
                            }
                        }else
                            System.err.println("ERROR: start coins wrong number of parameters");
                        break;
                    
                    /**------------------START LIVES------------------**/
                    //General: "registerStartLives|amount|senderId"    
                    case "registerStartLives":
                         if(parameters.length == 3)
                        {
                            //Set start bombs
                            entityManager.getPlayerManager().getMainPlayer().setLife(Integer.parseInt(parameters[1]));
                        
                            for(EnemyPlayer enemy : entityManager.getPlayerManager().getEnemyArray())
                            {
                                enemy.setLife(Integer.parseInt(parameters[1]));
                            }
                        }else
                            System.err.println("ERROR: start live wrong number of parameters");
                        break;    
                        
                    /**------------------START SPEED------------------**/
                    //General: "registerStartSpeed|amount|senderId"    
                    case "registerStartSpeed":
                         if(parameters.length == 3)
                        {
                            //Set start bombs
                            entityManager.getPlayerManager().getMainPlayer().setEntitySpeed(Integer.parseInt(parameters[1]));
                        
                            for(EnemyPlayer enemy : entityManager.getPlayerManager().getEnemyArray())
                            {
                                enemy.setEntitySpeed(Integer.parseInt(parameters[1]));
                            }
                        }else
                            System.err.println("ERROR: start speed wrong number of parameters");
                        break;     
                        
                    /**------------------START BOMB PLACE------------------**/
                    //General: "registerStartSpeed|amount|senderId"    
                    case "registerStartBombPlace":
                         if(parameters.length == 3)
                        {
                            //Set start bombs
                            entityManager.getPlayerManager().getMainPlayer().setMaxBombPlacing(Integer.parseInt(parameters[1]));
                        
                            for(EnemyPlayer enemy : entityManager.getPlayerManager().getEnemyArray())
                            {
                                enemy.setMaxBombPlacing(Integer.parseInt(parameters[1]));
                            }
                        }else
                            System.err.println("ERROR: start bomb place wrong number of parameters");
                        break;     
                        
                    /**------------------START RANGE------------------**/
                    //General: "registerStartRange|amount|senderId"    
                    case "registerStartRange":
                         if(parameters.length == 3)
                        {
                            //Set start bombs
                            entityManager.getPlayerManager().getMainPlayer().setBombRange(Integer.parseInt(parameters[1]));
                        
                            for(EnemyPlayer enemy : entityManager.getPlayerManager().getEnemyArray())
                            {
                                enemy.setBombRange(Integer.parseInt(parameters[1]));
                            }
                        }else
                            System.err.println("ERROR: start range wrong number of parameters");
                        break;         
                        
                    /**------------------COIN BONUS------------------**/    
                    //General: "registerCoinBonus|amount|senderId"    
                    case "registerCoinBonus":
                        if(parameters.length == 3)
                        {
                            //Set start bombs
                            entityManager.getPlayerManager().getMainPlayer().setCoinBonus(Integer.parseInt(parameters[1]));
                            
                            for(EnemyPlayer enemy : entityManager.getPlayerManager().getEnemyArray())
                            {
                                enemy.setCoinBonus(Integer.parseInt(parameters[1]));
                            }
                        }else
                            System.err.println("ERROR: coinBonus: wrong number of parameters");
                        break;      
                        
                    /**------------------SPAWN BOMB------------------**/
                    case "spawnBombOnItemfield":
                        //General:spawnBombOnItemfield|bombType|itemField|range|senderId
                        if(parameters.length == 5)
                        { 
                            try
                            {
                                ThinGridCoordinates itemField = mapManager.getSpawnerPositionsArray().get(Integer.parseInt(parameters[2]));
                                int range = Integer.parseInt(parameters[3]);
                                int playerId = 0;
                                
                                
                                switch(parameters[1])
                                {
                                    case "dynamite":
                                        entityManager.getBombManager().spawnBomb(new Dynamite(itemField, playerId, range, mapManager, entityManager));
                                        break;
                                    case "infinity":
                                        entityManager.getBombManager().spawnBomb(new Infinity(itemField, playerId, range, 4, mapManager, entityManager));
                                        break;
                                    case "X3":
                                        //ThinGridCoordinates pos, int playerId,  int range, int X, int explosionRepeat, MapLoader map, EntityManager entityManager
                                        entityManager.getBombManager().spawnBomb(new X3(itemField, playerId, range, 1, range, mapManager, entityManager));
                                        break;
                                        
                                }
                                
                            }catch(IndexOutOfBoundsException e)
                            {
                                System.err.println("ERROR: spawnBombOnItemfield wrong itemField number " + e);
                            }
                        }else
                            System.err.println("ERROR: spawnBombOnItemfield wrong number of parameters");
                        break;        
                    
                    /**------------------ENEMY CHANGE BOMB SELECTION------------------**/
                    case "enemyPlayerSetChoosenBomb":
                        //General:enemyPlayerSetChoosenBomb|index|playerId|senderId
                        if(parameters.length == 4)
                        { 
                            for(EnemyPlayer enemy : entityManager.getPlayerManager().getEnemyArray())
                            {
                                if(enemy.getPlayerId() == Integer.parseInt(parameters[2]))
                                {
                                    //placeBomb(Vector2 pos, String bombType)
                                    enemy.setChoosenBomb(Integer.parseInt(parameters[1]));
                                }
                            }
                            
                        }else
                            System.err.println("ERROR: enemyPlayerChangeBombSelection wrong number of parameters");
                        break;      
                    
                     /**------------------ENEMY SET BOMB COST------------------**/    
                    case "enemyPlayerSetBombCost":
                        //General:enemyPlayerSetBombCost|bombId|bombCost|playerId|senderId
                        if(parameters.length == 5)
                        { 
                            for(EnemyPlayer enemy : entityManager.getPlayerManager().getEnemyArray())
                            {
                                if(enemy.getPlayerId() == Integer.parseInt(parameters[3]))
                                {
                                    //placeBomb(Vector2 pos, String bombType)
                                    enemy.setBombPrice(Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2]));
                                }
                            }
                            
                        }else
                            System.err.println("ERROR: enemyPlayerSetBombCost wrong number of parameters");
                        break;   
                        
                    /**------------------SPAWN ITEM------------------**/
                    //General:spawnItem|itemType|itemField|senderId
                    case "spawnItem":
                        if(parameters.length == 4)
                        { 
                            try
                            {
                                ThinGridCoordinates itemField = mapManager.getSpawnerPositionsArray().get(Integer.parseInt(parameters[2]));
                                MapCellCoordinates itemFieldCellPos = new MapCellCoordinates(itemField);
                                
                                switch(parameters[1])
                                {
                                    case "CubicRangeUp":
                                        entityManager.getItemManager().spawnCubicRangeUp(itemFieldCellPos);
                                        break;
                                    
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
                    //General: setGameMap|mapPath|senderId
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
                    //General: registerPlayerId|playerId|senderId
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
                    //General: registerAmountPlayers|amount|senderId
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
                    //General: spawnPlayers|senderId
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
                    //General: "moveEnemyPlayer|playerId|posX|posY|DIRECTION|senderId"
                    case "moveEnemyPlayer":
                        if(parameters.length == 6)
                        {
                            for(EnemyPlayer enemy : entityManager.getPlayerManager().getEnemyArray())
                            {
                                if(enemy.getPlayerId() == Integer.parseInt(parameters[1]))
                                {
                                    ThinGridCoordinates newPos = new ThinGridCoordinates(Float.parseFloat(parameters[2]),Float.parseFloat(parameters[3]));
                                    enemy.setNewPos(newPos);
                                    
                                    String movementDirection = parameters[4];
                                    if(movementDirection.equalsIgnoreCase(Constants.NONE))
                                    {
                                        enemy.setStopPlayerMovement(true);
                                    }else
                                    {
                                        enemy.setStopPlayerMovement(false);
                                        enemy.setPlayerMovementDirection(movementDirection);
                                    }
                                }
                            }
                            
                        }else 
                            System.err.println("ERROR: moveEnemyPlayer wrong number of parameters");
                        break;
 
                    /**------------------ENEMY PLAYER PLACES BOMB------------------**/
                    //General: placeEnemyBomb|posX|posY|playerId|bombType|senderId"
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
                        
                    /**------------------ENEMY PLAYER PLACES INFINITY BOMB------------------**/
                    //General: "placeInfinityBomb|posX|posY|playerId|explodePath|senderId"
                    case "placeInfinityBomb":
                        if(parameters.length == 6)
                        {   //Check if bomb is placed by own player
                            if(Integer.parseInt(parameters[3]) != Constants.PLAYERID)
                            {
                                for(EnemyPlayer enemy : entityManager.getPlayerManager().getEnemyArray())
                                {
                                    if(enemy.getPlayerId() == Integer.parseInt(parameters[3]))
                                    {
                                        //placeBomb(Vector2 pos, String bombType)
                                                enemy.placeInfinityBomb(new ThinGridCoordinates(Float.parseFloat(parameters[1]), Float.parseFloat(parameters[2])), Integer.parseInt(parameters[4]));
                                        }
                                    }
                                }
                        }else
                            System.err.println("ERROR: placeBomb wrong number of parameters");
                        break;
                        
                    /**------------------ENEMY PLAYER GETS HIT------------------**/   
                    //General: "enemyPlayerLife|playerId|life|senderId"
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
                    //General: "enemyPlayerSetRange|playerId|bombRange|senderId"
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
                    
                    /**------------------ENEMY PLAYER CUBIC RANGE---------**/ 
                    //General: "enemyPlayerSetCubicRange|playerId|cubicRange|senderId"
                        case "enemyPlayerSetCubicRange":
                        if(parameters.length == 4)
                        {
                            for(EnemyPlayer enemy : entityManager.getPlayerManager().getEnemyArray())
                            {
                                if(enemy.getPlayerId() == Integer.parseInt(parameters[1]))
                                {
                                    enemy.setCubicRange(Integer.parseInt(parameters[2]));
                                }
                            }
                            
                            //DEBUG
                            if(Constants.PROCESSDATADEBUG)
                                System.out.println("Enemy player " + parameters[1] + " range now: " + parameters[2]);
                            
                        }else
                            System.err.println("ERROR: enemyPlayerSetRange wrong number of parameters");
                        break;
                        
                    /**------------------ENEMY PLAYER SPEED------------------**/
                    //General: "enemyPlayerSetSpeed|playerId|speed|*"
                    case "enemyPlayerSetSpeed":
                        if(parameters.length == 4)
                        {
                            for(EnemyPlayer enemy : entityManager.getPlayerManager().getEnemyArray())
                            {
                                if(enemy.getPlayerId() == Integer.parseInt(parameters[1]))
                                {
                                    enemy.setEntitySpeed(Integer.parseInt(parameters[2]));
                                }
                            }
                            
                            //DEBUG
                            if(Constants.PROCESSDATADEBUG)
                                System.out.println("Enemy player " + parameters[1] + " speed now: " + parameters[2]);
                            
                        }else
                            System.err.println("ERROR: enemyPlayerSetSpeed wrong number of parameters");
                        break;
                    
                    /**------------------ENEMY PLAYER COINS------------------**/
                    //General: "enemyPlayerSetCoins|playerId|coins|senderId"
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
                    //General: "enemyPlayerSetMaxBombs|playerId|maxBombPlace|senderId"
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
   
                    /**------------------ENEMY PLAYER MAX BOMBS------------------**/
                    //General: "enemyHitByBomb|playerId|senderId"    
                    case "enemyHitByBomb":
                        if(parameters.length == 3)
                        {
                            for(EnemyPlayer enemy : entityManager.getPlayerManager().getEnemyArray())
                            {
                                if(enemy.getPlayerId() == Integer.parseInt(parameters[1]))
                                {
                                    enemy.hitByBomb();
                                }
                            }
                            
                            //DEBUG
                            if(Constants.PROCESSDATADEBUG)
                                System.out.println("Enemy player " + parameters[1] + " has been hit by a bomb.");
                        }else
                            System.err.println("ERROR: enemyHitByBomb wrong number of parameters");
                        break;
                        
                    /**------------------ENEMY PLAYER MAX BOMBS------------------**/
                    //General: "triggerRemoteBomb|playerId|senderId"    
                    case "triggerRemoteBomb":
                        if(parameters.length == 3)
                        {
                            entityManager.getBombManager().triggerRemoteBomb(Integer.parseInt(parameters[1]));
                        }else
                            System.err.println("ERROR: enemyPlayerSetMaxBombs wrong number of parameters");
                        break;
                        
                    default:
                        System.err.println("ERROR: Command received from server is not valid");
                        System.err.println("Faulty command: " + receivedData);
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
    

