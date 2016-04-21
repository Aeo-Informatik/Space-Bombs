package gui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gui.screen.Screen;
import com.badlogic.gdx.graphics.Texture;

public class OptionScreen extends Screen
{
private Sprite splash;
private SpriteBatch batch;
    @Override
    public void create() {
   
    
    }

    @Override
    public void update() {
        batch = new SpriteBatch();
        Texture texture = new Texture (Gdx.files.internal("maps/mmenu.png"));  
        splash = new Sprite(texture);
        splash.setSize( Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
 

    @Override
    public void render(SpriteBatch sb, float DeltaTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);  
        batch.begin();
        splash.draw(batch);
        batch.end();
       
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {
   
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
    
}
        