/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.bomb;

import com.badlogic.gdx.utils.Array;

import gui.entity.EntityManager;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;

/**
 *
 * @author qubasa
 */
public class BombManager 
{
    //Bomb Arrays
    private Array <Bomb> bombArrayMainPlayer = new Array<>();
    private Array <Bomb> bombArrayEnemyPlayers = new Array<>();
    
    // All bombs that are here are also saved in the other arrayLists 
    //it is just simpler to search in here.
    private Array <RemoteBomb> remoteArray = new Array<>();
    
    //Objects
    private EntityManager entityManager;
    private MapLoader map;
    
    public BombManager(MapLoader map, EntityManager entityManager)
    {
        this.entityManager = entityManager;
        this.map = map;
    }
    
    public void render()
    {
        //Render bomb in main player list
        for (int i=0; i < getMainArraySize(); i++)
        {
            getBombMainArray(i).render();
        }
        

        //Render bomb in enemy player list
        for (int i=0; i < getEnemyArraySize(); i++)
        {
            getBombEnemyArray(i).render();
        }
    }
    
    public void update()
    {
        //Deletes main player bombs if exploded
        for (int i=0; i < getMainArraySize(); i++)
        {
            if(getBombMainArray(i).isExploded())
            {
                removeMainArray(i);
            }
        }
        
        //Deletes enemy player bombs if exploded
        for (int i=0; i < getEnemyArraySize(); i++)
        {
            if(getBombEnemyArray(i).isExploded())
            {
                removeEnemyArray(i);
            }
        }
    }
    
    /**
     * 
     * @param playerId
     * @return true if sucessfull
     */
    public void triggerRemoteBomb(int playerId)
    {
        
        for(int i = 0; i < getRemoteArraySize();i++)
        {
           RemoteBomb bomb = getBombRemoteArray(i);
            
           if(bomb.getPlayerId() == playerId)
           {
                bomb.setActivated(true);
                removeRemoteArray(i);
           }
        }
    }
    
        
    /**--------------------SPAWN FUNCTIONS--------------------**/
    public int spawnBomb(Bomb bomb)
    {
        if(entityManager.getPlayerManager().getMainPlayer() != null && entityManager.getPlayerManager().getMainPlayer().getPlayerId() == bomb.getPlayerId())
        {
            addMainArray(bomb);
        }else
        {
            addEnemyArray(bomb);
        }
        return bomb.getBombId();
    }
    

    public int spawnRemote(RemoteBomb bomb)
    {
         if(entityManager.getPlayerManager().getMainPlayer() != null && entityManager.getPlayerManager().getMainPlayer().getPlayerId() == bomb.getPlayerId())
        {
            addMainArray(bomb);
            addRemoteArray(bomb);
        }else
        {
            addEnemyArray(bomb);
            addRemoteArray(bomb);
        }
        return bomb.getBombId();
    }
    
    /**--------------------GETTER & SETTER--------------------**/
    /**
     * Returns the Bomb Object from a bomb on the specified coordinates. If there is no bomb return null.
     * @return Bomb Object or Null
     */
    public Bomb getBombObjectInCell(MapCellCoordinates cell)
    {
        //Check if mainPlayer has a bomb on given position
        for(int i=0; i < getMainArraySize(); i++)
        {
            Bomb mainPlayerBomb = getBombMainArray(i);
            
            if(mainPlayerBomb.getCellX() == cell.getX() && mainPlayerBomb.getCellY() == cell.getY())
            {
                return mainPlayerBomb;
            }
        }
        
        //Check if enemyPlayer has a bomb on given position
        for(int i=0; i < getEnemyArraySize(); i++)
        {
            Bomb enemyPlayerBomb = getBombEnemyArray(i);
            
            if(enemyPlayerBomb.getCellX() == cell.getX() && enemyPlayerBomb.getCellY() == cell.getY())
            {
                return enemyPlayerBomb;
            }
        }
        
        return null;
    }
        
    /**--------------------BOMB ENEMY ARRAY--------------------**/
    private synchronized Array <Bomb> getEnemyArray()
    {
        return this.bombArrayEnemyPlayers;
    }
    
    public synchronized void removeEnemyArray(int i)
    {
        getEnemyArray().removeIndex(i);
    }
    
    public synchronized void addEnemyArray(Bomb bomb)
    {
        getEnemyArray().add(bomb);
    }
    
    public synchronized Bomb getBombEnemyArray(int i)
    {
        return getEnemyArray().get(i);
    }
    
    public synchronized void clearEnemyArray()
    {
        getEnemyArray().clear();
    }
    
    public synchronized int getEnemyArraySize()
    {
        return getEnemyArray().size;
    }
    
    /**--------------------BOMB MAIN ARRAY--------------------**/
    private synchronized Array <Bomb> getMainArray()
    {
        return this.bombArrayMainPlayer;
    }
    
    public synchronized void removeMainArray(int i)
    {
        getMainArray().removeIndex(i);
    }
    
    public synchronized void addMainArray(Bomb bomb)
    {
        getMainArray().add(bomb);
    }
    
    public synchronized Bomb getBombMainArray(int i)
    {
        return getMainArray().get(i);
    }
    
    public synchronized void clearMainArray()
    {
        getMainArray().clear();
    }
    
    public synchronized int getMainArraySize()
    {
        return getMainArray().size;
    }
    
    /**--------------------BOMB REMOTE ARRAY--------------------**/
    private synchronized Array <RemoteBomb> getRemoteArray() 
    {
        return remoteArray;
    }
    
    public synchronized int getRemoteArraySize()
    {
        return getRemoteArray().size;
    }
    
    public synchronized void removeRemoteArray(int i)
    {
        getRemoteArray().removeIndex(i);
    }
    
    public synchronized void addRemoteArray(RemoteBomb bomb)
    {
        getRemoteArray().add(bomb);
    }
    
    public synchronized RemoteBomb getBombRemoteArray(int i)
    {
        return getRemoteArray().get(i);
    }
    
    public synchronized void clearRemoteArray()
    {
        getRemoteArray().clear();
    }
    
    public void clearAllBombs()
    {
        clearEnemyArray();
        clearMainArray();
        clearRemoteArray();
    }

    

}
