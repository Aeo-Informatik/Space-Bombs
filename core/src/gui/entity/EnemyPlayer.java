/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gdx.bomberman.Main;
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
    
    //General objects
    private SpriteBatch sb;
    private Array <Bomb> bombArray;
    
    //Online render variables
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
    
    //Player values
    private int playerId = 0;
    
    
    public EnemyPlayer(Vector2 pos, Vector2 direction, int playerId, MapManager map, Array<Bomb> bombArray) 
    {
        super(pos, direction, map);
        
        this.playerId = playerId;
        this.sb = Main.sb;
        
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
    public void update() 
    {
            
    }
    
    
    @Override
    public void render(SpriteBatch sb)
    {
        if(this.executeStopPlayer)
        {
            stopPlayer(this.stopX, this.stopY);
        }
        
        if(this.executeMovePlayer)
        {
            movePlayer(this.moveDirection);
        }
        
    }
    
    /**
     * Moves the player accordingly to the direction specified.
     * @param movement 
     */
    public void movePlayer(String movement)
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
                if(!collidesLeft())
                {
                    //Set the speed the texture moves in x and y axis
                    //this is the method inherited from Entity.java class
                    setDirection(-150, 0);

                    //Draw the walking animation
                    sb.draw(getFrame(walkAnimLeft), pos.x, pos.y);

                    //Sets the direction in which the still standing image will be rendered
                    lastMovementKeyPressed = "LEFT";
                }else
                {
                    //Stop player
                    setDirection(0,0);
                    sb.draw(getFrame(walkAnimLeft), pos.x, pos.y);
                    lastMovementKeyPressed = "LEFT";
                }
            break;

            case "RIGHT":
                if(!collidesRight())
                {
                    setDirection(150, 0);  
                    sb.draw(getFrame(walkAnimRight), pos.x, pos.y);
                    lastMovementKeyPressed = "RIGHT";
                }else
                {
                    //Stop player
                    setDirection(0,0);
                    sb.draw(getFrame(walkAnimRight), pos.x, pos.y);
                    lastMovementKeyPressed = "RIGHT";
                }
            break;

            case "UP":
                if(!collidesTop())
                {
                    setDirection(0, 150);
                    sb.draw(getFrame(walkAnimUp), pos.x, pos.y);
                    lastMovementKeyPressed = "UP";
                }else
                {
                    //Stop player
                    setDirection(0,0);
                    sb.draw(getFrame(walkAnimUp), pos.x, pos.y);
                    lastMovementKeyPressed = "UP";
                }
            break;

            case "DOWN":
                if(!collidesBottom())
                {
                    setDirection(0, -150);
                    sb.draw(getFrame(walkAnimDown), pos.x, pos.y);
                    lastMovementKeyPressed = "DOWN";
                }else
                {
                    //Stop player
                    setDirection(0,0);
                    sb.draw(getFrame(walkAnimDown), pos.x, pos.y);
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
    public void stopPlayer(float x, float y)
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
                sb.draw(walkAnimLeft.getKeyFrame(0), pos.x, pos.y);
                break;

            case "RIGHT":
                sb.draw(walkAnimRight.getKeyFrame(0), pos.x, pos.y);
            break;

            case "UP":
                sb.draw(walkAnimUp.getKeyFrame(0), pos.x, pos.y);
                break;

            case "DOWN":
                sb.draw(walkAnimDown.getKeyFrame(0), pos.x, pos.y);
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
    
}
