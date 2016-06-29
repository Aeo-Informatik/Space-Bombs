/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import com.badlogic.gdx.Game;
import gui.entity.bombs.Bomb;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gdx.bomberman.Constants;
import gui.camera.OrthoCamera;
import gui.map.MapManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import gui.entity.item.Item;
import gui.entity.item.*;

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
    private SpriteBatch renderItem = new SpriteBatch();
    
    //Array from libgdx is much faster in comparison to an arraylist
    private Array <EnemyPlayer> enemies = new Array<>();
    private MainPlayer mainPlayer;
    private Spectator spectator;
    private float itemTimer;
    private float timer;
    private MapManager map;
    private Array <Bomb> bombArray = new Array<>();
    private Array <Bomb> bombArrayEnemy = new Array<>();
    private Array <Item> itemArray = new Array<>();
    private Array <Item> tombs = new Array<>();
    
    //Constructor
    public EntityManager(OrthoCamera camera, MapManager map)
    {
        this.camera = camera;
        this.map = map;
        this.itemTimer = Constants.itemTimer;
    }
    

    public void render()
    {
        //Lets spriteBatch use the coordinate system specified by camera instead of the default one. This has to be done 
        //because both of the coordinate systems are different and the camera.combined will do the maths for you.
        renderMainPlayer.setProjectionMatrix(camera.combined);
        renderEnemyPlayer.setProjectionMatrix(camera.combined);
        renderSpectator.setProjectionMatrix(camera.combined);
        renderOther.setProjectionMatrix(camera.combined);
        renderItem.setProjectionMatrix(camera.combined);
        
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
        
        renderItem.begin();
        
        for(Item item: itemArray)
        {
            item.render(renderItem);
        }
        
        for(Item item: tombs)
        {
            item.render(renderItem);
        }
        renderItem.end();
    }
    
    
    public void update()
    {
        //If enemy player died execute onDeath and delete Object
        for(int i=0; i < enemies.size; i++)
        {
            if(enemies.get(i).getLife() <= 0)
            {
                enemies.get(i).onDeath();
                
                //Try to remove object till it succeed
                try
                {
                    while(enemies.get(i).getPlayerId() != -100)
                    {
                        enemies.removeIndex(i);
                    }
                }catch(IndexOutOfBoundsException e)
                {
                    
                }
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
        
        //
        for (int i=0; i < itemArray.size; i++)
        {
            if(this.itemArray.get(i).isCollected())
            {
                itemArray.get(i).clear();
                itemArray.removeIndex(i);
            }
        }
        
        //
        if(timer >= itemTimer)
        {
            for (int i=0; i < itemArray.size; i++)
            {
                itemArray.get(i).clear();
                itemArray.removeIndex(i);
            }   
            spawnItem();
            timer = 0;
        }else
        {
            timer += Constants.DELTATIME;
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
    
    public void spawnItem()
    {
        int i=0;
        
        for(int mapY=0; mapY < map.getFloorLayer().getHeight(); mapY++)
        {
            for(int mapX=0; mapX < map.getFloorLayer().getWidth(); mapX++)
            {
                try
                {
                    if(map.getFloorLayer().getCell(mapX, mapY).getTile().getProperties().containsKey("Item-Spawner"))
                    {
                        int item;
                        item = (int)(Math.random()*5);
                        switch(item)
                        {
                        
                            case(1):
                            {
                                
                                LifeUp lifeup = new LifeUp(new Vector2(mapX, mapY), new Vector2(0,0),map, this);
                                itemArray.add(lifeup);
                                break;
                            }
                            
                            case(2):
                            {
                                Coin coin = new Coin(new Vector2(mapX, mapY), new Vector2(0,0),map, this, 1);
                                itemArray.add(coin);
                                break;
                            }
                            
                            case(3):
                            {
                                RangeUp rangeUp = new RangeUp(new Vector2(mapX, mapY), new Vector2(0,0),map, this);
                                itemArray.add(rangeUp);
                                break;
                            }
                            
                            case(4):
                            {
                                BombUp bombUp = new BombUp(new Vector2(mapX, mapY), new Vector2(0,0),map, this);
                                itemArray.add(bombUp);
                                break;
                            }
                            
                            
                            default:
                            {

                            }
                        
                        }
                        
                    }
                }catch(NullPointerException e)
                {

                }
            }
        }
        System.out.println(itemArray.size);
    }
    
    public void spawnCoin(int x, int y)
    {
        
        Coin coin = new Coin(new Vector2(x, y), new Vector2(0,0),map, this, 2);
        itemArray.add(coin);
        System.out.println("Coin");
                                
    }
    
    public void spawnTombstone(int x, int y)
    {
        Tombstone tombstone = new Tombstone(new Vector2(x, y), new Vector2(0,0), map, this, mainPlayer.getCoins());
        tombs.add(tombstone);
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
    
    public int getPlayerIDOnPosCoordinates(int X, int Y)
    {
        
            for(EnemyPlayer enemy:enemies)
            {                
                try
                {
                    if(enemy.getPosition().x / Constants.MAPTEXTUREWIDTH == X 
                            && enemy.getPosition().y / Constants.MAPTEXTUREHEIGHT == Y)
                    {
                        return enemy.getPlayerId();
                    }
                }catch(NullPointerException e)
                {
                    
                }
            }
            try
            {
                if((int)(mainPlayer.getPosition().x / Constants.MAPTEXTUREWIDTH) == X 
                    && (int)(mainPlayer.getPosition().y / Constants.MAPTEXTUREHEIGHT) == Y)
                {
                    return mainPlayer.getPlayerId();
                }
            }catch(NullPointerException e)
            {
                
            }
            
       
        
           return -1; 
        
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
