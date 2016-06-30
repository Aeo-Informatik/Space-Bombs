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
    private int cellX, cellY;

    
    /**
     * 
     * @param pos the position of the Itemonly in int
     * @param direction alwalys 0, 0
     * @param map
     * @param entityManager 
     */
    public YellowHeart(Vector2 pos, Vector2 direction, MapManager map, EntityManager entityManager) {
        super(pos, direction, map, entityManager);
        this.yellowHeart = TextureManager.yellowHeart;
    }
   
    public void render(SpriteBatch renderObject)
    {
        cellX = (int) pos.x;
        cellY = (int) pos.y;
        
        
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(yellowHeart));
        cell.getTile().getProperties().put("yellowHeart", null);
        
        map.getItemLayer().setCell(cellX, cellY, cell);
        
        if(entityManager.getMainPlayer() != null)
        {
        if(entityManager.getMainPlayer().getLife()< Constants.maxLife)
        {
           int id = super.check(cellX, cellY) ;
            if(id != -1)
            {
                if(super.collectedbyMainPlayer(id) == true)
                {
                    doItem();
                }
            }
        }
        }
    }
    
    public void doItem()
    {

        entityManager.getMainPlayer().setLife(Constants.maxLife);
        
    }

    
}
