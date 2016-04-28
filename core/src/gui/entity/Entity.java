/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import gui.Constants;
import gui.TextureManager;
import gui.map.MapManager;

/**
 *
 * @author qubasa
 */
public abstract class Entity
{
    
    protected Vector2 pos, direction;
    protected MapManager map;
    protected TiledMapTileLayer blockLayer;
    protected TiledMapTileLayer bombLayer;
    protected float stateTime;
    
    //The first parameter is the image that should be drawn the second one is the position x, y
    //and the third is the movement direction and speed in which the texture moves x,y.
    public Entity(Vector2 pos, Vector2 direction, MapManager map){
        
        this.pos = pos;
        this.map = map;
        this.direction = direction;
        this.blockLayer = map.getBlockLayer();
        this.bombLayer = map.getBombLayer();
    }
    
    public abstract void update();
    
    
    public void render(SpriteBatch sb){}
    

    /**------------------COLLISION FUNCTIONS------------------**/
    protected boolean collidesLeft()
    {
        if(map.isCellBlocked(pos.x - 2, pos.y))
            return true;

        return false;
    }
    
    protected boolean collidesRight()
    {
        if(map.isCellBlocked(pos.x + TextureManager.playerWidth + 2, pos.y))
            return true;

        return false;
    }
    
    protected boolean collidesTop()
    {
        if(map.isCellBlocked(pos.x + 3, pos.y + TextureManager.playerHeight / 2 + 3) || map.isCellBlocked(pos.x  + TextureManager.playerWidth - 3, pos.y + TextureManager.playerHeight / 2 + 3))
            return true;

        return false;
    }
    
    protected boolean collidesBottom()
    {
        //Checks at the players feet on the left if there is a block and on the right
        if(map.isCellBlocked(pos.x + 3, pos.y - 3) || map.isCellBlocked(pos.x  + TextureManager.playerWidth -3, pos.y - 3))
            return true;

        return false;
    }

    protected boolean touchesDeadlyBlock()
    {
        float margin = 3f;
        
        //Checks from the walking right texture a collision on the down left, down right
        if(map.isCellDeadly(pos.x + margin, pos.y) || map.isCellDeadly(pos.x + TextureManager.playerWidth - margin, pos.y))
            return true;
        
        return false;
    }
    
    /**
    * Gets the frame out of the animation
    * @param animation
    * @return TextureRegion
    */
    protected TextureRegion getFrame(Animation animation)
    { 
        /* Adds the time elapsed since the last render to the stateTime.*/
        this.stateTime += Constants.DELTATIME; 

        /*
        Obtains the current frame. This is given by the animation for the current time. 
        The second variable is the looping. 
        Passing in true, will tell the animation to restart after it reaches the last frame.
        */
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        
        return currentFrame;
    }

    
    /**------------------Getter & Setter------------------**/
    public Vector2 getPosition()
    {
        return pos;
    }
    
    //Sets the direction in which the entity should move
    public void setDirection(float x, float y)
    {
        direction.set(x, y);
        
        //Makes the frames per second constant on every device (so it doesnt run faster on better devices)
        direction.scl(Gdx.graphics.getDeltaTime());
    }
}

