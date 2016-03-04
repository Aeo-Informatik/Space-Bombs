/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import gui.TextureManager;
import gui.camera.OrthoCamera;
import gui.entity.MainPlayer;

/**
 *
 * @author qubasa
 */
public class GameScreen extends Screen{
    
    //The viewpoint of the player 
    private OrthoCamera camera;
    private MainPlayer mainPlayer;
    
    @Override
    public void create() {
        //Set mainPlayer camera
        camera = new OrthoCamera();
        
        //Spawn player at x=430 and y=100, the second argument is the direction
        mainPlayer = new MainPlayer(new Vector2(430,100), new Vector2(0,0));
    }

    
    @Override
    public void render(SpriteBatch sb) {
        //Takes the matrix and everything containing in it will be rendered. 
        //The exact functionality is really complex with lots of math.
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        
        mainPlayer.render(sb);
        
        sb.end();
    }

    
    @Override
    public void update() {
        camera.update();
        mainPlayer.update();
    }
    
    
    @Override
    public void resize(int width, int height) {
       camera.resize();
    }

    
    @Override
    public void pause() {
        
    }

    
    @Override
    public void resume() {
        
    }

    
    @Override
    public void dispose() {
        
    }
    
}
