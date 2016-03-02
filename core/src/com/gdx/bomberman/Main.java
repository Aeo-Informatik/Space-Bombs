/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdx.bomberman;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;
    
    
    /**
     * Method called once when the application is created.
     */
    @Override
    public void create() {        
        batch = new SpriteBatch();    
        font = new BitmapFont();
        font.setColor(Color.RED);
    }
    
        
    /**
     * Method called by the game loop from the application every 
     * time rendering should be performed. 
     * Game logic updates are usually also performed in this method.
     */
    @Override
    public void render() {        
        Gdx.gl.glClearColor(1, 1, 1, 1);
        
        batch.begin();
        font.draw(batch, "Hello World", 200, 200);
        batch.end();
    }
    
    
    /**
     * This method is called every time the game screen is re-sized and the 
     * game is not in the paused state. It is also called once just after the create() method.
     * The parameters are the new width and height the screen has been resized to in pixels.
     */
    @Override
    public void resize(int width, int height) {
        
        
    }
    
    
    /**
     * On desktop this is called just before dispose() when exiting the application.
     * A good place to save the game state.
     */
    @Override
    public void pause() {
        
    }
    
    
    /**
     * Called when the application is destroyed
     */
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
    
    
    /**
     * This method is only called on Android, when the application resumes from a paused state.
     */
    @Override
    public void resume() {
    }
}