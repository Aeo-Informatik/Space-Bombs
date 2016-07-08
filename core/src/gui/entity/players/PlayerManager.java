/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.players;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gdx.bomberman.Constants;
import gui.entity.EntityManager;
import gui.entity.items.ItemManager;
import gui.map.MapManager;

/**
 *
 * @author qubasa
 */
public class PlayerManager 
{
    //Objects
    private OrthographicCamera camera;
    private MapManager map;
    private EntityManager entityManager;
      
    //Players
    private Array <EnemyPlayer> enemies = new Array<>();
    private MainPlayer mainPlayer;
    private Spectator spectator;
    
    //Constructor
    public PlayerManager(OrthographicCamera camera, MapManager map, EntityManager entityManager)
    {
        this.camera = camera;
        this.map = map;
        this.entityManager = entityManager;
    }
    
    
    public void render()
    {
        //Render every enemy object in list
        for(EnemyPlayer enemy: enemies)
        {
            enemy.render();
        }
        
        //Render main player
        if(mainPlayer != null)
        {            
            mainPlayer.render();

        //If main player equals null render spectator
        }else if(spectator != null)
        {
            spectator.render();
        }          
    }
    
    
    public void update()
    {
        //If enemy player received death message delete Object
        for(int i=0; i < enemies.size; i++)
        {
            if(enemies.get(i).isEnemyDead())
            {
                enemies.removeIndex(i);
            }
        }
        
        //If main player died spawn spectator and delete mainPlayer Object
        if(mainPlayer != null)
        {
            if(mainPlayer.getLife() <= 0)
            {
                //Create new spectator
                spectator = new Spectator(mainPlayer.getPosition(), new Vector2(0, 0), camera, map, enemies, entityManager);
                
                //On death
                mainPlayer.onDeath();
                
                //Delete main player
                mainPlayer = null; 
            }
        }
    }
    

    /**--------------------SPAWN FUNCTIONS--------------------**/       
    /**
     * Creates renders and adds a EnemyPlayer Object to the array
     * @param playerId: Id of player that goes from 1-4
     */
    public void spawnEnemyPlayer(int playerId)
    {
        //Iterate through all cells in the map on the y axis
        for(int mapY=0; mapY < map.getFloorLayer().getHeight(); mapY++)
        {
            //Iterate through all cells in the map on the x axis
            for(int mapX=0; mapX < map.getFloorLayer().getWidth(); mapX++)
            {
                try
                {
                    //If cell has attribute Spawn-P + playerId spawn enemy player there
                    if(map.getFloorLayer().getCell(mapX, mapY).getTile().getProperties().containsKey("Spawn-P" + playerId))
                    {
                        EnemyPlayer enemyPlayer = new EnemyPlayer(new Vector2(mapX * Constants.MAPTEXTUREWIDTH, mapY * Constants.MAPTEXTUREHEIGHT), new Vector2(0,0), playerId, map, entityManager, camera);
                        enemies.add(enemyPlayer);
                    }
                }catch(NullPointerException e)
                {

                }
            }
        }
    }
    
    
    /**
     * Spawns mainPlayer with given playerId on the apropriate spawn field.
     * @param playerId
     * @throws Exception 
     */
    public void spawnMainPlayer(int playerId)
    {
        for(int mapY=0; mapY < map.getFloorLayer().getHeight(); mapY++)
        {
            for(int mapX=0; mapX < map.getFloorLayer().getWidth(); mapX++)
            {
                try
                {
                    if(map.getFloorLayer().getCell(mapX, mapY).getTile().getProperties().containsKey("Spawn-P" + playerId))
                    {
                        mainPlayer = new MainPlayer(new Vector2(mapX * Constants.MAPTEXTUREWIDTH, mapY * Constants.MAPTEXTUREHEIGHT), new Vector2(0,0), playerId, camera, map, entityManager);
                    }
                }catch(NullPointerException e)
                {

                }
            }
        }
    }
    
    
    /**--------------------GETTER & SETTER--------------------**/
    /**
     * Returns the playerId from a player on specified coordinates.
     * @param x: Cell coordinates on x axis
     * @param y: Cell coordinates on y axis
     * @return playerId or -1
     */
    public int getPlayerIdOnCoordinates(int x, int y)
    {
            //Iterate through the enemy list
            for(EnemyPlayer enemy : enemies)
            {                
                try
                {
                    //Calculate enemy position in cell coordinates
                    int enemyCellX = (int) ((enemy.getPosition().x + Constants.PLAYERWIDTH / 2) / Constants.MAPTEXTUREWIDTH);
                    int enemyCellY = (int) ((enemy.getPosition().y + Constants.PLAYERHEIGHT / 2) / Constants.MAPTEXTUREWIDTH);
                    
                    //If enemy player found on given position
                    if(enemyCellX == x && enemyCellY == y)
                    {
                        return enemy.getPlayerId();
                    }
                    
                }catch(NullPointerException e)
                {
                    
                }
            }
            
            try
            {
                //Calculate main position in cell coordinates
                int mainCellX = (int)(mainPlayer.getPosition().x / Constants.MAPTEXTUREWIDTH);
                int mainCellY = (int)(mainPlayer.getPosition().y / Constants.MAPTEXTUREHEIGHT);
                
                //If main player found on given position
                if(mainCellX == x && mainCellY == y)
                {
                    return mainPlayer.getPlayerId();
                }
                
            }catch(NullPointerException e)
            {
                
            }
            
           return -1; 
    }
    
    public Array<EnemyPlayer> getEnemyArray()
    {
        return enemies;
    }
        
    public MainPlayer getMainPlayer()
    {
        return mainPlayer;
    }
    
    public Spectator getSpectator()
    {
        return this.spectator;
    }
    
}
