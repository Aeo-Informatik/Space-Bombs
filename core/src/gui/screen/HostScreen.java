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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.gdx.bomberman.Constants;
import static com.gdx.bomberman.Main.client;
import static com.gdx.bomberman.Main.game;
import gui.AudioManager;
import gui.TextureManager;
import static gui.TextureManager.skin;
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
    
    private int maximumplayer;
   private int clientsConnectionSize;
   private int hostButtonClickCounter;// because there was and error while having gamescreen , it was possible to click the button

    
    //Buttons
    private Label maxplayer;
    private TextButton hostbutton;
    private TextButton addMaxPlayer;
    private TextButton reduceMaxPlayer;
    

    private TextButton backButton;
    private Label playerLabel;
    
    /**------------------------CONSTRUCTOR-----------------------
     * @param game-**/
    public HostScreen()
    {
        //General Object initalisation
        this.stage = new Stage(new StretchViewport(Constants.SCREENWIDTH, Constants.SCREENHEIGHT));
        this.stack = new Stack();
        Gdx.input.setInputProcessor(stage);
        maximumplayer = 1;
        hostButtonClickCounter = 0;
        Constants.MAXPLAYERS = maximumplayer;
     

        //Initialise Font
        FreeTypeFontGenerator.FreeTypeFontParameter fontOptions = new FreeTypeFontGenerator.FreeTypeFontParameter();

        /**------------------------BUTTON STYLE------------------------**/
        
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        fontOptions.size = 14;
        labelStyle.font = TextureManager.menuFont.generateFont(fontOptions);
        labelStyle.fontColor = Color.BLUE;
        
   
 
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        fontOptions.size = 11;
        textButtonStyle.font = TextureManager.menuFont.generateFont(fontOptions);
        textButtonStyle.up   = skin.getDrawable("button_up");
        textButtonStyle.down = skin.getDrawable("button_down");
        textButtonStyle.over = skin.getDrawable("button_checked");
        
        
        /**------------------------POSITION------------------------**/
        rootTable = new Table();
        rootTable.setFillParent(true);
        
        Table stackTable = new Table();
        stackTable.setFillParent(true);
        
                stackTable.row();

        //Add playerlabel
        playerLabel =  new Label("MaxPlayer:",labelStyle);
        playerLabel.setVisible(true);
        stackTable.add(playerLabel).expandX();
        stackTable.row();
        
        //Add Label to screen
        maxplayer = new Label("1", labelStyle);
        stackTable.add(maxplayer).width(15).padTop(15).expandX().padRight(20);
        stackTable.row();
        
        //Add plus button
        addMaxPlayer = new TextButton("+", skin);
        stackTable.add(addMaxPlayer).width(40).expandX().padRight(180).padTop(35);         
        
        //Add minus button
        reduceMaxPlayer = new TextButton("-",skin);
        stackTable.add(reduceMaxPlayer).width(40).padLeft(10).expandX().padRight(110).padTop(35);
        stackTable.row();
        
        //Add host button to screen
        hostbutton = new TextButton("Host", textButtonStyle);
        stackTable.add(hostbutton).padTop(109).expandX().padLeft(-26);
      
        
        //Stack settings
        stack.setPosition(300, 185);
        stack.setWidth(240);
        
        //End
        stack.add(stackTable);
        stage.addActor(rootTable);
        stage.addActor(stack);
        
       
        
  
        
        
        
      addMaxPlayer.addListener(new ChangeListener() 
        {
         
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) 
            {    
                if(maximumplayer < 4)
              {
                 
                //Add click musik
                long id = AudioManager.clickSound.play();
                AudioManager.clickSound.setVolume(id, Constants.SOUNDVOLUME);
                maximumplayer = maximumplayer +1;
                maxplayer.setText(""+maximumplayer);
                
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
                
              }else
              {
                  
               
              }
                    
            }
        });
      reduceMaxPlayer.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) 
            {   
                if( maximumplayer <= 4 & maximumplayer> 1)
                {
                
              
                //Add click musik
                long id = AudioManager.clickSound.play();
                AudioManager.clickSound.setVolume(id, Constants.SOUNDVOLUME);
                maximumplayer = maximumplayer -1;
                maxplayer.setText(""+maximumplayer);                
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
              
              }else
              {
                  
           
              }
                    
            }
        });
      
                
                 
       hostbutton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) 
            {   
                
                if(hostButtonClickCounter <1){
                    hostButtonClickCounter++;
                Constants.SERVERIP = "localhost";
                //Add click musik
                Constants.MAXPLAYERS = maximumplayer;
                System.out.println( Constants.MAXPLAYERS);
                long id = AudioManager.clickSound.play();
                AudioManager.clickSound.setVolume(id, Constants.SOUNDVOLUME);      
                
                try 
            {
                // try to start the server and connect with it
                
                Server server = new Server(Constants.SERVERPORT, Constants.MAXPLAYERS);
                client = new Client(Constants.SERVERIP, Constants.CONNECTIONPORT);
                client.connectToServer();
                server.OpenLobby();
                game.setScreen(new GameScreen());

                
                clientsConnectionSize = Server.getClientList().size();

               if(  (clientsConnectionSize==maximumplayer))
               {
                   
                   server.startGame();
               } 
                
                
             
            
            }catch(Exception e) 
            {
          
            System.err.println("ERROR: Something went wrong on creating the server: " +e);
            e.printStackTrace();
            
            
            
            System.exit(1);
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

 
