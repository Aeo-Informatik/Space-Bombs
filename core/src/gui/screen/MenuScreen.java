/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gui.camera.OrthoCamera;

/**
 *
 * @author qubasa
 */
public class MenuScreen extends Screen{
    
    //The viewpoint of the player 
    private OrthoCamera camera;
    
    @Override
    public void create() {
        camera = new OrthoCamera();
    }

    
    @Override
    public void render(SpriteBatch sb) {
        
    }

    
    @Override
    public void update() {
        camera.update();
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
