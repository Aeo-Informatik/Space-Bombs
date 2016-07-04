/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.item;


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

    public CoinBag(int cellX, int cellY, MapManager map, EntityManager entityManager, int value) {
        super(cellX, cellY,TextureManager.coinBag, map, entityManager);
        this.value = value * 10;
    }
   
    @Override
    public void render()
    {
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
