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
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.map.MapManager;
import gui.entity.MainPlayer;


/**
 *
 * @author Christian
 */
public class BombUp extends Item{
    
    
    private final TextureRegion bombUp;
    private int cellX, cellY;

    
    
    public BombUp(Vector2 pos, Vector2 direction, MapManager map, EntityManager entityManager) {
        super(pos, direction, map, entityManager);
        this.bombUp = TextureManager.bombUp;
    }
   
    public void render(SpriteBatch renderObject)
    {
        cellX = (int) pos.x;
        cellY = (int) pos.y;
        
        
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(bombUp));
        cell.getTile().getProperties().put("bombUp", null);
        
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
        entityManager.getMainPlayer().setMaxBombPlacing((entityManager.getMainPlayer().getMaxBombPlacing() + 1));
    }
    
}
