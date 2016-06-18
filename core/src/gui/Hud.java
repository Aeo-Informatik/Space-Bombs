/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import gui.entity.EntityManager;
import gui.entity.MainPlayer;

/**
 *
 * @author qubasa
 */
public class Hud 
{
    /**
     * How to use tables: https://github.com/libgdx/libgdx/wiki/Table#quickstart
     * How to use elements in table: https://github.com/libgdx/libgdx/wiki/Scene2d.ui#stack
     * How to use scaling save fonts: https://github.com/libgdx/libgdx/wiki/Gdx-freetype
     */
    
    //Objects
    public Stage stage;
    private Viewport viewport;
    private Stack stack;
    private MainPlayer mainPlayer;
    private EntityManager entityManager;
    
    
    //Labels
    Label bombCounterLabel;
    Label coinCounterLabel;
    Label liveCounterLabel;
    
    //Constructor
    public Hud(SpriteBatch renderObject, EntityManager entityManager)
    {
        
        this.viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        this.stage = new Stage(viewport, renderObject);
        this.stack = new Stack(); //A container in wich you can place multiple widgets to "stack" them
        this.entityManager = entityManager;
        
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
        bombCounterLabel = new Label("000", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        coinCounterLabel = new Label("000", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        
        //Live & coins display image
        Texture uiTexture = new Texture(Gdx.files.internal("other/hud2_full_live.png"));
        TextureRegion uiCounter = new TextureRegion(uiTexture, 0, 0, 80, 18);
        Image uiCounterImage = new Image(uiCounter);
        
        //Calculate image width (scaleX) and image height (scaleY)
        float scaleX = uiCounter.getRegionWidth() * 3;
        float scaleY = uiCounter.getRegionHeight() * 3;
        
        //Add image to background table
        backgroundTable.add(uiCounterImage).width(scaleX).height(scaleY);
        
        //Add label to foreground table
        foregroundTable.row().expandX().padTop(27);
        foregroundTable.add(bombCounterLabel).padLeft(114);
        foregroundTable.add(coinCounterLabel).padLeft(18);
        
        //Set container to the height of the image and position it on the top left
        stack.setWidth(scaleX);
        stack.setHeight(scaleY);
        stack.setPosition(0, Gdx.graphics.getHeight() - scaleY - 10);
        
        //End table and container (stack)
        stack.add(backgroundTable);
        stack.add(foregroundTable);
        stage.addActor(stack);
    }
    
    public void update()
    {
        if(mainPlayer != null)
        {
            bombCounterLabel.setText(String.format("%03d", mainPlayer.getMaxBombs()));
            coinCounterLabel.setText(String.format("%03d", mainPlayer.getCoins()));
        }else
        {
            mainPlayer = entityManager.getMainPlayer();
        }
    }
}
