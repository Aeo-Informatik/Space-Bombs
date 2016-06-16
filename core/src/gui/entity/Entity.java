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
import gui.entity.bombs.Bomb;
import gui.map.MapManager;

/**
 *
 * @author qubasa
 */
public abstract class Entity
{
    //General objects & variables
    protected Vector2 pos, direction;
    protected MapManager map;
    protected TiledMapTileLayer blockLayer;
    protected TiledMapTileLayer bombLayer;
    protected EntityManager entityManager;
    protected float stateTime;
    
    //The first parameter is the image that should be drawn the second one is the position x, y
    //and the third is the movement direction and speed in which the texture moves x,y.
    public Entity(Vector2 pos, Vector2 direction, MapManager map, EntityManager entityManager){
        
        this.pos = pos;
        this.map = map;
        this.direction = direction;
        this.blockLayer = map.getBlockLayer();
        this.bombLayer = map.getBombLayer();
        this.entityManager = entityManager;
    }
    
    public void render(SpriteBatch renderObject){}
    

    /**------------------COLLISION FUNCTIONS------------------**/
    /**
     * Checks if entity collides with a blocked field on his left if so it returns true
     * @return boolean
     */
    protected boolean collidesLeft()
    {
        float marginX = 2;
        if(map.isCellBlocked(pos.x - marginX, pos.y) )
            return true;

        return false;
    }
    
    /**
     * Checks if entity collides with a blocked field on his right if so it returns true
     * @return boolean
     */
    protected boolean collidesRight()
    {
        float marginX = 2;
        if(map.isCellBlocked(pos.x + Constants.PLAYERWIDTH + marginX, pos.y) )
            return true;

        return false;
    }
    
    /**
     * Checks if entity collides with a blocked field on his top if so it returns true
     * @return boolean
     */
    protected boolean collidesTop()
    {
        float marginX = 3;
        float marginY = 3;
        
        //Checks at players half on the left and right if there is a block or bomb located
        if(map.isCellBlocked(pos.x + marginX, pos.y + Constants.PLAYERHEIGHT / 2 + marginY) 
                || map.isCellBlocked(pos.x  + Constants.PLAYERWIDTH - marginX, pos.y + Constants.PLAYERHEIGHT / 2 + marginY))
            return true;
        //else
        return false;
    }
    
    /**
     * Checks if entity collides with a blocked field on his bottom if so it returns true
     * @return boolean
     */
    protected boolean collidesBottom()
    {
        float marginX = 3;
        float marginY = 3;
        
        //Checks at players feet on the left if there is a block and on the right
        if(map.isCellBlocked(pos.x + marginX, pos.y - marginY) 
                || map.isCellBlocked(pos.x  + Constants.PLAYERWIDTH -marginX, pos.y - marginY))
            return true;
        //else
        return false;
    }

    
    /**--------------------------COLLIDES WITH BOMB--------------------------**/
    /**
     * Checks if entity collides with a bomb on his left if so it returns true
     * @return boolean
     */
    private int leftRefBombId = -1;
    protected boolean collidesLeftBomb()
    {
        float marginX = 2;
        
        //If player stands on bomb 
        if(map.isBombPlaced(pos.x + Constants.PLAYERWIDTH / 2, pos.y))
        {
            if(leftRefBombId == -1)
            {
                //Get bomb Id and save this Id for later
                Bomb bomb = entityManager.getBombObjOnPosCoordinates(pos.x + Constants.PLAYERWIDTH / 2, pos.y);
                if(bomb != null)
                {
                    leftRefBombId =  bomb.getBombId();
                    return false;
                }
            }
        }
        
        //If player walks against bomb from the left
        if(map.isBombPlaced(pos.x - marginX, pos.y))
        {
            //Compare saved bomb id with current one if they dont match lock movement
            Bomb bomb = entityManager.getBombObjOnPosCoordinates(pos.x - marginX, pos.y);
            if(bomb != null && bomb.getBombId() != leftRefBombId)
            {
                return true;
            }
        }
        
        //If the ids do match or there is no bomb unlock movement and reset reference bomb Id  
        leftRefBombId = -1;
        return false;
    }
    
    
    /**
     * Checks if entity collides with a bomb on his right if so it returns true
     * @return boolean
     */
    private int rightRefBombId = -1;
    protected boolean collidesRightBomb()
    {
        float marginX = 2;
        
        //If player stands on bomb 
        if(map.isBombPlaced(pos.x + Constants.PLAYERWIDTH / 2, pos.y))
        {
            if(rightRefBombId == -1)
            {
                //Get bomb Id and save this Id for later
                Bomb bomb = entityManager.getBombObjOnPosCoordinates(pos.x + Constants.PLAYERWIDTH / 2, pos.y);
                if(bomb != null)
                {
                    rightRefBombId =  bomb.getBombId();
                    return false;
                }
            }
        }
        
        //If player walks against bomb from the right 
        if(map.isBombPlaced(pos.x + Constants.PLAYERWIDTH + marginX, pos.y))
        {
            //Compare saved bomb id with current one if they dont match lock movement
            Bomb bomb = entityManager.getBombObjOnPosCoordinates(pos.x + Constants.PLAYERWIDTH + marginX, pos.y);
            if(bomb != null && bomb.getBombId() != rightRefBombId)
            {
                return true;
            }
        }
        
        //If the ids do match or there is no bomb unlock movement and reset reference bomb Id  
        rightRefBombId = -1;
        return false;
    }
    
    
    /**
     * Checks if entity collides with a bomb on his top if so it returns true
     * @return boolean
     */
    private int topRefBombId = -1;
    protected boolean collidesTopBomb()
    {
        float marginX = 3;
        float marginY = 3;
        
        //If player stands on bomb walk away
        if(map.isBombPlaced(pos.x + Constants.PLAYERWIDTH / 2, pos.y))
        {
            if(topRefBombId == -1)
            {
                //Get bomb Id and save this Id for later
                Bomb bomb = entityManager.getBombObjOnPosCoordinates(pos.x + Constants.PLAYERWIDTH / 2, pos.y);
                if(bomb != null)
                {
                    topRefBombId =  bomb.getBombId();
                    return false;
                }
            }
        }
        
        //Checks at players half on the left and right if there is a block located
        if(map.isBombPlaced(pos.x + marginX, pos.y + Constants.PLAYERHEIGHT / 2 + marginY) || map.isBombPlaced(pos.x  + Constants.PLAYERWIDTH - marginX, pos.y + Constants.PLAYERHEIGHT / 2 + marginY))
        {
            //Compare saved bomb id with current one if they dont match lock movement
            Bomb bomb1 = entityManager.getBombObjOnPosCoordinates(pos.x + marginX, pos.y + Constants.PLAYERHEIGHT / 2 + marginY);
            Bomb bomb2 = entityManager.getBombObjOnPosCoordinates(pos.x  + Constants.PLAYERWIDTH - marginX, pos.y + Constants.PLAYERHEIGHT / 2 + marginY);
            if((bomb1 != null && bomb1.getBombId() != topRefBombId) || (bomb2 != null && bomb2.getBombId() != topRefBombId))
            {
                return true;
            }
        }
        
        //If the ids do match or there is no bomb unlock movement and reset reference bomb Id  
        topRefBombId = -1;
        return false;

    }
    
    
    /**
     * Checks if entity collides with a bomb on his bottom if so it returns true
     * @return boolean
     */
    private int bottomRefBombId = -1;
    protected boolean collidesBottomBomb()
    {
        float marginX = 3;
        float marginY = 3;
        
        //If player stands on bomb
        if(map.isBombPlaced(pos.x + Constants.PLAYERWIDTH / 2, pos.y))
        {
            if(bottomRefBombId == -1)
            {
                //Get bomb Id and save this Id for later
                Bomb bomb = entityManager.getBombObjOnPosCoordinates(pos.x + Constants.PLAYERWIDTH / 2, pos.y);
                if(bomb != null)
                {
                    bottomRefBombId =  bomb.getBombId();
                    return false;
                }
            }
        }
        
        //Checks at players feet on the left if there is a block and on the right
        if(map.isBombPlaced(pos.x + marginX, pos.y - marginY) || map.isBombPlaced(pos.x  + Constants.PLAYERWIDTH -marginX, pos.y - marginY))
        {
            //Compare saved bomb id with current one if they dont match lock movement
            Bomb bomb1 = entityManager.getBombObjOnPosCoordinates(pos.x + marginX, pos.y - marginY);
            Bomb bomb2 = entityManager.getBombObjOnPosCoordinates(pos.x  + Constants.PLAYERWIDTH -marginX, pos.y - marginY);
            if((bomb1 != null && bomb1.getBombId() != bottomRefBombId) || (bomb2 != null && bomb2.getBombId() != bottomRefBombId))
            {
                return true;
            }
        }
        
        //If the ids do match or there is no bomb unlock movement and reset reference bomb Id  
        bottomRefBombId = -1;
        return false;
    }
    
    
    /**
     * Checks if entity is standing on a deadly field if so it returns true
     * @return boolean
     */
    protected boolean touchesDeadlyBlock()
    {
        float margin = 3f;
        
        //Checks from the walking right texture a collision on the down left, down right
        if(map.isCellDeadly(pos.x + margin, pos.y) || map.isCellDeadly(pos.x + Constants.PLAYERWIDTH - margin, pos.y))
            return true;
        
        return false;
    }
    
    
    /**
     * The texture flashs periodically on the screen
     * @param duration: Of the flashing length
     * @param timesPerSecond: How often the texture changes from visibel to invisibel in a second
     * @return Thread: Thread object to stop the blinking animation manually
     */
    protected Thread flashingAnimation(float duration, int timesPerSecond, SpriteBatch renderObject)
    {
        Thread blink = new Thread()
        {
            @Override
            public void run() 
            {
               for(float i = 0; i < duration; i++)
               {
                   for(int b = 0; b < timesPerSecond; b++)
                   {
                        renderObject.setColor(1.0f, 1.0f, 1.0f,0.0f); 
                       try 
                        {
                             Thread.sleep((1000 / timesPerSecond) / 2);
                        } catch (InterruptedException ex) 
                        {
                             System.err.println("ERROR: Interrupted blinkingAnimation()");
                        }
                        
                        renderObject.setColor(1.0f, 1.0f, 1.0f,1.0f); 
                        try 
                        {
                             Thread.sleep((1000 / timesPerSecond) / 2);
                        } catch (InterruptedException ex) 
                        {
                             System.err.println("ERROR: Interrupted blinkingAnimation()");
                        }
                   }
               }
            }
        };
          
        blink.start();
        
        return blink;
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

