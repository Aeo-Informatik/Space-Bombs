/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.gdx.bomberman.Constants;
import static com.gdx.bomberman.Main.client;
import static com.gdx.bomberman.Main.game;
import gui.AudioManager;
import gui.TextureManager;
import static gui.TextureManager.skin;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import networkClient.Client;
import networkServer.Server;


/**
 *
 * @author pl0614
 */
public class HostScreen implements Screen
{
    //Objects
    private Stage stage;
    private Table rootTable;
    private Stack stack;
    
    //Buttons
    private TextField maxplayer;
    private TextButton hostbutton;
    private TextButton addMaxPlayer;
    private TextButton reduceMaxPlayer;

    private TextButton backButton;
    private Label errorLabel;
    
    /**------------------------CONSTRUCTOR-----------------------
     * @param game-**/
    public HostScreen()
    {
        //General Object initalisation
        this.stage = new Stage(new StretchViewport(Constants.SCREENWIDTH, Constants.SCREENHEIGHT));
        this.stack = new Stack();
        Gdx.input.setInputProcessor(stage);
        Constants.MAXPLAYERS = 1;
        

        //Initialise Font
        FreeTypeFontGenerator.FreeTypeFontParameter fontOptions = new FreeTypeFontGenerator.FreeTypeFontParameter();

        /**------------------------BUTTON STYLE------------------------**/
        
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        fontOptions.size = 14;
        labelStyle.font = TextureManager.menuFont.generateFont(fontOptions);
        labelStyle.fontColor = Color.RED;
        labelStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture("menu/textBackground.png")));
        
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        fontOptions.size = 11;
        textButtonStyle.font = TextureManager.menuFont.generateFont(fontOptions);
        textButtonStyle.up   = skin.getDrawable("button_up");
        textButtonStyle.down = skin.getDrawable("button_down");
        textButtonStyle.over = skin.getDrawable("button_checked");
        
        /**------------------------BUTTON POSITION------------------------**/
        rootTable = new Table();
        rootTable.setFillParent(true);

        Table stackTable = new Table();
        stackTable.setFillParent(true);
        
        fontOptions.size = 14;
        errorLabel = new Label("", labelStyle);
        errorLabel.setVisible(false);
        errorLabel.setAlignment(Align.center);
        stackTable.add(errorLabel).height(40).width(300).padBottom(15);
        stackTable.row();
        
        //Add Textfield to screen
        maxplayer = new TextField("", skin);
        stackTable.add(maxplayer).width(210).padTop(25);
        stackTable.row();
        
        addMaxPlayer = new TextButton("+", skin);
        stackTable.add(addMaxPlayer).width(40).padTop(5);
        
        reduceMaxPlayer = new TextButton("-",skin);
        stackTable.add(reduceMaxPlayer).width(40).padLeft(10);
        stackTable.row();
        
        //Add join button to screen
        hostbutton = new TextButton("Host", textButtonStyle);
        stackTable.add(hostbutton).padTop(50);
        stackTable.row();
        
        //Set stack position
        stack.setPosition(287, 227);
        
        //End
        stage.addActor(rootTable);
        stage.addActor(stack);
        stack.add(stackTable);
        
      hostbutton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) 
            {   
                //Add click musik
                long id = AudioManager.clickSound.play();
                AudioManager.clickSound.setVolume(id, Constants.SOUNDVOLUME);
                Constants.MAXPLAYERS = Constants.MAXPLAYERS +1;
                maxplayer.setText("Maxplayer = "+"/n"+ Constants.MAXPLAYERS);
                
                //Wait till sound is done
                try 
                {
                    Thread.sleep(100);
                    
                } catch (InterruptedException ex) 
                {
                    
                }    
         catch(Exception e)
        {
            System.err.println("ERROR: Unexpected error has been thrown in main" + e);
            e.printStackTrace();
            System.exit(1);
        }
                
                
            }
        });
                
                 
       hostbutton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) 
            {   
                //Add click musik
                long id = AudioManager.clickSound.play();
                AudioManager.clickSound.setVolume(id, Constants.SOUNDVOLUME);
                new Thread(new ServerStart()).start();
        
       
        try 
        {
            client = new Client(Constants.SERVERIP, Constants.CONNECTIONPORT);
            
            client.connectToServer();
            //client.pingThread();
             game.setScreen(new GameScreen);
        }catch(ConnectException e)
        {
            //If wrong server ip or port are given
            System.err.println("Couldn't find server " + Constants.SERVERIP + " on port " + Constants.CONNECTIONPORT);
        
        }catch (IOException | InterruptedException e) 
        {
            System.err.println("ERROR: Unexpected client exception: " + e);
            Gdx.app.exit();
        }
                
                
                //Wait till sound is done
                try 
                {
                    Thread.sleep(100);
                    
                } catch (InterruptedException ex) 
                {
                    
                }
                
                 try
        {
            
            //Initialise server object
           
            
           
           
            
           
               
        }catch(Exception e)
        {
            System.err.println("ERROR: Unexpected error has been thrown in main" + e);
            e.printStackTrace();
            System.exit(1);
        }
                
                
            }
        });
        
                
               

                       
                    
               
        
     }
    
    /**------------------------RENDER------------------------**/
    @Override
    public void render(float f) 
    {
        //Debug
        //stage.setDebugAll(true);
        
        //Clear Screen
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        
        //Set background image
        rootTable.background(new TextureRegionDrawable(new TextureRegion(TextureManager.menuBackground)));
        
        //Render stage
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        
        /*------------------SWITCH TO FULLSCREEN AND BACK------------------*/
        if(Gdx.input.isKeyPressed(Input.Keys.F12))
        {
            if(Gdx.graphics.isFullscreen())
            {
                Gdx.graphics.setWindowedMode(Constants.SCREENWIDTH, Constants.SCREENHEIGHT);
            }else
            {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
        }
        
        /*------------------QUIT GAME------------------*/
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
        {
            game.setScreen(new MenuScreen());
        }
    }
   
    
    
    /**------------------------RESIZE------------------------**/
    @Override
    public void resize(int width, int height) 
    {
        stage.getViewport().update(width, height, false);
    }

    
    /**------------------------DISPOSE------------------------**/
    @Override
    public void dispose() 
    {
        stage.dispose();
    }

    
    
    /**------------------------OTHER------------------------**/
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }
    
    public boolean validateIPAddress( String ipAddress ) 
    { 
        try
        {
            String[] tokens = ipAddress.split("\\."); 

            if (tokens.length != 4) 
            { 
                return false; 
            } 

            for (String str : tokens) 
            { 
                int i = Integer.parseInt(str); 

                if ((i < 0) || (i > 255)) 
                { 
                    return false; 
                } 
            } 
            
        }catch(NumberFormatException e)
        {
            return false;
        }
        
        return true; 
    }

}

 
