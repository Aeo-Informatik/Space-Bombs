/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.gdx.bomberman.Constants;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.map.MapManager;


/**
 *
 * @author Christian
 */
public class Tombstone extends Item{
    
    
    private final TextureRegion tombstone;
    private int cellX, cellY;
    private int coins;

    
    
    public Tombstone(Vector2 pos, Vector2 direction, MapManager map, EntityManager entityManager, int coins) {
        super(pos, direction, map, entityManager);
        this.tombstone = TextureManager.tombstone;
        this.coins=coins;
    }
   
    public void render(SpriteBatch renderObject)
    {
        cellX = (int) pos.x;
        cellY = (int) pos.y;
        
        
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(tombstone));
        cell.getTile().getProperties().put("Tombstone", null);
        
        map.getItemLayer().setCell(cellX, cellY, cell);
        
        
        int id = super.check(cellX, cellY) ;
        
        if(id != -1)
        {
            if(super.collectedbyMainPlayer(id) == true)
            {
                doItem();
            }
        }

    }
    
    public void doItem()
    {
            entityManager.spawnCoin(cellX, cellX);        
    }

    
}
