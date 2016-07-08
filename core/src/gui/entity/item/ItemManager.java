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
    private Array <Item> tombArray = new Array<>();
    private Array <Item> coinArray = new Array<>();
    
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

        for(Item item: tombArray)
        {
            item.render();
        }

        for (Item item: coinArray)
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
        for (int i=0; i < tombArray.size; i++)
        {
            if(this.tombArray.get(i).isCollected())
            {
                tombArray.get(i).deleteItem();
                tombArray.removeIndex(i);
            }
        }
        
        //Delete coins if collected
        for (int i=0; i < coinArray.size; i++)
        {
            if(this.coinArray.get(i).isCollected())
            {
                coinArray.get(i).deleteItem();
                coinArray.removeIndex(i);
            }
        }
        
        //Spawn Items Randomly
        if(spawnTimer >= Constants.ITEMTIMER)
        {
            //Delete all items
            for (int i=0; i < itemArray.size; i++)
            {
                itemArray.get(i).deleteItem();
                itemArray.removeIndex(i);
            }
            
            //One second after timer is done
            if(spawnTimer >= (Constants.ITEMTIMER + 1))
            {
                spawnRandomItemOnField();
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
     * Spawns a random item in every block with the attribute "Item-Spawner"
     */
    public void spawnRandomItemOnField()
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
                                CoinBag coinBag = new CoinBag(mapX, mapY, map, entityManager, Constants.COINVALUE * 10);
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
    
    
    /**--------------------GETTER & SETTER--------------------**/
    public void spawnYellowHeart(int CellX, int CellY)
    {
        YellowHeart item = new YellowHeart(CellX, CellY, map, entityManager);
        this.itemArray.add(item);
    }
    
    public void spawnTombstone(int cellX, int cellY, int coins, int playerId)
    {
        Tombstone tombstone = new Tombstone(cellX, cellY, map, entityManager, this, coins, playerId);
        this.tombArray.add(tombstone);
    }
    
    public void spawnSpeedUp(int CellX, int CellY)
    {
        SpeedUp item = new SpeedUp(CellX,  CellY, map, entityManager);
        this.itemArray.add(item);
    }
    
    public void spawnRangeUp(int CellX, int CellY)
    {
        RangeUp item = new RangeUp(CellX, CellY, map, entityManager);
        this.itemArray.add(item);
    }
    
    public void spawnLifeUp(int CellX, int CellY)
    {
        LifeUp item = new LifeUp(CellX, CellY, map, entityManager);
        this.itemArray.add(item);
    }
    
    public void spawnCoinBag(int CellX, int CellY, int value)
    {
        CoinBag item = new CoinBag(CellX, CellY, map, entityManager, value);
        this.itemArray.add(item);
    }
    

    public void spawnCoin(int cellX, int cellY, int value)
    {
        Coin coin = new Coin(cellX, cellY, map, entityManager, value);
        this.coinArray.add(coin);
    }
    

    public void spawnBombUp(int CellX, int CellY)
    {
        BombUp item = new BombUp(CellX, CellY, map, entityManager);
        this.itemArray.add(item);
    }
}

