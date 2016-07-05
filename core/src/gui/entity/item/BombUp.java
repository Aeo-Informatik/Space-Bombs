/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.item;

import com.gdx.bomberman.Constants;
import static com.gdx.bomberman.Main.client;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.entity.MainPlayer;
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
        if(entityManager.getMainPlayer() != null)
        {
            //Check if mainPlayer has reached item max 
            if(entityManager.getMainPlayer().getMaxBombPlacing() < Constants.MAXBOMBS)
            {
                if(isMainPlayerCollectingItem() == true)
                {
                    itemEffect();
                }
            }
        }
    }
    
    @Override
    public void itemEffect()
    {
        
         MainPlayer mainP = entityManager.getMainPlayer();
        
        //Check if main player is alive
        if(mainP != null)
        {
            mainP.setBombRange((mainP.getMaxBombPlacing() + 1)); 
            client.sendData("enemyPlayerSetMaxBombs|" + mainP.getPlayerId() + "|" + (mainP.getMaxBombPlacing()) + "|*");
        }
    }
    
}
