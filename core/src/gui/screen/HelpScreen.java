/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.gdx.bomberman.Constants;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

/**
 *
 * @author JeJe
 */
public class HelpScreen implements Screen{
    







    //Objects
    private Stage stage;
    private TextButton backbutton;
    private TextButton.TextButtonStyle textButtonStyle;
    private BitmapFont font;
    private Skin skin;
    private TextureAtlas buttonAtlas;
    private Game game;
    private Texture backgroundTexture;
    private Sprite sprite;
    private SpriteBatch batch;
    

    /**------------------------CONSTRUCTOR------------------------**/
    public HelpScreen(Game game)
    {
       
        
        
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        skin = new Skin();
        batch = new SpriteBatch();
        
         //load the background texture
        backgroundTexture = new Texture(Gdx.files.internal("menu/help.png"));
        sprite = new Sprite(backgroundTexture);
        
       
       
        
        //Load button description into memory
        buttonAtlas = new TextureAtlas(Gdx.files.internal("button/button.pack"));
        skin.addRegions(buttonAtlas);

        
        //Add button style
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("button_up");
        textButtonStyle.down = skin.getDrawable("button_down");
        textButtonStyle.over = skin.getDrawable("button_checked");

       
        textButtonStyle.pressedOffsetY = -3;
        

        
        //Add button to screen
        backbutton = new TextButton("Back!", textButtonStyle);
        backbutton.setPosition(Gdx.graphics.getWidth() / 2 , Gdx.graphics.getHeight() / 2); // Add to the center even after resize
        stage.addActor(backbutton);
         //Add click listener --> Start Game
        backbutton.addListener(new ChangeListener() 
        {
            public void changed (ChangeEvent event, Actor actor) 
            {  //add click sound
                Music music = Gdx.audio.newMusic(Gdx.files.internal("audio/sounds/click.wav"));  
                music.play();
                
                game.setScreen(new MenuScreen(game));
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
