/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.gdx.bomberman.Constants;

import client.Client;
import gui.AudioManager;
import gui.TextureManager;
import inputHandling.InputHandler;
import server.Server;

import static gui.TextureManager.backSkin;

/**
 *
 * @author qubasa
 */
public class OptionScreen extends Screens implements Screen
{
    
    //Objects
    private Stage stage;
    private Table rootTable = new Table();
    private InputHandler inputHandler = new InputHandler();
    
    //Other buttons
    private TextButton backbutton;
   
    //Sound volume
    private Slider soundVolumeSlider;
    private Label descriptSoundVolumeLabel;
    private Label showSoundVolumeLabel;
    private WidgetGroup soundVolumeWidget = new WidgetGroup();
    
    // Music volume
    private Slider musicVolumeSlider;
    private Label descriptMusicVolumeLabel;
    private Label showMusicVolumeLabel;
    private WidgetGroup musicVolumeWidget = new WidgetGroup();
    
    //Fullscreen
    private CheckBox checkBoxFullscreen;
    
    public OptionScreen(final Game game,final Client client,final Server server)
    {
        super(game, client, server);
        
        //General Object initalisation
        stage = new Stage(new StretchViewport(Constants.SCREENWIDTH, Constants.SCREENHEIGHT));
        inputHandler.setInputSource(stage);
        
        // Set map preview and background
        rootTable.background(new TextureRegionDrawable(new TextureRegion(TextureManager.hostBackground)));

                
        //Set background
        rootTable.setFillParent(true);
        stage.addActor(rootTable);
        
        //Initialise Font
        FreeTypeFontGenerator.FreeTypeFontParameter fontOptions = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontOptions.size = 11;
        BitmapFont font = TextureManager.menuFont.generateFont(fontOptions);
        
        /**------------------------LABEL STYLE------------------------**/
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        fontOptions.size = 14;
        labelStyle.font = TextureManager.menuFont.generateFont(fontOptions);
        labelStyle.fontColor = Color.BLACK;
       
        
        /**------------------------SOUND VOLUME SLIDER------------------------**/
        Table table1 = new Table();
        
        descriptSoundVolumeLabel = new Label("Sound Volume:", labelStyle); 
        descriptSoundVolumeLabel.setAlignment(Align.center);
        table1.add(descriptSoundVolumeLabel).row();
        
        showSoundVolumeLabel = new Label("", labelStyle); 
        showSoundVolumeLabel.setText((int) (AudioManager.getSoundVolume() * 100)  + "%");
        table1.add(showSoundVolumeLabel).padTop(20).row();
        
        soundVolumeSlider = new Slider(0, 1f, 0.1f, false, TextureManager.skin);
        soundVolumeSlider.setValue(AudioManager.getSoundVolume());
        //soundVolumeSlider.setAnimateDuration(0.3f);
        table1.add(soundVolumeSlider).padTop(15);
        
        // Add to stage
        soundVolumeWidget.setPosition((Constants.SCREENWIDTH - soundVolumeWidget.getWidth()) / 2, 255);
        soundVolumeWidget.addActor(table1);
        stage.addActor(soundVolumeWidget);
        
        /**------------------------MUSIC VOLUME SLIDER------------------------**/
        Table table2 = new Table();
        
        descriptMusicVolumeLabel = new Label("Music Volume:", labelStyle); 
        descriptMusicVolumeLabel.setAlignment(Align.center);
        table2.add(descriptMusicVolumeLabel).row();
        
        showMusicVolumeLabel = new Label("", labelStyle); 
        showMusicVolumeLabel.setText((int) (AudioManager.getMusicVolume() * 1000)  + "%");
        table2.add(showMusicVolumeLabel).padTop(20).row();
        
        musicVolumeSlider = new Slider(0, 0.1f, 0.005f, false, TextureManager.skin);
        musicVolumeSlider.setValue(AudioManager.getMusicVolume());
        //soundVolumeSlider.setAnimateDuration(0.3f);
        table2.add(musicVolumeSlider).padTop(15);
        
        // Add to stage
        musicVolumeWidget.setPosition((Constants.SCREENWIDTH - musicVolumeWidget.getWidth()) / 2, 410);
        musicVolumeWidget.addActor(table2);
        stage.addActor(musicVolumeWidget);
        
        /**------------------------CHECK BOX FULLSCREEN------------------------**/
        checkBoxFullscreen = new CheckBox("Fullscreen", TextureManager.skin);
        checkBoxFullscreen.setPosition((Constants.SCREENWIDTH - checkBoxFullscreen.getWidth()) / 2, 140);
        checkBoxFullscreen.getStyle().fontColor = Color.BLACK;

        
        stage.addActor(checkBoxFullscreen);
        
        /**------------------------BACK BUTTON------------------------**/
        TextButton.TextButtonStyle textButtonStyleBack = new TextButton.TextButtonStyle();
        textButtonStyleBack.font = font;
        textButtonStyleBack.up   = backSkin.getDrawable("button_up");
        textButtonStyleBack.down = backSkin.getDrawable("button_down");
        textButtonStyleBack.over = backSkin.getDrawable("button_checked");
        
        /**------------------------OTHER BUTTONS------------------------**/
        // Back button
        backbutton = new TextButton("", textButtonStyleBack);
        backbutton.setPosition(0, Constants.SCREENHEIGHT - backbutton.getHeight() + 7);
        stage.addActor(backbutton);
        
        /**------------------------CHANGE LISTENER------------------------**/
        //Add click listener --> Music volume slider
        checkBoxFullscreen.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) 
            {   
                if(!checkBoxFullscreen.isChecked())
                {
                    Gdx.graphics.setWindowedMode(Constants.SCREENWIDTH, Constants.SCREENHEIGHT);
                }else
                {
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                }
            }
        });
        
        //Add click listener --> Music volume slider
        musicVolumeSlider.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) 
            {   
                AudioManager.setMusicVolume(musicVolumeSlider.getValue());
                
                float musicVolume = (float) Math.round(AudioManager.getMusicVolume()*1000);
                
                showMusicVolumeLabel.setText((int) musicVolume  + "%");
                
                AudioManager.getMenuMusic().setVolume(AudioManager.getMusicVolume());  
            }
        });

        //Add click listener --> Sound volume slider
        soundVolumeSlider.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) 
            {   
                
                AudioManager.setSoundVolume(soundVolumeSlider.getValue());
                
                float soundVolume = (float) Math.round(AudioManager.getSoundVolume()*100);
                
                showSoundVolumeLabel.setText((int) soundVolume  + "%");
            
                //Add click sound
                AudioManager.playClickSound();
            }
        });

        //Add click listener --> Back button
        backbutton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) 
            {   
                AudioManager.playClickSound();
                //Wait till sound is done
                try 
                {
                    Thread.sleep(100);
                    
                } catch (InterruptedException ex) 
                {
                    
                }

                game.setScreen(new MenuScreen(game, client, server));
            }
        });
    }
    
    /**------------------------RENDER------------------------**/
    @Override
    public void render(float f) 
    {        
        //Debug
        //stage.setDebugAll(true);
        
        if(Gdx.graphics.isFullscreen())
        {
            checkBoxFullscreen.setChecked(true);
        }else
        {
            checkBoxFullscreen.setChecked(false);
        }
        
        //Clear Screen
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        
        //Draw stage
        stage.act(Constants.DELTATIME);
        stage.draw();
        
        /*------------------SWITCH TO FULLSCREEN AND BACK------------------*/
        super.changeToFullScreenOnF12();
        
        /*------------------QUIT GAME------------------*/
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            game.setScreen(new MenuScreen(game, client, server));
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