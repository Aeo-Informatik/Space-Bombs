/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.item;

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
 * @author cb0703
 */
public class Item 
{
    
    //Variables
    protected boolean collected = false;
    protected TextureRegion emptyBlock;
    protected int cellX, cellY;
    protected EntityManager entityManager;
    protected MapManager map;
    
    //Constructor
    public Item(int cellX, int cellY, TextureRegion itemTexture, MapManager map, EntityManager entityManager) 
    {
        this.emptyBlock = TextureManager.emptyBlock;
        this.cellX = cellX;
        this.cellY = cellY;
        this.entityManager = entityManager;
        this.map = map;
        
        //Render Item once
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(itemTexture));
        map.getItemLayer().setCell(cellX, cellY, cell);
    }
    
    
    /**
     * Check if a player is on this item field.
     * @return playerId or -1
     */
    public int getPlayerIdCollectingItem()
    {
        if(entityManager.getPlayerIdOnCoordinates(cellX, cellY) != -1)
        {
            collected = true;
            return entityManager.getPlayerIdOnCoordinates(cellX, cellY);
        }
        return -1;
    }
    
    
    /**
     * Check if id is from main player.
     * @param ID player id
     * @return true or false
     */
    public boolean isMainPlayerCollectingItem()
    {
        if(entityManager.getMainPlayer() != null)
        {
            if(entityManager.getMainPlayer().getPlayerId() == getPlayerIdCollectingItem())
            {
                return true;
            }
        }
        return false;
    }

    
    /**
     * Deletes item texture in cell.
     */
    public void deleteItem()
    {
        TiledMapTileLayer.Cell cellCenter = new TiledMapTileLayer.Cell();
        cellCenter.setTile(new StaticTiledMapTile(emptyBlock));
        map.getItemLayer().setCell(cellX, cellY, cellCenter);
    }
    
    public void itemEffect()
    {
        
    }

    public void render()
    {
        
    }
    
    /**--------------------GETTER & SETTER--------------------**/
    public boolean isCollected() 
    {
        return collected;
    }

    public void setCollected(boolean collected)
    {
        this.collected = collected;
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
