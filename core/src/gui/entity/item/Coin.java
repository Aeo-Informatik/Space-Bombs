/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.item;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.gdx.bomberman.Constants;

import gui.AnimEffects;
import gui.AudioManager;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.entity.player.MainPlayer;
import gui.entity.player.Player;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;


/**
 *
 * @author Christian
 */
public class Coin extends Item{
    
    public String Discription = "You get the coinvalue in coins (default 10)";
    private AnimEffects animEffects = new AnimEffects();
    private int value;

    private long soundId = -1;
    private Sound sound;
    
    public Coin(MapCellCoordinates cellPos, MapLoader map, EntityManager entityManager, int value) {
        super(cellPos,(TextureRegion) TextureManager.coinAnim.getKeyFrame(0), map, entityManager);
        this.value = value;
        
    }
   
    @Override
    public void render()
    {
        //Render item
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(animEffects.getFrame(TextureManager.coinAnim, true)));
        map.getItemLayer().setCell(cellPos.getX(), cellPos.getY(), cell);
        
        super.deleteItemThroughBomb();
        
        if(isMainPlayerCollectingItem() == true)
        {
            itemEffect();
        }
        
        if(getPlayerIdCollectingItem() != -1)
        {
            sound = AudioManager.getSingleCoin();
            soundId = sound.play();
        }
        
        Player player = entityManager.getPlayerManager().getCurrentPlayerObject();
        if(player != null && sound != null)
        {
            player.playSoundInDistance(sound, soundId, pos, Constants.COINMOD);
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
            sendCommand.setPlayerCoins(mainP.getPlayerId(), mainP.getCoins());
        }
    }
    
    
    
}
