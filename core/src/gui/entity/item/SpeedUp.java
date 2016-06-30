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
public class SpeedUp extends Item{

    private final TextureRegion speedUp;
    
    public SpeedUp(int CellX, int CellY, Vector2 direction, MapManager map, EntityManager entityManager) {
        super(CellX, CellY, direction, map, entityManager);
        this.speedUp = TextureManager.speedUp;

    }
   
    public void render(SpriteBatch renderObject)
    {
        //Render Item
        Cell cell = new Cell();
        cell.setTile(new StaticTiledMapTile(speedUp));
        cell.getTile().getProperties().put("speed", null);
        map.getItemLayer().setCell(cellX, cellY, cell);
        
        
       int id = getPlayerCollectingItem();
    }
        
        

}
