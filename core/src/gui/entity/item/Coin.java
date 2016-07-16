/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.item;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.gdx.bomberman.Constants;
import static com.gdx.bomberman.Main.client;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.map.MapManager;
import gui.AnimEffects;
import gui.AudioManager;
import gui.entity.player.MainPlayer;


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
        
        super.deleteItemThroughBomb();
        
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
        MainPlayer mainP = entityManager.getPlayerManager().getMainPlayer();
        
        //Check if main player is alive
        if(mainP != null)
        {
            mainP.setCoins((mainP.getCoins() + value)); 
            client.sendData("enemyPlayerSetCoins|" + mainP.getPlayerId() + "|" + (mainP.getCoins()) + "|*");
        }
    }
    

    
}
