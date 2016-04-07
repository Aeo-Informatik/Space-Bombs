/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gui.camera.OrthoCamera;
import gui.entity.EntityManager;
import networkClient.ProcessData;


/**
 *
 * @author qubasa
 */
public class GameScreen extends Screen{
    
    //The viewpoint of the player 
    private OrthoCamera camera;
    private EntityManager entityManager;
    
    @Override
    public void create() 
    {
        this.camera = new OrthoCamera();
        this.entityManager = new EntityManager();
    }

    
    @Override
    public void render(SpriteBatch sb) {
        //Takes the matrix and everything containing in it will be rendered. 
        //The exact functionality is really complex with lots of math.
        sb.setProjectionMatrix(camera.combined);
        
        sb.begin();
        //Things to draw to screen come in here
        entityManager.render(sb);
        
        //Starts thread to render incoming server calls
        ProcessData processData = new ProcessData();
        Thread processDataThread = new Thread(processData);
        processDataThread.start();
        
        sb.end();
    }

    
    @Override
    public void update() 
    {
        camera.update();
        entityManager.update();
    }
    
    
    @Override
    public void resize(int width, int height) 
    {
       camera.resize();
    }

    
    @Override
    public void pause() 
    {
        
    }

    
    @Override
    public void resume() 
    {
        
    }

    
    @Override
    public void dispose() 
    {
        
    }
    
}
