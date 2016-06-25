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
import gui.entity.Entity;
import gui.entity.EntityManager;
import gui.map.MapManager;
/**
 *
 * @author cb0703
 */
public class Item extends Entity{

    private boolean collected = false;
    private TextureRegion emptyBlock;
    private int cellX, cellY;
    
    public Item(Vector2 pos, Vector2 direction, MapManager map, EntityManager entityManager) {
        super(pos, direction, map, entityManager);
        this.emptyBlock = TextureManager.emptyBlock;
        cellX = (int) pos.x;
        cellY = (int) pos.y;
        
    }
    
    public void render(SpriteBatch renderObject)
    {
        
    }
    
    public int check(int X, int Y)
    {
        if(entityManager.getPlayerIDOnPosCoordinates(X, Y) != -1)
        {
            collected = true;
            return entityManager.getPlayerIDOnPosCoordinates(X, Y);
        }
        return -1;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }
    
    public void clear()
    {
        TiledMapTileLayer.Cell cellCenter = new TiledMapTileLayer.Cell();
            cellCenter.setTile(new StaticTiledMapTile(emptyBlock));
             map.getItemLayer().setCell(cellX, cellY, cellCenter);
    }

    public int getCellX() {
        return cellX;
    }

    public void setCellX(int cellX) {
        this.cellX = cellX;
    }

    public int getCellY() {
        return cellY;
    }

    public void setCellY(int cellY) {
        this.cellY = cellY;
    }
    
    
    
    
}
