package gui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import gui.screen.Screen;

public class OptionScreen extends Screen
{
private Sprite splash;
private SpriteBatch batch;
private Stage stage;
private TextButton button;
private TextButtonStyle textButtonStyle;
private BitmapFont font;
private Skin skin;
private TextureAtlas buttonAtlas;
    @Override
    public void create() {
        
     stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("button/button.pack"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("startbutton_up");
        //textButtonStyle.down = skin.getDrawable("startbutton_up");
        //textButtonStyle.checked = skin.getDrawable("startbutton_up");
        button = new TextButton("Button1", textButtonStyle);
        stage.addActor(button);
    
    }

    @Override
    public void update() {
        batch = new SpriteBatch();
        
        Texture texture = new Texture (Gdx.files.internal("menu/menu.png"));  
        splash = new Sprite(texture);
        splash.setSize( Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
 

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);  
        batch.begin();
        splash.draw(batch);
        batch.end();
         
        stage.draw();
       
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {
     batch.dispose();
     splash.getTexture().dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
    
}
        