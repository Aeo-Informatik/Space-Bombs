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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 *
 * @author qubasa
 */
public class Hud 
{
    public Stage stage;
    private Viewport viewport;
    
    private Integer worldTimer;
    private float timeCount;
    private Integer coins;
    private Integer life;

    Label countdownLabel;
    Label coinBalanceLabel;
    Label liveCounterLabel;
    
    public Hud(SpriteBatch renderObject)
    {
        this.worldTimer = 0;
        this.coins = 0;
        this.timeCount = 0;
        this.life = 3;
        
        this.viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        this.stage = new Stage(viewport, renderObject);
        
        //Table settings
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        //table.debugAll();
        
        //Labels (textfields)
        liveCounterLabel = new Label("LIFE: ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        
        //Live & coins display image
        Texture uiTexture = new Texture(Gdx.files.internal("other/hud.png"));
        TextureRegion uiCounter = new TextureRegion(uiTexture, 0, 0, 63, 16);
        Image uiCounterImage = new Image(uiCounter);
        
        //Add things to table
        table.add(uiCounterImage).expandX().padTop(10).width(uiCounter.getRegionWidth() * 3).height(uiCounter.getRegionHeight() * 3).left().padLeft(15);
        table.add(liveCounterLabel).expandX().padTop(10);
        
        //End table 
        stage.addActor(table);
    }
}
