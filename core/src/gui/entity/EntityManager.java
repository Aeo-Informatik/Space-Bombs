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
    private SpriteBatch renderMainPlayer = new SpriteBatch();
    private SpriteBatch renderEnemyPlayer = new SpriteBatch();
    private SpriteBatch renderSpectator = new SpriteBatch();
    private SpriteBatch renderOther = new SpriteBatch();

    
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
    }
    

    public void render()
    {
        //Lets spriteBatch use the coordinate system specified by camera instead of the default one. This has to be done 
        //because both of the coordinate systems are different and the camera.combined will do the maths for you.
        renderMainPlayer.setProjectionMatrix(camera.combined);
        renderEnemyPlayer.setProjectionMatrix(camera.combined);
        renderSpectator.setProjectionMatrix(camera.combined);
        renderOther.setProjectionMatrix(camera.combined);
        
        /**--------------------PLAYER RENDERER--------------------**/
        //Render every enemy object in list
        for(EnemyPlayer enemy: enemies)
        {
            renderEnemyPlayer.begin();
            enemy.render(renderEnemyPlayer);
            renderEnemyPlayer.end();
        }
        
        //Render main player
        if(mainPlayer != null)
        {
            renderMainPlayer.begin();
            mainPlayer.render(renderMainPlayer);
            renderMainPlayer.end();
        
        //If main player equals null render spectator
        }else if(spectator != null)
        {
            renderSpectator.begin();
            spectator.render(renderSpectator);
            renderSpectator.end();
        }          
        
        /**--------------------MAP OBJECTS RENDERER--------------------**/
        renderOther.begin();
        
        //Render bomb in main player list
        for (Bomb bomb: bombArray)
        {
            //It doesnt need a renderObject because the textures are dynamically 
            //set into the tile layer without beeing drawn
            bomb.render(renderOther);
        }
        
        //Render bomb in enemy player list
        for (Bomb bomb: bombArrayEnemy)
        {
            bomb.render(renderOther);
        }      
        
        renderOther.end();
    }
    
    
    public void update()
    {
        //If enemy player died execute onDeath and delete Object
        for(int i=0; i < enemies.size; i++)
        {
            if(enemies.get(i).getLife() <= 0)
            {
                enemies.get(i).onDeath();
                enemies.removeIndex(i);
            }
        }
        
        //Deletes bomb texture and object if exploded
        for (int i=0; i < bombArray.size; i++)
        {
            if(this.bombArray.get(i).isExploded())
            {
                bombArray.removeIndex(i);
            }
        }
        
        //If main player died spawn spectator and delete mainPlayer Object
        if(mainPlayer != null)
        {
            if(mainPlayer.getLife() <= 0)
            {
                //Create new spectator
                spectator = new Spectator(mainPlayer.getPosition(), new Vector2(0, 0), camera, map, enemies, this);
                
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
    public Bomb getBombObjOnPosCoordinates(float posX, float posY)
    {
       int cellX = (int) (posX / Constants.MAPTEXTUREWIDTH);
       int cellY = (int) (posY / Constants.MAPTEXTUREHEIGHT);
        
        for(Bomb mainP : this.bombArray)
        {
            if(mainP.getCellX() == cellX && mainP.getCellY() == cellY)
            {
                return mainP;
            }
        }
        
        for(Bomb enemyP : this.bombArrayEnemy)
        {
            if(enemyP.getCellX() == cellX && enemyP.getCellY() == cellY)
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

    /**
     * Set the live of an enemy player
     * @param playerId
     * @param life 
     */
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
