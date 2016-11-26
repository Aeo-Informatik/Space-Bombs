/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gdx.bomberman.Constants;
import gui.entity.Entity;
import gui.entity.EntityManager;
import gui.entity.bomb.Bomb;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;

/**
 *
 * @author qubasa
 */
public abstract class Player extends Entity
{
    // Objects
    protected OrthographicCamera camera;
    
    // Variables
    protected float cameraSpeed = Constants.DEFAULTCAMERASPEED;
    
    //Debug
    private static ShapeRenderer debugRenderer = new ShapeRenderer();
    
    //Constructor
    public Player(ThinGridCoordinates pos, ThinGridCoordinates direction, MapLoader map, EntityManager entityManager, OrthographicCamera camera)
    {
        super(pos, direction, map, entityManager);
        
        this.camera = camera;
    }
    
    protected void collisionTest()
    {
        // Collide bottom
        ThinGridCoordinates point = this.getCollisionPosDownLeft();
        Player.DrawDebugLine(point, point, 5, Color.RED, camera.combined);
        ThinGridCoordinates point2 = this.getCollisionPosDownRight();
        Player.DrawDebugLine(point2, point2, 5, Color.RED, camera.combined);
        
        // Collide Top
        ThinGridCoordinates point3 = this.getCollisionPosTopHalfLeft();
        Player.DrawDebugLine(point3, point3, 5, Color.RED, camera.combined);
        ThinGridCoordinates point4 = this.getCollisionPosTopHalfRight();
        Player.DrawDebugLine(point4, point4, 5, Color.RED, camera.combined);
        
        // Collide left
        ThinGridCoordinates point5 = this.getCollisionPosMiddleLeft();
        Player.DrawDebugLine(point5, point5, 5, Color.RED, camera.combined);
        
        // Collide right
        ThinGridCoordinates point6 = this.getCollisionPosMiddleRight();
        Player.DrawDebugLine(point6, point6, 5, Color.RED, camera.combined);
        
        // Stand on block
        ThinGridCoordinates point7 = this.getFeetLocation();
        Player.DrawDebugLine(point7, point7, 5, Color.GOLD, camera.combined);
    }
    
    private static void DrawDebugLine(ThinGridCoordinates start, ThinGridCoordinates end, int lineWidth, Color color, Matrix4 projectionMatrix)
    {
        Gdx.gl.glLineWidth(lineWidth);
        debugRenderer.setProjectionMatrix(projectionMatrix);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(color);
        debugRenderer.line(new Vector2(start.getX(), start.getY()), new Vector2(end.getX() +5, end.getY() +1));
        debugRenderer.end();
        Gdx.gl.glLineWidth(1);
    }
    
    /**------------------CAMERA FUNCTIONS------------------**/
    /**
    * Follows the entity with smooth camera movements.
    */
    protected void cameraFollowPlayer(ThinGridCoordinates pos)
    {
        Vector3 cameraPos = camera.position;
        cameraPos.x += (pos.getX() - cameraPos.x) * cameraSpeed * Constants.DELTATIME;
        cameraPos.y += (pos.getY() - cameraPos.y) * cameraSpeed * Constants.DELTATIME;
    }
    
    
    /**
     * Checks if entity is standing on a deadly field if so it returns true
     * @return boolean
     */
    protected boolean touchesDeadlyBlock()
    {
        //Checks from the walking right texture a collision on the down left, down right
        if(map.isCellDeadly(new MapCellCoordinates(getCollisionPosDownLeft())) || map.isCellDeadly(new MapCellCoordinates(getCollisionPosDownRight())))
            return true;
        
        return false;
    }
    
    /**------------------BLOCKED COLLISION FUNCTIONS------------------**/
    /**
     * Checks if entity collides with a blocked field on his left if so it returns true
     * @return boolean
     */
    protected boolean collidesLeft()
    {
        if(map.isCellBlocked(new MapCellCoordinates(this.getCollisionPosMiddleLeft())))
            return true;

        return false;
    }
    
    /**
     * Checks if entity collides with a blocked field on his right if so it returns true
     * @return boolean
     */
    protected boolean collidesRight()
    {
        if(map.isCellBlocked(new MapCellCoordinates(this.getCollisionPosMiddleRight())))
            return true;

        return false;
    }
    
    /**
     * Checks if entity collides with a blocked field on his top if so it returns true
     * @return boolean
     */
    protected boolean collidesTop()
    {
        //Checks at players half on the left and right if there is a block or bomb located
        if(map.isCellBlocked(new MapCellCoordinates(getCollisionPosTopHalfLeft())) 
                || map.isCellBlocked(new MapCellCoordinates(getCollisionPosTopHalfRight())))
            return true;
        return false;
    }
    
    /**
     * Checks if entity collides with a blocked field on his bottom if so it returns true
     * @return boolean
     */
    protected boolean collidesBottom()
    {
        //Checks at players feet on the left if there is a block and on the right
        if(map.isCellBlocked(new MapCellCoordinates(getCollisionPosDownLeft())) 
                || map.isCellBlocked(new MapCellCoordinates(getCollisionPosDownRight())))
            return true;
        //else
        return false;
    }

    
    /**--------------------------COLLIDES WITH BOMB--------------------------**/
    private boolean abilityToWalkOverOwnBombs = false;
    
    /**
     * Checks if player stands on enemy bomb if true check if one direction is players own bomb 
     * if true let him go over own bomb. Reset this behaviour after there is no bomb below player anymore.
     * @param direction
     * @return 
     */
    private boolean stuckBetweenBombsBug(String direction)
    {
        // If player stands on a bomb
        if(map.isBombPlaced(new MapCellCoordinates(this.getFeetLocation())))
        {
            // If that bomb doesn't have the same playerId aka. isn't the players own bomb
            if(entityManager.getBombManager().getBombObjectOnCoordinates(this.getFeetLocation()).getPlayerId() != Constants.PLAYERID)
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
                    // If that bomb does have the same playerId aka. is the players own bomb
                    if(entityManager.getBombManager().getBombObjectOnCoordinates(this.getCollisionPosMiddleLeft()).getPlayerId() == Constants.PLAYERID)
                    {
                        // Let the player walk over his own bombs even if he walked away from them. 
                        // This prevents the player to be stuck between an enemy bomb and his own.
                        return true;
                    }
                    break;
                case "RIGHT":
                    // If that bomb does have the same playerId aka. is the players own bomb
                    if(entityManager.getBombManager().getBombObjectOnCoordinates(this.getCollisionPosMiddleRight()).getPlayerId() == Constants.PLAYERID)
                    {
                        // Let the player walk over his own bombs even if he walked away from them. 
                        // This prevents the player to be stuck between an enemy bomb and his own.
                        return true;
                    }
                    break;
                case "TOP":
                    // If that bomb does have the same playerId aka. is the players own bomb
                    if(entityManager.getBombManager().getBombObjectOnCoordinates(this.getCollisionPosTopHalfLeft()).getPlayerId() == Constants.PLAYERID || entityManager.getBombManager().getBombObjectOnCoordinates(this.getCollisionPosTopHalfRight()).getPlayerId() == Constants.PLAYERID )
                    {
                        // Let the player walk over his own bombs even if he walked away from them. 
                        // This prevents the player to be stuck between an enemy bomb and his own.
                        return true;
                    }
                    break;
                case "BOTTOM":
                    // If that bomb does have the same playerId aka. is the players own bomb
                    if(entityManager.getBombManager().getBombObjectOnCoordinates(this.getCollisionPosDownLeft()).getPlayerId() == Constants.PLAYERID || entityManager.getBombManager().getBombObjectOnCoordinates(this.getCollisionPosDownRight()).getPlayerId() == Constants.PLAYERID)
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
        //If player stands on bomb 
        if(map.isBombPlaced(new MapCellCoordinates(this.getFeetLocation())))
        {
            if(leftRefBombId == -1)
            {
                //Get bomb Id and save this Id for later
                Bomb bomb = entityManager.getBombManager().getBombObjectOnCoordinates(this.getFeetLocation());
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
        if(map.isBombPlaced(new MapCellCoordinates(this.getCollisionPosMiddleLeft())))
        {
            //Compare saved bomb id with current one if they dont match lock movement
            Bomb bomb = entityManager.getBombManager().getBombObjectOnCoordinates(this.getCollisionPosMiddleLeft());
            
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
        //If player stands on bomb 
        if(map.isBombPlaced(new MapCellCoordinates(this.getFeetLocation())))
        {
            if(rightRefBombId == -1)
            {
                //Get bomb Id and save this Id for later
                Bomb bomb = entityManager.getBombManager().getBombObjectOnCoordinates(this.getFeetLocation());
                if(bomb != null)
                {
                    rightRefBombId =  bomb.getBombId();
                    return false;
                }
            }
        }
        
        //If player walks against bomb from the right 
        if(map.isBombPlaced(new MapCellCoordinates(this.getCollisionPosMiddleRight())))
        {
            //Compare saved bomb id with current one if they dont match lock movement
            Bomb bomb = entityManager.getBombManager().getBombObjectOnCoordinates(this.getCollisionPosMiddleRight());
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
        //If player stands on bomb walk away
        if(map.isBombPlaced(new MapCellCoordinates(this.getFeetLocation())))
        {
            if(topRefBombId == -1)
            {
                //Get bomb Id and save this Id for later
                Bomb bomb = entityManager.getBombManager().getBombObjectOnCoordinates(this.getFeetLocation());
                if(bomb != null)
                {
                    topRefBombId =  bomb.getBombId();
                    return false;
                }
            }
        }
        
        //Checks at players half on the left and right if there is a block located
        if(map.isBombPlaced(new MapCellCoordinates(this.getCollisionPosTopHalfLeft())) || map.isBombPlaced(new MapCellCoordinates(this.getCollisionPosTopHalfRight())))
        {
            //Compare saved bomb id with current one if they dont match lock movement
            Bomb bomb1 = entityManager.getBombManager().getBombObjectOnCoordinates(this.getCollisionPosTopHalfLeft());
            Bomb bomb2 = entityManager.getBombManager().getBombObjectOnCoordinates(this.getCollisionPosTopHalfRight());
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
        //If player stands on bomb
        if(map.isBombPlaced(new MapCellCoordinates(this.getFeetLocation())))
        {
            if(bottomRefBombId == -1)
            {
                //Get bomb Id and save this Id for later
                Bomb bomb = entityManager.getBombManager().getBombObjectOnCoordinates(this.getFeetLocation());
                if(bomb != null)
                {
                    bottomRefBombId =  bomb.getBombId();
                    return false;
                }
            }
        }
        
        //Checks at players feet on the left if there is a block and on the right
        if(map.isBombPlaced(new MapCellCoordinates(this.getCollisionPosDownLeft())) || map.isBombPlaced(new MapCellCoordinates(this.getCollisionPosDownRight())))
        {
            //Compare saved bomb id with current one if they dont match lock movement
            Bomb bomb1 = entityManager.getBombManager().getBombObjectOnCoordinates(this.getCollisionPosDownLeft());
            Bomb bomb2 = entityManager.getBombManager().getBombObjectOnCoordinates(this.getCollisionPosDownRight());
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
    
    /**------------------GET COLLISION COORDINATES------------------**/
    public ThinGridCoordinates getFeetLocation()
    {
        return new ThinGridCoordinates(pos.getX() + Constants.PLAYERWIDTH / 2,pos.getY());
    }
    
    public ThinGridCoordinates getCollisionPosMiddleRight()
    {
        float marginX = 2f;
        float marginY = 3f;
        
        return new ThinGridCoordinates(pos.getX() + Constants.PLAYERWIDTH + marginX,pos.getY() + Constants.PLAYERHEIGHT / 4 + marginY);
    }
    
    public ThinGridCoordinates getCollisionPosMiddleLeft()
    {
        float marginX = 2f;
        float marginY = 3f;
        
        return new ThinGridCoordinates(pos.getX() - marginX,pos.getY() + Constants.PLAYERHEIGHT / 4 + marginY);
    }
    
    public ThinGridCoordinates getCollisionPosTopHalfRight()
    {
        float marginX = 2f;
        float marginY = 3f;
        
        return new ThinGridCoordinates(pos.getX() + Constants.PLAYERWIDTH - marginX,pos.getY() + Constants.PLAYERHEIGHT / 2 + marginY);
    }
    
    public ThinGridCoordinates getCollisionPosTopHalfLeft()
    {
        float marginX = 2f;
        float marginY = 3f;
        
        return new ThinGridCoordinates(pos.getX() + marginX,pos.getY() + Constants.PLAYERHEIGHT / 2 + marginY);
    }

    public ThinGridCoordinates getCollisionPosDownRight()
    {
        float marginX = 2f;
        float marginY = 3f;
        
        return new ThinGridCoordinates(pos.getX() + Constants.PLAYERWIDTH - marginX,pos.getY() - marginY);
    }
    
    public ThinGridCoordinates getCollisionPosDownLeft()
    {
        float marginX = 2f;
        float marginY = 3f;
        
        return new ThinGridCoordinates(pos.getX() + marginX, pos.getY() - marginY);
    }
    
    /**------------------GETTER & SETTER------------------**/
    public void setCameraFollowSpeed(float cameraSpeed)
    {
        this.cameraSpeed = cameraSpeed;
    }
    
    public float getCameraFollowSpeed()
    {
        return this.cameraSpeed;
    }

}
