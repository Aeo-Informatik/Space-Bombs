/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gui.Constants;
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
    private OrthoCamera camera;
    private EntityManager entityManager;
    private Thread processDataThread;
    private MapManager mapManager;
    private SpriteBatch sb;
    
    public GameScreen(SpriteBatch sb)
    {
        this.sb = sb;
    }
    
    @Override
    public void create() 
    {
        this.camera = new OrthoCamera();
        this.entityManager = new EntityManager(camera);
        
        this.mapManager = new MapManager(camera);
        mapManager.setMap(Constants.MAPPATH);
        
        ProcessData processData = new ProcessData(entityManager, sb);
        processDataThread = new Thread(processData);
    }

    
    @Override
    public void render(SpriteBatch sb) 
    { 
        //Takes the matrix and everything containing in it will be rendered. 
        //The exact functionality is really complex with lots of math.
        sb.setProjectionMatrix(camera.combined);
        
        //Map loading into screen
        mapManager.render(sb);
        
        //BEGIN DRAWING
        sb.begin();
        
        //Render entities
        entityManager.render(sb);
        
        //Render incoming server calls
        if(processDataThread.isAlive() == false)
        {
            processDataThread.start();
        }
        
        //END DRAWING
        sb.end();
    }

    
    @Override
    public void update() 
    {
        camera.update();
    }
    
    
    @Override
    public void resize(int width, int height) 
    {
       camera.resize();
       mapManager.resize(width, height);
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
        mapManager.dispose();
    }
    
}
