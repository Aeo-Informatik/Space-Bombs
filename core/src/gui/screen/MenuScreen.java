package gui.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.gdx.bomberman.Constants;

import client.Client;
import gui.AudioManager;
import gui.TextureManager;
import inputHandling.InputHandler;
import server.Server;

import static gui.TextureManager.skin;


public class MenuScreen extends Screens implements Screen
{
    //Objects
    private Stage stage;
    private Stack stack;
    private Table rootTable;
    private InputHandler inputHandler = new InputHandler();
    
    //Buttons
    private TextButton startButton;
    private TextButton hostButton;
    private TextButton exitButton;
    private TextButton helpButton;
    private TextButton optionButton;
    
    
    /**------------------------CONSTRUCTOR------------------------**/
    public MenuScreen(final Game game,final Client client,final Server server)
    {
        super(game, client, server);
        
        //General Object initalisation
        this.stage = new Stage(new StretchViewport(Constants.SCREENWIDTH, Constants.SCREENHEIGHT));
        this.stack = new Stack();
        inputHandler.setInputSource(stage);
        Gdx.input.setCursorCatched(false);
        
        //Initialise Font
        FreeTypeFontGenerator.FreeTypeFontParameter fontOptions = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontOptions.size = 11;
        BitmapFont font = TextureManager.menuFont.generateFont(fontOptions);
        
        
        /**------------------------AUDIO------------------------**/
        AudioManager.setCurrentMusic(AudioManager.getMenuMusic());
        AudioManager.getCurrentMusic().setLooping(true);
        AudioManager.getCurrentMusic().play();
        AudioManager.getCurrentMusic().setVolume(AudioManager.getMusicVolume());  

        /**------------------------BUTTON STYLE------------------------**/
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up   = skin.getDrawable("button_up");
        textButtonStyle.down = skin.getDrawable("button_down");
        textButtonStyle.over = skin.getDrawable("button_checked");
        
        
        /**------------------------BUTTON POSITION------------------------**/
        rootTable = new Table();
        rootTable.setFillParent(true);
        
        Table stackTable = new Table();
        stackTable.setFillParent(true);
        
        
        //Add exit button to screen
        exitButton = new TextButton("Exit", textButtonStyle);
        stackTable.add(exitButton).width(64).height(64);
        
        //Add help button to screen
        helpButton = new TextButton("Info", textButtonStyle);
        stackTable.add(helpButton).width(64).height(64).padLeft(17);
        
        //Add help button to screen
        optionButton = new TextButton("Opt.", textButtonStyle);
        stackTable.add(optionButton).width(64).height(64).padLeft(17);
        
        //Add help button to screen
        hostButton = new TextButton("Host", textButtonStyle);
        stackTable.add(hostButton).width(64).height(64).padLeft(17);
        
        
        //Add start button to screen
        startButton = new TextButton("Join", textButtonStyle);
        stackTable.add(startButton).width(64).height(64).padLeft(17);
        
        
        //Set stack position
        stack.setPosition(290, 230);
        
        //End 
        stage.addActor(rootTable);
        stage.addActor(stack);
        stack.add(stackTable);
        

        /**------------------------BUTTON FUNCTIONS------------------------**/
        //Add click listener --> Help Button
        optionButton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
                //Add click sound
                AudioManager.playClickSound();
                
                //Wait till sound is done
                try 
                {
                    Thread.sleep(100);
                    
                } catch (InterruptedException ex) 
                {
                    
                }
                
                game.setScreen(new OptionScreen(game, client, server)); 
            }
        });

        //Add click listener --> Help Button
        hostButton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
                //Add click sound
                AudioManager.playClickSound();
                
                //Wait till sound is done
                try 
                {
                    Thread.sleep(100);
                    
                } catch (InterruptedException ex) 
                {
                    
                }
                
                game.setScreen(new HostScreen(game, client, server)); 
            }
        });
        
        //Add click listener --> Start Game
        startButton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
                //Add click musik
                AudioManager.playClickSound();
                
                //Wait till sound is done
                try 
                {
                    Thread.sleep(100);
                    
                } catch (InterruptedException ex) 
                {
                    
                }
                
                game.setScreen(new JoinScreen(game, client, server));
            }
        });
        
        //Add click listener --> Exit Game
        exitButton .addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
                //Add click sound
                AudioManager.playClickSound();
                
                //Wait till sound is done
                try 
                {
                    Thread.sleep(100);
                    
                } catch (InterruptedException ex) 
                {
                    
                }
                
                Gdx.app.exit();
            }
        });
        
        //Add click listener --> Help Button
        helpButton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
                //Add click sound
                AudioManager.playClickSound();
                
                //Wait till sound is done
                try 
                {
                    Thread.sleep(100);
                    
                } catch (InterruptedException ex) 
                {
                    
                }
                
                game.setScreen(new HelpScreen(game, client, server)); 
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

        //Draw stage
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        
        /*------------------SWITCH TO FULLSCREEN AND BACK------------------*/
        super.changeToFullScreenOnF12();
        
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
    
}