/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import gui.camera.OrthoCamera;
import gui.map.MapManager;

/**
 *
 * @author qubasa
 */
public class EntityManager {
    
    //Vatiables
    private OrthoCamera camera;
    
    //Array from libgdx is much faster in comparison to an arraylist
    private Array<EnemyPlayer> enemies = new Array<>();
    private MainPlayer mainPlayer;
    private MapManager map;
    
    //Constructor
    public EntityManager(OrthoCamera camera, MapManager map)
    {
        this.camera = camera;
        this.map = map;
    }
    

    public void render(SpriteBatch sb)
    {
        //For every Enemy Player Object that is stored in the arraylist execute the render function in it
        for(EnemyPlayer enemy: enemies)
        {
            enemy.render(sb);
        }
        
        //Executes the render function in the mainPlayer object
        if(mainPlayer != null)
        {
            mainPlayer.render(sb);
        }
    }
    
    
    public void update()
    {
        //For every Enemy Player Object that is stored in the arraylist execute the update function in it
        for(EnemyPlayer enemy: enemies)
        {
            enemy.update();
        }
        
        //Executes the update function in the mainPlayer object
        if(mainPlayer != null)
            mainPlayer.update();
    }
    
    /**--------------------SPAWN FUNCTIONS--------------------**/
    
    /**
     * Creates renders and adds a EnemyPlayer Object to the array
     * @param playerId
     */
    public void spawnEnemyPlayer(int playerId)
    {
        for(int mapY=0; mapY < map.getFloor().getHeight(); mapY++)
        {
            for(int mapX=0; mapX < map.getFloor().getWidth(); mapX++)
            {
                try
                {
                    if(map.getFloor().getCell(mapX, mapY).getTile().getProperties().containsKey("Spawn-P" + playerId))
                    {
                        System.out.println("On cell coordinates " + mapX + " " + mapY + " found spawn for P" + playerId);
                        EnemyPlayer enemyPlayer = new EnemyPlayer(new Vector2(mapX * map.getFloor().getTileWidth(), mapY * map.getFloor().getTileHeight()), new Vector2(0,0), playerId, map);
                        enemies.add(enemyPlayer);
                    }
                }catch(NullPointerException e)
                {

                }
            }
        }
    }
    
    
    /**
     * Creates a MainPlayer Object and saves it in EntityManager
     * @param playerId
     * @throws Exception 
     */
    public void spawnMainPlayer(int playerId) throws Exception
    {
        try 
        {
            for(int mapY=0; mapY < map.getFloor().getHeight(); mapY++)
            {
                for(int mapX=0; mapX < map.getFloor().getWidth(); mapX++)
                {
                    try
                    {
                        if(map.getFloor().getCell(mapX, mapY).getTile().getProperties().containsKey("Spawn-P" + playerId))
                        {
                            System.out.println("On cell coordinates " + mapX + " " + mapY + " found spawn for P" + playerId);
                            mainPlayer = new MainPlayer(new Vector2(mapX * map.getFloor().getTileWidth(), mapY * map.getFloor().getTileHeight()), new Vector2(0,0), playerId, camera, map);
                        }
                    }catch(NullPointerException e)
                    {
                        
                    }
                }
            }
            
        } catch (Exception e) 
        {
            throw e;
        }
    }
    

    /**--------------------GETTER & SETTER--------------------**/
    /**
     * 
     * @return Array<EnemyPlayer>
     */
    public Array<EnemyPlayer> getEnemieArray()
    {
        return enemies;
    }
    
    /**
     * 
     * @return MainPlayer
     */
    public MainPlayer getMainPlayer()
    {
        return mainPlayer;
    }
}
