package gui.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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


public class MenuScreen implements Screen
{
    //Objects
    private Stage stage;
    private Skin skin;
    private Game game;
    private SpriteBatch batch;
    private Sprite sprite;
    private Stack stack;
    
    //Textures
    private Texture backgroundTexture;
    private TextureAtlas buttonAtlas;
    
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
        //General Object initalisation
        this.game = game;
        this.stack = new Stack();
        stage = new Stage();
        skin = new Skin();
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);
        
        //Initialise Font
        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/press-start/prstartk.ttf"));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        
        //Start Playing music in Menu @author Jemain 
        Music music = Gdx.audio.newMusic(Gdx.files.internal("audio/music/NyanCatoriginal.ogg"));  
//        music.setLooping(true);
//        music.play();
//        music.setVolume(0.5f);   
        
        //Load the background texture
        backgroundTexture = new Texture(Gdx.files.internal("menu/menu.png"));
        sprite = new Sprite(backgroundTexture);
        
        
        /**------------------------BUTTON STYLE------------------------**/
        //Load button description into memory
        buttonAtlas = new TextureAtlas(Gdx.files.internal("button/button.pack"));
        skin.addRegions(buttonAtlas);
        
        //Add button style
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button_up");
        textButtonStyle.down = skin.getDrawable("button_down");
        textButtonStyle.over = skin.getDrawable("button_checked");
        parameter.size = 11;
        textButtonStyle.font = generator.generateFont(parameter);
        textButtonStyle.pressedOffsetY = -3;
        
        
        /**------------------------BUTTON POSITION------------------------**/
        Table stackTable = new Table();
        stackTable.setFillParent(true);
        //stackTable.debugAll();
        
        //Add start button to screen
        startbutton = new TextButton("Join", textButtonStyle);
        stackTable.add(startbutton).width(64).height(64);
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
        stage.addActor(stack);
        
        
        /**------------------------BUTTON FUNCTIONS------------------------**/
        //Add click listener --> Start Game
        startbutton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeEvent event, Actor actor) 
            {   
                //Add click musik
                music.dispose();
                Music music = Gdx.audio.newMusic(Gdx.files.internal("audio/sounds/click.wav"));  
                music.play();
                
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
                music.dispose();
                Music music = Gdx.audio.newMusic(Gdx.files.internal("audio/sounds/click.wav"));  
                music.play();
                
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
                music.dispose();
                Music music = Gdx.audio.newMusic(Gdx.files.internal("audio/sounds/click.wav"));  
                music.play();
               
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
        //Clear Screen
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        
        stage.act(Gdx.graphics.getDeltaTime());
        
        //Render stage
        batch.begin();
            sprite.draw(batch);
            sprite.setSize(800,480);
        batch.end();
        
        stage.draw();
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
        buttonAtlas.dispose();
        stage.dispose();
        skin.dispose();
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
