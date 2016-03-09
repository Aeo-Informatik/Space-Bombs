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
import gui.TextureManager;

/**
 *
 * @author qubasa
 */
public class EnemyPlayer extends Entity{

    private float stateTime;
    private String lastMovementKeyPressed = "UP";
    private SpriteBatch sb;
    private int playerId = 0;
    
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
    
    
    public EnemyPlayer(Vector2 pos, Vector2 direction, int playerId) 
    {
        super(null, pos, direction);
        
        this.playerId = playerId;
        
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
                this.walkAnimUp = TextureManager.p2WalkingUpAnim;
                this.walkAnimDown = TextureManager.p2WalkingDownAnim;
                this.walkAnimLeft = TextureManager.p2WalkingLeftAnim;
                this.walkAnimRight = TextureManager.p2WalkingRightAnim;
                    
                this.staticUp = TextureManager.p2StaticUp;
                this.staticDown = TextureManager.p2StaticDown;
                this.staticLeft = TextureManager.p2StaticLeft;
                this.staticRight = TextureManager.p2StaticRight;
                break;

            case 4:
                this.walkAnimUp = TextureManager.p2WalkingUpAnim;
                this.walkAnimDown = TextureManager.p2WalkingDownAnim;
                this.walkAnimLeft = TextureManager.p2WalkingLeftAnim;
                this.walkAnimRight = TextureManager.p2WalkingRightAnim;
                    
                this.staticUp = TextureManager.p2StaticUp;
                this.staticDown = TextureManager.p2StaticDown;
                this.staticLeft = TextureManager.p2StaticLeft;
                this.staticRight = TextureManager.p2StaticRight;
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
    public void render(SpriteBatch sb)
    {
        //Changes the position of the texture 
        pos.add(direction);
        
        if(this.sb == null)
            this.sb = sb;
        
        //movePlayer(sb, "RIGHT");
            
    }
    
    @Override
    public void update() {
        
    }
    
    
    public void movePlayer(String direction)
    {
         //Input handling and moving the player
        if(direction.equalsIgnoreCase("LEFT"))
        {
            //Set the speed the texture moves in x and y axis
            //this is the method inherited from Entity.java class
            setDirection(-150, 0);

            //Draw the walking animation
            sb.draw(getFrame(walkAnimLeft), pos.x, pos.y);
            
            lastMovementKeyPressed = "LEFT";
            
        }else if(direction.equalsIgnoreCase("RIGHT"))
        {
            setDirection(150, 0);  
            
            sb.draw(getFrame(walkAnimRight), pos.x, pos.y);
            
            lastMovementKeyPressed = "RIGHT";
            
        }else if(direction.equalsIgnoreCase("UP"))
        {
            setDirection(0, 150);
            
            sb.draw(getFrame(walkAnimUp), pos.x, pos.y);
            
            lastMovementKeyPressed = "UP";
            
        }else if(direction.equalsIgnoreCase("DOWN"))
        {
            setDirection(0, -150);
            
            sb.draw(getFrame(walkAnimDown), pos.x, pos.y);
            
            lastMovementKeyPressed = "DOWN";
            
        }else
        {
            //Sets the texture to no movement
            setDirection(0, 0);
            
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
    }
    
    public int getPlayerId()
    {
        return this.playerId;
    }
    
    public void setPlayerid(int playerId)
    {
        this.playerId = playerId;
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
    
}
