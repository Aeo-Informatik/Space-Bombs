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
    TextureRegion currentFrame;
    
    //Constructor
    public MainPlayer(Vector2 pos, Vector2 direction) {
        super(TextureManager.p1SpawnPosition, pos, direction);
    }


    //Update is the same as render only that it doesn't have the SpriteBatch Object
    @Override
    public void update()
    {
        pos.add(direction);
        
        //Input handling and moving the player in pixels
        if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT))
        {
            //Velocity the texture moves in x and y axis
            setDirection(-150, 0);
            
        }else if(Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT))
        {
            setDirection(150, 0);  
            
        }else if(Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP))
        {
            setDirection(0, 150);
            
        }else if(Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN))
        {
            setDirection(0, -150);
            
        }else
            setDirection(0, 0);
    }
    
    public TextureRegion animation(Animation animation)
    {
        /* Adds the time elapsed since the last render to the stateTime.*/
        this.stateTime += Gdx.graphics.getDeltaTime(); 
        
        /*Obtains the current frame. This is given by the animation for the current time. The second variable is the looping. Passing in true, we tell the animation to restart after it reaches the last frame.*/
        currentFrame = animation.getKeyFrame(stateTime, true);
        
        return currentFrame;
    }

    

    
}
