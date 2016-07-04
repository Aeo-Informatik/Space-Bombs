/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.map.MapManager;


/**
 *
 * @author Christian
 */
public class Tombstone extends Item{
    
    private int coins;

    //Constructor
    public Tombstone(int CellX, int CellY, MapManager map, EntityManager entityManager, int coins) {
        super(CellX, CellY,TextureManager.tombstone, map, entityManager);
        this.coins = coins;
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
        entityManager.spawnCoin(cellX, cellX);        
    }
}
