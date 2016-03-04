/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author qubasa
 */
public class EntityManager {
    
    private final Array<Entity> entities = new Array<Entity>();
    private final MainPlayer mainPlayer;
    
    public EntityManager()
    {
        //Spawn player at x=430 and y=100, the second argument is the direction
        mainPlayer = new MainPlayer(new Vector2(430,100), new Vector2(0,0));
    }
    
    public void update()
    {
        for(Entity e: entities)
        {
            e.update();
        }
        
        mainPlayer.update();
    }
    
    
    public void render(SpriteBatch sb)
    {
        for(Entity e: entities)
        {
            e.render(sb);
        }
        
        mainPlayer.render(sb);
    }
    
    
    public void addEntity(Entity entity)
    {
        entities.add(entity);
    }
    
    
    
}
