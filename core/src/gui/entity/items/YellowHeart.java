/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.items;

import com.gdx.bomberman.Constants;
import static com.gdx.bomberman.Main.client;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.entity.players.MainPlayer;
import gui.map.MapManager;


/**
 *
 * @author Christian
 */
public class YellowHeart extends Item{
    

    /**
     * 
     * @param pos the position of the Itemonly in int
     * @param direction alwalys 0, 0
     * @param map
     * @param entityManager 
     */
    public YellowHeart(int CellX, int CellY, MapManager map, EntityManager entityManager) {
        super(CellX, CellY,TextureManager.yellowHeart, map, entityManager);
    }
   
    @Override
    public void render()
    {
        if(entityManager.getPlayerManager().getMainPlayer() != null)
        {
            if(entityManager.getPlayerManager().getMainPlayer().getLife()< Constants.MAXLIFE)
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
    }
    
    
    @Override
    public void itemEffect()
    {
        MainPlayer mainP = entityManager.getPlayerManager().getMainPlayer();
        
        for(int i=0; i < 3; i++)
        {
            //Check if main player is alive
            if(mainP != null)
            {
                mainP.setLife((mainP.getLife() + 1));
            }
        }
        
        if(mainP != null)
        {
            client.sendData("enemyPlayerLife|" + mainP.getPlayerId() + "|" + (mainP.getLife()) + "|*");        
        }
    }
}
