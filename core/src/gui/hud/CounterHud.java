/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.gdx.bomberman.Constants;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.entity.players.MainPlayer;

/**
 *
 * @author qubasa
 */
public class CounterHud 
{
    /**
     * How to use tables: https://github.com/libgdx/libgdx/wiki/Table#quickstart
     * How to use elements in table: https://github.com/libgdx/libgdx/wiki/Scene2d.ui#stack
     */
    //Objects
    public Stage stage;
    private Stack stack;
    private MainPlayer mainPlayer;
    private EntityManager entityManager;
    private Image uiCounterImage;
    
    //Font import
    private FreeTypeFontGenerator generator;
    private FreeTypeFontParameter parameter;
    
    //Variables
    private int live = -1;
    
    //Labels
    Label bombCounterLabel;
    Label coinCounterLabel;
    Label liveCounterLabel;
    
    //Constructor
    public CounterHud(SpriteBatch renderObject, EntityManager entityManager)
    {
        //Initialise Objects
        this.stage = new Stage(new StretchViewport(Constants.SCREENWIDTH, Constants.SCREENHEIGHT));
        this.stack = new Stack(); //A container in wich you can place multiple widgets to "stack" them
        this.entityManager = entityManager;
        
        //Initialise Font
        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/lunchtime-doubly-so/lunchds.ttf"));
        this.parameter = new FreeTypeFontParameter();
        parameter.size = 15;
        
        //Table settings
        Table backgroundTable = new Table();
        backgroundTable.top();
        backgroundTable.setFillParent(true);
        //backgroundTable.debugAll();
        
        //Table settings 2
        Table foregroundTable = new Table();
        foregroundTable.top();
        foregroundTable.setFillParent(true);
        //foregroundTable.debugAll();
        
        //Labels (textfields)
        bombCounterLabel = new Label("000", new Label.LabelStyle(generator.generateFont(parameter), Color.WHITE));
        coinCounterLabel = new Label("000", new Label.LabelStyle(generator.generateFont(parameter), Color.WHITE));
        
        generator.dispose();
        
        //Live & coins display image
        uiCounterImage = new Image(TextureManager.hudCounterFullLive);
        
        //Calculate image width (scaleX) and image height (scaleY)
        float scaleX = TextureManager.hudCounterFullLive.getWidth() * 3;
        float scaleY = TextureManager.hudCounterFullLive.getHeight() * 3;
        
        //Add image to background table
        backgroundTable.add(uiCounterImage).width(scaleX).height(scaleY);
        
        //Add label to foreground table
        foregroundTable.row().expandX().padTop(27);
        foregroundTable.add(bombCounterLabel).padLeft(114);
        foregroundTable.add(coinCounterLabel).padLeft(21);
        
        //Set container to the height of the image and position it on the top left
        stack.setWidth(scaleX);
        stack.setHeight(scaleY);
        stack.setPosition(0, 470 - scaleY);
        
        //End table and container (stack)
        stack.add(backgroundTable);
        stack.add(foregroundTable);
        stage.addActor(stack);
    }
    
    public void update()
    {
        stage.getCamera().update();
        
        if(mainPlayer != null)
        {
            bombCounterLabel.setText(String.format("%03d", mainPlayer.getMaxBombs()));
            coinCounterLabel.setText(String.format("%03d", mainPlayer.getCoins()));
            
            //If first time live is set
            if(live == -1)
            {
                live = mainPlayer.getLife();
                
            //If mainPlayer life changed
            }else if(live != mainPlayer.getLife())
            {
                live = mainPlayer.getLife();
                
                //Change heard texture
                switch (live) 
                {
                    case 6:
                        uiCounterImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudCounterSixLive)));
                        break;
                    case 5:
                        uiCounterImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudCounterFiveLive)));
                        break;
                    case 4:
                        uiCounterImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudCounterFourLive)));
                        break;
                    case 3:
                        uiCounterImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudCounterFullLive)));
                        break;
                    case 2:
                        uiCounterImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudCounterTwoThirdLive)));
                        break;
                    case 1:
                        uiCounterImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudCounterOneThirdLive)));
                        break;
                    case 0:
                        uiCounterImage.setDrawable(new TextureRegionDrawable(new TextureRegion(TextureManager.hudCounterNoLive)));
                        break;
                }
            }
            
        }else
        {
            mainPlayer = entityManager.getPlayerManager().getMainPlayer();
        }
    }
    
    public void resize(int width, int height)
    {
        stage.getViewport().update(width, height, false);
    }
}
