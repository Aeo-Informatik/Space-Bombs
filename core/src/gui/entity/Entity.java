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
import static com.gdx.bomberman.Main.sb;
import gui.Constants;
import gui.entity.bombs.Bomb;
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
    protected EntityManager entityManager;
    
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
    
    public abstract void update();
    
    
    public void render(SpriteBatch sb){}
    

    /**------------------COLLISION FUNCTIONS------------------**/
    protected boolean collidesLeft()
    {
        float marginX = 2;
        if(map.isCellBlocked(pos.x - marginX, pos.y) )
            return true;

        return false;
    }
    
    protected boolean collidesRight()
    {
        float marginX = 2;
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
            return true;
        //else
        return false;
    }

    private int previousBombId = -1;
    /**--------------------------COLLIDES WITH BOMB--------------------------**/
    protected boolean collidesLeftBomb()
    {
        float marginX = 2;
        
        //If player stands on bomb 
        if(map.isBombPlaced(pos.x + Constants.PLAYERWIDTH / 2, pos.y))
        {
            //If player didn't stand on a bomb before
            if(this.previousBombId == -1)
            {
                //Get Bomb Object and Id
                Bomb bomb = entityManager.getBombObjOnCellCoordinates((int) (pos.x / Constants.MAPTEXTUREWIDTH), (int) (pos.y / Constants.MAPTEXTUREHEIGHT));
                if(bomb != null)
                {
                    System.out.println("Standing on Bomb Id: " + bomb.getBombId());
                }else
                {
                    System.out.println("GetBombObj returned null!");
                }
            }
            
            return false;
        }
        
        //If player hits bomb from the left stop walking
        if(map.isBombPlaced(pos.x - marginX, pos.y) )
        {
            return true;
        }
        
        return false;
    }
    
    protected boolean collidesRightBomb()
    {
        float marginX = 2;
        
        //If player stands on bomb walk away
        if(map.isBombPlaced(pos.x + Constants.PLAYERWIDTH / 2, pos.y))
        {
            return false;
        }
        
        //If player hits bomb from the right stop walking
        if(map.isBombPlaced(pos.x + Constants.PLAYERWIDTH + marginX, pos.y))
        {
            return true;
        }
        
        return false;
    }
    
    protected boolean collidesTopBomb()
    {
        float marginX = 3;
        float marginY = 3;
        
        //If player stands on bomb walk away
        if(map.isBombPlaced(pos.x + Constants.PLAYERWIDTH / 2, pos.y))
        {
            return false;
        }
        
        //Checks at players half on the left and right if there is a block located
        if(map.isBombPlaced(pos.x + marginX, pos.y + Constants.PLAYERHEIGHT / 2 + marginY) 
                || map.isBombPlaced(pos.x  + Constants.PLAYERWIDTH - marginX, pos.y + Constants.PLAYERHEIGHT / 2 + marginY))
            return true;
        
        return false;
    }
    
    protected boolean collidesBottomBomb()
    {
        float marginX = 3;
        float marginY = 3;
        
        //If player stands on bomb walk away
        if(map.isBombPlaced(pos.x + Constants.PLAYERWIDTH / 2, pos.y))
        {
            return false;
        }
        
        //Checks at players feet on the left if there is a block and on the right
        if(map.isBombPlaced(pos.x + marginX, pos.y - marginY) 
                || map.isBombPlaced(pos.x  + Constants.PLAYERWIDTH -marginX, pos.y - marginY))
            return true;
        //else
        return false;
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
     * The texture blinks periodically on the screen
     * @param duration: Of the blinking length
     * @param timesPerSecond: How often the texture changes from visibel to invisibel
     */
    protected Thread blinkingAnimation(float duration, int timesPerSecond)
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
                        //sb.setColor(1.0f, 1.0f, 1.0f,0.0f); 
                       try 
                        {
                             Thread.sleep((1000 / timesPerSecond) / 2);
                        } catch (InterruptedException ex) 
                        {
                             System.err.println("ERROR: Interrupted blinkingAnimation()");
                        }
                        
                        sb.setColor(1.0f, 1.0f, 1.0f,1.0f); 
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

