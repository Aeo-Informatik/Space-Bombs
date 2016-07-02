/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import com.badlogic.gdx.graphics.OrthographicCamera;
import gui.entity.bombs.Bomb;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gdx.bomberman.Constants;
import gui.map.MapManager;
import gui.entity.item.Item;
import gui.entity.item.*;

/**
 *
 * @author qubasa
 */
public class EntityManager {
    
    //Variables & Objects
    private OrthographicCamera camera;
    private float timer;
    private MapManager map;
    
    //Array from libgdx is much faster in comparison to an arraylist
    //Players
    private Array <EnemyPlayer> enemies = new Array<>();
    private MainPlayer mainPlayer;
    private Spectator spectator;
    
    //Bombs
    private Array <Bomb> bombArray = new Array<>();
    private Array <Bomb> bombArrayEnemy = new Array<>();
    
    //Items
    private Array <Item> itemArray = new Array<>();
    private Array <Item> tombs = new Array<>();
    private Array <Item> coins = new Array<>();
    
    //Constructor
    public EntityManager(OrthographicCamera camera, MapManager map)
    {
        this.camera = camera;
        this.map = map;
    }
    

    public void render()
    {

        /**--------------------PLAYER RENDERER--------------------**/
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
        
        /**--------------------MAP OBJECTS RENDERER--------------------**/
        //Render bomb in main player list
        for (Bomb bomb: bombArray)
        {
            //It doesnt need a renderObject because the textures are dynamically 
            //set into the tile layer without beeing drawn
            bomb.render();
        }

        //Render bomb in enemy player list
        for (Bomb bomb: bombArrayEnemy)
        {
            bomb.render();
        }                       

        for(Item item: itemArray)
        {
            item.render();
        }

        for(Item item: tombs)
        {
            item.render();
        }

        for (Item item: coins)
        {
            item.render();
        }
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
        
        //Delete item if collected
        for (int i=0; i < itemArray.size; i++)
        {
            if(this.itemArray.get(i).isCollected())
            {
                itemArray.get(i).deleteItem();
                itemArray.removeIndex(i);
            }
        }
        
        //Delete tomb stone if collected
        for (int i=0; i < tombs.size; i++)
        {
            if(this.tombs.get(i).isCollected())
            {
                tombs.get(i).deleteItem();
                tombs.removeIndex(i);
            }
        }
        
        //Delete coins if collected
        for (int i=0; i < coins.size; i++)
        {
            if(this.coins.get(i).isCollected())
            {
                coins.get(i).deleteItem();
                coins.removeIndex(i);
            }
        }
        
        //Spawn Items Randomly
        if(timer >= Constants.ITEMTIMER)
        {
            for (int i=0; i < itemArray.size; i++)
            {
                itemArray.get(i).deleteItem();
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
                        EnemyPlayer enemyPlayer = new EnemyPlayer(new Vector2(mapX * Constants.MAPTEXTUREWIDTH, mapY * Constants.MAPTEXTUREHEIGHT), new Vector2(0,0), playerId, map, bombArrayEnemy, this, camera);
                        enemies.add(enemyPlayer);
                    }
                }catch(NullPointerException e)
                {

                }
            }
        }
    }
    
    /**
     * Spawns a random item in every block with the attribute "Item-Spawner"
     */
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
                        item = (int)(Math.random()*7);
                        switch(item)
                        {
                        
                            case(1):
                            {
                                
                                LifeUp lifeup = new LifeUp(mapX, mapY, new Vector2(0,0),map, this);
                                itemArray.add(lifeup);
                                break;
                            }
                            
                            case(2):
                            {
                                CoinBag coinBag = new CoinBag(mapX, mapY, new Vector2(0,0),map, this, Constants.COINVALUE);
                                itemArray.add(coinBag);
                                break;
                            }
                            
                            case(3):
                            {
                                RangeUp rangeUp = new RangeUp(mapX, mapY, new Vector2(0,0),map, this);
                                itemArray.add(rangeUp);
                                break;
                            }
                            
                            case(4):
                            {
                                BombUp bombUp = new BombUp(mapX, mapY, new Vector2(0,0),map, this);
                                itemArray.add(bombUp);
                                break;
                            }
                            
                            case(5):
                            {
                                YellowHeart yellowHeart = new YellowHeart(mapX, mapY, new Vector2(0,0),map, this);
                                itemArray.add(yellowHeart);
                                break;
                            }
                            
                            case(6):
                            {
                                SpeedUp speedUp = new SpeedUp(mapX, mapY, new Vector2(0,0),map, this);
                                itemArray.add(speedUp);
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
    }
    
    
    /**
     * Spawns a coin with a given value.
     * @param x: Cell coordinates on x axis
     * @param y: Cell coordinates on y axis
     */
    public void spawnCoin(int x, int y)
    {
        Coin coin = new Coin(x, y, new Vector2(0,0),map, this, Constants.COINVALUE);
        coins.add(coin);                                
    }
    
    
    /**
     * Spawns a tombstone with the remaining coins of the dead player.
     * @param x: Cell coordinates on x axis
     * @param y: Cell coordinates on y axis
     */
    public void spawnTombstone(int x, int y)
    {
        Tombstone tombstone = new Tombstone(x, y, new Vector2(0,0), map, this, mainPlayer.getCoins());
        tombs.add(tombstone);
    }
    
    
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
        for(Bomb mainP : this.bombArray)
        {
            if(mainP.getCellX() == cellX && mainP.getCellY() == cellY)
            {
                return mainP;
            }
        }
        
        //Check if enemyPlayer has a bomb on given position
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
                        mainPlayer = new MainPlayer(new Vector2(mapX * Constants.MAPTEXTUREWIDTH, mapY * Constants.MAPTEXTUREHEIGHT), new Vector2(0,0), playerId, camera, map, bombArray, this);
                    }
                }catch(NullPointerException e)
                {

                }
            }
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
