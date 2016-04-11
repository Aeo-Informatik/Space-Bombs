/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdx.bomberman;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gui.Constants;
import gui.TextureManager;
import gui.screen.GameScreen;
import gui.screen.ScreenManager;
import static gui.screen.ScreenManager.currentScreen;
import networkClient.Client;
import networkServer.ServerStart;

public class Main implements ApplicationListener {
    
    /**
    * It is very common to draw a texture mapped to rectangular geometry. 
    * It is also very common to draw the same texture or various regions of 
    * that texture many times. It would be inefficient to send each rectangle 
    * one at a time to the GPU to be drawn. Instead, many rectangles for the same 
    * texture can be described and sent to the GPU all at once. 
    * This is what the SpriteBatch class does.
    */
    private SpriteBatch sb;
    
    /**
     * LibGdx makes use of bitmap files (pngs) to render fonts. 
     * Each glyph in the font has a corresponding TextureRegion.
     */
    private BitmapFont font;
    
    
    public static Client client;
    
    /**
     * Method called once when the application is created.
     */
    @Override
    public void create() 
    {   
        TextureManager.load();
        sb = new SpriteBatch();    
        font = new BitmapFont();
        
        //Starts the server ----- FOR BUILDING PURPOSES ONLY
        //new Thread(new ServerStart()).start();
        
        
        //Sets the currentScreen to the GameScreen.java that means everything like
        //ScreenManager.getCurrentScreen() is equals to GameScreen().methodName
        ScreenManager.setScreen(new GameScreen(sb));
        
        try {
            //Start Client
            client = new Client(Constants.CLIENTHOST, Constants.CLIENTPORT);
            Client.DEBUG = true;
            
            //Listen for incomming data
            client.receiveData();
        
        //If an error occurs shut application down
        } catch (Exception e) 
        {
            System.err.println("ERROR: Client could't connect to server " + e);
            System.exit(0);
        }
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
        
        if(currentScreen != null)
            ScreenManager.getCurrentScreen().update();
            ScreenManager.getCurrentScreen().render(sb);
    }
    
    
    /**
     * This method is called every time the game screen is re-sized and the 
     * game is not in the paused state. It is also called once just after the create() method.
     * The parameters are the new width and height the screen has been resized to in pixels.
     */
    @Override
    public void resize(int width, int height) 
    {
        if(currentScreen != null)
            ScreenManager.getCurrentScreen().resize(width, height);
        
    }
    
    
    /**
     * On desktop this is called just before dispose() when exiting the application.
     * A good place to save the game state.
     */
    @Override
    public void pause() 
    {
        
        if(currentScreen != null)
            ScreenManager.getCurrentScreen().pause();
        
    }
    
    
    /**
     * Called when the application is destroyed
     */
    @Override
    public void dispose() 
    {
        
        if(currentScreen != null)
            ScreenManager.getCurrentScreen().dispose();
        
        sb.dispose();
        font.dispose();
    }
    
    
    /**
     * This method is only called on Android, when the application resumes from a paused state.
     */
    @Override
    public void resume() 
    {
        if(currentScreen != null)
            ScreenManager.getCurrentScreen().resume();
    }
}
