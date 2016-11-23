/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.entity.item;

import client.SendCommand;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import gui.TextureManager;
import gui.entity.Entity;
import gui.entity.EntityManager;
import gui.map.MapLoader;
import com.gdx.bomberman.Constants;
import gui.map.MapCellCoordinates;
import gui.map.ThinGridCoordinates;
/**
 *
 * @author phinix
 */
public class Item extends Entity
{
    
    //Variables
    protected boolean collected = false;
    protected TextureRegion emptyBlock;
    protected MapCellCoordinates cellPos;
    protected SendCommand sendCommand;
    
    protected float timer;
    protected float spawnProtection = Constants.SPAWNPROTECTION;
    
    //Constructor
    public Item(MapCellCoordinates cellPos, TextureRegion itemTexture, MapLoader map, EntityManager entityManager) 
    {
        super(new ThinGridCoordinates(cellPos.getX(), cellPos.getY()), new ThinGridCoordinates(0,0), map, entityManager);
        
        this.emptyBlock = TextureManager.emptyBlock;
        this.cellPos = cellPos;
        this.sendCommand = entityManager.getSendCommand();
        
        //Render Item once
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(itemTexture));
        map.getItemLayer().setCell(cellPos.getX(), cellPos.getY(), cell);
    }
    
    
    @Override
    public void render()
    {
        if(spawnProtection <= timer && collected == false)
        {
            deleteItemThroughBomb();
        
            if(entityManager.getPlayerManager().getMainPlayer() != null)
            {
                //Check if mainPlayer has reached item max 
                if(canGetCollectedByMainPlayer() == true)
                {
                    if(isMainPlayerCollectingItem() == true)
                    {
                        itemEffect();
                    }
                }
            }else //To make it possible for other players to despawn an item even after main player death
            {
                getPlayerIdCollectingItem();
            }
        }else
        {
           timer += Constants.DELTATIME; 
        }
    }
    
    /**
     * Check if a player is on this item field.
     * @return playerId or -1
     */
    protected int getPlayerIdCollectingItem()
    {
        if(entityManager.getPlayerManager().getPlayerIdOnCoordinates(cellPos) != -1)
        {
            collected = true;
            return entityManager.getPlayerManager().getPlayerIdOnCoordinates(cellPos);
        }
        return -1;
    }
    
    
    /**
     * Check if id is from main player.
     * @param ID player id
     * @return true or false
     */
    protected boolean isMainPlayerCollectingItem()
    {
        if(entityManager.getPlayerManager().getMainPlayer() != null)
        {
            if(entityManager.getPlayerManager().getMainPlayer().getPlayerId() == getPlayerIdCollectingItem())
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
        map.getItemLayer().setCell(cellPos.getX(), cellPos.getY(), cellCenter);
        collected = true;
    }
    
    /**
     * delete item if it is hit by a bomb
     */
    public void deleteItemThroughBomb()
    {
        if(Constants.DELETEITEMSTHROUGHBOMB)
        {
            //Check if item has been hit by deadly tile
            if(map.isCellDeadly(cellPos))
            {
                collected = true;
            }
        }
    }
    
    /**
     * check if item is usable for the player
     * @return true if it is collectable
     */
    public boolean canGetCollectedByMainPlayer()
    {
        return true;
    }
    
    /**
     * the effect of the item
     */
    public void itemEffect()
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
    
    public MapCellCoordinates getCellPos() {
        return this.cellPos;
    }

    public void setCellX(MapCellCoordinates cellPos) {
        this.cellPos = cellPos;
    }
}
