/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.gdx.bomberman.Constants;
import gui.entity.bomb.Bomb;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;

/**
 *
 * @author qubasa
 */
public abstract class Entity
{
    //General objects & variables
    protected ThinGridCoordinates pos, direction;
    protected MapLoader map;
    protected TiledMapTileLayer blockLayer;
    protected TiledMapTileLayer bombLayer;
    protected EntityManager entityManager;
    protected SpriteBatch renderObject;
    
    
    protected float entitySpeed = Constants.DEFAULTENTITYSPEED;
    
    
    //The first parameter is the image that should be drawn the second one is the position x, y
    //and the third is the movement direction and speed in which the texture moves x,y.
    public Entity(ThinGridCoordinates pos, ThinGridCoordinates direction, MapLoader map, EntityManager entityManager){
        
        this.pos = pos;
        this.map = map;
        this.direction = direction;
        this.blockLayer = map.getBlockLayer();
        this.bombLayer = map.getBombLayer();
        this.entityManager = entityManager;
        this.renderObject = new SpriteBatch();
    }
    
    public abstract void render();
    

    /**------------------COLLISION FUNCTIONS------------------**/
    /**
     * Checks if entity collides with a blocked field on his left if so it returns true
     * @return boolean
     */
    protected boolean collidesLeft()
    {
        float marginX = 2;
        if(map.isCellBlocked(new MapCellCoordinates(pos.getX() - marginX, pos.getY())))
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
        if(map.isCellBlocked(new MapCellCoordinates(pos.getX() + Constants.PLAYERWIDTH + marginX,pos.getY())))
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
        if(map.isCellBlocked(new MapCellCoordinates(pos.getX() + marginX,pos.getY() + Constants.PLAYERHEIGHT / 2 + marginY)) 
                || map.isCellBlocked(new MapCellCoordinates(pos.getX()  + Constants.PLAYERWIDTH - marginX,pos.getY() + Constants.PLAYERHEIGHT / 2 + marginY)))
            return true;
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
        if(map.isCellBlocked(new MapCellCoordinates(pos.getX() + marginX,pos.getY() - marginY)) 
                || map.isCellBlocked(new MapCellCoordinates(pos.getX()  + Constants.PLAYERWIDTH -marginX,pos.getY() - marginY)))
            return true;
        //else
        return false;
    }

    
    /**--------------------------COLLIDES WITH BOMB--------------------------**/
    private boolean abilityToWalkOverOwnBombs = false;
    private boolean stuckBetweenBombsBug(String direction)
    {
        float marginX,marginY = 0;
        
        // If player stands on a bomb
        if(map.isBombPlaced(new MapCellCoordinates(pos.getX() + Constants.PLAYERWIDTH / 2,pos.getY())))
        {
            // If that bomb doesn't have the same playerId aka. isn't the players own bomb
            if(entityManager.getBombManager().getBombObjectOnCoordinates(pos.getX() + Constants.PLAYERWIDTH / 2,pos.getY()).getPlayerId() != Constants.PLAYERID)
            {
                // As long as the player stands on a bomb he will be granted with this feature
                abilityToWalkOverOwnBombs = true;
            }
        }else
        {
            abilityToWalkOverOwnBombs = false;
        }
        
        if(abilityToWalkOverOwnBombs == true)
        {
            switch(direction)
            {
                case "LEFT":
                    marginX = 2;
                    // If that bomb does have the same playerId aka. is the players own bomb
                    if(entityManager.getBombManager().getBombObjectOnCoordinates(pos.getX() - marginX,pos.getY()).getPlayerId() == Constants.PLAYERID)
                    {
                        // Let the player walk over his own bombs even if he walked away from them. 
                        // This prevents the player to be stuck between an enemy bomb and his own.
                        return true;
                    }
                    break;
                case "RIGHT":
                    marginX = 2;
                    // If that bomb does have the same playerId aka. is the players own bomb
                    if(entityManager.getBombManager().getBombObjectOnCoordinates(pos.getX() + Constants.PLAYERWIDTH + marginX,pos.getY()).getPlayerId() == Constants.PLAYERID)
                    {
                        // Let the player walk over his own bombs even if he walked away from them. 
                        // This prevents the player to be stuck between an enemy bomb and his own.
                        return true;
                    }
                    break;
                case "TOP":
                    marginX = 3;
                    marginY = 3;
                    // If that bomb does have the same playerId aka. is the players own bomb
                    if(map.isBombPlaced(new MapCellCoordinates(pos.getX() + marginX,pos.getY() + Constants.PLAYERHEIGHT / 2 + marginY)) || map.isBombPlaced(new MapCellCoordinates(pos.getX()  + Constants.PLAYERWIDTH - marginX,pos.getY() + Constants.PLAYERHEIGHT / 2 + marginY)))
                    {
                        // Let the player walk over his own bombs even if he walked away from them. 
                        // This prevents the player to be stuck between an enemy bomb and his own.
                        return true;
                    }
                    break;
                case "BOTTOM":
                    marginX = 3;
                    marginY = 3;
                    // If that bomb does have the same playerId aka. is the players own bomb
                    if(map.isBombPlaced(new MapCellCoordinates(pos.getX() + marginX,pos.getY() - marginY)) || map.isBombPlaced(new MapCellCoordinates(pos.getX()  + Constants.PLAYERWIDTH -marginX,pos.getY() - marginY)))
                    {
                        // Let the player walk over his own bombs even if he walked away from them. 
                        // This prevents the player to be stuck between an enemy bomb and his own.
                        return true;
                    }
                    
                    break;
            }
        }
        return false;
    }
    
    
    /**
     * Checks if entity collides with a bomb on his left if so it returns true
     * @return boolean
     */
    private int leftRefBombId = -1;
    protected boolean collidesLeftBomb()
    {
        float marginX = 2;
        
        //If player stands on bomb 
        if(map.isBombPlaced(new MapCellCoordinates(pos.getX() + Constants.PLAYERWIDTH / 2,pos.getY())))
        {
            if(leftRefBombId == -1)
            {
                //Get bomb Id and save this Id for later
                Bomb bomb = entityManager.getBombManager().getBombObjectOnCoordinates(pos.getX() + Constants.PLAYERWIDTH / 2,pos.getY());
                if(bomb != null)
                {
                    // Save the current bomb id
                    leftRefBombId =  bomb.getBombId();
                    
                    // Let the player walk normally
                    return false;
                }
            }
        }
        
        //If player walks against bomb from the left
        if(map.isBombPlaced(new MapCellCoordinates(pos.getX() - marginX,pos.getY())))
        {
            //Compare saved bomb id with current one if they dont match lock movement
            Bomb bomb = entityManager.getBombManager().getBombObjectOnCoordinates(pos.getX() - marginX,pos.getY());
            
            // Check if it is the same bomb on which you previously standed on
            if(bomb != null && bomb.getBombId() != leftRefBombId)
            {
                /* 
                Now check if it is an enemy bomb you are colliding with.
                If so give the player the ability to walk over his own bombs
                even if he hasn't the apropriate id for it.
                */
                if(stuckBetweenBombsBug("LEFT"))
                {
                    return false;
                }
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
        if(map.isBombPlaced(new MapCellCoordinates(pos.getX() + Constants.PLAYERWIDTH / 2,pos.getY())))
        {
            if(rightRefBombId == -1)
            {
                //Get bomb Id and save this Id for later
                Bomb bomb = entityManager.getBombManager().getBombObjectOnCoordinates(pos.getX() + Constants.PLAYERWIDTH / 2,pos.getY());
                if(bomb != null)
                {
                    rightRefBombId =  bomb.getBombId();
                    return false;
                }
            }
        }
        
        //If player walks against bomb from the right 
        if(map.isBombPlaced(new MapCellCoordinates(pos.getX() + Constants.PLAYERWIDTH + marginX,pos.getY())))
        {
            //Compare saved bomb id with current one if they dont match lock movement
            Bomb bomb = entityManager.getBombManager().getBombObjectOnCoordinates(pos.getX() + Constants.PLAYERWIDTH + marginX,pos.getY());
            if(bomb != null && bomb.getBombId() != rightRefBombId)
            {
                /* 
                Now check if it is an enemy bomb you are colliding with.
                If so give the player the ability to walk over his own bombs
                even if he hasn't the apropriate id for it.
                */
                if(stuckBetweenBombsBug("RIGHT"))
                {
                    return false;
                }
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
        if(map.isBombPlaced(new MapCellCoordinates(pos.getX() + Constants.PLAYERWIDTH / 2,pos.getY())))
        {
            if(topRefBombId == -1)
            {
                //Get bomb Id and save this Id for later
                Bomb bomb = entityManager.getBombManager().getBombObjectOnCoordinates(pos.getX() + Constants.PLAYERWIDTH / 2,pos.getY());
                if(bomb != null)
                {
                    topRefBombId =  bomb.getBombId();
                    return false;
                }
            }
        }
        
        //Checks at players half on the left and right if there is a block located
        if(map.isBombPlaced(new MapCellCoordinates(pos.getX() + marginX,pos.getY() + Constants.PLAYERHEIGHT / 2 + marginY)) || map.isBombPlaced(new MapCellCoordinates(pos.getX()  + Constants.PLAYERWIDTH - marginX,pos.getY() + Constants.PLAYERHEIGHT / 2 + marginY)))
        {
            //Compare saved bomb id with current one if they dont match lock movement
            Bomb bomb1 = entityManager.getBombManager().getBombObjectOnCoordinates(pos.getX() + marginX,pos.getY() + Constants.PLAYERHEIGHT / 2 + marginY);
            Bomb bomb2 = entityManager.getBombManager().getBombObjectOnCoordinates(pos.getX()  + Constants.PLAYERWIDTH - marginX,pos.getY() + Constants.PLAYERHEIGHT / 2 + marginY);
            if((bomb1 != null && bomb1.getBombId() != topRefBombId) || (bomb2 != null && bomb2.getBombId() != topRefBombId))
            {
                /* 
                Now check if it is an enemy bomb you are colliding with.
                If so give the player the ability to walk over his own bombs
                even if he hasn't the apropriate id for it.
                */
                if(stuckBetweenBombsBug("TOP"))
                {
                    return false;
                }
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
        if(map.isBombPlaced(new MapCellCoordinates(pos.getX() + Constants.PLAYERWIDTH / 2,pos.getY())))
        {
            if(bottomRefBombId == -1)
            {
                //Get bomb Id and save this Id for later
                Bomb bomb = entityManager.getBombManager().getBombObjectOnCoordinates(pos.getX() + Constants.PLAYERWIDTH / 2,pos.getY());
                if(bomb != null)
                {
                    bottomRefBombId =  bomb.getBombId();
                    return false;
                }
            }
        }
        
        //Checks at players feet on the left if there is a block and on the right
        if(map.isBombPlaced(new MapCellCoordinates(pos.getX() + marginX,pos.getY() - marginY)) || map.isBombPlaced(new MapCellCoordinates(pos.getX()  + Constants.PLAYERWIDTH -marginX,pos.getY() - marginY)))
        {
            //Compare saved bomb id with current one if they dont match lock movement
            Bomb bomb1 = entityManager.getBombManager().getBombObjectOnCoordinates(pos.getX() + marginX,pos.getY() - marginY);
            Bomb bomb2 = entityManager.getBombManager().getBombObjectOnCoordinates(pos.getX()  + Constants.PLAYERWIDTH -marginX,pos.getY() - marginY);
            if((bomb1 != null && bomb1.getBombId() != bottomRefBombId) || (bomb2 != null && bomb2.getBombId() != bottomRefBombId))
            {
                /* 
                Now check if it is an enemy bomb you are colliding with.
                If so give the player the ability to walk over his own bombs
                even if he hasn't the apropriate id for it.
                */
                if(stuckBetweenBombsBug("BOTTOM"))
                {
                    return false;
                }
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
        if(map.isCellDeadly(new MapCellCoordinates(pos.getX() + margin,pos.getY())) || map.isCellDeadly(new MapCellCoordinates(pos.getX() + Constants.PLAYERWIDTH - margin,pos.getY())))
            return true;
        
        return false;
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
    public ThinGridCoordinates getPosition()
    {
        return pos;
    }
    
    public void setPosition(ThinGridCoordinates pos)
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
}

