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
import com.gdx.bomberman.Constants;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.map.MapManager;
import gui.AudioManager;


/**
 *
 * @author Christian
 */
public class CoinBag extends Item{
    
    private int value;

    public CoinBag(int cellX, int cellY, Vector2 direction, MapManager map, EntityManager entityManager, int value) {
        super(cellX, cellY, direction,TextureManager.coinBag, map, entityManager);
        this.value = value * 10;
    }
   
    @Override
    public void render(SpriteBatch renderObject)
    {
        //Render item
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(TextureManager.coinBag));
        map.getItemLayer().setCell(cellX, cellY, cell);
        
        if(isMainPlayerCollectingItem() == true)
        {
            itemEffect();
        }
        
        if(getPlayerIdCollectingItem() != -1)
        {
            long id = AudioManager.singleCoin.play();
            AudioManager.singleCoin.setVolume(id, Constants.SOUNDVOLUME);
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
