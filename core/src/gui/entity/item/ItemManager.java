/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.item;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gdx.bomberman.Constants;
import gui.entity.EntityManager;
import gui.map.MapLoader;
import java.util.Random;

/**
 *
 * @author qubasa
 */
public class ItemManager 
{
    //Objects
    private MapLoader map;
    private EntityManager entityManager;
    
    //Variables
    private float spawnTimer;
    
    //Items
    private Array <Item> itemArray = new Array<>();
    private Array <Item> tombArray = new Array<>();
    private Array <Item> coinArray = new Array<>();
    
    public ItemManager(MapLoader map, EntityManager entityManager)
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
        
    }
    
    
    /**--------------------SPAWN FUNCTIONS--------------------**/
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

