package gui.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.gdx.bomberman.Constants;



public class MenuScreen implements Screen
{
    //Objects
    private Stage stage;
    private TextButton startbutton;
    private TextButton exitbutton;
    private TextButton helpbutton;
    private TextButtonStyle textButtonStyle;
    private BitmapFont font;
    private Skin skin;
    private TextureAtlas buttonAtlas;
    private Game game;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Sprite sprite;
    
    /**------------------------CONSTRUCTOR------------------------**/
    public MenuScreen(Game game)
    {
       //Start Playing music in Menu @author Jemain 
      Music music = Gdx.audio.newMusic(Gdx.files.internal("audio/music/NyanCatoriginal.ogg"));  
      music.setLooping(true);
      music.play();
      music.setVolume(0.5f);   
     
      
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        skin = new Skin();
        batch = new SpriteBatch();
        
         //load the background texture
        backgroundTexture = new Texture(Gdx.files.internal("menu/menu.png"));
        sprite = new Sprite(backgroundTexture);
        
 
        //Load button description into memory
        buttonAtlas = new TextureAtlas(Gdx.files.internal("button/button.pack"));
        skin.addRegions(buttonAtlas);
        
        buttonAtlas = new TextureAtlas(Gdx.files.internal("button/exit.pack"));
        skin.addRegions(buttonAtlas);
        
        
        //Add button style
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button_up");
        textButtonStyle.down = skin.getDrawable("button_down");
        textButtonStyle.over = skin.getDrawable("button_checked");
        textButtonStyle.font = font;
        textButtonStyle.pressedOffsetY = -3;
        
        //Add button to screen
        startbutton = new TextButton("Start Game!", textButtonStyle);
        startbutton.setPosition(Gdx.graphics.getWidth() / 2 - (startbutton.getWidth() / 2), Gdx.graphics.getHeight() / 2); // Add to the center even after resize
        stage.addActor(startbutton);
        
        
        //Add click listener --> Start Game
        startbutton.addListener(new ChangeListener() 
        {
            public void changed (ChangeEvent event, Actor actor) 
            {   //add click musik
                music.dispose();
                Music music = Gdx.audio.newMusic(Gdx.files.internal("audio/sounds/click.wav"));  
                music.play();
                game.setScreen(new JoinScreen(game));
                
            }
        });
        
        
        //Add button to screen
        exitbutton = new TextButton("Exit!", textButtonStyle);
        exitbutton .setPosition(Gdx.graphics.getWidth() / 6, Gdx.graphics.getHeight() / 2); // Add to the center even after resize
        stage.addActor(exitbutton);
        
        
        //Add click listener --> Exit Game
        exitbutton .addListener(new ChangeListener() 
        {
            public void changed (ChangeEvent event, Actor actor) 
            {   //add click sound
                music.dispose();
                Music music = Gdx.audio.newMusic(Gdx.files.internal("audio/sounds/click.wav"));  
                music.play();
                Gdx.app.exit();
            }
        });
        
        
        //Add button to screen
        helpbutton = new TextButton("Help", textButtonStyle);
        helpbutton .setPosition(Gdx.graphics.getWidth() / 3 - 1, Gdx.graphics.getHeight() / 2); // Add to the center even after resize
        stage.addActor(helpbutton);
        
        
        //Add click listener --> Exit Game
        helpbutton .addListener(new ChangeListener() 
        {
            public void changed (ChangeEvent event, Actor actor) 
            {   //add click sound
                music.dispose();
                Music music = Gdx.audio.newMusic(Gdx.files.internal("audio/sounds/click.wav"));  
                music.play();
               
                
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
        
        //Render stage
        stage.act(Gdx.graphics.getDeltaTime());
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
        font.dispose();
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
