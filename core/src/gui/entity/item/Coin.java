/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.item;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.gdx.bomberman.Constants;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.map.MapManager;
import gui.AnimEffects;
import gui.AudioManager;


/**
 *
 * @author Christian
 */
public class Coin extends Item{
    
    private AnimEffects animEffects = new AnimEffects();
    private int value;

    public Coin(int cellX, int cellY, MapManager map, EntityManager entityManager, int value) {
        super(cellX, cellY,TextureManager.coinAnim.getKeyFrame(0), map, entityManager);
        this.value = value;
    }
   
    @Override
    public void render()
    {
        //Render item
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(animEffects.getFrame(TextureManager.coinAnim)));
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
