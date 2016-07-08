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
import gui.entity.item.ItemManager;

/**
 *
 * @author qubasa
 */
public class EntityManager {
    
    //Variables & Objects
    private OrthographicCamera camera;
    private MapManager map;
    private ItemManager itemManager;
    
    //NOTE: Array from libgdx is much faster in comparison to an arraylist
    
    //Players
    private Array <EnemyPlayer> enemies = new Array<>();
    private MainPlayer mainPlayer;
    private Spectator spectator;
    
    //Bombs
    private Array <Bomb> bombArrayMainPlayer = new Array<>();
    private Array <Bomb> bombArrayEnemyPlayers = new Array<>();
    
    //Constructor
    public EntityManager(OrthographicCamera camera, MapManager map)
    {
        this.camera = camera;
        this.map = map;
        this.itemManager = new ItemManager(map, this);
    }
    

    public void render()
    {
        //Render Items
        itemManager.render();
        
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
        
        /**--------------------BOMB RENDERER--------------------**/
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
        //Update Items
        itemManager.update();
        
        /**--------------------PLAYER UPDATE--------------------**/
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
                spectator = new Spectator(mainPlayer.getPosition(), new Vector2(0, 0), camera, map, enemies, this);
                
                //On death
                mainPlayer.onDeath();
                
                //Delete main player
                mainPlayer = null; 
            }
        }
        
        /**--------------------BOMB UPDATE--------------------**/
        //Deletes bomb texture and object if exploded
        for (int i=0; i < bombArrayMainPlayer.size; i++)
        {
            if(this.bombArrayMainPlayer.get(i).isExploded())
            {
                bombArrayMainPlayer.removeIndex(i);
            }
        }
    }
    
    
    /**--------------------OTHER FUNCTIONS--------------------**/

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
                        EnemyPlayer enemyPlayer = new EnemyPlayer(new Vector2(mapX * Constants.MAPTEXTUREWIDTH, mapY * Constants.MAPTEXTUREHEIGHT), new Vector2(0,0), playerId, map, this, camera);
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
                        mainPlayer = new MainPlayer(new Vector2(mapX * Constants.MAPTEXTUREWIDTH, mapY * Constants.MAPTEXTUREHEIGHT), new Vector2(0,0), playerId, camera, map, this);
                    }
                }catch(NullPointerException e)
                {

                }
            }
        }
    }
    
    public void spawnNormalBomb(Vector2 pos, int playerId, int bombRange)
    {
        Bomb entity = new Bomb(pos, new Vector2(0,0), map, playerId, bombRange, this);
        
        //If id is from main player add to mainPlayer bomb array if not to enemy bomb array
        if(mainPlayer != null && mainPlayer.getPlayerId() == playerId)
        {
            bombArrayMainPlayer.add(entity);
        }else
        {
            bombArrayEnemyPlayers.add(entity);
        }
    }
    
    /**--------------------GETTER & SETTER--------------------**/
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
    
    public Array <Bomb> getBombArrayEnemy()
    {
        return this.bombArrayEnemyPlayers;
    }
    
    public Array <Bomb> getBombArrayMain()
    {
        return this.bombArrayMainPlayer;
    }
    
    public ItemManager getItemManager()
    {
        return this.itemManager;
    }    
}
