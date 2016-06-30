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
public class YellowHeart extends Item{
    
    
    private final TextureRegion yellowHeart;

    
    /**
     * 
     * @param pos the position of the Itemonly in int
     * @param direction alwalys 0, 0
     * @param map
     * @param entityManager 
     */
    public YellowHeart(int CellX, int CellY, Vector2 direction, MapManager map, EntityManager entityManager) {
        super(CellX, CellY, direction, map, entityManager);
        this.yellowHeart = TextureManager.yellowHeart;
    }
   
    @Override
    public void render(SpriteBatch renderObject)
    {
        //Render Item
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(yellowHeart));
        cell.getTile().getProperties().put("yellowHeart", null);
        map.getItemLayer().setCell(cellX, cellY, cell);
        
        if(entityManager.getMainPlayer() != null)
        {
            if(entityManager.getMainPlayer().getLife()< Constants.maxLife)
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
            entityManager.getMainPlayer().setLife(Constants.maxLife);
        }
    }
}
