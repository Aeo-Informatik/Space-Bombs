package gui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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
import com.gdx.bomberman.Main;

public class MenuScreen extends Screen
{
    //Objects
    Stage stage;
    TextButton button;
    TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;
    
    @Override
    public void create() 
    {
        //Create objects
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        skin = new Skin();
        
        
        //Load button description into memory
        buttonAtlas = new TextureAtlas(Gdx.files.internal("button/button.pack"));
        skin.addRegions(buttonAtlas);
        
        //Add button style
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("startbutton_up");
        textButtonStyle.down = skin.getDrawable("startbutton_down");
        //textButtonStyle.checked = skin.getDrawable("checked-button");
        
        //Add button to screen
        button = new TextButton("Start Game", textButtonStyle);
        button.setPosition(Gdx.graphics.getWidth() / 2 - (button.getWidth() / 2), Gdx.graphics.getHeight() / 2); // Add to the center even after resize
        stage.addActor(button);
        
        //Add click listener --> Start Game
        button.addListener(new ChangeListener() 
        {
            public void changed (ChangeEvent event, Actor actor) 
            {
                System.out.println("Starting game screen!");
                ScreenManager.setScreen( new GameScreen());
            }
        });
        
    }

    
    @Override
    public void update() 
    {

    }
 

    @Override
    public void render(SpriteBatch sb) 
    {
        //Clear Screen
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        
        
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    
    @Override
    public void resize(int width, int height) 
    {
        
    }

    
    @Override
    public void dispose() 
    {
        buttonAtlas.dispose();
        font.dispose();
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
    
}
        