/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.map;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import gui.screen.GameScreen;


/**
 *
 * @author qubasa
 */
public class MapManager extends Map
{
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private String mapSelection;
    
    public MapManager(String mapSelection)
    {
        this.mapSelection = mapSelection;
    }
    
    @Override
    public void create() 
    {
        tiledMap = new TmxMapLoader().load(mapSelection);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    @Override
    public void update() 
    {
        
    }

    @Override
    public void render(SpriteBatch sb) 
    {
        //tiledMapRenderer.setView(GameScreen.camera);
        tiledMapRenderer.render();
    }

    @Override
    public void resize(int width, int height) 
    {
        
    }

    @Override
    public void dispose() 
    {
        
    }

    @Override
    public void pause() 
    {
        
    }

    @Override
    public void resume() 
    {
        
    }
    
    public TiledMap getTiledMap()
    {
        return tiledMap;
    }
    
}
