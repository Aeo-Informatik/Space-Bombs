/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.bomberman.Constants;

/**
 *
 * @author qubasa
 */
public class AnimEffects 
{
    private float stateTime = 0;
    
    
    //Constructor
    public AnimEffects()
    {
        
    }
    
    
    /**
     * The texture flashs periodically on the screen
     * @param duration: Of the flashing length
     * @param timesPerSecond: How often the texture changes from visibel to invisibel in a second
     * @return Thread: Thread object to stop the blinking animation manually
     */
    public Thread flashing(float duration, int timesPerSecond, SpriteBatch renderObject)
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
                        renderObject.setColor(1.0f, 1.0f, 1.0f,0.0f); 
                       try 
                        {
                             Thread.sleep((1000 / timesPerSecond) / 2);
                        } catch (InterruptedException ex) 
                        {
                             System.err.println("ERROR: Interrupted blinkingAnimation()");
                        }
                        
                        renderObject.setColor(1.0f, 1.0f, 1.0f,1.0f); 
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
    * Gets the textureRegion frame out of the animation.
    * @param animation
    * @return TextureRegion
    */
    public TextureRegion getFrame(com.badlogic.gdx.graphics.g2d.Animation animation)
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
}
