/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
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
        
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        
        liveCounterLabel = new Label("LIFE: ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        
        table.add(liveCounterLabel).expandX().padTop(10);
        stage.addActor(table);
        
    }
    
}
