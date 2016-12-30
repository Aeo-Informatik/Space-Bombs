/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.screen;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.gdx.bomberman.Constants;

import java.util.ArrayList;
import java.util.Random;

import client.Client;
import client.ClientProcessData;
import client.SendCommand;
import gui.AudioManager;
import gui.entity.EntityManager;
import gui.map.MapLoader;
import inputHandling.InputHandler;
import server.Server;


/**
 *
 * @author qubasa
 */
public class GameScreen extends Screens implements Screen{
    
    //Objects 
    private OrthographicCamera camera;
    private EntityManager entityManager;
    private MapLoader mapManager;
    private ClientProcessData processData;
    private Random random = new Random();
    private Stage stage;
    private SendCommand sendCommand;
    private InputHandler inputHandler = new InputHandler();
    
    //Variables
    private int previousMusicIndex = -1;
    private float musicTimer = 0;
    private float musicStart = 15; //Seconds after game start or after music is finished
    
    //Display winner screen timer
    private float winnerTimer = 0;
    private float winnerStart = 4; // After 4 seconds the screen wll be displayed
    
    //Main Player HUD
    private MainPlayerHud mainPlayerHud;
    
    /**
     * Constructor
     * @param game 
     */
    public GameScreen(Game game, Client client, Server server)
    {
        super(game, client, server);

        this.sendCommand = client.getSendCommand();
        this.camera = new OrthographicCamera();
        this.stage = new Stage(new StretchViewport(Constants.SCREENWIDTH, Constants.SCREENHEIGHT, camera));
        this.mapManager = new MapLoader(camera, sendCommand);
        this.entityManager = new EntityManager(camera, mapManager, sendCommand, inputHandler);
        this.processData = new ClientProcessData(entityManager, mapManager);
        this.mainPlayerHud = new MainPlayerHud(entityManager, game, server, client);
        this.camera.zoom = Constants.DEFAULTZOOM;

        // Controls
        if(Gdx.app.getType().equals(ApplicationType.Android))
        {
            Gdx.input.setInputProcessor(mainPlayerHud.stage);
        }else {
            inputHandler.setInputSource(mainPlayerHud.stage);
            Gdx.input.setCursorCatched(true);
        }

    }
    
    
    /**
     * Render the game
     * @param f: Don't know what it means
     */
    @Override
    public void render(float f)
    {
        //Set bacckground color
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        
        //Play next music title after last one finished and make sure the next one is unequal the last one
        if((AudioManager.getCurrentMusic() == null || !AudioManager.getCurrentMusic().isPlaying()) && musicStart < musicTimer)
        {
            int nextMusic = random.nextInt(6);
            while(nextMusic == previousMusicIndex)
            {
                nextMusic = random.nextInt(6);
            }
            
            previousMusicIndex = nextMusic;
            
            AudioManager.setCurrentMusic(AudioManager.nextIngameMusic(nextMusic));
            AudioManager.getCurrentMusic().play();
            AudioManager.getCurrentMusic().setVolume(AudioManager.getMusicVolume());
            
            musicTimer = 0;
        }else if ((AudioManager.getCurrentMusic() == null || !AudioManager.getCurrentMusic().isPlaying()))
        {
            musicTimer += Constants.DELTATIME;
        }

        //Draw stage
        stage.act(Constants.DELTATIME);
        stage.draw();
        
        mapManager.render();
        
        // Starts processing icoming data
        processData.start();
        
        // Render entities
        entityManager.render();
        entityManager.update();
        
        // Render MainPlayerHud
        mainPlayerHud.render(0);

        // Update camera
        camera.update();
        
         /*------------------DISPLAY WINNER SCREEN------------------*/
        ArrayList<Integer> deadPlayers = entityManager.getPlayerManager().getDeadPlayersArray();
        
        if(Constants.PLAYERSPAWNED)
        {
            // If number of dead players is equals as the amount of playing players -1
            if(deadPlayers.size() == Constants.AMOUNTPLAYERS || deadPlayers.size() == Constants.AMOUNTPLAYERS -1 && Constants.AMOUNTPLAYERS > 1)
            {
                if(this.winnerTimer >= this.winnerStart)
                {
                    if(deadPlayers.size() == Constants.AMOUNTPLAYERS)
                    {
                        deadPlayers.add(-1);
                    }

                    // Add main Player as last "dead" player if he is the last one alive
                    if(entityManager.getPlayerManager().getMainPlayer() != null)
                    {
                        deadPlayers.add(Constants.PLAYERID);
                    }else if(entityManager.getPlayerManager().getEnemyArray().size > 0)
                    {
                        deadPlayers.add(entityManager.getPlayerManager().getEnemyArray().get(0).getPlayerId());
                    }

                    //Stop music
                    if(AudioManager.getCurrentMusic() != null)
                    {
                        AudioManager.getCurrentMusic().dispose();
                    }
                    game.setScreen(new WinnerScreen(deadPlayers, game, client, server));
                }else
                {
                    winnerTimer += Constants.DELTATIME;
                }
            }
        }
        
        //If client has been disconnected from server
        if(!client.isConnectedToServer())
        {
            if(AudioManager.getCurrentMusic() != null)
            {
                //Stop music
                AudioManager.getCurrentMusic().stop();
            }
            System.err.println("CLIENT: Connection lost to server.");
            //Go to menuscreen
            game.setScreen(new MenuScreen(game, client, server));
        }
        
        /*------------------SWITCH TO FULLSCREEN AND BACK------------------*/
        super.changeToFullScreenOnF12();
        
        if(Gdx.input.isKeyPressed(Keys.ALT_RIGHT))
        {
            if(Gdx.input.isCursorCatched())
            {
                Gdx.input.setCursorCatched(false);
            }else
            {
                Gdx.input.setCursorCatched(true);
            }
        }
        
        /*------------------PAUSE MENU------------------*/
        if(inputHandler.isEscKey())
        {
            Gdx.input.setCursorCatched(false);
            mainPlayerHud.showExitDialog();
            System.out.println("Esc Key pressed " + Constants.DELTATIME);
        }
    }
    
    /**
     * Execute on window resize
     * @param width
     * @param height 
     */
    @Override
    public void resize(int width, int height) 
    {
       stage.getViewport().update(width, height, false);
       mapManager.resize(width, height);
       mainPlayerHud.resize(width, height);
       
       //If screen gets resized set camera to player position
       if(Constants.PLAYERSPAWNED)
       {
           if(entityManager.getPlayerManager().getMainPlayer() != null)
           {
                camera.position.set(entityManager.getPlayerManager().getMainPlayer().getPosition().getX(), entityManager.getPlayerManager().getMainPlayer().getPosition().getY(), 0);
           
           }else if(entityManager.getPlayerManager().getSpectator() != null)
           {
               camera.position.set(entityManager.getPlayerManager().getSpectator().getPosition().getX(), entityManager.getPlayerManager().getSpectator().getPosition().getY(), 0);
           }
       }
    }
    
    /**------------------------DISPOSE------------------------**/
    @Override
    public void dispose() 
    {
        mapManager.dispose();
        AudioManager.getCurrentMusic().dispose();
        stage.dispose();
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