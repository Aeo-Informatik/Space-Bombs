/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import gui.entity.bombs.Bomb;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import gui.AnimEffects;
import gui.TextureManager;
import gui.map.MapManager;

/**
 *
 * @author qubasa
 */
public class EnemyPlayer extends Entity
{
    //General variables
    private String lastMovementKeyPressed = "UP";
    private int playerId = 0;
    private AnimEffects animEffects = new AnimEffects();
    
    //Server render variables
    private boolean executeMovePlayer = false;
    private boolean executeStopPlayer = true;
    private String moveDirection = "";
    private float stopX = pos.x; 
    private float stopY = pos.y;
    
    //Player animation when he is moving around
    private final Animation walkAnimUp;
    private final Animation walkAnimDown;
    private final Animation walkAnimRight;
    private final Animation walkAnimLeft;
    
    //Player settings
    private int life = 1;
    
    //Constructor
    public EnemyPlayer(Vector2 pos, Vector2 direction, int playerId, MapManager map, Array<Bomb> bombArray, EntityManager entityManager) 
    {
        super(pos, direction, map, entityManager);
        
        //Save variables & objects as global
        this.playerId = playerId;
        
        //Set player id textures
        switch(playerId)
        {
            case 1:
                this.walkAnimUp = TextureManager.p1WalkingUpAnim;
                this.walkAnimDown = TextureManager.p1WalkingDownAnim;
                this.walkAnimLeft = TextureManager.p1WalkingLeftAnim;
                this.walkAnimRight = TextureManager.p1WalkingRightAnim;
                break;

            case 2:
                this.walkAnimUp = TextureManager.p2WalkingUpAnim;
                this.walkAnimDown = TextureManager.p2WalkingDownAnim;
                this.walkAnimLeft = TextureManager.p2WalkingLeftAnim;
                this.walkAnimRight = TextureManager.p2WalkingRightAnim;
                break;

            case 3:
                this.walkAnimUp = TextureManager.p3WalkingUpAnim;
                this.walkAnimDown = TextureManager.p3WalkingDownAnim;
                this.walkAnimLeft = TextureManager.p3WalkingLeftAnim;
                this.walkAnimRight = TextureManager.p3WalkingRightAnim;
                break;

            case 4:
                this.walkAnimUp = TextureManager.p4WalkingUpAnim;
                this.walkAnimDown = TextureManager.p4WalkingDownAnim;
                this.walkAnimLeft = TextureManager.p4WalkingLeftAnim;
                this.walkAnimRight = TextureManager.p4WalkingRightAnim;
                break;
                
            default:
                System.err.println("ERROR: Wrong player number: " + playerId + " in EnemyPlayer. Using default p1");
                this.walkAnimUp = TextureManager.p1WalkingUpAnim;
                this.walkAnimDown = TextureManager.p1WalkingDownAnim;
                this.walkAnimLeft = TextureManager.p1WalkingLeftAnim;
                this.walkAnimRight = TextureManager.p1WalkingRightAnim;
        }
    }

    
    @Override
    public void render(SpriteBatch renderObject)
    {
        //System.out.println("Enemy player is existing");
        if(this.executeStopPlayer)
        {
            stopPlayer(this.stopX, this.stopY, renderObject);
        }
        
        if(this.executeMovePlayer)
        {
            movePlayer(this.moveDirection, renderObject);
        }
    }
    
    
    //Execute on player death
    public void onDeath()
    {
        System.out.println("---------------------Player " + playerId + " died!-------------------");
    }
    
    
    /**
     * Moves the player accordingly to the direction specified.
     * @param movement 
     */
    public void movePlayer(String movement, SpriteBatch renderObject)
    {
        //If triggered from outside it will be rendered/activated in the object till stopPlayer gets called
        this.moveDirection = movement;
        this.executeMovePlayer = true;
        this.executeStopPlayer = false;
        
        //Changes the position of the texture by adding the number of pixels 
        //in which the player should go to the position
        pos.add(this.direction);
        
        switch(this.moveDirection)
        {
            case "LEFT":
                if(!collidesLeft() && !collidesLeftBomb())
                {
                    //Set the speed the texture moves in x and y axis
                    //this is the method inherited from Entity.java class
                    setDirection(-150, 0);

                    //Draw the walking animation
                    renderObject.draw(animEffects.getFrame(walkAnimLeft), pos.x, pos.y);

                    //Sets the direction in which the still standing image will be rendered
                    lastMovementKeyPressed = "LEFT";
                }else
                {
                    //Stop player
                    setDirection(0,0);
                    renderObject.draw(animEffects.getFrame(walkAnimLeft), pos.x, pos.y);
                    lastMovementKeyPressed = "LEFT";
                }
            break;

            case "RIGHT":
                if(!collidesRight() && !collidesRightBomb())
                {
                    setDirection(150, 0);  
                    renderObject.draw(animEffects.getFrame(walkAnimRight), pos.x, pos.y);
                    lastMovementKeyPressed = "RIGHT";
                }else
                {
                    //Stop player
                    setDirection(0,0);
                    renderObject.draw(animEffects.getFrame(walkAnimRight), pos.x, pos.y);
                    lastMovementKeyPressed = "RIGHT";
                }
            break;

            case "UP":
                if(!collidesTop() && !collidesTopBomb())
                {
                    setDirection(0, 150);
                    renderObject.draw(animEffects.getFrame(walkAnimUp), pos.x, pos.y);
                    lastMovementKeyPressed = "UP";
                }else
                {
                    //Stop player
                    setDirection(0,0);
                    renderObject.draw(animEffects.getFrame(walkAnimUp), pos.x, pos.y);
                    lastMovementKeyPressed = "UP";
                }
            break;

            case "DOWN":
                if(!collidesBottom() && !collidesBottomBomb())
                {
                    setDirection(0, -150);
                    renderObject.draw(animEffects.getFrame(walkAnimDown), pos.x, pos.y);
                    lastMovementKeyPressed = "DOWN";
                }else
                {
                    //Stop player
                    setDirection(0,0);
                    renderObject.draw(animEffects.getFrame(walkAnimDown), pos.x, pos.y);
                    lastMovementKeyPressed = "DOWN";
                }
            break;
           
        }
    }
    
    
    /**
     * Stops the player movements and render him on given positions
     * @param x int
     * @param y int
     */
    public void stopPlayer(float x, float y, SpriteBatch renderObject)
    {
        //If triggered from outside it will be rendered/activated in the object till movePlayer gets called
        this.executeStopPlayer = true;
        this.executeMovePlayer = false;
        this.stopX = x;
        this.stopY = y;
        
        //Sets the texture to no movement
        setDirection(0, 0);
            
        //Sets the positon where to draw the player.
        pos.set(x, y);
        pos.add(this.direction);
        
        //Draws the player if he stands still
        switch(lastMovementKeyPressed)
        {
            case "LEFT":
                renderObject.draw(walkAnimLeft.getKeyFrame(0), pos.x, pos.y);
                break;

            case "RIGHT":
                renderObject.draw(walkAnimRight.getKeyFrame(0), pos.x, pos.y);
            break;

            case "UP":
                renderObject.draw(walkAnimUp.getKeyFrame(0), pos.x, pos.y);
                break;

            case "DOWN":
                renderObject.draw(walkAnimDown.getKeyFrame(0), pos.x, pos.y);
                break;
        } 
    }

    
    /**--------------------GETTER & SETTER--------------------**/
    public int getPlayerId()
    {
        return this.playerId;
    }
    
    public void setPlayerId(int playerId)
    {
        this.playerId = playerId;
    }
    
    public int getLife()
    {
        return this.life;
    }
    
    public void setLife(int life)
    {
        this.life = life;
    }
}
