/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.gdx.bomberman.Constants;

import client.Client;
import gui.AudioManager;
import gui.TextureManager;
import inputHandling.InputHandler;
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
    private InputHandler inputHandler = new InputHandler();
    
    //Press any key flashing timer
    private float flashingTimer = 0;
    private float endFlashingTimer = 0.7f;
    
    // Hide label after button press
    private float hideTimer = 0;
    private float endHideTimer = 0.7f;
    
    // Label
    private Label gameTitel;
    private Label pressAnyKey;

    public StartScreen(Game game, Client client, Server server) 
    {
        super(game, client, server);
        
        //General Object initalisation
        this.stage = new Stage(new StretchViewport(Constants.SCREENWIDTH, Constants.SCREENHEIGHT));
        inputHandler.setInputSource(stage);
    
        //Initialise Font
        FreeTypeFontGenerator.FreeTypeFontParameter fontOptions = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontOptions.size = 11;

        
        /*------------------------AUDIO------------------------**/
        
        AudioManager.setCurrentMusic(AudioManager.getStartScreenMusic());
        AudioManager.getCurrentMusic().setLooping(true);
        AudioManager.getCurrentMusic().play();
        AudioManager.getCurrentMusic().setVolume(AudioManager.getMusicVolume());  

        
        /*------------------------LABEL STYLE------------------------**/
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        fontOptions.size = 70;
        labelStyle.font = TextureManager.menuFont.generateFont(fontOptions);
        labelStyle.fontColor = Color.GOLD;
        
        rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);
        
        gameTitel = new Label("I like trains", labelStyle);
        gameTitel.setText(Constants.WINDOWTITEL);
        gameTitel.setAlignment(Align.center);
        gameTitel.setPosition((Constants.SCREENWIDTH - gameTitel.getWidth()) / 2, Constants.SCREENHEIGHT - gameTitel.getHeight() - 10);
        stage.addActor(gameTitel);
        
        /*------------------------LABEL STYLE------------------------**/
        Label.LabelStyle labelStyle2 = new Label.LabelStyle();
        fontOptions.size = 30;
        labelStyle2.font = TextureManager.menuFont.generateFont(fontOptions);
        labelStyle2.fontColor = Color.GOLDENROD;
        
        pressAnyKey = new Label("Press any key...", labelStyle2);
        pressAnyKey.setAlignment(Align.center);
        pressAnyKey.setPosition((Constants.SCREENWIDTH - pressAnyKey.getWidth()) / 2, Constants.SCREENHEIGHT - gameTitel.getHeight() - pressAnyKey.getHeight() - 40);
        stage.addActor(pressAnyKey);

        if(Constants.ISRUNNINGONSMARTPHONE)
        {
            pressAnyKey.setText("Tap anywhere...");
        }

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
        
        if(flashingTimer >= endFlashingTimer)
        {
            if(pressAnyKey.isVisible())
            {
                pressAnyKey.setVisible(false);
            }else
            {
                pressAnyKey.setVisible(true);
            }
            flashingTimer = 0;
        }else
        {
            flashingTimer += Constants.DELTATIME;
        }
        
        if(inputHandler.isAnyKey() || hideTimer != 0)
        {
            pressAnyKey.setVisible(false);
            
            AudioManager.getCurrentMusic().dispose();
            
            if(hideTimer == 0)
            {
                //Add click sound
                Sound sound = AudioManager.getNormalExplosion();
                long id = sound.play();
                sound.setVolume(id, AudioManager.getSoundVolume() * Constants.NORMALBOMBEXPLOSIONMOD);
            }

            if(hideTimer >= endHideTimer)
            {
                game.setScreen(new MenuScreen(game, client, server));
            }else
            {
                hideTimer += Constants.DELTATIME;
            }
        }
        
        /*------------------SWITCH TO FULLSCREEN AND BACK------------------*/
        super.changeToFullScreenOnF12();
        
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