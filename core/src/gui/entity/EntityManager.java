/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import gui.entity.bombs.Bomb;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import gui.Constants;
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
    private Array <EnemyPlayer> enemies = new Array<>();
    private MainPlayer mainPlayer;
    private Spectator spectator;
    private MapManager map;
    private Array <Bomb> bombArray = new Array<>();
    private Array <Bomb> bombArrayEnemy = new Array<>();
    
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
        
        for (Bomb bomb: bombArray)
        {
            bomb.render(sb);
        }
        
        for (Bomb bomb: bombArrayEnemy)
        {
            bomb.render(sb);
        }        
        
        //Executes the render function in the mainPlayer object
        if(mainPlayer != null)
        {
            mainPlayer.render(sb);
        }else if(spectator != null)
        {
            spectator.render(sb);
        }
                    
    }
    
    
    public void update()
    {
        //For every Enemy Player Object that is stored in the arraylist execute the update function in it
        for(EnemyPlayer enemy: enemies)
        {
            enemy.update();
        }
        
        //Deletes bomb if exploded
        for (int i=0; i < bombArray.size; i++)
        {
            this.bombArray.get(i).update();
            
            if(this.bombArray.get(i).isExploded())
            {
                bombArray.removeIndex(i);
            }
        }
        
        //Executes the update function in the mainPlayer object
        if(mainPlayer != null)
        {
            mainPlayer.update();
            
            if(mainPlayer.getLife() == 0)
            {
                //Create new spectator
                spectator = new Spectator(mainPlayer.getPosition(), new Vector2(0, 0), camera, map, enemies);
                
                //Delete main player
                mainPlayer = null; 
            }
        }else if(spectator != null)
        {
            spectator.update();
        }
    }
    
    /**--------------------SPAWN FUNCTIONS--------------------**/
    /**
     * Creates renders and adds a EnemyPlayer Object to the array
     * @param playerId
     */
    public void spawnEnemyPlayer(int playerId)
    {
        for(int mapY=0; mapY < map.getFloorLayer().getHeight(); mapY++)
        {
            for(int mapX=0; mapX < map.getFloorLayer().getWidth(); mapX++)
            {
                try
                {
                    if(map.getFloorLayer().getCell(mapX, mapY).getTile().getProperties().containsKey("Spawn-P" + playerId))
                    {
                        EnemyPlayer enemyPlayer = new EnemyPlayer(new Vector2(mapX * Constants.MAPTEXTUREWIDTH, mapY * Constants.MAPTEXTUREHEIGHT), new Vector2(0,0), playerId, map, bombArrayEnemy);
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
            for(int mapY=0; mapY < map.getFloorLayer().getHeight(); mapY++)
            {
                for(int mapX=0; mapX < map.getFloorLayer().getWidth(); mapX++)
                {
                    try
                    {
                        if(map.getFloorLayer().getCell(mapX, mapY).getTile().getProperties().containsKey("Spawn-P" + playerId))
                        {
                            mainPlayer = new MainPlayer(new Vector2(mapX * Constants.MAPTEXTUREWIDTH, mapY * Constants.MAPTEXTUREHEIGHT), new Vector2(0,0), playerId, camera, map, bombArray);
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
    
    /**
     * Places the specified bomb type into the map
     * @param pos
     * @param direction
     * @param playerId
     * @param bombType 
     */
    public void placeBomb(Vector2 pos, Vector2 direction, int playerId, String bombType)
    {
        switch(bombType)
        {
            case "default":
                Bomb defaultBomb = new Bomb(pos, direction, map, playerId);
                bombArray.add(defaultBomb);
                break;
            
            default:
                Bomb defaultBomb1 = new Bomb(pos, direction, map, playerId);
                bombArray.add(defaultBomb1);
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
    
    public Spectator getSpectator()
    {
        return this.spectator;
    }
    
    public Array <Bomb> getBombArrayEnemy()
    {
        return this.bombArrayEnemy;
    }
    
    public Array <Bomb> getBombArrayMain()
    {
        return this.bombArray;
    }
}
