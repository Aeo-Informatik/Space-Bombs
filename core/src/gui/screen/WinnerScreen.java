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
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.gdx.bomberman.Constants;

import java.util.ArrayList;

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
public class WinnerScreen extends Screens implements Screen
{
    //Objects
    private Stage stage;
    private Table rootTable = new Table();
    private InputHandler inputHandler = new InputHandler();
    
    // Player positions
    ArrayList<Integer> playerPositions = new ArrayList<>();
    private boolean isWinner;
    
    // Buttons
    private TextButton backButton;
    
    // Labels
    private Label titel;
    private WidgetGroup positionPlayerWidget = new WidgetGroup();
    private Label p1Position;
    private Label p2Position;
    private Label p3Position;
    private Label p4Position;
    
    // Player dispaly widget
    private WidgetGroup joinedPlayerGroup = new WidgetGroup();
    private Container p1Field;
    private Container p2Field;
    private Container p3Field;
    private Container p4Field;
    
    //Player highlight
    private WidgetGroup playerhighlightWidget = new WidgetGroup();
    private Container p1FieldHighlight;
    private Container p2FieldHighlight;
    private Container p3FieldHighlight;
    private Container p4FieldHighlight;
    
    public WinnerScreen(ArrayList<Integer> playerPositions,final Game game,final Client client,final Server server)
    {
        super(game, client, server);
        
        //Set input and viewpoint
        stage = new Stage(new StretchViewport(Constants.SCREENWIDTH, Constants.SCREENHEIGHT));
        inputHandler.setInputSource(stage);
        
        // Unhides the cursor
        Gdx.input.setCursorCatched(false);
        
        this.playerPositions = playerPositions;
        
        //Set background
        rootTable.background(new TextureRegionDrawable(new TextureRegion(TextureManager.hostBackground)));
        rootTable.setFillParent(true);
        stage.addActor(rootTable);
        
        //Initialise Font
        FreeTypeFontGenerator.FreeTypeFontParameter fontOptions = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontOptions.size = 11;
        BitmapFont font = TextureManager.menuFont.generateFont(fontOptions);

        /**------------------------LABEL STYLE------------------------**/
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        fontOptions.size = 60;
        labelStyle.font = TextureManager.menuFont.generateFont(fontOptions);
        labelStyle.fontColor = Color.GOLD;
        
        /**------------------------PLAYER DISPLAY WIDGET------------------------**/
        //Table options
        Table table = new Table();
        table.setFillParent(true);

        //P1 spawn field
        p1Field = new Container();
        p1Field.background(new TextureRegionDrawable((TextureRegion) TextureManager.p1WalkingDownAnim.getKeyFrame(0)));
        table.add(p1Field).width(64).height(64);
        
        //P2 spawn field
        p2Field = new Container();
        p2Field.setVisible(false);
        p2Field.background(new TextureRegionDrawable((TextureRegion) TextureManager.p2WalkingDownAnim.getKeyFrame(0)));
        table.add(p2Field).width(64).height(64).padLeft(96);
        
        //P3 spawn field
        p3Field = new Container();
        p3Field.setVisible(false);
        p3Field.background(new TextureRegionDrawable((TextureRegion) TextureManager.p3WalkingDownAnim.getKeyFrame(0)));
        table.add(p3Field).width(64).height(64).padLeft(96);
        
        //P4 spawn field
        p4Field = new Container();
        p4Field.setVisible(false);
        p4Field.background(new TextureRegionDrawable((TextureRegion) TextureManager.p4WalkingDownAnim.getKeyFrame(0)));
        table.add(p4Field).width(64).height(64).padLeft(96);
        
        //Stage & group options
        joinedPlayerGroup.addActor(table);
        joinedPlayerGroup.setPosition(443, 150);
        stage.addActor(joinedPlayerGroup);
        
        /**------------------------PLAYER HIGHLIGHT WIDGET------------------------**/
        //Table options
        Table table2 = new Table();
        table2.setFillParent(true);

        //P1 spawn field
        p1FieldHighlight = new Container();
        p1FieldHighlight.setVisible(false);
        p1FieldHighlight.background(new TextureRegionDrawable(new TextureRegion(TextureManager.playerMarker)));
        table2.add(p1FieldHighlight).width(80).height(77);
        
        //P2 spawn field
        p2FieldHighlight = new Container();
        p2FieldHighlight.setVisible(false);
        p2FieldHighlight.background(new TextureRegionDrawable(new TextureRegion(TextureManager.playerMarker)));
        table2.add(p2FieldHighlight).width(80).height(77).padLeft(80);
        
        //P3 spawn field
        p3FieldHighlight = new Container();
        p3FieldHighlight.setVisible(false);
        p3FieldHighlight.background(new TextureRegionDrawable(new TextureRegion(TextureManager.playerMarker)));
        table2.add(p3FieldHighlight).width(80).height(77).padLeft(80);
        
        //P4 spawn field
        p4FieldHighlight = new Container();
        p4FieldHighlight.setVisible(false);
        p4FieldHighlight.background(new TextureRegionDrawable(new TextureRegion(TextureManager.playerMarker)));
        table2.add(p4FieldHighlight).width(80).height(77).padLeft(80);
        
        //Stage & group options
        playerhighlightWidget.addActor(table2);
        playerhighlightWidget.setPosition(442, 152);
        stage.addActor(playerhighlightWidget);
        
        /**------------------------LABELS------------------------**/     
        
        // Titel
        titel = new Label("", labelStyle);
        titel.setAlignment(Align.center);
        titel.setPosition((Constants.SCREENWIDTH - titel.getWidth()) / 2 + 50, 385);
        stage.addActor(titel);

        // If you are the winner
        if(Constants.PLAYERID == playerPositions.get(playerPositions.size() -1))
        {
            titel.setText("YOU WON!");
            isWinner = true;
        }else
        {
            isWinner = false;
            titel.setText("YOU LOOSE!");
            titel.setColor(Color.RED);
        }
        
        if(-1 == playerPositions.get(playerPositions.size() -1))
        {
            titel.setText("DRAW!");
            titel.setColor(Color.DARK_GRAY);
            isWinner = false;
        }
        
        Table positionTable = new Table();
        positionTable.setFillParent(true);
        
        p1Position = new Label("", labelStyle);
        p1Position.setAlignment(Align.center);
        
        p2Position = new Label("", labelStyle);
        p2Position.setAlignment(Align.center);
        
        p3Position = new Label("", labelStyle);
        p3Position.setAlignment(Align.center);
        
        p4Position = new Label("", labelStyle);
        p4Position.setAlignment(Align.center);
        
        positionTable.add(p1Position).width(64).height(64);
        positionTable.add(p2Position).width(64).height(64).padLeft(96);
        positionTable.add(p3Position).width(64).height(64).padLeft(96);
        positionTable.add(p4Position).width(64).height(64).padLeft(96);
        
        positionPlayerWidget.addActor(positionTable);
        positionPlayerWidget.setPosition(443, 230);
        stage.addActor(positionPlayerWidget);

        /**------------------------MUSIC------------------------**/
        
        if(isWinner == false)
        {
            AudioManager.setCurrentMusic(AudioManager.getLooserMusic());
            AudioManager.getCurrentMusic().setLooping(true);
            AudioManager.getCurrentMusic().play();
            AudioManager.getCurrentMusic().setVolume(AudioManager.getMusicVolume() * 4);  
        }else
        {
            AudioManager.setCurrentMusic(AudioManager.getWinnerMusic());
            AudioManager.getCurrentMusic().setLooping(true);
            AudioManager.getCurrentMusic().play();
            AudioManager.getCurrentMusic().setVolume(AudioManager.getMusicVolume() * 6);  
        }
        
        /**------------------------BUTTONS------------------------**/
        TextButton.TextButtonStyle textButtonStyleBack = new TextButton.TextButtonStyle();
        textButtonStyleBack.font = font;
        textButtonStyleBack.up   = backSkin.getDrawable("button_up");
        textButtonStyleBack.down = backSkin.getDrawable("button_down");
        textButtonStyleBack.over = backSkin.getDrawable("button_checked");
        
        // Back button
        backButton = new TextButton("", textButtonStyleBack);
        backButton.setPosition(0, Constants.SCREENHEIGHT - backButton.getHeight() + 7);
        stage.addActor(backButton);
        
        renderPlayers();
        
        //Add click listener --> Back button
        backButton.addListener(new ChangeListener() 
        {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) 
            {   
                //Add click musik
                AudioManager.playClickSound();
                
                // Wait till sound is done
                try 
                {
                    Thread.sleep(100);
                    
                } catch (InterruptedException ex) 
                {
                    
                }
                
                if(isWinner)
                {
                    AudioManager.getCurrentMusic().stop();
                }else
                {
                    AudioManager.getCurrentMusic().stop();
                }

                
                server.stopServer();
                game.setScreen(new MenuScreen(game, client, server));
            }
        });  
    }
    
    public final void renderPlayers()
    {
        // Display participated players
        for(int i=0; i < playerPositions.size(); i++)
        {
            switch(playerPositions.get(i))
            {
                case 1:
                    p1Position.setText(Integer.toString(playerPositions.size() -i));
                    p1Field.setVisible(true);
                    
                    if(1 == Constants.PLAYERID)
                    {
                        p1FieldHighlight.setVisible(true);
                    }
                    
                    break;

                case 2:
                    p2Position.setText(Integer.toString(playerPositions.size() -i));
                    p2Field.setVisible(true);
                    
                    if(2 == Constants.PLAYERID)
                    {
                        p2FieldHighlight.setVisible(true);
                    }
                    
                    break;

                case 3:
                    p3Position.setText(Integer.toString(playerPositions.size() -i));
                    p3Field.setVisible(true);
                    
                    if(3 == Constants.PLAYERID)
                    {
                        p3FieldHighlight.setVisible(true);
                    }
                    
                    break;

                case 4:
                    p4Position.setText(Integer.toString(playerPositions.size() -i));
                    p4Field.setVisible(true);
                    
                    if(4 == Constants.PLAYERID)
                    {
                        p4FieldHighlight.setVisible(true);
                    }
                    
                    break;
            }
        }
    }
    
    /**------------------------RENDER------------------------**/
    @Override
    public void render(float f) 
    {
        //Debug
        //stage.setDebugAll(true);
        
        //Clear Screen
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        
        //Draw stage
        stage.act(Constants.DELTATIME);
        stage.draw();

        /*------------------SWITCH TO FULLSCREEN AND BACK------------------*/
        super.changeToFullScreenOnF12();
        
        /*------------------QUIT GAME------------------*/
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
        {
            if(isWinner)
            {
                AudioManager.getCurrentMusic().stop();
            }else
            {
                AudioManager.getCurrentMusic().stop();
            }
            
            server.stopServer();
            game.setScreen(new MenuScreen(game, client, server));
        }
    }

    @Override
    public void dispose() 
    {
        stage.dispose();
        backSkin.dispose();
    }
    
    @Override
    public void show() {
    }

    @Override
    public void resize(int i, int i1) {
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
    
}