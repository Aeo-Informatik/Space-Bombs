/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gdx.bomberman.Constants;
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
    protected SpriteBatch renderObject;
    protected OrthographicCamera camera;
    
    protected float entitySpeed = Constants.DEFAULTENTITYSPEED;
    protected float cameraSpeed = Constants.DEFAULTCAMERASPEED;
    
    //The first parameter is the image that should be drawn the second one is the position x, y
    //and the third is the movement direction and speed in which the texture moves x,y.
    public Entity(Vector2 pos, Vector2 direction, MapManager map, EntityManager entityManager, OrthographicCamera camera){
        
        this.pos = pos;
        this.map = map;
        this.camera = camera;
        this.direction = direction;
        this.blockLayer = map.getBlockLayer();
        this.bombLayer = map.getBombLayer();
        this.entityManager = entityManager;
        this.renderObject = new SpriteBatch();
    }
    
    public void render(){}
    

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
                Bomb bomb = entityManager.getBombManager().getBombObjectOnCoordinates(pos.x + Constants.PLAYERWIDTH / 2, pos.y);
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
            Bomb bomb = entityManager.getBombManager().getBombObjectOnCoordinates(pos.x - marginX, pos.y);
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
                Bomb bomb = entityManager.getBombManager().getBombObjectOnCoordinates(pos.x + Constants.PLAYERWIDTH / 2, pos.y);
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
            Bomb bomb = entityManager.getBombManager().getBombObjectOnCoordinates(pos.x + Constants.PLAYERWIDTH + marginX, pos.y);
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
                Bomb bomb = entityManager.getBombManager().getBombObjectOnCoordinates(pos.x + Constants.PLAYERWIDTH / 2, pos.y);
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
            Bomb bomb1 = entityManager.getBombManager().getBombObjectOnCoordinates(pos.x + marginX, pos.y + Constants.PLAYERHEIGHT / 2 + marginY);
            Bomb bomb2 = entityManager.getBombManager().getBombObjectOnCoordinates(pos.x  + Constants.PLAYERWIDTH - marginX, pos.y + Constants.PLAYERHEIGHT / 2 + marginY);
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
                Bomb bomb = entityManager.getBombManager().getBombObjectOnCoordinates(pos.x + Constants.PLAYERWIDTH / 2, pos.y);
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
            Bomb bomb1 = entityManager.getBombManager().getBombObjectOnCoordinates(pos.x + marginX, pos.y - marginY);
            Bomb bomb2 = entityManager.getBombManager().getBombObjectOnCoordinates(pos.x  + Constants.PLAYERWIDTH -marginX, pos.y - marginY);
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
     * Follows the entity with smooth camera movements.
     */
    protected void cameraFollowEntity()
    {
        Vector3 cameraPos = camera.position;
        cameraPos.x += (pos.x - cameraPos.x) * cameraSpeed * Constants.DELTATIME;
        cameraPos.y += (pos.y - cameraPos.y) * cameraSpeed * Constants.DELTATIME;
    }
    
    
    /**
     * Sets the entity movement direction to left controlled from entitySpeed.
     */
    protected void goLeft()
    {
        direction.set(-100 * entitySpeed * Constants.DELTATIME, 0);
    }
    
    
    /**
    * Sets the entity movement direction to right controlled from entitySpeed.
    */
    protected void goRight()
    {
        direction.set(100 * entitySpeed * Constants.DELTATIME, 0);
    }
    
    
    /**
    * Sets the entity movement direction to up controlled from entitySpeed.
    */
    protected void goUp()
    {
        direction.set(0, 100 * entitySpeed * Constants.DELTATIME);
    }
    
    
    /**
    * Sets the entity movement direction to down controlled from entitySpeed.
    */
    protected void goDown()
    {
        direction.set(0, -100 * entitySpeed * Constants.DELTATIME);
    }
    
    
    /**
    * Sets the entity movement direction to 0.
    */
    protected void stopMoving()
    {
        direction.set(0, 0);
    }
    
    /**------------------Getter & Setter------------------**/
    public Vector2 getPosition()
    {
        return pos;
    }
    
    public void setPosition(Vector2 pos)
    {
        this.pos = pos;
    }
    
    public void setEntitySpeed(float entitySpeed)
    {
        this.entitySpeed = entitySpeed;
    }
    
    public float getEntitySpeed()
    {
        return this.entitySpeed;
    }
    
    public void setCameraFollowSpeed(float cameraSpeed)
    {
        this.cameraSpeed = cameraSpeed;
    }
    
    public float getCameraFollowSpeed()
    {
        return this.cameraSpeed;
    }
}

