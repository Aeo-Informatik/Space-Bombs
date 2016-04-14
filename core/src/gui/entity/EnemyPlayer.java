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
import com.badlogic.gdx.math.Vector2;
import com.gdx.bomberman.Main;
import gui.TextureManager;
import gui.map.MapManager;

/**
 *
 * @author qubasa
 */
public class EnemyPlayer extends Entity
{

    private float stateTime;
    private String lastMovementKeyPressed = "UP";
    private int playerId = 0;
    private SpriteBatch sb;
    private MapManager map;
    
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
    
    //Player Static image when he stands still
    private final TextureRegion staticUp;
    private final TextureRegion staticDown;
    private final TextureRegion staticRight;
    private final TextureRegion staticLeft;
    
    
    public EnemyPlayer(Vector2 pos, Vector2 direction, int playerId, MapManager map) 
    {
        super(null, pos, direction);
        
        this.playerId = playerId;
        this.sb = Main.sb;
        this.map = map;
        
        switch(playerId)
        {
            case 1:
                this.walkAnimUp = TextureManager.p1WalkingUpAnim;
                this.walkAnimDown = TextureManager.p1WalkingDownAnim;
                this.walkAnimLeft = TextureManager.p1WalkingLeftAnim;
                this.walkAnimRight = TextureManager.p1WalkingRightAnim;
                    
                this.staticUp = TextureManager.p1StaticUp;
                this.staticDown = TextureManager.p1StaticDown;
                this.staticLeft = TextureManager.p1StaticLeft;
                this.staticRight = TextureManager.p1StaticRight;
                break;

            case 2:
                this.walkAnimUp = TextureManager.p2WalkingUpAnim;
                this.walkAnimDown = TextureManager.p2WalkingDownAnim;
                this.walkAnimLeft = TextureManager.p2WalkingLeftAnim;
                this.walkAnimRight = TextureManager.p2WalkingRightAnim;
                    
                this.staticUp = TextureManager.p2StaticUp;
                this.staticDown = TextureManager.p2StaticDown;
                this.staticLeft = TextureManager.p2StaticLeft;
                this.staticRight = TextureManager.p2StaticRight;
                break;

            case 3:
                this.walkAnimUp = TextureManager.p3WalkingUpAnim;
                this.walkAnimDown = TextureManager.p3WalkingDownAnim;
                this.walkAnimLeft = TextureManager.p3WalkingLeftAnim;
                this.walkAnimRight = TextureManager.p3WalkingRightAnim;
                    
                this.staticUp = TextureManager.p3StaticUp;
                this.staticDown = TextureManager.p3StaticDown;
                this.staticLeft = TextureManager.p3StaticLeft;
                this.staticRight = TextureManager.p3StaticRight;
                break;

            case 4:
                this.walkAnimUp = TextureManager.p4WalkingUpAnim;
                this.walkAnimDown = TextureManager.p4WalkingDownAnim;
                this.walkAnimLeft = TextureManager.p4WalkingLeftAnim;
                this.walkAnimRight = TextureManager.p4WalkingRightAnim;
                    
                this.staticUp = TextureManager.p4StaticUp;
                this.staticDown = TextureManager.p4StaticDown;
                this.staticLeft = TextureManager.p4StaticLeft;
                this.staticRight = TextureManager.p4StaticRight;
                break;
                
            default:
                System.err.println("ERROR: Wrong player number: " + playerId + " in EnemyPlayer. Using default p1");
                this.walkAnimUp = TextureManager.p1WalkingUpAnim;
                this.walkAnimDown = TextureManager.p1WalkingDownAnim;
                this.walkAnimLeft = TextureManager.p1WalkingLeftAnim;
                this.walkAnimRight = TextureManager.p1WalkingRightAnim;
                    
                this.staticUp = TextureManager.p1StaticUp;
                this.staticDown = TextureManager.p1StaticDown;
                this.staticLeft = TextureManager.p1StaticLeft;
                this.staticRight = TextureManager.p1StaticRight;
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
            //Set the speed the texture moves in x and y axis
            //this is the method inherited from Entity.java class
            setDirection(-150, 0);

            //Draw the walking animation
            sb.draw(getFrame(walkAnimLeft), pos.x, pos.y);
            
            //Sets the direction in which the still standing image will be rendered
            lastMovementKeyPressed = "LEFT";
            break;

            case "RIGHT":
            setDirection(150, 0);  
            sb.draw(getFrame(walkAnimRight), pos.x, pos.y);
            lastMovementKeyPressed = "RIGHT";
            break;

            case "UP":
            setDirection(0, 150);
            sb.draw(getFrame(walkAnimUp), pos.x, pos.y);
            lastMovementKeyPressed = "UP";
            break;

            case "DOWN":
            setDirection(0, -150);
            sb.draw(getFrame(walkAnimDown), pos.x, pos.y);
            lastMovementKeyPressed = "DOWN";
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
                sb.draw(staticLeft, pos.x, pos.y);
                break;

            case "RIGHT":
                sb.draw(staticRight, pos.x, pos.y);
                break;

            case "UP":
                sb.draw(staticUp, pos.x, pos.y);
                break;

            case "DOWN":
                sb.draw(staticDown, pos.x, pos.y);
                break;

        } 
    }

    
    /**
    * Gets the frame out of the animation
    * @param animation
    * @return 
    */
    private TextureRegion getFrame(Animation animation)
    {
        /* Adds the time elapsed since the last render to the stateTime.*/
        this.stateTime += Gdx.graphics.getDeltaTime(); 
        
        /*
        Obtains the current frame. This is given by the animation for the current time. 
        The second variable is the looping. 
        Passing in true, we tell the animation to restart after it reaches the last frame.
        */
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        
        return currentFrame;
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
