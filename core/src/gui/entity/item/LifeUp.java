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
public class LifeUp extends Item{
    
    
    //Constructor
    public LifeUp(int cellX, int cellY, Vector2 direction, MapManager map, EntityManager entityManager) 
    {
        super(cellX, cellY, direction,TextureManager.lifeUp, map, entityManager);
    }
    
    @Override
    public void itemEffect()
    {
        //Check if main player is alive
        if(entityManager.getMainPlayer() != null)
        {
            entityManager.getMainPlayer().setLife((entityManager.getMainPlayer().getLife() + 1));
        }
    }

    @Override
    public boolean canGetCollectedByMainPLayer()
    {
        if(entityManager.getMainPlayer().getLife()< Constants.MAXLIFE)
        {
            return true;
        }
        return false;
    }
}
