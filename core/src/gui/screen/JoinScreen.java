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
import java.net.SocketException;
import java.net.UnknownHostException;
import networkClient.Client;
import networkServer.Server;
import networkServer.ServerStart;


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
        stackTable.add(errorLabel).height(40).width(300);
        stackTable.row();
        
        //Add Textfield to screen
        ipTextField = new TextField("", skin);
        stackTable.add(ipTextField).width(230).padTop(40);
        stackTable.row();

        //Add join button to screen
        joinButton = new TextButton("Join", textButtonStyle);
        stackTable.add(joinButton).padTop(30);
        stackTable.row();
        
        //Set stack position
        stack.setPosition(287, 220);
        
        //End
        stage.addActor(rootTable);
        stage.addActor(stack);
        stack.add(stackTable);
        
        //Add click listener --> Start Game
        joinButton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) 
            {
                AudioManager.clickSound.play();
                
                if(!ipTextField.getText().equals(""))
                {
                    Constants.SERVERIP = ipTextField.getText();
                }else
                {
                    Constants.SERVERIP = "localhost";
                }

                //Starts local server for 1 Player
                if(Constants.TESTSERVER)
                {
                    new Thread()
                    {
                        @Override
                        public void run() 
                        {
                    
                            Server server = new Server(Constants.SERVERPORT, 1);
                            server.AcceptConnections();
                            server.startGame();
                        }
                    }.start();
                }
                
                //Connect to server
                try 
                {
                    client = new Client(Constants.SERVERIP, Constants.CONNECTIONPORT);

                    client.connectToServer();
                    //client.pingThread();

                    AudioManager.menuMusic.stop();
                    
                    game.setScreen(new GameScreen());
                    
                }catch(SocketException | UnknownHostException e)
                {
                    errorLabel.setText("Invalid ip address given");
                    errorLabel.setVisible(true);
                    System.err.println("ERROR: " + e.toString()); 
                    
                }catch (IOException | InterruptedException e) 
                {
                    System.err.println("ERROR: Unexpected client exception: " + e.toString());
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
}
    
