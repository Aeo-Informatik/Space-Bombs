/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.map.MapManager;
import gui.AnimEffects;


/**
 *
 * @author Christian
 */
public class Coin extends Item{
    
    
    private Animation coinAnim;
    private AnimEffects animEffects = new AnimEffects();
    private int value;

    
    
    public Coin(int cellX, int cellY, Vector2 direction, MapManager map, EntityManager entityManager, int value) {
        super(cellX, cellY, direction, map, entityManager);
        this.coinAnim = TextureManager.coinAnim;
        this.value = value;
    }
   
    @Override
    public void render(SpriteBatch renderObject)
    {
        //Render item
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(animEffects.getFrame(coinAnim)));
        cell.getTile().getProperties().put("coin", null);
        map.getItemLayer().setCell(cellX, cellY, cell);
        
        
        int id = getPlayerCollectingItem();
        
        if(entityManager.getMainPlayer() != null)
        {
            if(id != -1)
            {
                if(isMainPlayer(id) == true)
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
            entityManager.getMainPlayer().setCoins((entityManager.getMainPlayer().getCoins()+ value));
        }
    }
    

    
}
