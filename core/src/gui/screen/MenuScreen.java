package gui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.gdx.bomberman.Constants;
import static com.gdx.bomberman.Main.game;
import gui.AudioManager;
import gui.TextureManager;
import static gui.TextureManager.skin;
import java.util.ArrayList;


public class MenuScreen implements Screen
{
    //Objects
    private Stage stage;
    private Stack stack;
    private Table rootTable;
    
    //Buttons
    private TextButton startbutton;
    private TextButton hostButton;
    private TextButton exitbutton;
    private TextButton helpbutton;
    
    /**------------------------CONSTRUCTOR------------------------**/
    public MenuScreen()
    {
        //General Object initalisation
        this.stage = new Stage(new StretchViewport(Constants.SCREENWIDTH, Constants.SCREENHEIGHT));
        this.stack = new Stack();
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCursorCatched(false);
  
        
        //Reset server
        if(Constants.OWNSERVEROBJ != null)
        {
            Constants.OWNSERVEROBJ.resetServer();
        }
        
        //Initialise Font
        FreeTypeFontGenerator.FreeTypeFontParameter fontOptions = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontOptions.size = 11;
        BitmapFont font = TextureManager.menuFont.generateFont(fontOptions);
        
        
        /**------------------------AUDIO------------------------**/
        if(AudioManager.currentIngameMusic != null)
        {
            AudioManager.currentIngameMusic.dispose();
        }
        
        AudioManager.menuMusic.setLooping(true);
        AudioManager.menuMusic.play();
        AudioManager.menuMusic.setVolume(Constants.MUSICVOLUME);  

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
        
        //Add start button to screen
        startbutton = new TextButton("Join", textButtonStyle);
        stackTable.add(startbutton);
        stackTable.row();
        
        
        //Add help button to screen
        hostButton = new TextButton("Host", textButtonStyle);
        stackTable.add(hostButton).padTop(15);
        stackTable.row();
        
        //Add help button to screen
        helpbutton = new TextButton("Help", textButtonStyle);
        stackTable.add(helpbutton).padTop(15);
        stackTable.row();
        
        //Add exit button to screen
        exitbutton = new TextButton("Exit", textButtonStyle);
        stackTable.add(exitbutton).padTop(15);
        stackTable.row();
        
        //Set stack position
        stack.setPosition(287, 195);
        
        //End 
        stage.addActor(rootTable);
        stage.addActor(stack);
        stack.add(stackTable);
        
        
        /**------------------------BUTTON FUNCTIONS------------------------**/
        //Add click listener --> Help Button
        hostButton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
                //Add click sound
                long id = AudioManager.clickSound.play();
                AudioManager.clickSound.setVolume(id, Constants.SOUNDVOLUME);
                
                //Wait till sound is done
                try 
                {
                    Thread.sleep(100);
                    
                } catch (InterruptedException ex) 
                {
                    
                }
                
                game.setScreen(new HostScreen()); 
            }
        });
        
        //Add click listener --> Start Game
        startbutton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
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
                
                game.setScreen(new JoinScreen());
            }
        });
        
        //Add click listener --> Exit Game
        exitbutton .addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
                //Add click sound
                long id = AudioManager.clickSound.play();
                AudioManager.clickSound.setVolume(id, Constants.SOUNDVOLUME);
                
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
        helpbutton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
                //Add click sound
                long id = AudioManager.clickSound.play();
                AudioManager.clickSound.setVolume(id, Constants.SOUNDVOLUME);
               
                //Wait till sound is done
                try 
                {
                    Thread.sleep(100);
                    
                } catch (InterruptedException ex) 
                {
                    
                }
                
                game.setScreen(new HelpScreen(game)); 
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
        stage.act(Constants.DELTATIME);
        stage.draw();
        
        if(Gdx.input.isKeyPressed(Input.Keys.C))
        {
            ArrayList<Integer> winners = new ArrayList<>();
            winners.add(1);
            winners.add(4);
            winners.add(2);
            winners.add(3);
            game.setScreen(new WinnerScreen(winners, 2));
        }
        
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
