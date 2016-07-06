/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.item;


import gui.TextureManager;
import gui.entity.EntityManager;
import gui.map.MapManager;


/**
 *
 * @author Christian
 */
public class Tombstone extends Item{
    
    private int coins;
    private ItemManager itemManager;
    
    //Constructor
    public Tombstone(int CellX, int CellY, MapManager map, EntityManager entityManager, ItemManager itemManager, int coins) 
    {
        super(CellX, CellY,TextureManager.tombstone, map, entityManager);
        this.coins = coins;
        this.itemManager = itemManager;
    }
   
    @Override
    public void render()
    {
        if(entityManager.getMainPlayer() != null)
        {
            if(isMainPlayerCollectingItem() == true)
            {
                itemEffect();
            }
        }
    }
    
    @Override
    public void itemEffect()
    {
        System.out.println("COLLECTED BY MAIN PLAYER TOMB STONE!");
        itemManager.spawnCoin(cellX, cellX);        
    }
}
