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
public class RangeUp extends Item{
    
    //Objects
    private final TextureRegion rangeUp;

    //Constructor
    public RangeUp(int cellX, int cellY, Vector2 direction, MapManager map, EntityManager entityManager) {
        super(cellX, cellY, direction, map, entityManager);
        this.rangeUp = TextureManager.rangeUp;
    }
   
    @Override
    public void render(SpriteBatch renderObject)
    {
    
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(rangeUp));
        cell.getTile().getProperties().put("rangeUp", null);
        map.getItemLayer().setCell(cellX, cellY, cell);
        
        if(entityManager.getMainPlayer() != null)
        {
            if(entityManager.getMainPlayer().getBombRange() < Constants.maxBombRange)
            {
                if(isMainPlayerCollectingItem() == true)
                {
                    itemEffect();
                }
            }
        } 
    }
    
    @Override
    public void itemEffect()
    {
        if(entityManager.getMainPlayer() != null)
        {
            entityManager.getMainPlayer().setBombRange((entityManager.getMainPlayer().getBombRange() + 1));   
        }
    }

    
}
