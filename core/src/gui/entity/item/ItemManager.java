/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.item;

import com.badlogic.gdx.utils.Array;
import com.gdx.bomberman.Constants;
import gui.entity.EntityManager;
import gui.map.MapManager;

/**
 *
 * @author qubasa
 */
public class ItemManager 
{
    //Objects
    private MapManager map;
    private EntityManager entityManager;
    
    //Variables
    private float spawnTimer;
    
    //Items
    private Array <Item> itemArray = new Array<>();
    private Array <Item> tombs = new Array<>();
    private Array <Item> coins = new Array<>();
    
    public ItemManager(MapManager map, EntityManager entityManager)
    {
        this.map = map;
        this.entityManager = entityManager;
    }
    
    
    public void render()
    {
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
        if(spawnTimer >= Constants.ITEMTIMER)
        {
            for (int i=0; i < itemArray.size; i++)
            {
                itemArray.get(i).deleteItem();
                itemArray.removeIndex(i);
            }
            if(spawnTimer >= (Constants.ITEMTIMER + 1))
            {
                spawnItem();
                spawnTimer = 0;
            }else
            {
                spawnTimer += Constants.DELTATIME;
            }
        }else
        {
            spawnTimer += Constants.DELTATIME;
        }
    }
    
    /**
     * Spawns a coin with a given value.
     * @param x: Cell coordinates on x axis
     * @param y: Cell coordinates on y axis
     */
    public void spawnCoin(int x, int y)
    {
        Coin coin = new Coin(x, y, map, entityManager, Constants.COINVALUE);
        coins.add(coin);                                
    }
    
    
    /**
     * Spawns a tombstone with the remaining coins of the dead player.
     * @param x: Cell coordinates on x axis
     * @param y: Cell coordinates on y axis
     */
    public void spawnTombstone(int x, int y)
    {
        if(entityManager.getMainPlayer() != null)
        {
            Tombstone tombstone = new Tombstone(x, y, map, entityManager, this, entityManager.getMainPlayer().getCoins());
            tombs.add(tombstone);
        }else
        {
            System.err.println("ERROR: Mainplayer is dead cannot spawn tombstone");
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
                                
                                LifeUp lifeup = new LifeUp(mapX, mapY, map, entityManager);
                                itemArray.add(lifeup);
                                break;
                            }
                            
                            case(2):
                            {
                                CoinBag coinBag = new CoinBag(mapX, mapY, map, entityManager, Constants.COINVALUE);
                                itemArray.add(coinBag);
                                break;
                            }
                            
                            case(3):
                            {
                                RangeUp rangeUp = new RangeUp(mapX, mapY, map, entityManager);
                                itemArray.add(rangeUp);
                                break;
                            }
                            
                            case(4):
                            {
                                BombUp bombUp = new BombUp(mapX, mapY, map, entityManager);
                                itemArray.add(bombUp);
                                break;
                            }
                            
                            case(5):
                            {
                                YellowHeart yellowHeart = new YellowHeart(mapX, mapY, map, entityManager);
                                itemArray.add(yellowHeart);
                                break;
                            }
                            
                            case(6):
                            {
                                SpeedUp speedUp = new SpeedUp(mapX, mapY, map, entityManager);
                                itemArray.add(speedUp);
                                break;
                            }
                        }
                        
                    }
                }catch(NullPointerException e)
                {

                }
            }
        }
    }
    
}
