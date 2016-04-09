/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gui.camera.OrthoCamera;
import gui.entity.EntityManager;
import gui.map.MapManager;
import networkClient.ProcessData;


/**
 *
 * @author qubasa
 */
public class GameScreen extends Screen{
    
    //The viewpoint of the player 
    public static OrthoCamera camera;
    private EntityManager entityManager;
    private Thread processDataThread;
    private MapManager mapManager;
    
    @Override
    public void create() 
    {
        this.camera = new OrthoCamera();
        this.entityManager = new EntityManager();
        
        this.mapManager = new MapManager("maps/BasicMap.tmx");
        mapManager.create();
        
        ProcessData processData = new ProcessData();
        processDataThread = new Thread(processData);
    }

    
    @Override
    public void render(SpriteBatch sb) 
    { 
        //Takes the matrix and everything containing in it will be rendered. 
        //The exact functionality is really complex with lots of math.
        sb.setProjectionMatrix(camera.combined);
        
        //Render Map
        mapManager.render(sb);
        
        sb.begin();
        
        //Render Entites 
        entityManager.render(sb);
        
        //Starts one thread to render incoming server calls
        if(processDataThread.isAlive() == false)
        {
            processDataThread.start();
        }
        
        sb.end();
    }

    
    @Override
    public void update() 
    {
        camera.update();
        entityManager.update();
        mapManager.update();
    }
    
    
    @Override
    public void resize(int width, int height) 
    {
       camera.resize();
    }

    
    @Override
    public void pause() 
    {
        mapManager.pause();
    }

    
    @Override
    public void resume() 
    {
        mapManager.resume();
    }

    
    @Override
    public void dispose() 
    {
        mapManager.dispose();
    }
    
}
