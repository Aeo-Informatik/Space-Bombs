/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import gui.entity.bombs.Bomb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import gui.Constants;
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
        float marginX = 2;
        //|| map.isBombPlaced(pos.x - marginX, pos.y)
        if(map.isCellBlocked(pos.x - marginX, pos.y) )
            return true;

        return false;
    }
    
    protected boolean collidesRight()
    {
        float marginX = 2;
        //|| map.isBombPlaced(pos.x + Constants.PLAYERWIDTH + marginX, pos.y)
        if(map.isCellBlocked(pos.x + Constants.PLAYERWIDTH + marginX, pos.y) )
            return true;

        return false;
    }
    
    protected boolean collidesTop()
    {
        float marginX = 3;
        float marginY = 3;
        
        //Checks at players half on the left and right if there is a block or bomb located
        if(map.isCellBlocked(pos.x + marginX, pos.y + Constants.PLAYERHEIGHT / 2 + marginY) 
                || map.isCellBlocked(pos.x  + Constants.PLAYERWIDTH - marginX, pos.y + Constants.PLAYERHEIGHT / 2 + marginY))
//                || map.isBombPlaced(pos.x + marginX, pos.y + Constants.PLAYERHEIGHT / 2 + marginY)
//                || map.isBombPlaced(pos.x  + Constants.PLAYERWIDTH - marginX, pos.y + Constants.PLAYERHEIGHT / 2 + marginY))
            return true;
        //else
        return false;
    }
    
    protected boolean collidesBottom()
    {
        float marginX = 3;
        float marginY = 3;
        
        //Checks at players feet on the left if there is a block and on the right
        if(map.isCellBlocked(pos.x + marginX, pos.y - marginY) 
                || map.isCellBlocked(pos.x  + Constants.PLAYERWIDTH -marginX, pos.y - marginY))
//                || map.isBombPlaced(pos.x + marginX, pos.y - marginY)
//                || map.isBombPlaced(pos.x  + Constants.PLAYERWIDTH -marginX, pos.y - marginY))
            return true;
        //else
        return false;
    }

    protected boolean collidesLeftBomb(Array<Bomb> bombArray)
    {
        float marginX = 2;
        if(map.isBombPlaced(pos.x , pos.y) == true)
        {
           return false; 
        }else
        { 
            if( !map.isBombPlaced(pos.x - marginX, pos.y))
            {
                return false;
            }else
            {
               for (Bomb bomb: bombArray)
                {
                    if(bomb.getCellX() == (int)((pos.x - marginX)/Constants.MAPTEXTUREWIDTH) && bomb.getCellY() == (int) (pos.y / Constants.MAPTEXTUREHEIGHT))
                    {
                     return true;   
                    }
                } 
               return false;
            }
        }
    }
    
    protected boolean collidesRightBomb(Array<Bomb> bombArray)
    {
        float marginX = 2;
        if(map.isBombPlaced(pos.x , pos.y) == true)
        {
           return false; 
        }else
        { 
            if( !map.isBombPlaced(pos.x + marginX, pos.y))
            {
                return false;
            }else
            {
               for (Bomb bomb: bombArray)
                {
                    if(bomb.getCellX() == (int)((pos.x + marginX)/Constants.MAPTEXTUREWIDTH) && bomb.getCellY() == (int) (pos.y / Constants.MAPTEXTUREHEIGHT))
                    {
                     return true;   
                    }
                } 
               return false;
            }
        }
    }
    
    protected boolean collidesTopBomb(Array<Bomb> bombArray)
    {
        float marginX = 3;
        float marginY = 3;
        if(map.isBombPlaced(pos.x , pos.y) == true)
        {
           return false; 
        }else
        { 
            if( !map.isBombPlaced(pos.x + marginX, pos.y + Constants.PLAYERHEIGHT / 2 + marginY))
            {
                return false;
            }else
            {
               for (Bomb bomb: bombArray)
                {
                    if(bomb.getCellX() == (int)((pos.x + marginX)/Constants.MAPTEXTUREWIDTH) && bomb.getCellY() == (int) ((pos.y + Constants.PLAYERHEIGHT / 2 + marginY) / Constants.MAPTEXTUREHEIGHT))
                    {
                     return true;   
                    }
                } 
               return false;
            }
        }
    }
    
    protected boolean collidesBottomBomb(Array<Bomb> bombArray)
    {
        float marginX = 3;
        float marginY = 3;
        if(map.isBombPlaced(pos.x , pos.y) == true)
        {
           return false; 
        }else
        { 
            if( !map.isBombPlaced(pos.x  + Constants.PLAYERWIDTH -marginX, pos.y - marginY))
            {
                return false;
            }else
            {
               for (Bomb bomb: bombArray)
                {
                    if(bomb.getCellX() == (int)((pos.x - marginX)/Constants.MAPTEXTUREWIDTH) && bomb.getCellY() == (int) ((pos.y - marginY) / Constants.MAPTEXTUREHEIGHT))
                    {
                     return true;   
                    }
                } 
               return false;
            }
        }
    }
    
    
    
    protected boolean touchesDeadlyBlock()
    {
        float margin = 3f;
        
        //Checks from the walking right texture a collision on the down left, down right
        if(map.isCellDeadly(pos.x + margin, pos.y) || map.isCellDeadly(pos.x + Constants.PLAYERWIDTH - margin, pos.y))
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
    
    public Vector2 getDirection()
    {
        return direction;
    }
    
    public void setPosition(Vector2 pos)
    {
        this.pos = pos;
    }
}

