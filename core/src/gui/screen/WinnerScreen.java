/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.screen;

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
import static com.gdx.bomberman.Main.game;
import gui.AudioManager;
import gui.TextureManager;
import static gui.TextureManager.backSkin;
import java.util.ArrayList;

/**
 *
 * @author qubasa
 */
public class WinnerScreen implements Screen
{
    //Objects
    private Stage stage;
    private Table rootTable = new Table();
    
    // Player positions
    ArrayList<Integer> playerPositions = new ArrayList<>();
    private int ownPlayerId = -1;
    
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
    
    public WinnerScreen(ArrayList<Integer> playerPositions, int ownPlayerId) 
    {
        
        //Set input and viewpoint
        stage = new Stage(new StretchViewport(Constants.SCREENWIDTH, Constants.SCREENHEIGHT));
        Gdx.input.setInputProcessor(stage);
        this.playerPositions = playerPositions;
        this.ownPlayerId = ownPlayerId;
        
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
        p1Field.background(new TextureRegionDrawable(TextureManager.p1WalkingDownAnim.getKeyFrame(0)));
        table.add(p1Field).width(64).height(64);
        
        //P2 spawn field
        p2Field = new Container();
        p2Field.setVisible(false);
        p2Field.background(new TextureRegionDrawable(TextureManager.p2WalkingDownAnim.getKeyFrame(0)));
        table.add(p2Field).width(64).height(64).padLeft(96);
        
        //P3 spawn field
        p3Field = new Container();
        p3Field.setVisible(false);
        p3Field.background(new TextureRegionDrawable(TextureManager.p3WalkingDownAnim.getKeyFrame(0)));
        table.add(p3Field).width(64).height(64).padLeft(96);
        
        //P4 spawn field
        p4Field = new Container();
        p4Field.setVisible(false);
        p4Field.background(new TextureRegionDrawable(TextureManager.p4WalkingDownAnim.getKeyFrame(0)));
        table.add(p4Field).width(64).height(64).padLeft(96);
        
        //Stage & group options
        joinedPlayerGroup.addActor(table);
        joinedPlayerGroup.setPosition(443, 150);
        stage.addActor(joinedPlayerGroup);
        
        /**------------------------LABELS------------------------**/     
        
        // Titel
        titel = new Label("", labelStyle);
        titel.setAlignment(Align.center);
        titel.setPosition((Constants.SCREENWIDTH - titel.getWidth()) / 2 + 50, 385);
        stage.addActor(titel);

        // If you are the winner
        if(ownPlayerId == playerPositions.get(0))
        {
            titel.setText("YOU WON!");
        }else
        {
            titel.setText("YOU LOOSE!");
            titel.setColor(Color.RED);
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
                long id = AudioManager.clickSound.play();
                AudioManager.clickSound.setVolume(id, Constants.SOUNDVOLUME);
                
                // Wait till sound is done
                try 
                {
                    Thread.sleep(100);
                    
                } catch (InterruptedException ex) 
                {
                    
                }

                game.setScreen(new MenuScreen());
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
                    p1Position.setText(Integer.toString(i +1));
                    p1Field.setVisible(true);
                    
                    if(1 == ownPlayerId)
                    {
                    }
                    
                    break;

                case 2:
                    p2Position.setText(Integer.toString(i+1));
                    p2Field.setVisible(true);
                    
                    if(2 == ownPlayerId)
                    {
                    
                    }
                    
                    break;

                case 3:
                    p3Position.setText(Integer.toString(i+1));
                    p3Field.setVisible(true);
                    
                    if(3 == ownPlayerId)
                    {
                    
                    }
                    
                    break;

                case 4:
                    
                    if(4 == ownPlayerId)
                    {
                        p4Field.setBackground(new TextureRegionDrawable(new TextureRegion(TextureManager.playerMarker)));
                    
                    }
                    
                    p4Position.setText(Integer.toString(i+1));
                    p4Field.setVisible(true);
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
        if(Gdx.input.isKeyPressed(Input.Keys.F12))
        {
            if(Gdx.graphics.isFullscreen())
            {
                Gdx.graphics.setWindowedMode(Constants.SCREENWIDTH, Constants.SCREENHEIGHT);
            }else
            {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
        }
        
        /*------------------QUIT GAME------------------*/
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
        {
            game.setScreen(new MenuScreen());
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
