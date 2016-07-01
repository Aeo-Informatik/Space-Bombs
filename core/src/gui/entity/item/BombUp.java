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
public class BombUp extends Item{
    
    //Constructor
    public BombUp(int cellX, int cellY, Vector2 direction, MapManager map, EntityManager entityManager) 
    {
        super(cellX, cellY, direction,TextureManager.bombUp, map, entityManager);
    }
   
    @Override
    public void render(SpriteBatch renderObject)
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
        if(entityManager.getMainPlayer() != null)
        {
            entityManager.getMainPlayer().setMaxBombPlacing((entityManager.getMainPlayer().getMaxBombPlacing() + 1));
        }
    }
    
}
