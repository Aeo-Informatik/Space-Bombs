/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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
public class RangeUp extends Item{
    

    //Constructor
    public RangeUp(int cellX, int cellY, MapManager map, EntityManager entityManager) {
        super(cellX, cellY,TextureManager.rangeUp, map, entityManager);
    }
   
    @Override
    public void render()
    {       
        if(entityManager.getMainPlayer() != null)
        {
            if(entityManager.getMainPlayer().getBombRange() < Constants.MAXBOMBRANGE)
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
         MainPlayer mainP = entityManager.getMainPlayer();
        
        //Check if main player is alive
        if(mainP != null)
        {
            mainP.setBombRange((mainP.getBombRange() + 1)); 
            client.sendData("enemyPlayerSetRange|" + mainP.getPlayerId() + "|" + (mainP.getBombRange()) + "|*");
        }
    }

    
}
