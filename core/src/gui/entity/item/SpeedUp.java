/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.entity.item;


import com.badlogic.gdx.math.Vector2;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.map.MapManager;
import com.gdx.bomberman.Constants;

/**
 *
 * @author cb0703
 */
public class SpeedUp extends Item{

    public SpeedUp(int CellX, int CellY, MapManager map, EntityManager entityManager) {
        super(CellX, CellY,TextureManager.speedUp, map, entityManager);
    }
   
    @Override
    public void render()
    {            
        if(entityManager.getMainPlayer() != null)//check if MAinplayer stil exsitst
        {
            if(entityManager.getMainPlayer().getEntitySpeed() < Constants.MAXSPEED)//check if item is usable for the mainplayer
            {
                if(isMainPlayerCollectingItem() == true)//check if item is collected by the mainplayer
                {
                    itemEffect();
                }
            }
        }
    }
    
    /**
     * do wahat the item does
     */
    @Override
    public void itemEffect()
    {
        if(entityManager.getMainPlayer() != null)//check if MAinplayer stil exsitst
        {
            entityManager.getMainPlayer().setEntitySpeed((entityManager.getMainPlayer().getEntitySpeed()+ 0.1f));
        }
    }
}
