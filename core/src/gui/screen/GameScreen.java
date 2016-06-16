/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import static com.gdx.bomberman.Main.client;
import gui.Constants;
import gui.camera.OrthoCamera;
import gui.entity.EntityManager;
import gui.map.MapManager;
import java.io.IOException;
import java.net.ConnectException;
import networkClient.Client;
import networkClient.ProcessData;
import networkServer.ServerStart;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author qubasa
 */
public class GameScreen implements Screen{
    
    //Genral Objects & variables
    private OrthoCamera camera;
    private EntityManager entityManager;
    private MapManager mapManager;
    private ProcessData processData;
    private Game game;
    
    /**
     * Constructor
     * @param game 
     */
    public GameScreen(Game game)
    {
        this.game = game;
        this.camera = new OrthoCamera();
        this.mapManager = new MapManager(camera);
        this.entityManager = new EntityManager(camera, mapManager, this);
        this.processData = new ProcessData(entityManager);
        
        
        //Starts local server for 1 Player
        if(Constants.TESTSERVER)
            new Thread(new ServerStart()).start();

        //Connect to server
        try 
        {
            client = new Client(Constants.SERVERIP, Constants.CONNECTIONPORT);
            
            client.connectToServer();
            //client.pingThread();
            
        }catch(ConnectException e)
        {
            //If wrong server ip or port are given
            System.err.println("Couldn't find server " + Constants.SERVERIP + " on port " + Constants.CONNECTIONPORT);
        
        }catch (IOException | InterruptedException e) 
        {
            System.err.println("ERROR: Unexpected client exception: " + e);
            Gdx.app.exit();
        }
    }
    
    
    /**
     * Render the game
     * @param f: Don't know what it means
     */
    @Override
    public void render(float f)
    {
        //Check if client is connected to server
        if(client != null)
        {
            //If client has been disconnected from server
            if(!client.isConnectedToServer())
            {
                System.err.println("Server connection lost to: " + Constants.SERVERIP);
                game.setScreen(new MenuScreen(game));
            }
            
        }else //If error occured on creating connection to server
        {
            game.setScreen(new MenuScreen(game));
        }
        

        //Takes the matrix and everything containing in it will be rendered. 
        //The exact functionality is really complex with lots of math.
        //renderServer.setProjectionMatrix(camera.combined);
        
        //Map loading into screen shouldn't be in the sb.begin() tags
        mapManager.render();
           
        //Render entities
        entityManager.render();
        
        //Render incoming server instructions
        processData.start();
        
        //Update functions
        camera.update();
        entityManager.update();
    }
    
    
    /**
     * Execute on window resize
     * @param width
     * @param height 
     */
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

    
    /**------------------------DISPOSE------------------------**/
    @Override
    public void dispose() 
    {
        mapManager.dispose();
    }
    
    
    /**------------------------OTHER------------------------**/
    @Override
    public void pause() 
    {
    }

    
    @Override
    public void resume() 
    {
    }

    @Override
    public void show() {
        
    }

    @Override
    public void hide() 
    {
        
    }
 
    
    /**------------------------GETTER & SETTER-----------------------**/    
}
