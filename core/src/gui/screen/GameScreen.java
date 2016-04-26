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
    private MapManager mapManager;
    private ProcessData processData;
    
    public GameScreen()
    {
    }
    
    @Override
    public void create() 
    {
        this.camera = new OrthoCamera();
        this.mapManager = new MapManager(camera);
        this.entityManager = new EntityManager(camera, mapManager);
        this.processData = new ProcessData(entityManager);
    }

    
    @Override
    public void render(SpriteBatch sb) 
    { 
        //Takes the matrix and everything containing in it will be rendered. 
        //The exact functionality is really complex with lots of math.
        sb.setProjectionMatrix(camera.combined);
        
        //Map loading into screen
        mapManager.render(sb);
        
        /*---------------BEGIN DRAWING---------------*/
        sb.begin();
        
        //Render entities
        entityManager.render(sb);
        
        //Render incoming server instructions
        processData.start();
        
        sb.end();
        /*---------------END DRAWING---------------*/
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
       mapManager.resize(width, height);
       
       //If screen gets resized set camera to player position
       if(Constants.PLAYERSPAWNED)
       {
           if(entityManager.getMainPlayer() != null)
           {
                camera.setPosition(entityManager.getMainPlayer().getPosition().x, entityManager.getMainPlayer().getPosition().y);
           
           }else if(entityManager.getSpectator() != null)
           {
               camera.setPosition(entityManager.getSpectator().getPosition().x, entityManager.getSpectator().getPosition().y);
           }
       }
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
