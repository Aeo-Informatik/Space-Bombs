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
public class BombUp extends Item{
    
    //Constructor
    public BombUp(int cellX, int cellY, MapManager map, EntityManager entityManager) 
    {
        super(cellX, cellY,TextureManager.bombUp, map, entityManager);
    }
   
    @Override
    public void render()
    {   
        if(entityManager.getPlayerManager().getMainPlayer() != null)
        {
            //Check if mainPlayer has reached item max 
            if(entityManager.getPlayerManager().getMainPlayer().getMaxBombPlacing() < Constants.MAXBOMBS)
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
        
        //Check if main player is alive
        if(mainP != null)
        {
            mainP.setMaxBombPlacing((mainP.getMaxBombPlacing() + 1)); 
            client.sendData("enemyPlayerSetMaxBombs|" + mainP.getPlayerId() + "|" + (mainP.getMaxBombPlacing()) + "|*");
        }
    }
    
}
