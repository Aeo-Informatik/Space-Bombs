/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import gui.entity.bombs.Bomb;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import gui.Constants;
import gui.camera.OrthoCamera;
import gui.map.MapManager;
import gui.screen.GameScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author qubasa
 */
public class EntityManager {
    
    //Variables & Objects
    private OrthoCamera camera;
    private SpriteBatch renderObject;
    
    //Array from libgdx is much faster in comparison to an arraylist
    private Array <EnemyPlayer> enemies = new Array<>();
    private MainPlayer mainPlayer;
    private Spectator spectator;
    private MapManager map;
    private Array <Bomb> bombArray = new Array<>();
    private Array <Bomb> bombArrayEnemy = new Array<>();
    
    //Constructor
    public EntityManager(OrthoCamera camera, MapManager map, GameScreen gameScreen)
    {
        this.camera = camera;
        this.map = map;
        this.renderObject = gameScreen.getRenderObject();
    }
    

    public void render()
    {
        //For every Enemy Player Object that is stored in the arraylist execute the render function in it
        for(EnemyPlayer enemy: enemies)
        {
            renderObject.begin();
            enemy.render(renderObject);
            renderObject.end();
        }
        
        for (Bomb bomb: bombArray)
        {
            renderObject.begin();
            bomb.render(renderObject);
            renderObject.end();
        }
        
        for (Bomb bomb: bombArrayEnemy)
        {
            renderObject.begin();
            bomb.render(renderObject);
            renderObject.end();
        }        
        
        //Executes the render function in the mainPlayer object
        if(mainPlayer != null)
        {
            renderObject.begin();
            mainPlayer.render(renderObject);
            renderObject.end();
            
        }else if(spectator != null)
        {
            renderObject.begin();
            spectator.render(renderObject);
            renderObject.end();
        }
                    
    }
    
    
    public void update()
    {
        //For every Enemy Player Object that is stored in the arraylist execute the update function in it
        for(int i=0; i < enemies.size; i++)
        {
            enemies.get(i).update();
            
            //If player is dead
            if(enemies.get(i).getLife() <= 0)
            {
                enemies.get(i).onDeath();
                enemies.removeIndex(i);
            }
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
            
            if(mainPlayer.getLife() <= 0)
            {
                //Create new spectator
                spectator = new Spectator(mainPlayer.getPosition(), new Vector2(0, 0), camera, map, enemies, this);
                
                //On death
                mainPlayer.onDeath();
                
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
                        EnemyPlayer enemyPlayer = new EnemyPlayer(new Vector2(mapX * Constants.MAPTEXTUREWIDTH, mapY * Constants.MAPTEXTUREHEIGHT), new Vector2(0,0), playerId, map, bombArrayEnemy, this);
                        enemies.add(enemyPlayer);
                    }
                }catch(NullPointerException e)
                {

                }
            }
        }
    }
    
    /**
     * Returns the Bomb Object on the specified coordinates. If there is no bomb return null.
     * @param x Cell coordiante on x axis
     * @param y Cell coordiante on y axis
     * @return Bomb Object or Null
     */
    public Bomb getBombObjOnCellCoordinates(int x, int y)
    {
        System.out.println("Checking if a bomb obj is on Coordinates: X=" + x + " Y=" + y);
        for(Bomb mainP : this.bombArray)
        {
            System.out.println("MainPlayer Bomb: X="+ mainP.getCellX() + " Y=" + mainP.getCellY());
            if(mainP.getCellX() == x && mainP.getCellY() == y)
            {
                return mainP;
            }
        }
        System.out.println("No matching main Player bomb found!");
        
        for(Bomb enemyP : this.bombArrayEnemy)
        {
            if(enemyP.getCellX() == x && enemyP.getCellY() == y)
            {
                return enemyP;
            }
        }
        
        return null;
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
                            mainPlayer = new MainPlayer(new Vector2(mapX * Constants.MAPTEXTUREWIDTH, mapY * Constants.MAPTEXTUREHEIGHT), new Vector2(0,0), playerId, camera, map, bombArray, this);
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
     * Places the specified bomb type into the map for enemy player
     * @param pos
     * @param direction
     * @param playerId
     * @param bombType 
     */
    public void placeEnemyBomb(Vector2 pos, Vector2 direction, int playerId, String bombType)
    {
        switch(bombType)
        {
            case "default":
                Bomb defaultBomb = new Bomb(pos, direction, map, playerId, this);
                bombArrayEnemy.add(defaultBomb);
                break;
            
            default:
                Bomb defaultBomb1 = new Bomb(pos, direction, map, playerId, this);
                bombArray.add(defaultBomb1);
        }
    }


    public void setLiveEnemyPlayer(int playerId, int life)
    {
        for(int i=0; i < enemies.size; i++)
        {
            if(enemies.get(i).getPlayerId() == playerId)
            {
                enemies.get(i).setLife(life);
            }
        }
    }
    
    
    /**--------------------GETTER & SETTER--------------------**/
    /**
     * 
     * @return Array<EnemyPlayer>
     */
    public Array<EnemyPlayer> getEnemyArray()
    {
        return enemies;
    }
    
    public SpriteBatch getRenderObject()
    {
        return this.renderObject;
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

    public MapManager getMap() {
        return map;
    }

    public void setMap(MapManager map) {
        this.map = map;
    }
    
}
