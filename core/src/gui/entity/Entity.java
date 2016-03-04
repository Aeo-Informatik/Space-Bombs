/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author qubasa
 */
public abstract class Entity {
    
    protected TextureRegion textureRegion;
    protected Vector2 pos, direction;
    
    public Entity(TextureRegion textureRegion, Vector2 pos, Vector2 direction, int cutX, int cutY){
        this.pos = pos;
        this.direction = direction;
        this.textureRegion = textureRegion;
    }
    
    public abstract void update();
    
    
    public void render(SpriteBatch sb)
    {
        //texture = the texture to be drawn
        //pos.x/pos.y = the coordinates where to draw the texture in the screen
        //cutX/cutY = to cut out one portion of the texture
        sb.draw(textureRegion, pos.x, pos.y);
    }
    
    
    public Vector2 getPosition()
    {
        return pos;
    }
    
    public void setDirection(float x, float y)
    {
        direction.set(x, y);
        
        //Makes the frames per second constant on every device (so it doesnt run faster on better devices)
        direction.scl(Gdx.graphics.getDeltaTime());
    }
    
}
