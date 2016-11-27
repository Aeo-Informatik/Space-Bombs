/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.screen;

import client.Client;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.gdx.bomberman.Constants;
import gui.TextureManager;
import server.Server;

/**
 *
 * @author qubasa
 */
public class StartScreen extends Screens implements Screen
{
    //Objects
    private Stage stage;
    private Table rootTable;
    private float timer = 0;
    private float endTimer = 3;
    
    public StartScreen(Game game, Client client, Server server) 
    {
        super(game, client, server);
        
        //General Object initalisation
        this.stage = new Stage(new StretchViewport(Constants.SCREENWIDTH, Constants.SCREENHEIGHT));
        Gdx.input.setInputProcessor(stage);
    
        //Initialise Font
        FreeTypeFontGenerator.FreeTypeFontParameter fontOptions = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontOptions.size = 11;
        BitmapFont font = TextureManager.menuFont.generateFont(fontOptions);
        
        rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);
        
        //Set background image
        rootTable.background(new TextureRegionDrawable(new TextureRegion(TextureManager.startBackground)));

    }



    @Override
    public void render(float f) 
    {
        //Clear Screen
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        //Draw stage
        stage.act(Constants.DELTATIME);
        stage.draw();
        
        if(timer >= endTimer)
        {
            game.setScreen(new MenuScreen(game, client, server));
        }else
        {
            timer += Constants.DELTATIME;
        }
        
        if(Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY))
        {
            game.setScreen(new MenuScreen(game, client, server));
        }
        
        /*------------------SWITCH TO FULLSCREEN AND BACK------------------*/
        if(Gdx.input.isKeyJustPressed(Input.Keys.F12))
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

    @Override
    public void resize(int width, int height) 
    {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void show() {
    }
    
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
    
}
