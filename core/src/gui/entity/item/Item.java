/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.entity.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import gui.entity.Entity;
import gui.entity.EntityManager;
import gui.map.MapManager;
/**
 *
 * @author cb0703
 */
public class Item extends Entity{

    private boolean collected = false;
    
    public Item(Vector2 pos, Vector2 direction, MapManager map, EntityManager entityManager) {
        super(pos, direction, map, entityManager);
    }
    
    public void render(SpriteBatch renderObject)
    {
        
    }
    
    public int check(int X, int Y)
    {
        if(entityManager.getPlayerIDOnPosCoordinates(X, Y) != -1)
        {
            System.out.println("yeah!"); 
            collected = true;
            return entityManager.getPlayerIDOnPosCoordinates(X, Y);
        }
        return -1;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }
    
    
    
}
