/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.bomb;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gdx.bomberman.Constants;
import gui.entity.EntityManager;
import gui.map.MapManager;

/**
 *
 * @author qubasa
 */
public class BombManager 
{
    //Bomb Arrays
    private Array <Bomb> bombArrayMainPlayer = new Array<>();
    private Array <Bomb> bombArrayEnemyPlayers = new Array<>();
    
    //Objects
    private EntityManager entityManager;
    private MapManager map;
    
    public BombManager(MapManager map, EntityManager entityManager)
    {
        this.entityManager = entityManager;
        this.map = map;
    }
    
    public void render()
    {
        //Render bomb in main player list
        for (Bomb bomb: bombArrayMainPlayer)
        {
            bomb.render();
        }

        //Render bomb in enemy player list
        for (Bomb bomb: bombArrayEnemyPlayers)
        {
            bomb.render();
        }
    }
    
    
    public void update()
    {
        //Deletes main player bombs if exploded
        for (int i=0; i < bombArrayMainPlayer.size; i++)
        {
            if(this.bombArrayMainPlayer.get(i).isExploded())
            {
                bombArrayMainPlayer.removeIndex(i);
            }
        }
        
        //Deletes enemy player bombs if exploded
        for (int i=0; i < bombArrayEnemyPlayers.size; i++)
        {
            if(this.bombArrayEnemyPlayers.get(i).isExploded())
            {
                bombArrayEnemyPlayers.removeIndex(i);
            }
        }
    }
    
    
    /**--------------------SPAWN FUNCTIONS--------------------**/
    public void spawnNormalBomb(Vector2 pos, int playerId, int bombRange)
    {
        Bomb entity = new Bomb(pos, new Vector2(0,0), map, playerId, bombRange, entityManager);
        
        //If id is from main player add to mainPlayer bomb array if not to enemy bomb array
        if(entityManager.getPlayerManager().getMainPlayer() != null && entityManager.getPlayerManager().getMainPlayer().getPlayerId() == playerId)
        {
            bombArrayMainPlayer.add(entity);
        }else
        {
            bombArrayEnemyPlayers.add(entity);
        }
    }
    
    
    /**--------------------GETTER & SETTER--------------------**/
    /**
     * Returns the Bomb Object from a bomb on the specified coordinates. If there is no bomb return null.
     * @param x: Entity coordinates on x axis
     * @param y: Entity coordinates on y axis
     * @return Bomb Object or Null
     */
    public Bomb getBombObjectOnCoordinates(float posX, float posY)
    {
       //Calculate cell coordiantes
       int cellX = (int) (posX / Constants.MAPTEXTUREWIDTH);
       int cellY = (int) (posY / Constants.MAPTEXTUREHEIGHT);
        
        //Check if mainPlayer has a bomb on given position
        for(Bomb mainP : this.bombArrayMainPlayer)
        {
            if(mainP.getCellX() == cellX && mainP.getCellY() == cellY)
            {
                return mainP;
            }
        }
        
        //Check if enemyPlayer has a bomb on given position
        for(Bomb enemyP : this.bombArrayEnemyPlayers)
        {
            if(enemyP.getCellX() == cellX && enemyP.getCellY() == cellY)
            {
                return enemyP;
            }
        }
        
        return null;
    }
        
    
    public Array <Bomb> getBombArrayEnemy()
    {
        return this.bombArrayEnemyPlayers;
    }
    
    
    public Array <Bomb> getBombArrayMain()
    {
        return this.bombArrayMainPlayer;
    }
    
    public void clearAllBombs()
    {
        this.bombArrayEnemyPlayers.clear();
        this.bombArrayMainPlayer.clear();
    }
}