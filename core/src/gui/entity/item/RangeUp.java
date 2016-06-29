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
    
    
    private final TextureRegion rangeUp;
    private int cellX, cellY;

    
    
    public RangeUp(Vector2 pos, Vector2 direction, MapManager map, EntityManager entityManager) {
        super(pos, direction, map, entityManager);
        this.rangeUp = TextureManager.rangeUp;
    }
   
    public void render(SpriteBatch renderObject)
    {
        cellX = (int) pos.x;
        cellY = (int) pos.y;
        
        
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(rangeUp));
        cell.getTile().getProperties().put("rangeUp", null);
        
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
        if(entityManager.getMainPlayer().getBombRange() < Constants.maxBombRange)
        {
            entityManager.getMainPlayer().setBombRange((entityManager.getMainPlayer().getBombRange() + 1));
        }
        
    }

    
}
