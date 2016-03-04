/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import gui.TextureManager;

/**
 *
 * @author qubasa
 */
public class EntityManager {
    
    private final Array<Entity> entities = new Array<Entity>();
    private final MainPlayer mainPlayer;
    
    
    public EntityManager()
    {
        //Spawn mainPlayer at x=430 and y=100, the second argument is the direction in which the player
        //should move on creation x=0 y=0 means no movement
        mainPlayer = new MainPlayer(new Vector2(430,100), new Vector2(0,0), 
                TextureManager.p1WalkingUpAnim, TextureManager.p1WalkingDownAnim, 
                TextureManager.p1WalkingRightAnim, TextureManager.p1WalkingLeftAnim);
    }
    
    
    //Update is the same as render only that it doesn't have the SpriteBatch Object
    public void update()
    {
        //For every entity Object that is stored in the arraylist execute the update function in it
        for(Entity e: entities)
        {
            e.update();
        }
        
        //Executes the update function in the mainPlayer object
        mainPlayer.update();
    }
    
    
    public void render(SpriteBatch sb)
    {
        //For every entity Object that is stored in the arraylist execute the render function in it
        for(Entity e: entities)
        {
            e.render(sb);
        }
        
        //Executes the render function in the mainPlayer object
        mainPlayer.render(sb);
    }
    
    //Add an entity object to the arraylist
    public void addEntity(Entity entity)
    {
        entities.add(entity);
    }
    
}
