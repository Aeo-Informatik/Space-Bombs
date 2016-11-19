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
import java.net.SocketException;
import java.net.UnknownHostException;
import client.Client;
import static com.gdx.bomberman.Main.game;
import static gui.TextureManager.backSkin;
import gui.map.AvailableMaps;
import server.Server;
import server.ServerConstants;


/**
 *
 * @author pl0614
 */
public class JoinScreen implements Screen
{
    //Objects
    private Stage stage;
    private Table rootTable;
    private Stack stack;
    
    //Variables
    private String connectionIp = "";
    
    //Buttons
    private TextField ipTextField;
    private TextButton joinButton;
    private TextButton backButton;
    private Label errorLabel;
    
    /**------------------------CONSTRUCTOR-----------------------
     * @param game-**/
    public JoinScreen()
    {
        //General Object initalisation
        this.stage = new Stage(new StretchViewport(Constants.SCREENWIDTH, Constants.SCREENHEIGHT));
        this.stack = new Stack();
        Gdx.input.setInputProcessor(stage);

        //Initialise Font
        FreeTypeFontGenerator.FreeTypeFontParameter fontOptions = new FreeTypeFontGenerator.FreeTypeFontParameter();

        // Label style
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        fontOptions.size = 14;
        labelStyle.font = TextureManager.menuFont.generateFont(fontOptions);
        labelStyle.fontColor = Color.BLACK;
        labelStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture("menu/textBackground.png")));
        
        /**------------------------BOMB BUTTON STYLE------------------------**/
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        fontOptions.size = 11;
        textButtonStyle.font = TextureManager.menuFont.generateFont(fontOptions);
        textButtonStyle.up   = skin.getDrawable("button_up");
        textButtonStyle.down = skin.getDrawable("button_down");
        textButtonStyle.over = skin.getDrawable("button_checked");
        
        /**------------------------BACK BUTTON------------------------**/
        TextButton.TextButtonStyle textButtonStyleBack = new TextButton.TextButtonStyle();
        textButtonStyleBack.font = TextureManager.menuFont.generateFont(fontOptions);
        textButtonStyleBack.up   = backSkin.getDrawable("button_up");
        textButtonStyleBack.down = backSkin.getDrawable("button_down");
        textButtonStyleBack.over = backSkin.getDrawable("button_checked");
        
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
        ipTextField = new TextField("", skin);
        ipTextField.setMessageText("Server IP address...");
        stackTable.add(ipTextField).width(230).padTop(25);
        stackTable.row();

        //Add join button to screen
        joinButton = new TextButton("Join", textButtonStyle);
        stackTable.add(joinButton).padTop(30);
        stackTable.row();
        
        //Set stack position
        stack.setPosition(287, 227);
        
        //End
        stage.addActor(rootTable);
        stage.addActor(stack);
        stack.add(stackTable);
        
        // Back button
        backButton = new TextButton("", textButtonStyleBack);
        backButton.setPosition(0, Constants.SCREENHEIGHT - backButton.getHeight() + 7);
        stage.addActor(backButton);
        
        //Add click listener --> Back button
        backButton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) 
            {   
                //Add click musik
                long id = AudioManager.clickSound.play();
                AudioManager.clickSound.setVolume(id, Constants.SOUNDVOLUME);
                
                //Wait till sound is done
                try 
                {
                    Thread.sleep(100);
                    
                } catch (InterruptedException ex) 
                {
                    
                }

                game.setScreen(new MenuScreen());
            }
        });
        
        //Add click listener --> Start Game
        joinButton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) 
            {
                long id = AudioManager.clickSound.play();
                AudioManager.clickSound.setVolume(id, Constants.SOUNDVOLUME);

                //Get the ip out of the textfield
                connectionIp = ipTextField.getText();
                
                
                //Starts local server for 1 Player
                if(Constants.TESTSERVER)
                {
                    connectionIp = "127.0.0.1";
                    
                    System.out.println("CLIENT: Launching test server force IP to localhost");
                    
                    if(Constants.OWNSERVEROBJ == null)
                    {
                        Constants.OWNSERVEROBJ = new Server(ServerConstants.LISTENINGPORT, 4, new AvailableMaps().getTestMap()); 
                    }   
                    //Set map to testmap
                    //Constants.TESTSERVEROBJ.setMap("maps/Test-Map_(26x26).tmx");
                    
                    //Accept client connections
                    Constants.OWNSERVEROBJ.OpenLobby();
                }
                
                try 
                {
                    //Check if ip is valid
                    if(validateIPAddress(connectionIp))
                    {     
                        //Connect to server
                        client = new Client(connectionIp, Constants.CONNECTIONPORT);
                        client.connectToServer();
                        

                        //Start game on server
                        if(Constants.TESTSERVER)
                        {
                            Thread.currentThread().sleep(100);
                            Constants.OWNSERVEROBJ.startGame();
                        }
                        
                        System.out.println("CLIENT: Connecting to server " + connectionIp + ":" + Constants.CONNECTIONPORT);
                        
                        AudioManager.menuMusic.stop();

                        game.setScreen(new GameScreen());
                    }else
                    {
                        //Create error message on screen
                        errorLabel.setText("Invalid IP address");
                        errorLabel.setVisible(true);
                    }
                    
                }catch(SocketException | UnknownHostException e)
                {
                    //Create error message on screen
                    errorLabel.setText("Connection refused");
                    errorLabel.setVisible(true); 

                }catch (Exception e) 
                {
                    System.err.println("ERROR: Unexpected exception in joinScreen: " + e);
                    Gdx.app.exit();
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
    
