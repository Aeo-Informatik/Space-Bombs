package gui.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;


public class MenuScreen implements Screen
{
    //Objects
    private Stage stage;
    private Stack stack;
    Table rootTable;
    
    //Textures
    private Texture backgroundTexture;
    
    //Music
    private Music clickSound;
    private Music titleMusic;
    
    //Buttons
    private TextButtonStyle textButtonStyle;
    private TextButton startbutton;
    private TextButton hostButton;
    private TextButton exitbutton;
    private TextButton helpbutton;
    
    //Font import
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    
    /**------------------------CONSTRUCTOR------------------------**/
    public MenuScreen(Game game)
    {
        //Look for ben briggs music!http://benbriggs.net/
        
        //General Object initalisation
        this.stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        this.stack = new Stack();
        Gdx.input.setInputProcessor(stage);
        
        //Initialise Font
        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/press-start/prstartk.ttf"));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        
        //Start Playing titleMusic in Menu @author Jemain 
        titleMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music/music.mp3"));  
        titleMusic.setLooping(true);
        titleMusic.play();
        titleMusic.setVolume(0.5f);   
        
        //Add click sound
        clickSound = Gdx.audio.newMusic(Gdx.files.internal("audio/sounds/click.wav"));
        
        //Load the background texture
        backgroundTexture = new Texture(Gdx.files.internal("menu/menu.png"));
        
        
        /**------------------------BUTTON STYLE------------------------**/
        //Load button description into memory
        
        //Add button style
        textButtonStyle = new TextButtonStyle();
        parameter.size = 11;
        textButtonStyle.font = generator.generateFont(parameter);
        textButtonStyle.pressedOffsetY = -3;
        textButtonStyle.up   = new TextureRegionDrawable(new TextureRegion(new Texture("button/button.png"), 0, 0, 64, 64));
        textButtonStyle.over = new TextureRegionDrawable(new TextureRegion(new Texture("button/button.png"), 64, 0, 64, 64));
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("button/button.png"), 128, 0, 64, 64));
        
 
        
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
        int padX = -37;
        int padY = -46;
        stack.setPosition(Gdx.graphics.getWidth() / 2 - (stack.getWidth() / 2) + padX, Gdx.graphics.getHeight() / 2 + padY);
        
        //End 
        stack.add(stackTable);
        stage.addActor(rootTable);
        stage.addActor(stack);
        
        
        /**------------------------BUTTON FUNCTIONS------------------------**/
        //Add click listener --> Start Game
        startbutton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
                //Add click musik
                titleMusic.dispose();  
                clickSound.play();
                
                //Wait till sound is done
                try 
                {
                    Thread.sleep(100);
                    
                } catch (InterruptedException ex) 
                {
                    
                }
                
                game.setScreen(new JoinScreen(game));
            }
        });
        
        //Add click listener --> Exit Game
        exitbutton .addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
                //Add click sound
                titleMusic.dispose();
                clickSound.play();
                
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
        
        //Add click listener --> Exit Game
        helpbutton .addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
                //Add click sound
                titleMusic.dispose();
                clickSound.play();
               
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
        stage.setDebugAll(true);
        
        //Clear Screen
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        
        //Set background image
        rootTable.background(new TextureRegionDrawable(new TextureRegion(backgroundTexture)));

        //Draw stage
        stage.draw();
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
