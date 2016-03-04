/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import gui.TextureManager;

/**
 *
 * @author qubasa
 */
public class MainPlayer extends Entity{
    

    private float stateTime;
    private String lastMovementKeyPressed = "UP";
    
    private Animation walkAnimUp;
    private Animation walkAnimDown;
    private Animation walkAnimRight;
    private Animation walkAnimLeft;
    
    /**
     * 
     * @param pos
     * @param direction
     * @param walkAnimUp
     * @param walkAnimDown
     * @param walkAnimRight
     * @param walkAnimLeft 
     */
    public MainPlayer(Vector2 pos, Vector2 direction, Animation walkAnimUp, 
            Animation walkAnimDown, Animation walkAnimRight, Animation walkAnimLeft) 
    {
        super(null, pos, direction);
        
        this.walkAnimUp = walkAnimUp;
        this.walkAnimDown = walkAnimDown;
        this.walkAnimLeft = walkAnimLeft;
        this.walkAnimRight = walkAnimRight;
    }

    /**
     * Update is the same as render only that it doesn't have the SpriteBatch Object.
     * It is used for game logic updates.
     */
    @Override
    public void update() {
        
    }
    
    /**
     * Draws the player to screen
     * @param sb 
     */
    @Override
    public void render(SpriteBatch sb)
    {
          inputMovePlayer(sb);          
    }
    
    
    /**
     * Moves the player if keyboard input is received
     * @param sb 
     */
    private void inputMovePlayer(SpriteBatch sb)
    {
        //Changes the position of the texture 
        pos.add(direction);
        
        //Input handling and moving the player
        if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT))
        {
            //Set the speed the texture moves in x and y axis
            //this is the method inherited from Entity.java class
            setDirection(-150, 0);

            //Draw the walking animation
            sb.draw(getFrame(walkAnimLeft), pos.x, pos.y);
            
            lastMovementKeyPressed = "LEFT";
            
        }else if(Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT))
        {
            setDirection(150, 0);  
            
            sb.draw(getFrame(walkAnimRight), pos.x, pos.y);
            
            lastMovementKeyPressed = "RIGHT";
            
        }else if(Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP))
        {
            setDirection(0, 150);
            
            sb.draw(getFrame(walkAnimUp), pos.x, pos.y);
            
            lastMovementKeyPressed = "UP";
            
        }else if(Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN))
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
                    sb.draw(TextureManager.p1StaticLeft, pos.x, pos.y);
                    break;

                case "RIGHT":
                    sb.draw(TextureManager.p1StaticRight, pos.x, pos.y);
                break;

                case "UP":
                    sb.draw(TextureManager.p1StaticUp, pos.x, pos.y);
                    break;

                case "DOWN":
                    sb.draw(TextureManager.p1StaticDown, pos.x, pos.y);
                    break;
            }

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
      
}
