/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.player;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import gui.entity.EntityManager;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;
import java.util.ArrayList;

/**
 *
 * @author qubasa
 */
public class PlayerManager 
{
    //Objects
    private OrthographicCamera camera;
    private MapLoader map;
    private EntityManager entityManager; 
    
    //Players
    private Array <EnemyPlayer> enemies = new Array<>();
    private MainPlayer mainPlayer;
    private Spectator spectator;
    private ArrayList<Integer> deadPlayers = new ArrayList<>();
    
    //Constructor
    public PlayerManager(OrthographicCamera camera, MapLoader map, EntityManager entityManager)
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
                deadPlayers.add(enemies.get(i).getPlayerId());
                enemies.removeIndex(i);
            }
        }
        
        //If main player died spawn spectator and delete mainPlayer Object
        if(mainPlayer != null)
        {
            if(mainPlayer.getLife() <= 0)
            {
                deadPlayers.add(mainPlayer.getPlayerId());
                
                //Create new spectator
                spectator = new Spectator(mainPlayer.getPosition(), new ThinGridCoordinates(0, 0), camera, map, enemies, entityManager);
                
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
                        EnemyPlayer enemyPlayer = new EnemyPlayer(new ThinGridCoordinates(mapX, mapY), new ThinGridCoordinates(0,0), playerId, map, entityManager, camera);
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
                        mainPlayer = new MainPlayer(new ThinGridCoordinates(mapX, mapY), new ThinGridCoordinates(0,0), playerId, camera, map, entityManager, entityManager.getSendCommand());
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
    public int getPlayerIdOnCoordinates(MapCellCoordinates localCellPos)
    {
            //Iterate through the enemy list
            for(EnemyPlayer enemy : enemies)
            {                
                try
                {
                    //Calculate enemy position in cell coordinates
                    MapCellCoordinates enemyCell = new MapCellCoordinates(enemy.getFeetLocation());
                    
                    //If enemy player found on given position
                    if(enemyCell.getX() == localCellPos.getX() && enemyCell.getY() == localCellPos.getY())
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
                MapCellCoordinates mainCell = new MapCellCoordinates(mainPlayer.getFeetLocation());

                //If main player found on given position
                if(mainCell.getX() == localCellPos.getX() && mainCell.getY() == localCellPos.getY())
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
    
    public ArrayList<Integer> getDeadPlayersArray()
    {
        return this.deadPlayers;
    }
}
