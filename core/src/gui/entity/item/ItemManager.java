/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.item;

import client.SendCommand;
import com.badlogic.gdx.utils.Array;
import gui.entity.EntityManager;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;


/**
 *
 * @author qubasa
 */
public class ItemManager 
{
    //Objects
    private MapLoader map;
    private EntityManager entityManager;
    private SendCommand sendCommand;
    
    //Items
    private Array <Item> itemArray = new Array<>();
    private Array <Item> tombArray = new Array<>();
    private Array <Item> coinArray = new Array<>();
    
    public ItemManager(MapLoader map, EntityManager entityManager, SendCommand sendCommand)
    {
        this.map = map;
        this.entityManager = entityManager;
        this.sendCommand = sendCommand;
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
        
    }
    
    
    /**--------------------SPAWN FUNCTIONS--------------------**/
    public void spawnYellowHeart(MapCellCoordinates localCellPos)
    {
        YellowHeart item = new YellowHeart(localCellPos, map, entityManager);
        this.itemArray.add(item);
    }
    
    public void spawnTombstone(MapCellCoordinates localCellPos, int coins, int playerId)
    {
        Tombstone tombstone = new Tombstone(localCellPos, map, entityManager, this, coins, playerId);
        this.tombArray.add(tombstone);
    }
    
    public void spawnSpeedUp(MapCellCoordinates localCellPos)
    {
        SpeedUp item = new SpeedUp(localCellPos, map, entityManager);
        this.itemArray.add(item);
    }
    
    public void spawnRangeUp(MapCellCoordinates localCellPos)
    {
        RangeUp item = new RangeUp(localCellPos, map, entityManager);
        this.itemArray.add(item);
    }
    
    public void spawnLifeUp(MapCellCoordinates localCellPos)
    {
        LifeUp item = new LifeUp(localCellPos, map, entityManager);
        this.itemArray.add(item);
    }
    
    public void spawnCoinBag(MapCellCoordinates localCellPos, int value)
    {
        CoinBag item = new CoinBag(localCellPos, map, entityManager, value);
        this.itemArray.add(item);
    }
    

    public void spawnCoin(MapCellCoordinates localCellPos, int value)
    {
        Coin coin = new Coin(localCellPos, map, entityManager, value);
        this.coinArray.add(coin);
    }
    

    public void spawnBombUp(MapCellCoordinates localCellPos)
    {
        BombUp item = new BombUp(localCellPos, map, entityManager);
        this.itemArray.add(item);
    }
    
    /**--------------------GETTER & SETTER--------------------**/
    public void clearAllItems()
    {
        for (Item item: itemArray)
        {
            item.deleteItem();            
        }
        for (Item item: coinArray)
        {
            item.deleteItem();            
        }
        for (Item item: tombArray)
        {
            item.deleteItem();            
        }
    }
    
    public void clearItems()
    {
        for (Item item: itemArray)
        {
            item.deleteItem();            
        }
    }
}

