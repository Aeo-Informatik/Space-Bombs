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
public class YellowHeart extends Item{
    

    /**
     * 
     * @param pos the position of the Itemonly in int
     * @param direction alwalys 0, 0
     * @param map
     * @param entityManager 
     */
    public YellowHeart(int CellX, int CellY, Vector2 direction, MapManager map, EntityManager entityManager) {
        super(CellX, CellY, direction,TextureManager.yellowHeart, map, entityManager);
    }  
   
    @Override
    public void itemEffect()
    {
        if(entityManager.getMainPlayer() != null)
        {            
            for(int i=0; i < 3; i++)
            {
                if(entityManager.getMainPlayer().getLife()< Constants.MAXLIFE)
                {
                    entityManager.getMainPlayer().setLife((entityManager.getMainPlayer().getLife() + 1));
                }
            }
        }
    }
    
    @Override
    public boolean canGetCollectedByMainPLayer()
    {
        if(entityManager.getMainPlayer().getLife()< Constants.MAXLIFE)
        {
            return true;
        }else
        {
            return false;
        }
    }
}
