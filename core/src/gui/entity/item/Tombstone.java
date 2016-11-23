/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.item;


import gui.TextureManager;
import gui.entity.EntityManager;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;


/**
 *
 * @author Christian
 */
public class Tombstone extends Item{
    
    public String Discription = "You are dead youre enemy cancollect youre coins";
    private int coins;
    private ItemManager itemManager;
    private int playerId;
    
    //Constructor
    public Tombstone(MapCellCoordinates cellPos, MapLoader map, EntityManager entityManager, ItemManager itemManager, int coins, int playerId) 
    {
        super(cellPos,TextureManager.tombstone, map, entityManager);
        this.coins = coins;
        this.itemManager = itemManager;
        this.playerId = playerId;
    }
   
    
    /**
     * Check if a player is on this item field.
     * @return playerId or -1
     */
    @Override
    protected int getPlayerIdCollectingItem()
    {
        int currentPlayerId = entityManager.getPlayerManager().getPlayerIdOnCoordinates(cellPos);
        
        //Check if player is on item cell
        if( currentPlayerId != -1)
        {
            //If player standing on item isnt the same as who spawned it. 
            //This is to prevent the tomstone to despawn instantly because the dying player stands on it
            if(currentPlayerId != playerId)
            {
                collected = true;
                return entityManager.getPlayerManager().getPlayerIdOnCoordinates(cellPos);
            }
        }
        return -1;
    }
    
    @Override
    public void render()
    {
        if(entityManager.getPlayerManager().getMainPlayer() != null)
        {
            if(isMainPlayerCollectingItem() == true)
            {
                itemEffect();
            }
            
        }else //To make it possible for other players to despawn an item even after main player death
        {
            getPlayerIdCollectingItem();
        }
    }
    
    @Override
    public void itemEffect()
    {
        itemManager.spawnCoinBag(cellPos, coins);
    }
}
