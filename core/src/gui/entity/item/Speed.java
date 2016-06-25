/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.entity.item;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.map.MapManager;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;


/**
 *
 * @author cb0703
 */
public class Speed extends Item{

    private final TextureRegion speedUp;
    private int cellX, cellY;
    
    
    public Speed(Vector2 pos, Vector2 direction, MapManager map, EntityManager entityManager) {
        super(pos, direction, map, entityManager);
        this.speedUp = TextureManager.speedUp;

    }
   
    public void render(SpriteBatch renderObject)
    {
        cellX = (int) pos.x;
        cellY = (int) pos.y;
        
        
        Cell cell = new Cell();
        cell.setTile(new StaticTiledMapTile(speedUp));
        cell.getTile().getProperties().put("bomb", null);
        
        map.getItemLayer().setCell(cellX, cellY, cell);
        
        
        super.check(cellX, cellY);
    }
    

    
}
