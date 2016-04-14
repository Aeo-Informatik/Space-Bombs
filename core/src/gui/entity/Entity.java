/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author qubasa
 */
public abstract class Entity
{
    
    protected TextureRegion textureRegion;
    protected Vector2 pos, direction;
    
    //The first parameter is the image that should be drawn the second one is the position x, y
    //and the third is the movement direction and speed in which the texture moves x,y.
    public Entity(TextureRegion textureRegion, Vector2 pos, Vector2 direction){
        this.pos = pos;
        this.direction = direction;
        this.textureRegion = textureRegion;
    }
    
    //Update is the same as render only that it doesn't have the SpriteBatch Object
    public abstract void update();
    
    
    public void render(SpriteBatch sb)
    {
        //texture = the texture to be drawn
        //pos.x/pos.y = the coordinates where to draw the texture in the screen
        sb.draw(textureRegion, pos.x, pos.y);
    }
    
    
    //Get current position on screen
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
