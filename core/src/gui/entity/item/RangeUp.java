/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.gdx.bomberman.Constants;
import gui.TextureManager;
import gui.entity.EntityManager;
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
        } 
    }
    
    @Override
    public void itemEffect()
    {
        if(entityManager.getMainPlayer() != null)
        {
            entityManager.getMainPlayer().setBombRange((entityManager.getMainPlayer().getBombRange() + 1));   
        }
    }

    
}
