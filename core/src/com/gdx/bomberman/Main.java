/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdx.bomberman;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import gui.AudioManager;
import gui.TextureManager;
import gui.screen.MenuScreen;
import client.Client;

public class Main extends Game implements ApplicationListener {
    
    /**
    * It is very common to draw a texture mapped to rectangular geometry. 
    * It is also very common to draw the same texture or various regions of 
    * that texture many times. It would be inefficient to send each rectangle 
    * one at a time to the GPU to be drawn. Instead, many rectangles for the same 
    * texture can be described and sent to the GPU all at once. 
    * This is what the SpriteBatch class does.
    */
    public static Game game;
    public static Client client;
    
    
    //Constructor
    public Main()
    {
        //Objects
        Main.game = this;
    }
    
    /**
     * Method called once when the application is created.
     */
    @Override
    public void create() 
    {     
        //Load all textures
        TextureManager.load();
        AudioManager.load();
        
        game.setScreen(new MenuScreen());
    }
    
        
    /**
     * Method called by the game loop from the application every 
     * time rendering should be performed. 
     * Game logic updates are usually also performed in this method.
     */
    @Override
    public void render() 
    {    
        //Clear screen
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //Sets the delta time to a constant
        Constants.DELTATIME = Gdx.graphics.getDeltaTime();

        super.render();
    }
    
    
    /**
     * This method is called every time the game screen is re-sized and the 
     * game is not in the paused state. It is also called once just after the create() method.
     * The parameters are the new width and height the screen has been resized to in pixels.
     */
    @Override
    public void resize(int width, int height) 
    {
        super.resize(width, height);
    }
    
    
    /**
    * Called when the application is closed
    */
    @Override
    public void dispose() 
    {
        super.dispose();
    }
    
    
    /**
     * On desktop this is called just before dispose() when exiting the application.
     * A good place to save the game state.
     */
    @Override
    public void pause() 
    {
        super.pause();
    }
    

    /**
     * This method is only called on Android, when the application resumes from a paused state.
     */
    @Override
    public void resume() 
    {
        super.resume();
    }
}
