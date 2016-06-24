/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.entity.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.map.MapManager;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import gui.AnimEffects;

/**
 *
 * @author cb0703
 */
public class Speed extends Item{

    private final Animation speedAnim;
    private int cellX, cellY;
    private AnimEffects animEffects = new AnimEffects();
    
    public Speed(Vector2 pos, Vector2 direction, MapManager map, EntityManager entityManager) {
        super(pos, direction, map, entityManager);
        this.speedAnim = TextureManager.speed_Anim;
        cellX = (int) pos.x;
        cellY = (int) pos.y;
    }
   
    public void render(SpriteBatch renderObject)
    {
        cellX = (int) pos.x;
        cellY = (int) pos.y;
        
        Cell cell = new Cell();
        cell.setTile(new StaticTiledMapTile(animEffects.getFrame(speedAnim)));
        cell.getTile().getProperties().put("bomb", null);
        
        map.getItemLayer().setCell(cellX, cellY, cell);
        
        
        super.check();
    }
    

    
}
